# ADS - Algorithm Picker

## Quickstart

For quick-starting the services, run `start.bat` (Windows) or `start.sh` (Linux/Mac). This will build and run the Docker images for the _client_ and _server_ using a tool named [docker-compose](https://docs.docker.com/compose/). You will need [Docker](https://www.docker.com/get-started) installed and running on your machine.

The website (client service) will be available at [localhost:5000](http://localhost:5000), and the API (server service) will be available at [localhost:5001](http://localhost:5001).

A list of all the endpoints available by the API can be seen at: http://localhost:5001/swagger-ui.html

If you're having difficulty setting up the application, there is a production version at https://algorithm.sandrohc.net.

### Loading images manually

If you wish to load the Docker images manually, please execute:

`docker load image <path-to-client-image>`

`docker load image <path-to-server-image>`

If for any reason you are unable to load the images, you can load them from [DockerHub](https://hub.docker.com/u/tiagomartinho) by executing:

`docker pull tiagomartinho/ads-client`

`docker pull tiagomartinho/ads-server`

### Starting the services manually

For starting the _client_ and _server_ services manually, please execute: `docker-compose -p ads up`.

## Continuous Integration & Continuous Delivery (CI/CD)

Through the use of [GitHub Actions](https://github.com/tiago-martinho/ads-algorithm-picker/actions), new Docker images are tested and deployed automatically for every commit sent to this Git repository.

## Documentation

| Class                     | Description                                                                                                                                                                                                                           |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Model**                 |                                                                                                                                                                                                                                       |
| model.\*                  | All the DTOs and ata structures used internally can be found here.                                                                                                                                                                    |
| **Controllers**           |                                                                                                                                                                                                                                       |
| AlgorithmController       | Controller that specifies the endpoints of our RESTful API for all the algorithm-related features.                                                                                                                                    |
| **Services**              |                                                                                                                                                                                                                                       |
| AlgorithmService          | The generic service for all of the algorithm-related features.                                                                                                                                                                        |
| AlgorithmServiceImpl      | The service implementation that contains the business logic for all of the algorithm-related features. This implementation can be swapped with a new one without affecting the rest of the code, because of the use of the interface. |
| OwlService                | The generic service for all of the OWL-related tasks.                                                                                                                                                                                 |
| OwlServiceImpl            | The service implementation that loads the OWL ontology specified in the configuration file. Also allows the rest of the application to query the OWL ontology.                                                                        |
| **Other Design Patterns** |                                                                                                                                                                                                                                       |
| ProblemFactory            | Factory that abstracts the instantiation of the problems. The problem implements the evaluation function based on the user input.                                                                                                     |
| AlgorithmFactory          | Factory that abstracts the instantiation of the algorithms through reflection (see the relevant section for more details).                                                                                                            |
| OwlQueryBuilder           | Builder to create an OWL query in a modular way. Can be expanded in a way that won't break old code.                                                                                                                                  |

### Endpoints

Only one endpoint is available in the public API. The user input is received from the request body as a JSON string, and the output is a JSON string.

| Verb | Endpoint | Input                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | Output                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| ---- | -------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| POST | /        | <code>{<br> "description": "Find the best compromise between grain cost and grain quality",<br> "type": "DOUBLE",<br> "objectives": [<br> {<br> "name": "Price",<br> "goal": "MINIMIZE"<br> },<br> {<br> "name": "Quality",<br> "goal": "MAXIMIZE"<br> }<br> ],<br> "variables": [<br> {<br> "name": "Grain quality index (bigger is better quality)",<br> "lowerLimit": 10,<br> "upperLimit": 100<br> },<br> {<br> "name": "Grain cost (lower is lower cost)",<br> "lowerLimit": 500,<br> "upperLimit": 1000<br> }<br> ],<br>}</code> | <code>{<br> "inputs": {<br> "description": "Find the best compromise between grain cost and grain quality",<br> "type": "DOUBLE",<br> "variables": [<br> {<br> "name": "Grain quality index (bigger is better quality)",<br> "lowerLimit": 10.000,<br> "upperLimit": 100.000<br> },<br> {<br> "name": "Grain cost (lower is lower cost)",<br> "lowerLimit": 500.000,<br> "upperLimit": 1000.000<br> }<br> ],<br> "objectives": [<br> {<br> "name": "Price",<br> "goal": "MINIMIZE"<br> },<br> {<br> "name": "Quality",<br> "goal": "MAXIMIZE"<br> }<br> ],<br> "options": {<br> "heavyProcessing": null,<br> "populationSize": 10,<br> "iterations": 10,<br> "crossoverProbability": 1.000,<br> "crossoverDistributionIndex": 5.000,<br> "mutationProbability": 1.000,<br> "mutationDistributionIndex": 10.000<br> }<br> },<br> "problem": {<br> "numberOfVariables": 2,<br> "numberOfObjectives": 2,<br> "numberOfConstraints": 0,<br> "name": "Double Problem"<br> },<br> "results": {<br> "algorithm": {<br> "maxPopulationSize": 16,<br> "selectionOperator": {},<br> "crossoverOperator": {<br> "distributionIndex": 5.000,<br> "crossoverProbability": 1.000,<br> "numberOfRequiredParents": 2,<br> "numberOfGeneratedChildren": 2<br> },<br> "mutationOperator": {<br> "distributionIndex": 10.000,<br> "mutationProbability": 0.500<br> },<br> "description": "Nondominated Sorting Genetic Algorithm version III",<br> "name": "NSGAIII"<br> },<br> "solutions": [<br> {<br> "objectives": [<br> 3.646,<br> 151.442<br> ],<br> "variables": [<br> 10.000,<br> 500.000<br> ],<br> "attributes": {<br> "org.uma.jmetal.util.solutionattribute.impl.DominanceRanking": 0,<br> "org.uma.jmetal.algorithm.multiobjective.nsgaiii.util.EnvironmentalSelection": [<br> 0.000,<br> 10856628588.374<br> ]<br> },<br> "numberOfVariables": 2,<br> "numberOfObjectives": 2<br> }<br> ]<br> }<br>}</code> |

### Instantiating the algorithms

In order to keep the algorithm instantiation code generic and future-proof, Java reflection was used. This approach is generic enough to allow our code to adapt to new updates to jMetal without our code having to be updated as well. The implementation can be seen at `pt.ads.server.algorithm.AlgorithmFactory#getAlgorithmReflection`. What follows is a detailed description of the implementation.

First we find all the algorithm classes and builders, that extend the Algorithm and AlgorithmBuilder interfaces respectively, in the classpath containing the algorithm name. We give preference to builders because they contain sensible default values for each algorithm. For each class found, we iterate over it's public constructors, giving priority to the constructors with fewer parameters (to use most of the default values specified by the jMetal team). For each constructor, we we try to fill each of the parameters by looking at it's type (e.g. using the appropriate value for the parameter of type/class "SelectionOperator"). If we don't know what to put on a specific constructor parameter, the value `null`is used. After all the constructor parameters are filled, we then try to call the constructor to create a new instance of the algorithm class. Assuming no NullPointerException was thrown because of parameters being mapped to `null`, all that's missing is to fill some known fields (like 'populationSize', 'iterations', ...) with our values. After all this work, we're left with is a working instance of our desired algorithm! Assuming our reflection approach failed and we were not able to use any of the constructors and classes, we switch to a fallback solution where we wrote default implementations for some of the algorithms; this is only used as a last resort to give the user "something" instead of an error.

### Docker

WIP

## Development

### Client

Install [Node.js](https://nodejs.org) (tested only with version 12.16.1). Download all the dependencies by running `npm install` on the client directory.

Install the angular CLI globally by running `npm install -g @angular/cli`.

Start the application locally by running `ng serve` on the client directory. The server will be available at [localhost:4200](http://localhost:4200).

### Server

The server is developed with Java version 11. Install an appropriate JDK to run the server locally (e.g [AdoptOpenJDK](https://adoptopenjdk.net/)).

The project can be imported to your IDE of choice as a Maven project and run from there. To run the server application from the command line run `mvn spring-boot:run`. 

The server will be available at [localhost:8080](http://localhost:8080)
