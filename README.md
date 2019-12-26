# script-sidekick

Project to help create bash scripts.  Generates the relevant boilerplate and helps you keep your scripts sane.

# Architecture

There are two main part of the application.

## UI

The frontend part of the application is a Vuejs application that's delivered to the user via nginx.  The Vue code is packaged and placed in a Docker image.  When the Docker image starts up, nginx listens on port 80 and serves up the UI pages.

When developing the UI, you can use a node server to compile and test the UI.  Node starts on the first port that's available starting with 8080.

## Service

The backing service for the application is a Spring boot application with various REST endpoints.  It starts up on port 8080.

The service takes a json payload and uses [StringTemplate](https://www.stringtemplate.org) to generate the various sections in the Bash script. 

# Docker images

## Service : Spring boot

Start the Spring boot application using Docker

```bash
docker run  -p 8080:8080 --rm -t mikeleitz/sidescript-service
```

## UI : VueJs

```bash
docker run -p 8081:80 --rm -t mikeleitz/sidescript-ui
```

## Access via Browser

```
http://localhost:8081/
```

# Building the project

Currently the build uses two separate mechanisms to create the deliverable (Docker images).  The Java Spring boot service is created with Google's [jib](https://github.com/GoogleContainerTools/jib).  The UI/vuejs Docker image is created traditionally using a Dockerfile.

## To do a full build

### Build the Docker images but don't deploy

```bash
./gradlew jibDockerBuild docker 
```
### Deploy the Docker images to Docker hub

```bash
./gradlew jib dockerPush
```

# Next features

 1. Bundle as .zip file.
 2. Include mechanism for the actual script logic (non-boilerplate).
 3. Add installer.  Check for OS? copy to /usr/local/bin?
 4. Install support: getopts
 5. Option for w/o getopts
 6. Format code/linter
 
# Resources

 1. [Best practices](https://www.tothenew.com/blog/foolproof-your-bash-script-some-best-practices/)


