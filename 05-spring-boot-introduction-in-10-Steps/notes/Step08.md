## Step 08 - Overview of different Spring Boot Starter Projects

https://www.springboottutorial.com/spring-boot-vs-spring-mvc-vs-spring

In this step, we would want to look at the various starter projects which are present in Spring Boot. Spring Boot provides a wide range of starter projects. Spring Initializr suppports all of them and more. Among a varied range of starter projects and options supported are:

```
spring-boot-starter-web-services : For building applications exposing SOAP Web Services
spring-boot-starter-web - Build Web applications & RESTful applications
spring-boot-starter-test - Write great Unit and Integration Tests
spring-boot-starter-jdbc - Traditional JDBC Applications
spring-boot-starter-hateoas - Make your services more RESTful by adding HATEOAS features
spring-boot-starter-security - Authentication and Authorization using Spring Security
spring-boot-starter-data-jpa - Spring Data JPA with Hibernate
spring-boot-starter-cache - Enabling Spring Framework’s caching support
spring-boot-starter-data-rest - Expose Simple REST Services using Spring Data REST
```

One of the important starters that is offered by Spring Boot is spring-boot-starter-web-services, this is the starter you would need to use when you want to develop SOAP Web services. If you understand Web services, then there are basically REST services and SOAP services. Actually the classification is not as simples as that because SOAP web services can also fit into the bracket of REST web services. So, if you want to define WSDL and then implement your web service, that' s a SOAP web service. If you are developing a SOAP web service then you can use this starter, *spring-boot-starter-web-services*. 

We already looked at using a RESTful application. What did we use to build ? We used *spring-boot-starter-web*. Similar to that, even if you wanted a typical web application, you can use spring-boot-starter-web.

If you want to write applications using Spring JDBC then *spring-boot-starter-jdbc* is perfect for you. 

If you're writing RESTful web services and you would want to add HATEOAS features to your services, then *spring-boot-starter-hateoas* is the one to go for.

All web applications and all RESTful services should be secured, *spring-boot-starter-security* is the starter for using Spring security. You can implement both authentication and authorization and *spring-boot-starter-security* has support for basic authentication as well as Oauth authentication. It also has support for Oauth2 authentication as well. So whichever one you are using, you can use *spring-boot-starter-security*.

As we looked in the previous steps, *spring-boot-starter-data-jpa* is the option when you want to do JPA. **By default**, *spring-boot-starter-data-jpa* **uses hibernate as the ORM framework**.

If you want to implement caching then you can go for *spring-boot-starter-cache*. It has support for a wide range of caching options including distributed caches like hazelcast.

If you're using *spring-boot-starter-data-jpa* and you would want to expose your entities as RESTful services, then you can go with *spring-boot-starter-data-rest*, this starter makes it very easy to expose Spring Boot data JPA component. I mean Spring Boot data JPA entities as RESTful web services. **They expose all the typical request methods  like get, post, put, and delete**.

The others starters of Spring Boot are more technical, *spring-boot-starter-actuator* which will use it in one of the next steps is for monitoring and also you have starters for the embedded servers. We saw that **by default** we were **using *spring-boot-starter-tomcat*.** So you can also use *spring-boot-starter-undertow* or *spring-boot-starter-jetty* if you'd want to use those embedded servlet containers. And also there are starters for logging and log4j.

The idea behind this step is to give you the wide variety of starters that Spring Boot provides. SpringBoot actually provides a wide variety of starters, so whenever you have a problem to solve, go ahead and look at the [reference guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-starter) to see there if there is a starter that solve your problem.