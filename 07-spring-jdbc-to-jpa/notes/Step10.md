## Step 10 - Implementing deleteById Spring JDBC Update Method

In this step, let's write a simple query to delete by Id. So it should be very simple to do that. I'm just copying the findById method. Now what we want to do is not findById but deleteById.

So I would want to delete the user who has a specific Id. So instead of the query for Object *jdbcTemplate.queryForObject*, what we would use is a method which is present called *jdbcTemplate.update*. So remember, whenever you want to execute a query to do some update, you use the update method on JDBC template *jdbcTemplate.update*, whether the update is an update or a delete, irrespective of that we would be using the update method which is present in the JDBC template. 

For the query "delete from person where id=?", I don't need the mapper because I don't really need to map anything in here. The thing is deleteById returns a number back. If I go to the *jdbcTemplate.update* declaration method you would see that it returns a int back. If you go further up JdbcTemplate, you would see that it actually prints how many rows were affected. So it returns out how many rows are affected by the query. So I'll return that back, so I'll change the return type to *public int deleteById*, this would return how many rows were deleted.

```java
package com.imh.spring.database.databasedemo.jdbc;

@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int deleteById(int id) {
		return jdbcTemplate.update("delete from person where id=?", new Object[] { id });	//added
	}
}
```

Now I go to the SpringDatabaseApplication and I would want to delete not thousand one. Let's delete ten thousand two. Now I can stop and start the application and see what would happen.

```java
package com.imh.spring.database.databasedemo;

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
		logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
				dao.deleteById(10002));	//added
	}
}
```

```
Console output:

[2m2020-02-25 21:33:57.018[0;39m [32m INFO[0;39m [35m11392[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 1.9 seconds (JVM running for 2.365)
[2m2020-02-25 21:33:57.028[0;39m [32m INFO[0;39m [35m11392[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$f6e8e83c[0;39m [2m:[0;39m All users -> [com.imh.spring.database.databasedemo.entity.Person@10933627, com.imh.spring.database.databasedemo.entity.Person@1e49336e, com.imh.spring.database.databasedemo.entity.Person@3e01f3c4]
[2m2020-02-25 21:33:57.032[0;39m [32m INFO[0;39m [35m11392[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$f6e8e83c[0;39m [2m:[0;39m User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@56a3b8fb
[2m2020-02-25 21:33:57.034[0;39m [32m INFO[0;39m [35m11392[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$f6e8e83c[0;39m [2m:[0;39m Deleting 10002 -> No of Rows Deleted - 1
```

Over here you can see that the deleting ten thousand two, and number of rows deleted is one because we are expecting one row to be deleted, and we can even go to the H2 console to make sure that we have the right thing present. So I'll go to *localhost:8080/h2-console*. I'll do a connect and run. So you can see that ten thousand two is no longer present in the H2 database. It's been deleted because we have executed the delete query as part of our launch. So here *dao.deleteById(10002)* we are deleting the Id ten thousand two. And the row is completely deleted.

```java
logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
			dao.deleteById(10002));	//added
```

In this step, we wrote the delete method by Id. I would recommend you to play around with the delete. So try and delete different persons by different characteristics by passing different parameters in. You can even have multiple parameters passed in.
 
```java
 public int deleteById(int id, String something) {
	return jdbcTemplate.update("delete from person where id=? or xyz=?" new Object[] { id, something }); 
}
```

So I can say xyz="something" and passing the value for that in here *{Id, something}*, that's how you would add additional parameters to the query, actually I only can say where and or whichever SQL expression thing that you would want to make use of, so try that and I'll see you in the next step.

## Complete Code Example

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
import com.in28minutes.database.databasedemo.springdata.PersonSpringDataRepository;

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
		logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
				dao.deleteById(10002));
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
	
	public int deleteById(int id) {
		return jdbcTemplate.update("delete from person where id=?", new Object[] { id });
	}
}
```