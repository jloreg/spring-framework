## Step 10 - Why is Spring popular ?

In the last few steps, we discussed about the most important features of Spring framework. One of the important things about Spring is that it's one of the very few frameworks that remains as popular today
as it was 15 years back.

But Spring is one of the very few frameworks which retains its importance even today. So what's the reason for this? How did playing maintain its popularity through these one and half decades? That's the subject of this step.

* The most important reason Spring is so popular is because **it enables writing testable code**. The core feature of Spring is dependency injection, and if we use Dependency Injection properly then we would be able to write unit tests for our code very easily. Spring has a really good integration frameworks like Junit and Mockito and it enables us to write good unit tests very quickly.

* The second reason why Spring is so popular **there is no plumbing code at all**. What do you mean by plumbing code? I'll take an example.

##### https://github.com/in28minutes/SpringIn28Minutes/blob/master/4.SpringJDBC/src/main/java/com/in28minutes/jdbc/data/service/TodoDataService.java
```java
package com.in28minutes.jdbc.data.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.in28minutes.jdbc.hsql.HsqlDatabase;
import com.in28minutes.jdbc.model.Todo;

public class TodoDataService {

	private static final String INSERT_TODO_QUERY = "INSERT INTO TODO(DESCRIPTION,IS_DONE) VALUES(?, ?)";

	private static final String DELETE_TODO_QUERY = "DELETE FROM TODO WHERE ID=?";

	HsqlDatabase db = new HsqlDatabase();

	public static Logger logger = LogManager.getLogger(TodoDataService.class);

	private void insertTodo(Todo todo) {
	
		PreparedStatement st = null;
		try {
			st = db.conn.prepareStatement(INSERT_TODO_QUERY);
			st.setString(1, todo.getDescription());
			st.setBoolean(2, todo.isDone());
			st.execute();
		} catch (SQLException e) {
			logger.fatal("Query Failed : " + INSERT_TODO_QUERY, e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// Ignore - nothing we can do..
				}
			}
		}
	}
}
```
Look at this example which was done using JDBC. You can see that this is about 20 lines of code in the insertTodo() method. I tried a lot of code to get simple functionality working. If you look at code most of it is involved in try catch and exception handling. Execute method throws a checked exception. So you have to really handle it. And once you have to handle it then you'd need to make sure that everything is closed properly. And that's where Spring comes in.

With Spring the amount of code that you need to write becomes almost next to nothing. 

##### https://github.com/in28minutes/SpringIn28Minutes/blob/master/4.SpringJDBC/src/main/java/com/in28minutes/jdbc/data/service/TodoDataServiceSpringJDBC2.java

```java
public class TodoDataServiceSpringJDBC2 {

	private static final String INSERT_TODO_QUERY = "INSERT INTO TODO(DESCRIPTION,IS_DONE) VALUES(?, ?)";

	private static final String DELETE_TODO_QUERY = "DELETE FROM TODO WHERE ID=?";

	HsqlDatabase db = new HsqlDatabase();

	JdbcTemplate jdbcTemplate = new JdbcTemplate(
			new SingleConnectionDataSource(db.conn, false));

	public static Logger logger = LogManager
			.getLogger(TodoDataServiceSpringJDBC2.class);
			
	private void insertTodo(Todo todo) {
		jdbcTemplate.update(INSERT_TODO_QUERY, todo.getDescription(),
				todo.isDone());
	}
}
```
So if you look at the corresponding insertTodo() method in Spring Framework almost zero exception handling code because Spring makes all its exceptions unchecked. So you don't really need to do all the exception handling and stuff. This code which you've seen before is the plumbing code. So with Spring you don't have any need to write plumbing code. There is no plumbing code at all. So if I'm writing hundred lines of code it's most probably some business logic. It's not unnecessary code which is not needed by the application.

* The other reason why Spring is very popular is the architecture flexibility it brings in. As we looked at in the previous steps, Spring is very modular. 

There are Spring modules and Spring projects for very specific purposes. And I can use a specific Spring module without using all others. So even though Spring has a really good MVC framework - Spring MVC - it still offers good support to all the other MVC frameworks like Struts. Even though Spring has its own REST support through Spring MVC, It does offer good support with Jersey as well. I mean JAX RS as well. If I use Spring in my project my options are not really restricted. So if I choose to use Spring in my project, I still have the flexibility to choose other frameworks, and that's very important.

* And the last reason why Spring is very popular is it is able to stay with the trend. 

For example, in the last few years there is a lot of evolution micro services and Cloud. And Spring is able to come up with projects
which helps it to stay relevant. Spring has introduced the new project called Spring Cloud. There’s Spring Boot as well which help you to develop micro services very quickly. So all these things help Spring stay current and that helps Spring framework retain its popularity. 

In this step, we looked at the four reasons why Spring remains popular because it enables testable code. Because it does not have any plumbing code. Because of its flexible architecture and because it stays up with the trends.