# ADS - Algorithm Picker

## Quickstart

For quick-starting the services, run `start.bat`. This will build and run the Docker images for the _client_ and _server_ using a tool named docker-compose.

The website (client service) will be available atm [localhost:5000](http://localhost:5000), and the API (server service) will be available at [localhost:5001](http://localhost:5001).

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


## Development

### Client

Install [Node.js](https://nodejs.org) (tested only with version 12.16.1). Download all the dependencies by running `npm install` on the project directory.

Start a debug server by typing `npm start`. The server will be available at [localhost:4200](http://localhost:4200).


### Server

Import the project into Intellij IDEA or your editor of choice. Make sure you have the Lombok plugin installed and that you enabled _Annotation Processing_ (found in `Settings > Build, Execution, Deployment > Compiler > Annotation Processors`).
