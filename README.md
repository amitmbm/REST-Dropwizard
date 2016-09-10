## REST API Developement,Using the popular opensource java framework Dropwizard.

This project's aim is to reduce the development time of web-service using the Dropwizard framework , which has become the de-facto standard for writing the micro-services in java. its comes with lots of feature which you need to build the API.
Read more about Dropwizard here :- http://www.dropwizard.io
This Project uses a Lombok Plugin: https://projectlombok.org/
### Prerequisities
* JDK :- 1.7  or later
* Maven:-3.0 or later
* Elastic-Search 2.3 or later
### Installion
1.Clone the project.
```
git clone
```
2.Build the fat jar (By Skipping Tests)
```
mvn clean package -DskipTests
```
3.Run the application.
```
java -jar target/dropwizard-1.0-SNAPSHOT.jar server productservice.yml
```

### Running tests
For running only units tests
```
mvn test
```
For running only integration tests, Note It can be run only after the build.
```
mvn failsafe:integration-test
```
For running both unit and integration tests. It will first run UT, then build the package and then run the IT.
```
mvn verify
```
### Checking the code-coverage report using the "Jacoco" plugin

### Misc :-
This project also Uses Lombok Plugin. Please install Lombok Plugin in intellij. If this plugin is not installed, compilation errors will be shown in the project.

This project Uses Elastic Search. Set up Elastic Search and create an index to get started.
Refer :- https://www.elastic.co/blog/running-elasticsearch-on-aws for setting up ES.

#### API Documentation
I am using swagger for the documenation of my API. Read more about it.<br>
http://swagger.io/<br>
Hit below URL to get all the API's and their documenation.
***http://localhost:<app-port-number>/swagger***
For example you can access my application documentation from below URL.

