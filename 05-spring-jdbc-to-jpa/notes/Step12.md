## Step 12 - Creating a custom Spring JDBC RowMapper
 
 Until now, we were using a RowMapper called *new BeanPropertyRowMapper(Person.class));*. So in all the steps, we made us of this RowMapper to map the results of the query to the Person bean. However, the thing is you can't even define your own custom RowMapper. So if the data which is coming back from the table int the method *public List<Person> findAll()* is of a different structure compared to your bean *new BeanPropertyRowMapper <Person>(Person.class)*, for example, if the column names don't really match. So if the column name does not match with the field name in Person. So in those kinds of situations, you would want to create your own RowMapper. Let's define a simple RowMapper for this Person class.

```java
public List<Person> findAll() {
	return jdbcTemplate.query("select * from person", 
			new BeanPropertyRowMapper(Person.class));
}
```

The way you can define a RowMapper is by implementing a simple interface in the PersonJdbcDao class which is called  *RowMapper <Person>*. So I'll say RowMapper of which bean you would want to map the values to. Now, I can define a PersonRowMapper that implements RowMapper *class PersonRowMapper implements RowMapper<Person>.* **I'm creating an inner class here because I would want this to be used only inside the PersonJdbcDao**. I would import the RowMapper interface of *springframework.Jdbc* because that's the one which we are looking for, and add unimplemented methods in here.

```java
@Repository
public class PersonJdbcDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			//Todo: add necessary instructions to fill the Person bean with the right values.
			return person;
		}
		
	}
}
```

The code above is the specific thing we would need to implement. So what I can do here is to return a Person back. What the RowMapperInterface defines is how should you re-map one specific row. One row in the *ResultSet rs*, how do you want to map it to the *Person person = new Person();* ?. The number of the row is available but most of the time you don't really make use of the row number *int rowNum*. All that we do is, get the value from the ResultSet *rs.getInt(columnIndex)*, and then we can say the name of the column. So I can say *rs.getInt("id")*. Where do I want to take that value and put it in? *person.setId(rs.getInt("id"))*, similar to this we can map all the other columns manually. 

Be careful with the type of values that you would want to retrieve from the database, in Person class was defined as *private Date birthDate;*, however on the H2 database, we defined the mapped attribute as *birth_date timestamp*. So, when you retrieve values from the database, you would need to use the right type, in this case, must be *person.setBirthDate(rs.getTimestamp("birth_date"));*

```java
class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getTimestamp("birth_date"));
			return person;
		}
		
	}
```

That's it. You have your person RowMapper ready and over here now *public List<Person> findAll()* instead of using this *new BeanPropertyRowMapper(Person.class));*, what I can start doing is use PersonRowMapper. So for a Person object, it does not make a huge difference because the exact names match, so id is id, name is name, location is location, birthDate is birth_date. The framework is able to understand the fact that in Java we use *person.setBirthDate* with the Type Date, and in the Person table we use *birth_date* with the type timestamp.

```java
	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", 
				//new BeanPropertyRowMapper(Person.class));
				new PersonRowMapper());
	}
```

So in this kind of scenario, Person kind of scenario, you don't really need a RowMapper, **but a lot of times, your table definitions will be different from how your beans are defined. In those kinds of situations, you'd need to define RowMappers**. The advantage RowMapper provides is now I can use this PersonRowMapper everywhere. So where ever I would need to map this table or this kind of rows, I can use the RowMapper in all those kind of situations. So this specific logic which we are writing in here, we get a little bit of reusability around that by creating a RowMapper.

If you go ahead and run you would see that there is no change in functionality as such. So you'd see that *public List<Person> findAll()* would still be able to get all the rows from the database and it would print it to the log. You would see that nothing has changed in that stuff.

In this step, we looked at the last important concept in JDBC, and that is a RowMapper.

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
		
		logger.info("All users -> {}", dao.findAll());
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
	
	class PersonRowMapper implements RowMapper<Person>{
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person person = new Person();
			person.setId(rs.getInt("id"));
			person.setName(rs.getString("name"));
			person.setLocation(rs.getString("location"));
			person.setBirthDate(rs.getTimestamp("birth_date"));
			return person;
		}
		
	}

	public List<Person> findAll() {
		return jdbcTemplate.query("select * from person", 
//				new BeanPropertyRowMapper(Person.class));
				new PersonRowMapper());
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