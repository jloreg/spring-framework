## Step 04 - Creating a Simple REST Controller

What we'll do  is we'll create a simple REST service with the URL /books, and I would want to return a few hardcoded books. Since the Tomcat runs on port 8080 by default. So the URL would be something like http://localhost:8080/books and I would want to return a few harcoded books.

I would want that the the class below be a REST controller (annotation @RestController).I would want it to serve requests, REST requests. For that, I would want to map a URL to that, so the one which I want to map is "/books".So whenever somebody executes a GET request on http://localhost:8080/books I would want to return a set of harcoded books. So how do I do that ? that's by using @GetMapping("/books"). I'm mapping a get URL, so GET is the type of the request.

If you understand HTTP well, then you would know that there are multiple types of HTTP method. That's right GET, POST, DELETE. GET is typically used to retrieve data. So I would want to use get. Let's create the method.

```java
package com.imh.springboot.basics.springbootin10steps;

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

Now I would actually go ahead and now startup the server. I'm running it as a Spring Boot Application. 
```
[2m2020-02-11 13:32:39.184[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.i.s.b.s.WebApplication                [0;39m [2m:[0;39m Starting WebApplication on laptop with PID 10789 (/home/jon/spring-workspace/spring-framework/03-spring-boot-introduction-in-10-Steps/target/classes started by jon in /home/jon/spring-workspace/spring-framework/03-spring-boot-introduction-in-10-Steps)
[2m2020-02-11 13:32:39.185[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.i.s.b.s.WebApplication                [0;39m [2m:[0;39m No active profile set, falling back to default profiles: default
[2m2020-02-11 13:32:39.732[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.s.b.w.embedded.tomcat.TomcatWebServer [0;39m [2m:[0;39m Tomcat initialized with port(s): 8080 (http)
[2m2020-02-11 13:32:39.737[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.apache.catalina.core.StandardService  [0;39m [2m:[0;39m Starting service [Tomcat]
[2m2020-02-11 13:32:39.738[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36morg.apache.catalina.core.StandardEngine [0;39m [2m:[0;39m Starting Servlet engine: [Apache Tomcat/9.0.30]
[2m2020-02-11 13:32:39.777[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.a.c.c.C.[Tomcat].[localhost].[/]      [0;39m [2m:[0;39m Initializing Spring embedded WebApplicationContext
[2m2020-02-11 13:32:39.777[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.s.web.context.ContextLoader           [0;39m [2m:[0;39m Root WebApplicationContext: initialization completed in 550 ms
[2m2020-02-11 13:32:39.873[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.s.s.concurrent.ThreadPoolTaskExecutor [0;39m [2m:[0;39m Initializing ExecutorService 'applicationTaskExecutor'
[2m2020-02-11 13:32:39.964[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mo.s.b.w.embedded.tomcat.TomcatWebServer [0;39m [2m:[0;39m Tomcat started on port(s): 8080 (http) with context path ''
[2m2020-02-11 13:32:39.966[0;39m [32m INFO[0;39m [35m10789[0;39m [2m---[0;39m [2m[           main][0;39m [36mc.i.s.b.s.WebApplication                [0;39m [2m:[0;39m Started WebApplication in 0.983 seconds (JVM running for 1.38)
```

Server has started up and in the log you can see that there is our mapping has also been picked up directly. So you would see that "/books" get method is mapped on the method getAllBooks() that we wrote. So let's see what would happen when I execute the request http://localhost:8080/books in the Mozilla Web browser.

```
[{"id":1,"name":"Mastering Spring 5.2","author":"Ranga Karanam"}]
```

You can see the server response back with a JSON response. So this request is actually returning back the details in a JSON format. This is a rest service. The magical part of the whole thing is the fact that we have directly focused on creating the rest service, you have created a REST service in less than 5 minutes, that's what Spring Boot aims to enable. We have not focused on any of the infrastructure stuff. We did not focus on either configuring framework or configuring some beans, or configuring a dispatcher servlet, or configuring a view resolver. We did nothing. But still magically this service starts working. 
 
## Complete Code Example

##### /src/main/java/com/imh/springboot/basics/springbootin10steps/Book.java

```java
package com.imh.springboot.basics.springbootin10steps;

public class Book {
	long id;
	String name;
	String author;

	public Book(long id, String name, String author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		return String.format("Book [id=%s, name=%s, author=%s]", id, name, author);
	}

}
```

---

##### /src/main/java/com/imh/springboot/basics/springbootin10steps/BooksController.java

```java
package com.imh.springboot.basics.springbootin10steps;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return Arrays.asList(
				new Book(1l, "Mastering Spring 5.2", "Ranga Karanam"));
	}
}
```