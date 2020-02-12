## Step 6 - Spring Boot vs Spring vs Spring MVC

https://www.springboottutorial.com/spring-boot-vs-spring-mvc-vs-spring

Spring, Spring MVC and Spring Boot have their own rules. They are not really compeatting for the same space, they solve different problems, and they solve really well.

The core problem Spring framework solves is testability. If you don't define properly dependencies, your applications is not testable. The most important feature is Dependency Injection. The core of the Spring Framework is IOC Inversion of Control. Spring Framework takes control of all the beans and their dependencies, and helps us to build loosely coupled applications, and loosely coupled applications can be easily unit tested. That's the main problem Spring Framework solves. Other problems Spring Framework also solves, are the Duplication/Plumbing Code and good integration with other frameworks.

Spring MVC provides a simple way of developing web applications. One of the great things about Spring MVC is the separation of concepts. DispatcherServlet -the basic front controller-, ModelAndView and View Resolver -resolving a view name with a physical view-, etc. With this kind of simple concepts, Spring MVC makes very easy to develop your web applications, as well as your RESTful services. 

Spring Boot solves the problem of configuration with auto-configuration, and implements some features like monitoring, integrated server containers, logging, logs, etc.

**Note**. Whether you are developing web applications or REST services with Spring Boot, internally you are using Spring MVC framework, that's Spring MVC web framework.