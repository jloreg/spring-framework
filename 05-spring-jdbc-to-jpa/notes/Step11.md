## Step 11 - Implementing insert and update Spring JDBC Update Methods

In the previous steps, we created simple methods to find, to delete, and do different stuff with your Person. Now, in this step, we'll start with creating an insert method and then we would move on to and update method as well.

So let's get started with the insert method. I would want to insert Person and over here *jdbcTemplate.update* the query I would need to execute is going to be the same query that we used in the */src/main/resources/data.sql*, except that the values will be a little different. You'd need to ser the values that you would want to use. So I need to use this *VALUES(10001,  'Ranga', 'Hyderabad',sysdate());* copied in the method *public int insert (Person person)*. Instead of the values, I would need to pass in question mark *?*.

```java
	public int insert (Person person) {
		return jdbcTemplate.update
		//"insert into person (id,  name, location, birth_date) "
		+ "values (?,?,?,?)", 
		new Object [] {
				person.getId(), persongetName(), 
				person.getLocation(), 
				new TimeStamp (person.getBirthDate().getTime())});	
	}
```
So this is the query we would want to fire. One of the things is when we write the queries it's better to use all small (lowercase letters). Explicitly providing the columns is very good because then **you're not depending on which order the columns are present in the database**. So, we are creating a simple insert query, we are setting the values to each of the question mark *?*. 

One of the things is in our database we define *birth_date* as a time stamp. So we need to create the time stamp here *new TimeStamp (person.getBirthDate().getTime())* as well. So what we did now, instead of *Person.getBirthDate()*, we are creating a TimeStamp and passing it in. That's all we need to do to be able to insert a row into our database.

The last one we wanted to do is to be able to update. So, you would want to update the details of a Person. We don't want to update the id. Id is the primary key, so the only things which would be updated would be the name *set name = ?*, location *set location = ?*, and birth_date *birth_date = ?*

```java
	public int update(Person person) {
		return jdbcTemplate.update(
				"update person " 
				+ " set name = ?, location = ?, birth_date = ? " 
				+ " where id = ?",
				new Object[] { 
						person.getId(), person.getName(), 
						person.getLocation(), 
						new Timestamp(person.getBirthDate().getTime()) });
	}
```
So I'm saying update Person, set the values where Id is equal to question mark "where id = ?". Make sure that you have spaces at the end of the two firsts subqueries so that the query is correct. A problem with this query, is that the actual order not correspond with the necessary order imposed by the question mark attributes, let's to fix the right order; getName is the first one, getLocation is the second one, getTime is the third one, and getId is the last one.

```java
	public int update(Person person) {
		return jdbcTemplate.update(
				"update person " 
				+ " set name = ?, location = ?, birth_date = ? " 
				+ " where id = ?",
				new Object[] { 
						person.getName(),		//FIXED
						person.getLocation(), 
						new Timestamp(person.getBirthDate().getTime()), 
						person.getId() });
	}
```

So now we have the methods to insert and update a Person. Let's make use of these methods right now. So let's go to SpringDatabaseApplication and run them in *public void run*. So I would try and insert a new Person, and update an existing one; be careful with the Ids because that would be exactly what you would be updating. Let's say that "Pieter" shifted from Amsterdam to let's say Utrecht, I don't want to change the name and I'll leave his birthdate as sysdate.

```java
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
		
														//Added
		logger.info("Inserting 10004 -> {}", 
				dao.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				dao.update(new Person(10003, "Pieter", "Utrecht", new Date())));
	}
}
```
So we are basically doing an update of ten thousand three, we are shifting him to Ultretch and we are inserting a new row for Tara in Berlin. Let's start the application now. 

```
Console output:

[2m2020-02-26 07:34:57.456[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 2.394 seconds (JVM running for 3.051)
[2m2020-02-26 07:34:57.466[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$27236da7[0;39m [2m:[0;39m All users -> [com.imh.spring.database.databasedemo.entity.Person@74b31cc3, com.imh.spring.database.databasedemo.entity.Person@9155b84, com.imh.spring.database.databasedemo.entity.Person@5bdddbaa]
[2m2020-02-26 07:34:57.470[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$27236da7[0;39m [2m:[0;39m User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@1b274751
[2m2020-02-26 07:34:57.471[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$27236da7[0;39m [2m:[0;39m Deleting 10002 -> No of Rows Deleted - 1
[2m2020-02-26 07:34:57.472[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$27236da7[0;39m [2m:[0;39m Inserting 10004 -> 1
[2m2020-02-26 07:34:57.473[0;39m [32m INFO[0;39m [35m6923[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$27236da7[0;39m [2m:[0;39m Update 10003 -> 1
```

You can see that deleting the number of rows deleted is one, inserting thousand four it returned one row updated, and even updating ten thousand three returned one-row update. So that's right because we were expecting only one row to be updated for each of these queries. Now let's go to the database and check the data which is present in there. Let's reconnect to the H2 console on *localhost:8080/h2-console* and fire the query *SELECT * FROM PERSON* against the H2 database.

```
Database output:

SELECT * FROM PERSON;
ID  	NAME  	LOCATION  	BIRTH_DATE
10001	Ranga	Hyderabad	2020-02-26 00:00:00
10003	Pieter	Utrecht	2020-02-26 07:34:57.472
10004	Tara	Berlin	2020-02-26 07:34:57.472
(3 rows, 10 ms)
```
If you see the Person here, what data does it have? It has three rows. We have the last row which is inserted in, we deleted ten thousand two, so that's not here. Ten thousand four Tara Berlin is properly inserted in. And the other thing you can see is Peter is now shifted to Utrecht. He's no longer in Amsterdam. The update also worked. 

What we have done in the last few steps, is we have defined methods in our PersonJdbcDao to be able to retrieve values, update values, delete values. Whenever we are using JDBC it's very simple, right. You just write the query which you'd want to write, so the complexities in writing the query, once you have the query ready then all that you need to do is pass in the appropriate parameters and that's all. And that's all there is, once you write the query, then you can set the parameters in and then execute them.

I'll recommend you to try with a different query. So try and write different queries. Try and execute those things through the Dao. And try and call them from the SpringDatabaseApplication class and see how you'd be able to do different things with it. 

## Complete Code Example

##### /src/main/java/com/imh/spring/database/databasedemo/SpringDatabaseApplication.java

```java
package com.imh.spring.database.databasedemo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.imh.spring.database.databasedemo.entity.Person;
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
		logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
				dao.deleteById(10002));
		
		logger.info("Inserting 10004 -> {}", 
				dao.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				dao.update(new Person(10003, "Pieter", "Utrecht", new Date())));
	}

}
```

---

##### /src/main/java/com/imh/spring/database/databasedemo/jdbc/PersonJdbcDao.java

```java
package com.imh.spring.database.databasedemo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	public int insert (Person person) {
		return jdbcTemplate.update("insert into person (id, name, location, birth_date) " + "values(?,  ?, ?, ?)",
				new Object[] { person.getId(), person.getName(), person.getLocation(),
						new Timestamp(person.getBirthDate().getTime()) });	
	}
	
	public int update(Person person) {
		return jdbcTemplate.update("update person " + " set name = ?, location = ?, birth_date = ? " + " where id = ?",
				new Object[] { person.getName(), person.getLocation(), new Timestamp(person.getBirthDate().getTime()),
						person.getId() });
	}
}
```