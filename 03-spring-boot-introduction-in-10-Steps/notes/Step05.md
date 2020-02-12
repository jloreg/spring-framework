## Step 05 - What is Spring Boot Auto Configuration?

In this step, we will understand what is auto configuration. How did all the things that are needed for the REST service to be up and running get configured automatically.

```java
package com.imh.springboot.basics.springbootin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//For web application
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
```

@SpringBootApplication indicates that:

* this is a Spring context file.
* enables something called auto configuration.
* enables something called component scan. 

Component scan is one of the important features of Spring, where it would start automatically scanning these classes in this *package com.imh.springboot.basics.springbootin10steps;*, and its sub-packages for any beans.

``` java
package com.imh.springboot.basics.springbootin10steps;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController	//Annotation to create a simple REST Controller
public class BooksController {
	//Process the GET request "/books"
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return Arrays.asList(
				new Book(1l, "Mastering Spring 5.2", "Ranga Karanam"));
	}
}
```

So, when we added the @RestController annotation in BooksController class, this is going to be one of the annotations which is scanned for it. So this would be registered as a component. So the BooksController would be registered as a bean and it would me managed by the Spring Framework.

There are three things that are essentially done by @SpringBootApplication. It indicates that this is a Spring Context. The second thing is the fact that this enables auto configuration. The third thing is that it enables automatic scan of this specific package com.imh.springboot.basics.springbootin10steps;.

Now if we want to look at auto configuration in depth. SpringApplication.run(WebApplication.class, args); method is used to run a Spring context. So I'm giving a Spring context as an input to it, and it would be able to run that. The run method also returns a application context. So I'll actually get the application context back and loops around it to print the name of all the beans which are configured. 

```java
	ApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);
		
	//Loop around the applicationContext and prints the name of all the beans which are configured
	for (String name: applicationContext.getBeanDefinitionNames()) {
		System.out.println(name);
	}
```

There are a lot of things that are printed and configured for us automatically, how are these being configured ? That's basically the feature called auto-configuration. What Spring Boot does as a part of the Spring framework, there is something called spring-boot-autoconfigure. So if I look at one of the dependencies spring-boot-autoconfigure.jar has a lot of logic built into it. So you can see that there is a lot of classes that are written. These auto configuration classes are what are creating these beans.

How does the auto-configuration work ? Sprig Boot basically looks at a) Frameworks available on the CLASSPATH, b) Existing configuration for the application. Based on these, Spring Boot provides basic configuration needed to configure the application with these frameworks. This is called Auto-Configuration. So for example, if you are using a web starter, the basic things that you would need would be a dispatcher servlet, you would need some internationalization, some messaging, etc. All that kind stuff would get auto-configured for you.

If you want to find out more about what auto configuration is happening, what we can turn on *logging.level.org.springframework = DEBUG logging* on /src/main/resources/application.properties and see what happens.

If you run the WebApplication, you can also see all the things that were auto configured. Not only that, the auto-configuration report also shows what are the things that were not auto-configured; it would show why the rest of the  auto-configuration things did not match, and why they were not executed -why beans for them were not created-.

```
Negative matches:
-----------------

   ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

   AopAutoConfiguration.AspectJAutoProxyingConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.aspectj.weaver.Advice' (OnClassCondition)

   ArtemisAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

   BatchAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.springframework.batch.core.launch.JobLauncher' (OnClassCondition)

   CacheAutoConfiguration:
      Did not match:
         - @ConditionalOnBean (types: org.springframework.cache.interceptor.CacheAspectSupport; SearchStrategy: all) did not find any beans of type org.springframework.cache.interceptor.CacheAspectSupport (OnBeanCondition)
      Matched:
         - @ConditionalOnClass found required class 'org.springframework.cache.CacheManager' (OnClassCondition)
```

**Note**. See the web page springboottutorial.com.

## Complete Code Example

##### /src/main/java/com/imh/springboot/basics/springbootin10steps/WebApplication.java
```java
package com.imh.springboot.basics.springbootin10steps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//For web application
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);
		
		//Loop around the applicationContext and prints the name of all the beans which are configured
		for (String name: applicationContext.getBeanDefinitionNames()) {
			System.out.println(name);
		}
	}
}
```