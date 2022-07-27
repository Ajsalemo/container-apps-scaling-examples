FROM node:18.6.0-alpine3.15

WORKDIR /app
COPY package.json /app
RUN yarn install 

COPY . /app

EXPOSE 3000 

CMD [ "node", "server.js" ]