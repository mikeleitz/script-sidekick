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
docker run  -p 8080:8080 --rm c813eeebfb4c
```

## VueJs

```bash
docker run -p 8081:80 --rm 06e2c1ecc0ab
```

## Access via Browser

```
http://localhost:8081/
```
