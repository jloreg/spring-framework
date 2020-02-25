## Step 08 - Implementing findById Spring JDBC Query Method

In this step, we'll get our hands dirty and we'll write a query to get the details of a specific person. So we want to pass in the ID and get the details of a specific person. So how do we do that? Let's look at it.

So I'm copying the previous method. I'll call this method as *findById (int id)* and I would pass in the ID of the person. The ID of the person is an Integer.

Over here in JdbcTemplate when you are querying for a single Person, so I'm not querying for a list of persons, but I'm querying for a single person. In these kinds of situations, you can use something called *jdbcTemplate.queryForObject*. So query for object is when you are actually querying for a specific object, when for a specific thing.

So what we want to do is we would want to query from a specific person. So I would want to say "select * frim person where id=?", so now I'd need to replace this question mark '?' with the Id which is passed in. So when Id is equal to 3 the question mark should be replaced by 3. How do I do that? The query for object method accepts a list of parameters. So I would need to create an array of objects *new Object[] { id }*, and pass the parameter Id in. So this query might be having multiple parameters, in that case, you can pass multiple parameters in, but for now, we only have one parameter that needs to be passed. I would do the same BeanRowMapper which we defined earlier. And now what I'll do is change *public <List>*, it's not no longer a list of Person, it's just a *Person*. Very simple, isn't it? findById a specific thing, and I'll go to the SpringDatabaseApplication and say

```java
package com.imh.spring.database.databasedemo;

@SpringBootApplication
public class SpringDatabaseApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users -> {}", dao.findAll());
		logger.info("User id 10001 -> {}", dao.findById(10001));	//Added
	}
}
```
Let's make sure we have that in the data.sql. Yes, we have inserted the *VALUES(10001,  'Ranga', 'Hyderabad',sysdate());*. So let's get the details of ten thousand one. The application is in debug mode. I would rather stop this and remove the debug mode so that I can see the log much more clearly. I'd comment this one *#logging.level.root=INFO* by putting a hash in front of it and started again. Let's run this.

```java
Console output:

[2m2020-02-25 18:23:29.245[0;39m [32m INFO[0;39m [35m5810[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 1.95 seconds (JVM running for 2.406)
[2m2020-02-25 18:23:29.255[0;39m [32m INFO[0;39m [35m5810[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$bcffd8e0[0;39m [2m:[0;39m All users -> [com.imh.spring.database.databasedemo.entity.Person@244a410d, com.imh.spring.database.databasedemo.entity.Person@4d7e41c8, com.imh.spring.database.databasedemo.entity.Person@504a971d]
[2m2020-02-25 18:23:29.259[0;39m [32m INFO[0;39m [35m5810[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$bcffd8e0[0;39m [2m:[0;39m User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@45c49279
```

What we looked at in this step is to retrieve the details of a specific Id. As an exercise what I would recommend you to do is to create similar queries. So, for now, we are trying to find by Id. When you find by Id there'd be only one row back, but you can create other methods for findByName. For findByName you can have multiple results back because there can be multiple persons with the same name, and also you can do find by location. 

So I would recommend you try and play around with the data and create a few more methods in PersonJdbcDao class. You can try to create a few more retrieve methods, trying to write queries for them, and trying to retrieve the data for them, and them you can call them from the SpringDatabaseApplication from the run method, to check if they're all working fine.

## Complete Code Example

##### /src/main/resources/application.properties

```
spring.h2.console.enabled=true
#logging.level.root=info
``` 

##### /src/main/java/com/imh/spring/database/databasedemo/SpringDatabaseApplication.java

```java
package com.imh.spring.database.databasedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.imh.spring.database.databasedemo.jdbc.PersonJdbcDao;

@SpringBootApplication
public class SpringDatabaseApplication implements CommandLineRunner {


	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJdbcDao dao;

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users -> {}", dao.findAll());
		logger.info("User id 10001 -> {}", dao.findById(10001));
	}

}
```

---

##### /src/main/java/com/imh/spring/database/databasedemo/jdbc/PersonJdbcDao.java

```java
package com.imh.spring.database.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.imh.spring.database.databasedemo.entity.Person;

@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", 
				new BeanPropertyRowMapper(Person.class));
	}
	
	public Person findById(int id) {
		return jdbcTemplate.queryForObject
				("select * from person where id=?", new Object[] { id }, 
				new BeanPropertyRowMapper <Person>(Person.class));
	}
}
```