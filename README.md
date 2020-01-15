# BookingGo Techincal Test Submission

## Prerequisites

* Java 1.8 
* JDK (1.8+)

Command-line assumed is either Windows PowerShell or Bash

## Dependencies
The unit tests use JUnit 5, as this was when `assertThrows()` was implemented.
Spring Boot was used for the REST API server.

These should all be pulled in once gradle is run; ensure that Java 1.8 is installed and targeted by the IDE (if one is used).

## Setup
The project was developed in IntelliJ IDEA Community Edition 2019.3.

**Clone the repository and enter into the project root:**

For Windows and Linux:

`git clone https://github.com/jamesscully/ApiTest; cd ApiTest` 

**Build the project:**

For Windows:

``./gradlew.bat build``

For Linux:

``./gradlew build``

<hr>

**NOTE:** If you run into the error 
`Could not find tools.jar. Please check that C:\Program Files (x86)\Java\jre1.8.0_231 contains a valid JDK installation.` whilst building from the command-line,

* Locate your JDK install folder (for me on a fresh Windows VM, `C:\Program Files\Java\jdk-13.0.1\`)
* Uncomment `# org.gradle.java.home=` in `gradle.properties` and set the path to your JDK folder.

Example: `org.gradle.java.home=C:\\Program Files\\Java\\jdk-13.0.1\` (escaping backslashes important)

This shouldn't be a problem within a properly set-up IDE however.

<hr>



**Move into the build directory**:
For Windows and Linux:

`cd build/libs/`

If all is successful, running `ls` should show ApiTest.jar.





## Part 1

The following commands are executed in the format of:

`java -jar ApiTest.jar --taskX (pickup) (dropoff) [passengers]`

Task **A** does not take a passengers argument, as I worked though the tasks sequentially before passengers came into play.
Tasks **B** and **C** however will assume that only one passenger is riding if the parameter is omitted.


#### Task A: Console application to print the search results for Dave's Taxis

To print the results for Dave's Taxis:

`java -jar ApiTest.jar --taskA 51,1 51,2`


#### Task B: Console application to filter by number of passengers
To filter by number of passengers, i.e. 5 passengers: 

`java -jar ApiTest.jar --taskB 51,1 51,2 5`

#### Task C: Console application to find cheapest results from each supplier
To find the cheapest journeys from each supplier by passengers, i.e. 7:

`java -jar ApiTest.jar --taskC 51,1 51,2 7`


## Part 2
Firstly, start the webserver by running:

`java -jar ApiTest.jar --server`

There are four main endpoints to choose from: 
* `/eric`,  `/dave`,  `/jeff` which return results from the respective supplier
* `/search` which will compare the prices of car types from each of the above and return the cheapest

Simply append any of these to `localhost:8080` with the required parameters:

`?pickup=[location]&dropoff=[location]`

where location is in the format of latitude/longitude, i.e. *51,2* to find results.

For example, to find journeys with a pickup of *50,2* and dropoff of *51,3* from Dave:
`http://localhost:8080/dave?pickup=50,2&dropoff=51,3`

Optionally, you can add a passengers parameter as such:
`http://localhost:8080/dave?pickup=50,2&dropoff=51,3&passengers=5`

which will find rides that are suitable for 5 people.


## Tests

You can run tests by executing the following:

**Windows**

`./gradlew.bat test`

**Linux**

`./gradlew test`


## Notes
#### Exceptions
If the program is started with wrong parameters, i.e. 51,aaaaaa 51,2 the program will print **a lot** of stack traces; primarily due to Spring Boot being bundled within the JAR. I've tried to separate error messages from the stack traces as much as possible through line-breaks, but some may remain.

#### Cheapest suppliers
The API's `/search` endpoint is essentially based off of Task1C.java; I've included a SearchResult builder so that "artifical" API results can be created to test this, as well as generate a SearchResult for ALL suppliers (found in SearchResult.java + MainTest.java). 

I've also wrote debug output to stdout (for the server), so you can observe the comparisons and general functionality of it (you can turn this off by setting `SHOW_COMPARISONS` to `false` in SearchTaxis.java @ Line ~28. 

#### JSON
To parse the JSON returned from the Rideways API, I've used the standard JSON library (org.json in Maven repo). 

Spring Boot automatically serializes objects using the Jackson JSON API.


