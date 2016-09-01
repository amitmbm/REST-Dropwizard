## REST API Developement,Using the popular opensource java framework Dropwizard.

This project's aim is to reduce the development time of web-service using the dropwizard framework , which has become the de-facto standard for writing the micro-services in java. its comes with lots of feature which you need to build the API.
Read more about Dropwizard here :- http://www.dropwizard.io
This Project uses a Lombok Plugin: https://projectlombok.org/
### Prerequisities
JDK :- 1.7  or later
Maven:-3.0 or later

### Installing
1.Clone the project using below command.
```
git clone
```
############################# HOW to Run this on Intellij ##################################

To Run this project in Intellij, Go To Run->Edit Configurations. Click on the Select Application in left pane and click + button.

You will get a window in the right. Requesting Certain Fields. Please fill the fields with following info


MainClass: com.lithium.cspservice.CSPServiceApplication

VM Options: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=39054 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false

Program arguments: server cspservice.yml

Working Directory: Path to your Code for CSP Service

Use of ClassPath Module:  your CSP-Service Project in Intellij


############################# ############################# #################################

############################# Running this as a jar #########################################
This project also Uses Lombok Plugin. Please install Lombok Plugin in intellij. If this plugin is not installed, compilation errors will be shown in the project.

This project Uses Elastic Search. This project is already pointing to QA setup of Elastic Search. If you wish to point to a local instance. Set up Elastic Search and create an index by name "communities" to get started.

This project compiles into a fat jar.

mvn package install -DskipTests will give a fat jar in /target folder.

To run this application. run /target/fat.jar {VM Options}  server cspservice.yml.
############################################################################################

#### API Documentation
I am using swagger for the documenation of my API. Read more about it.<br>
http://swagger.io/<br>
Hit below URL to get all the API's and their documenation.
http://localhost:<your-port-number>/swagger****
