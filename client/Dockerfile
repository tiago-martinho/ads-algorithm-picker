#
# COMPILE
#

FROM node:14-alpine AS build

# Create app directory
WORKDIR /app

# Install app dependencies
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH" 

# Bundle app source
COPY . ./
RUN ng build --prod



#
# HTTP SERVER
#

FROM nginx:1.19-alpine AS http
EXPOSE 80

COPY docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf 
COPY --from=build /app/dist/client /usr/share/nginx/html