#! /bin/bash

##### Define variables

#jdbcURL="localhost:3306/Shipmee" # URL without users and reconect ! !
#jdbcUser="shipmee-manager"
#jdbcPassword="ACME-M@n@ger-6874"


##### Edit files

sed -i 's@localhost:3306/Shipmee@'"$jdbcURL"'@g' src/main/resources/META-INF/persistence.xml
sed -i 's/shipmee-manager/'$jdbcUser'/g' src/main/resources/META-INF/persistence.xml
sed -i 's/ACME-M@n@ger-6874/'$jdbcPassword'/g' src/main/resources/META-INF/persistence.xml

sed -i 's@localhost:3306/Shipmee@'$jdbcURL'@g' src/main/resources/spring/config/data.xml
sed -i 's/shipmee-user/'$jdbcUser'/g' src/main/resources/spring/config/data.xml
sed -i 's/ACME-Us3r-P@ssw0rd/'$jdbcPassword'/g' src/main/resources/spring/config/data.xml


##### Populate the database

mvn clean install -Dmaven.test.skip=true
# mvn exec:java -Dexec.mainClass="utilities.PopulateDatabase"
