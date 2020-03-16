The app demonstrates Spring Boot features for external configuration

#### The project directory structure is 

./ext/application.properties<br />
	app.name=Externally defined application name (ext directory)
	
./config/application.properties<br />
	app.name=Externally defined application name (config directory)
	
./src/main/resources/application.properties<br />
	app.name=Default application name<br />
	app.default=Default application value

./src/main/resources/application-test.properties<br />
	app.name=Profile specific application name

#### Below the output of running the app in command line

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar

Application name is: Externally defined application name (config directory)
Application default value is: Default application value

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=ext/application.properties

Application name is: Externally defined application name (ext directory)
Application default value is: null

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=ext/

Application name is: Externally defined application name (ext directory)
Application default value is: null

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=config/application.properties

Application name is: Externally defined application name (config directory)
Application default value is: null

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.additional-location=ext/application.properties

Application name is: Externally defined application name (ext directory)
Application default value is: Default application value

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=config/

Application name is: Externally defined application name (config directory)
Application default value is: null

C:\Users\nicola.tuveri\Sandbox\app-properties-config>java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

Application name is: Profile specific application name
Application default value is: Default application value
