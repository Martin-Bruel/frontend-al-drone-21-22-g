FROM node:12.18.1
WORKDIR /app
COPY ["package.json", "package-lock.json", "./"]
RUN npm install
COPY "app" .
CMD ["npm","run", "start"]