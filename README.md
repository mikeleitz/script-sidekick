# script-sidekick
Project to help create small scripts.  Generates the relevant boilerplate and helps you keep your scripts sane.

# Architecture Options

There are two options having a spring-boot backend with a Vue front end.

 1. Have a single webapp with Spring MVC delivering the [Vue code via Thymeleaf templates](https://www.baeldung.com/spring-boot-vue-js).
 2. Use two webapps: Spring for the backend and Node for the front end.  [Two app solution](https://blog.codecentric.de/en/2018/04/spring-boot-vuejs/).
 
For this simple application we'll use the Thymeleaf option.  We only need a single page to submit the request with a few parameters and deliver a file as the result.   
