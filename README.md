# ADS - Algorithm Picker

## Para o professor

Primeiro é necessário importar as imagens Docker:
`docker load image ...` (ou `docker pull ghcr.io/sandrohc/ads-client`)
`docker load image ...` (ou `docker pull ghcr.io/sandrohc/ads-server`)

Depois basta correr as imagens na mesma rede
`docker-compose -p ads up -d client server`
//`docker run -d --name ads-client -p 8080:8080 ghcr.io/sandrohc/ads-client`
//`docker run -d --name ads-server -p 8080:8080 ghcr.io/sandrohc/ads-server`

O website irá ficar disponível em [localhost:8080](http://localhost:8080) e a API em [localhost:8080/api/v1](http://localhost:8080/api/v1).

A lista de endpoints disponibilizados pela API pode ser visto em: http://localhost:8080/swagger-ui.html

Caso esteja a ter dificuldades em fazer o setup das aplicações, pode ver o site em produção em https://algorithm.sandrohc.net.



## Development setup

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