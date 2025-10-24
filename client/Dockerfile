# base image 
FROM node:20.16.0

# set working directory (docker specifiy location)
WORKDIR /app

# copy package.json file
COPY package*.json ./

# install dependencies
RUN npm install

# copy the source code (source, destination /app)
COPY . .

# expose port
EXPOSE 5173

# run the app
CMD ["npm", "start"]