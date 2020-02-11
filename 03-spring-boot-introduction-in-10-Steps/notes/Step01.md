## Step 1 - Introduction to Spring Boot - Goals and Important Features

Today world moving toward Microservices, instead of developing large monolithic applications. We're building a lot of microservices. So instead of building one big application, we are building 20-25 maybe 50 smaller micro services. One of the important things with these microservices, is you would want to be able to build them quickly. That's where Spring Boot comes in.

### Goals
- A) Enable building production ready applications quickly
- B) Provide common non-functional features 
  - embedded servers
  - metrics
  - health checks
  - externalized configuration

### What Spring Boot is NOT!
- ZERO code generation; actually Spring Boot does zero code generation, and that's what makes this concept really great.
- Neither an application server not a web server; Spring Boot provides great integration with embedded servers like Tomcat, Jetty, but by itself Spring Boot is not a web server, is not an a apllication server. 

### Features
- Quick Starter Projects with Auto Configuration. The most important part of Spring Boot is this concept called Starter Projects.
   - Web. Consider example of developing a web application. If let's say I want to develop a web application I would need Spring MVC, Sprinc Core, some validation framework, some logging framework. In addition to that, I would need to configure all this stuff that is needed. For example, if I'm using Spring MVC, I would need to configure dispatcher servlet, view resolver, and a lot of things like that. However with Spring Boot starter projects it becomes very easy. All that you need to do  is to add  a starter called  -spring-boot-web- into you project and that's it. You'll get Spring MVC for free, Spring core for free, you get a validation framework for free and also a logging framework for free.
   - JPA. Similarly, for JPA there is a starter called Spring Boot starter JPA. Once we use this starter. You would not only get JPA but also a default implementation of JPA with Hibernate and also a default implementation of JPA with Hibernate and also auto configuration of that. So you would not need to worry about the framework part and you can directly start creating your entities.
- Embedded Servers - Tomcat, Jetty or Undertow. Let's say I'm developing a web application and I would want to deploy it on to a Linux box. In the olden days, the way it used to work is first I would need to install the Linux box. Then I would install java on it and then I would need to install a web server. So I would need to install either Tomcat, Weblogic or webspher and then I would take my application war and deploy it. This is the usual way we use to deploy stuff. With Spring Boot, comes a concept called embedded server. What you can do is you can package your server, you can package Tomcat along with your application jar. So I can include  the Tomcat server in my application jar. So I don't need to install it on the Linux box. So all that  I need to do, on the Linux box is if I have Java installed that's sufficent. I can go ahead and run my application. I don't need any other server installed on the linux box. In the world of Micro services, this makes a huge difference.
- Production-ready features. Spring Boot providesa number of production ready features.
   - Metrics and health checks. Spring Boot provides monitoring for your application through something called Spring Boot actuator. For example, you can find out how many times a particular services is called. You can find out how many times a particular service failed, and also you can check whether the application is up and running or not. All these features como built in in.
   - Externalized configuration. Another important feature that Spring Boot provide is externalised configuration. The configuration of applications varies between different enviroments. Your configuration from dev different from your configuration in production. Spring Boot provides these features built in. You can simply create property files matching a simple naming convention and that's it. You're ready with externalised configuration. Spring Boot also provides support for different profiles.

http://localhost:8080/books => Few hardcoded books

---
   