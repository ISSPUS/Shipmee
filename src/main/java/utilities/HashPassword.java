package utilities;

import java.io.IOException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import utilities.internal.ConsoleReader;

public class HashPassword {

	public static void main(final String[] args) throws IOException {
		Md5PasswordEncoder encoder;
		ConsoleReader reader;
		String line, hash;

		try {
			System.out.printf("HashPassword%n");
			System.out.printf("----------------%n%n");

			encoder = new Md5PasswordEncoder();
			reader = new ConsoleReader();

			line = reader.readLine();
			while (!line.equals("quit")) {
				hash = encoder.encodePassword(line, null);
				System.out.println(hash);
				line = reader.readLine();
			}
		} catch (final Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			//oops.printStackTrace(System.out);			
		}
	}

}
