# lickety-sidekick

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
docker run  -p 8080:8080 --rm -t mikeleitz/lickety-script-service
```

## UI : VueJs

```bash
docker run -p 8081:80 --rm -t mikeleitz/lickety-script-ui
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

### Both build images locally and deploy to Docker Hub.

```bash
./gradlew jibDockerBuild jib docker dockerPush
```

## Copy Docker image over to Google registry

You must tag before pushing.

```bash
docker tag mikeleitz/lickety-script-ui gcr.io/lickety-script/lickety-script-ui:current
docker tag mikeleitz/lickety-script-service gcr.io/lickety-script/lickety-script-service:current

docker push gcr.io/lickety-script/lickety-script-ui:current
docker push gcr.io/lickety-script/lickety-script-service:current
```

## Deploy to Google Cloud Run

```bash
gcloud builds submit --tag gcr.io/lickety-script/lickety-script-service

gcloud builds submit --tag gcr.io/lickety-script/lickety-script-ui
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
 2. [GKE using static IP and DNS](https://cloud.google.com/kubernetes-engine/docs/tutorials/configuring-domain-name-static-ip)
 3. [Bash flags for sensible operation](http://redsymbol.net/articles/unofficial-bash-strict-mode/)
 4. [Exit codes](https://www.cyberciti.biz/faq/linux-bash-exit-status-set-exit-statusin-bash/)
 4. [Returning values from functions](https://www.linuxjournal.com/content/return-values-bash-functions)

# Todo

## Aggregate build and deploy

Create higher level scripts to build and deploy to GKE automatically.

## Continue working on type section

Since the deployment is figured out.  Get back to functionality. 

Finishing adding validations for each data type: numeric, string, boolean, and other.

## Configure both the UI and REST endpoint for https access.

(done) Use kubernetes service for http.

Use kubernetes ingress for https.

 * [Use static IP and DNS](https://cloud.google.com/kubernetes-engine/docs/tutorials/configuring-domain-name-static-ip)
 
## Troubleshoot multiple replicas

Find out why GKE doesn't want to run two instances of both pods on the node pair.  There's always some un-described resource issue.

# Link to github projects this application uses

 * [shUnit2](https://github.com/kward/shunit2)
