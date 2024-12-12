FROM node:20-alpine

# Create app directory
WORKDIR /usr/app

# Copy package.json and package-lock.json first
# This allows Docker to cache npm install if dependencies haven't changed
COPY /usr/app/package.json /usr/app/package-lock.json ./

# Install dependencies
RUN npm install

# Copy application code (excluding files in .dockerignore)
COPY . .

# Expose application port
EXPOSE 3000

# Start the application
CMD ["node", "server.js"]
