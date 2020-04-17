The app demonstrates Spring Boot features for external configuration.  
See docs for details https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config

#### Below the output of running the app with command line for some cases

1) By default content of the config directory is added to classpath. Inherits from main application.properties. Combines self, generic and specific levels.
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar

app.name: Externally defined application name (config directory)
app.default: Default application value
app.composite-generic: Composite Generic with Specific application value (config directory)
app.composite-specific: Composite Specific with Generic application value
app.self-generic: Self Generic with Generic application value
app.self-specific: Self Specific with Specific application value (config directory)


2) Profile-specific settings win
con spring.profiles.active=test
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar
or 
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

app.name: Profile specific application name
app.default: Default application value
app.composite-generic: Composite Generic with Specific application value (profile)
app.composite-specific: Composite Specific with Generic application value
app.self-generic: Self Generic with Generic application value
app.self-specific: Self Specific with Specific application value (profile)


3) Only the file config/application.properties is considered
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=config/application.properties

app.name: Externally defined application name (config directory)
app.default: null
app.composite-generic: null
java.lang.IllegalArgumentException: Could not resolve placeholder 'app.generic' in value "Composite Specific with ${app.generic}"


4) Only the content of the config/ directory is considered
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.location=config/

app.name: Externally defined application name (config directory)
app.default: null
app.composite-generic: null
java.lang.IllegalArgumentException: Could not resolve placeholder 'app.generic' in value "Composite Specific with ${app.generic}"


5) The file config/application.properties is considered. Inherits from main application.properties. Combines self, generic and specific levels.
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.additional-location=config/application.properties

app.name: Externally defined application name (config directory)
app.default: Default application value
app.composite-generic: Composite Generic with Specific application value (config directory)
app.composite-specific: Composite Specific with Generic application value
app.self-generic: Self Generic with Generic application value
app.self-specific: Self Specific with Specific application value (config directory)


6) Profile-specific settings win
con spring.profiles.active=test
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.additional-location=config/application.properties 
or 
java -jar .\build\libs\app-properties-config-0.0.1-SNAPSHOT.jar --spring.config.additional-location=config/application.properties --spring.profiles.active=test

app.name: Profile specific application name
app.default: Default application value
app.composite-generic: Composite Generic with Specific application value (profile)
app.composite-specific: Composite Specific with Generic application value
app.self-generic: Self Generic with Generic application value
app.self-specific: Self Specific with Specific application value (profile)
