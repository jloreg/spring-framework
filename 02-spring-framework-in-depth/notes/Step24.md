## Step 24 - IOC Container vs Application Context vs Bean Factory

### Disambiguation

* IOC Container
* Application Context
* Bean Factory

In this step, let's look at different terms, which are used, to refer to application context:  IOC Container, Application Context and Bean Factory.
What is inversion of control ? Typically, let's say the WelcomeController, 

```java
//#1. Old form

@RestController
public class WelcomeController {

	private WelcomeService service = new WelcomeService();
	
	@RequestMapping ("/welcome")
	public String welcome() {
		return service.retrieveWelcomeMessage();
	}
}
```
Typically,  let's say the WelcomeController, meets the WelcomeService. The way it used to be is, the WelcomeService is actually a dependency of the WelcomeController. So, the WelcomeController, directly creates an instance off it. That means this is tightly coupled with this.

#### With Spring

```java
//#2. New form

@Component
public class WelcomeService {
	//Bla bla bla
}

@RestController
public class WelcomeController {

	private WelcomeService service = new WelcomeService();
	
	@RequestMapping ("/welcome")
	public String welcome() {
		return service.retrieveWelcomeMessage();
	}
}
```

However, when we use Spring, then we would do this. We would create WelcomeServiceas as a component. @Component, and we would put @Autowired on this, and we would @Autowire the WelcomeService into the WelcomeController. Who does that for us ? Spring Framework does that for us. 

In the old form (see #1), we are inverting the controller. The Controller of creating the WelcomeService. The control of creating the dependency, was with the bean itself (WelcomeController). So the bean, the WelcomeController was deciding when to create the WelcomeService, 
when to create the dependency, how to create the dependency? 

But now with Spring (see #2), we are shifting that responsability out to Spring. This is called  inversion of control (IOC). The control moves out of the controller, or the component which needs the dependency, to the framework which is injecting the dependency in. Now, the framework is responsible to understand what are the dependencies that this component needs. Make sure they are available and @Autowire them in. This is callled inversion of control, and the program of the framework which provides this, is called IOC Container. 

The IOC Container is the one which manages these Beans. So it creates an instance of the WelcomeService. It creates a Bean for the WelcomeController. It would Autowire the WelcomeService Bean, into the WelcomeController. All these things are done by IOC Container. IOC Container is kind of a generic concept, it is not really framework specific. 
So this whole thing, whichever does this stuff. Whichever does the wiring, creation of Beans and thing like that is called an IOC Container. It's a very generic concept.

In Spring there are two implementations of the IOC Container. One is the Application Context, and the second one is the Bean Factory. Actually, the first one is Bean Factory, and the second one is Application Context. In all the examples until now, we have been making use of Application Context.

### Application Context

* Bean Factory
	* Spring's AOP features
	* l18n capabilities
	* WebApplicationContext for web applications etc.

Because Spring recommends to use Application Context in 99% of these scenarios. Now, what is the difference between an Application Context and a Bean Factory ? Application Factory is nothing but, Bean Factory++. The core features of Spring are inside the jar called Spring-Core, and that's what the Bean factory provides.

Bean Factory provides the basic management for Beans and wiring of the dependencies. That's it, it doesn't provide anyting more. The Application Context provides more features than a Bean Factory. It provides all the features, that are typically needed by Enterprise Applications. So you can kind of call the Application Context as Bean Factory++. So examples of features, that the Application Context contains, but the Bean Factory does not, are the Spring AOP features, Internationalization, and capabilities, like WebApplicationContext for web applications.

Spring recommends to use Application Context in all the situations, except when memory is at a premium. So let's say you have a device, an IOD device of something. Where the amount of memory you can use. The amount of memory that is present on the device, is very, very less.
In those kind of situations, you can go for a Bean Factory. But in all typical Enterprise Application scenarios, you can go with an Application Context.