# ADS - Algorithm Picker


## Configuration

### Client

Install [Node.js](https://nodejs.org) (tested only with version 12.16.1). Download all the dependencies by running `npm install` on the project directory.

Start a debug server by typing `npm start`. The server will be available at [localhost:4200](http://localhost:4200).


### Server

Import the project into Intellij IDEA or your editor of choice. Make sure you have the Lombok plugin installed and that you enabled _Annotation Processing_ (found in `Settings > Build, Execution, Deployment > Compiler > Annotation Processors`).


### Docker

To build the server, execute: `./server/mvnw clean install` followed by `docker-compose build server`.
To build the client, execute: `docker-compose build client`.

You can manage your Docker containers with:
* `docker-compose -p ads up -d client server`
* `docker-compose -p ads down`