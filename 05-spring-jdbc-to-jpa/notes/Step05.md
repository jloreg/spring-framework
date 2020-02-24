## Step 05 - Implement findAll persons Spring JDBC Query Method

In the previous step, we populated some data into the Person table. In this step, we'll start with writing our Spring JDBC stuff.

What we would want to do is create a new PersonJdbcDAO class in the package com.imh.spring.database.databasedemo.jdbc to talk to the database, and get the details of Person table. At a later point, I would use JPA to get the same detail.

What does class Person Jdbc Dao do? This talks to the database get values from there. What annotation should I use? @Component or @Repository. The @Repository is the right one, I could have called this @Component, but it would not make any difference as far as this specific example goes, but it's always good to use the right annotation, and the right annotation in this specific situation is @Respository. It talks to the database, so it's @Repository.

```java
@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
}
```
 
So over here I would want to create a simple method to be able to get the details of all the TODOs. So what I would want to do is, I would want to do a query from Person table, and I would want to be able to return all the values of Person. I would want to be able to print the values of the Person.

So how do I write a query for that? So the return type would be a List of person and I would want to call this method 	*public List<Person> findAll() *. Inside the method, you would want to be able to talk to the database. How do we talk to the database? Typically, if you're using normal JDBC, you would use a database connection. So you would create a connection to the database and use it, but we are using Spring, so we would want Spring to give us the database connection to us. How do we do that? The way we can do that is to use something called JDBC template.

If you're using Spring, then you have to use the JDBCTemplate to execute a query, and I would want Spring to @Autowire a JDBC template. We'll talk a little bit more about JDBC template a little later. For now, let's get the example running. 

```java
@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			//
			return person;
		}
	}
	
	//#1
	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", new PersonRowMapper());
	}
}
```

So I would want to execute a query *"select * from person"*, and the query I would want to map it in the *List<Person>* bean. Thing is we have not really defined a Person bean yet. So let's go ahead and quickly define a Person bean with exactly the same names as we created the table. So I would create Person class, and I want to use it a different package com.imh.database.databasedemo.entity. When we talk about JPA you'll understand what an entity is. But for now, let's not really worry about it.

```java
public class Person {

	private int id;
	private String name;
	private String location;
	private Date birthDate;
	
	public Person(int id, String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
```

So now we have a Person bean which is present, and now I can use this *public List<Person> findAll()* bean in the PersonJdbcDaoclass.

```java
@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			//
			return person;
		}
	}
	
	//#1: The Bean is Person, not the method
	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", new ... );
	}
}
```

So the Person bean is now available here. I would need to know, map whatever data is coming from the query to the Person bean. When the query is executed, you get something called the result set back. From that result set, I'd need to map it into the Person, how do I do that? In Spring JDBC, there is an automatic mapper that is provided by Spring which can be used when the column names match. So this Id *private int id* located in the bean Person class maps to the Id field in the database, *private String name* maps to name, location maps to location, etc. So this is the exact mapping, so we don't really need to define a row mapper or something like that -we'll look at what is a

So the Person bean is now available in here. I would need to know, map whatever data is coming from the query to the Person bean. When the query is executed, you get something called the result set back. From that result set, I'd need to map it into the Person, how do I do that? In Spring JDBC, there is an automatic mapper that is provided by Spring which can be used when the column names match. So this Id *private int id* located in the bean Person class maps to the Id field in the database, *private String name* maps to name, location maps to location, etc. So this is the exact mapping, so we don't really need to define a row mapper or something like that -we'll look at what is a row mapper and other stuff a little later-. For now, we'd use the default row mapper which is provided by Spring *new BeanPropertyRowMapper(Person.class)*, and return this back. Let's organize the code a little bit better.

```java
public List<Person> findAll() {
	return jdbcTemplate.query("select * from person", 
				new BeanPropertyRowMapper(Person.class));
}
```

I can see that I'm getting the query form Person, and for that, I'm using the default Spring Rowmapper. In this step what we did was we created a simple repository PersonJdbcDao. We used the JDBC template to fire a query. We are using a bean property row mapper on the Person class to map the results out, and we are returning a list of Person back. In the next step, we will execute this query and see if it's working fine.

## Complete Code Example

##### /src/main/java/com/imh/spring/database/databasedemo/Person.java

```java
package com.imh.spring.database.databasedemo.entity;

import java.sql.Date;

public class Person {

	private int id;
	private String name;
	private String location;
	private Date birthDate;
	
	public Person(int id, String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
```

---

##### /src/main/java/com/imh/spring/databasedemo/jdbc/PersonJdbcDao.java

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
}
```