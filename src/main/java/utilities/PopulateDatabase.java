package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import utilities.internal.DatabaseUtil;
import utilities.internal.EclipseConsole;
import utilities.internal.ThrowablePrinter;
import domain.DomainEntity;

public class PopulateDatabase {

	public static void main(final String[] args) {
		DatabaseUtil databaseUtil;
		ApplicationContext populationContext;
		Map<String, Object> entityMap;
		List<Entry<String, Object>> sortedEntities;
		HashMap<String, Integer> persistedEntities;

		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		databaseUtil = null;

		try {
			System.out.println("PopulateDatabase");
			System.out.println("--------------------");
			System.out.println();

			System.out.printf("Initialising persistence context `%s'.%n", DatabaseConfig.PersistenceUnit);
			databaseUtil = new DatabaseUtil();
			databaseUtil.open();

			System.out.printf("Creating database `%s' (%s).%n", databaseUtil.getDatabaseName(), databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			System.out.print("Reading web of entities from `PopulateDatabase.xml'");
			populationContext = new ClassPathXmlApplicationContext("classpath:PopulateDatabase.xml");
			entityMap = populationContext.getBeansWithAnnotation(Entity.class);
			System.out.printf(" (%d entities found).%n", entityMap.size());

			System.out.println("Computing a topological order for your entities.");
			sortedEntities = PopulateDatabase.sort(databaseUtil, entityMap);

			System.out.println("Trying to save the best order found.");
			persistedEntities = PopulateDatabase.persist(databaseUtil, sortedEntities);
			
			System.out.println("Trying to persist in a file the best order found.");
			persistIds(persistedEntities);
		} catch (final Throwable oops) {
			ThrowablePrinter.print(oops);
		} finally {
			if (databaseUtil != null) {
				System.out.println("Closing persistence context.");
				databaseUtil.close();
			}
		}
	}

	protected static List<Entry<String, Object>> sort(final DatabaseUtil databaseUtil, final Map<String, Object> entityMap) {
		LinkedList<Entry<String, Object>> result;
		LinkedList<Entry<String, Object>> cache;
		Entry<String, Object> entry;
		DomainEntity entity;
		int passCounter;
		boolean done;

		result = new LinkedList<>();
		result.addAll(entityMap.entrySet());
		cache = new LinkedList<>();
		passCounter = 0;

		do {
			try {
				databaseUtil.openTransaction();
				PopulateDatabase.cleanEntities(result);

				while (!result.isEmpty()) {
					entry = result.getFirst();
					entity = (DomainEntity) entry.getValue();
					databaseUtil.persist(entity);
					result.removeFirst();
					cache.addLast(entry);
				}
				databaseUtil.undoTransaction();
				done = true;
				result.addAll(cache);
				cache.clear();
			} catch (final Throwable oops) {
				databaseUtil.undoTransaction();
				done = passCounter >= entityMap.size() - 1;
				entry = result.removeFirst();
				cache.addAll(result);
				cache.addLast(entry);
				result.clear();
				result.addAll(cache);
				cache.clear();
			}
			passCounter++;
		} while (!done);

		PopulateDatabase.cleanEntities(result);

		return result;
	}

	protected static HashMap<String, Integer> persist(final DatabaseUtil databaseUtil, final List<Entry<String, Object>> sortedEntities) {
		String name;
		DomainEntity entity;
		HashMap<String, Integer> persistedEntities = new HashMap<>();

		System.out.println();
		databaseUtil.openTransaction();
		for (final Entry<String, Object> entry : sortedEntities) {
			name = entry.getKey();
			entity = (DomainEntity) entry.getValue();

			System.out.printf("> %s", name);
			databaseUtil.persist(entity);
			System.out.printf(": %s%n", entity.toString());
			// TODO: print the entity using SchemaPrinter.  This should get a map in which 
			// every persisted entity is mapped onto the corresponding bean name in the 
			// PopulateDatabase.xml file; otherwise traceability will be a nightmare.
			persistedEntities.put(name, entity.getId());
		}
		databaseUtil.closeTransaction();
		System.out.println();
		return persistedEntities;
	}

	protected static void cleanEntities(final LinkedList<Entry<String, Object>> result) {
		for (final Entry<String, Object> entry : result) {
			DomainEntity entity;

			entity = (DomainEntity) entry.getValue();
			entity.setId(0);
			entity.setVersion(0);
		}
	}

	protected static void persistIds(HashMap<String, Integer> input) {
		DocumentBuilderFactory icFactory;
		DocumentBuilder icBuilder;
		Document doc;
		Element mainRootElement;
		Transformer transformer;
		DOMSource source;
		File file;

		try {
			icFactory = DocumentBuilderFactory.newInstance();
			icBuilder = icFactory.newDocumentBuilder();

			doc = icBuilder.newDocument();

			mainRootElement = doc.createElement("entities");
			doc.appendChild(mainRootElement);

			// append child elements to root element
			for (Map.Entry<String, Integer> entry : input.entrySet()) {
				mainRootElement.appendChild(getEntity(doc, entry.getValue(), entry.getKey()));
			}

			// output DOM XML to console
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			source = new DOMSource(doc);

			file = new File("tmp/persistedIds.xml");
			file.getParentFile().mkdirs();

			StreamResult console = new StreamResult(file);
			transformer.transform(source, console);

			System.out.println("\nXML DOM Created Successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Node getEntity(Document doc, Integer entityId, String name) {
		Element entity = doc.createElement("entity");
		entity.setAttribute("beanId", name);
		entity.appendChild(getEntityElements(doc, "databaseId", entityId.toString()));
		return entity;
	}

	// utility method to create text node
	private static Node getEntityElements(Document doc, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}
