# script-sidekick

Project to help create small scripts.  Generates the relevant boilerplate and helps you keep your scripts sane.

# Architecture Options

There are two options having a spring-boot backend with a Vue front end.

 1. Have a single webapp with Spring MVC delivering the [Vue code via Thymeleaf templates](https://www.baeldung.com/spring-boot-vue-js).
 2. Use two webapps: Spring for the backend and Node for the front end.  [Two app solution](https://blog.codecentric.de/en/2018/04/spring-boot-vuejs/).
 
For this simple application we'll use option 2.  A Node application to serve up the vue files.   

# Docker

## Spring boot
Start the Spring boot application using Docker

```bash
docker run  -p 8080:8080 --rm -t mikeleitz/sidescript-service
```

## VueJs

```bash
docker run -p 8081:80 --rm -t mikeleitz/sidescript-ui
```

## Access via Browser

```
http://localhost:8081/
```

# Todo

[Best practices](https://www.tothenew.com/blog/foolproof-your-bash-script-some-best-practices/)

## Installer 

## Delivery

 1. Bundle as .zip file.
 2. As place for new code.
 3. Add installer.  Check for OS? copy to /usr/local/bin?
 4. Install support: getopts
 5. Option for w/o getopts
 6. Format code/linter
