## Step 18 - Implementing findAll using JPQL Named Query

The only method which is not implemented in the PersonJpaRepository which is present in the PersonJdbcDao class is the *findAll()* method. So I would want to find all the persons who are present in the database.

How do I do that? With an EntityManager, you have to write something called an HQL or Hibernate Query Language. So this is not really your SQL, in SQL we would say "select * from person", but with JPA you need to write a query using something called JPQL or Java Persistence Query Language. 

So in this step, we'll use JPQL to define a simple query. Instead of directly defining the query, we'll use a concept called named query. Where we'll give the query a name and use it. The way you can execute a named query is by saying *entityManager.createNamedQuery("queryName",nameOfClassToReturn.class)*.

So we would want to give the query a name *find_all_persons* and use it here in entityManager.createNamedQuery("find_all_persons",X.class). I would give the query a name as *find_all_persons*. When we create a named query, we need to tell what kind of entity it would return, so this named query would return a Person.class. What I'll do is I'll not return it here *return entityManager.createNamedQuery("find_all_persons", Person.class);*, in the *return* instruction, so let's remove the return, unless assign the statement to a new local variable called *namedQuery*. To get the results out of the name query, what we would need to do is *namedQuery.getResultList();*, because we are expecting a list of persons as the return, and return this back. So that's what we would need to do in the method.

```java
@Repository
@Transactional
public class PersonJpaRepository {

	// connect to the database
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Person> findAll() {
		TypedQuery<Person> namedQuery = entityManager.createNamedQuery("find_all_persons", Person.class);
		return namedQuery.getResultList();
	}

}
```

Thing is, we have not really created the named query yet. So where do we define the named query? The named query we can define it on the entity for which the query maps to.

```java
@Entity
@NamedQuery (name="find_all_persons", query="select p from Person p")	//#1
public class Person {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String location;
	private Date birthDate;
}
```

So here in Person class I can define a a named query saying @NamedQuery annotation, and over here I can say the name of the query is this *name="find_all_persons"* (see #1), and the query to use is JPQL. The Java Persistance Query Language does not use database, it does not use tables from the database, It uses the entities. So what we would use here in the query is entities, so here I would want to query Person, so I would want to get all persons. Query is very simple, select all persons in here *(query="select p from Person p");*. So here *Person p* instead of the table, this *Person* is actually referring to the entity Person, which is present here in the Person class. 

Let's go to the JPA repository, organize our imports and go to the SpringJPAApplication to print the *repository.findAll()* method. Now let's see if it prints only users.

```
Console output:

[2m2020-02-28 13:31:43.003[0;39m [32m INFO[0;39m [35m10488[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringJpaApplication          [0;39m [2m:[0;39m Started SpringJpaApplication in 2.247 seconds (JVM running for 2.698)
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
[2m2020-02-28 13:31:43.050[0;39m [32m INFO[0;39m [35m10488[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$96377653[0;39m [2m:[0;39m User id 10001 -> Person [id=10001, name=Ranga, location=Hyderabad, birthDate=2020-02-28 00:00:00.0]
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
Hibernate: call next value for hibernate_sequence
Hibernate: insert into person (birth_date, location, name, id) values (?, ?, ?, ?)
[2m2020-02-28 13:31:43.060[0;39m [32m INFO[0;39m [35m10488[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$96377653[0;39m [2m:[0;39m Inserting 10004 -> Person [id=1, name=Tara, location=Berlin, birthDate=Fri Feb 28 13:31:43 CET 2020]
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
Hibernate: update person set birth_date=?, location=?, name=? where id=?
[2m2020-02-28 13:31:43.064[0;39m [32m INFO[0;39m [35m10488[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$96377653[0;39m [2m:[0;39m Update 10003 -> Person [id=10003, name=Pieter, location=Utrecht, birthDate=Fri Feb 28 13:31:43 CET 2020]
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
Hibernate: delete from person where id=?
Hibernate: select person0_.id as id1_0_, person0_.birth_date as birth_da2_0_, person0_.location as location3_0_, person0_.name as name4_0_ from person person0_
[2m2020-02-28 13:31:43.080[0;39m [32m INFO[0;39m [35m10488[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$96377653[0;39m [2m:[0;39m All users -> [Person [id=1, name=Tara, location=Berlin, birthDate=2020-02-28 13:31:43.05], Person [id=10001, name=Ranga, location=Hyderabad, birthDate=2020-02-28 00:00:00.0], Person [id=10003, name=Pieter, location=Utrecht, birthDate=2020-02-28 13:31:43.061]]
```

Since we are calling findAll at the end, it's printing the latest data which is present in the table. So id 1 Tara, 10001 Ranga, 10003 Peter. Those are the rows from the database that are being printed. We are using a named query to get the values from the database. We use JPQL to write the query. 

In the last few steps, with very little effort we were able to migrate all our Dao stuff which was written by using JDBC or Spring JDBC to our person JPA repository. So whatever we were doing in the Dao, now we are doing in the JPA repository as well. 

## Complete Code Example

##### /src/main/java/com/imh/spring/database/databasedemo/SpringJpaApplication.java

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
import com.imh.spring.database.databasedemo.jpa.PersonJpaRepository;

@SpringBootApplication
public class SpringJpaApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJpaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
				
		logger.info("User id 10001 -> {}", repository.findById(10001));
		
		logger.info("Inserting 10004 -> {}", 
				repository.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				repository.update(new Person(10003, "Pieter", "Utrecht", new Date())));
		
		repository.deleteById(10002);
		
//		logger.info("All users -> {}", repository.findAll());	//Original
		logger.info("All users -> {}", repository.findAll().toString());	//Improved
	}

}
```

---

##### /src/main/java/com/imh/spring/database/databasedemo/jpa/PersonJpaRepository.java

```java
package com.imh.spring.database.databasedemo.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.imh.spring.database.databasedemo.entity.Person;

@Repository
@Transactional
public class PersonJpaRepository {

	// connect to the database
	@PersistenceContext
	EntityManager entityManager;

	public Person findById(int id) {
		return entityManager.find(Person.class, id);// JPA
	}
	
	public Person update(Person person) {
		return entityManager.merge(person);
	}
	
	public Person insert(Person person) {
		return entityManager.merge(person);
	}
	
	public void deleteById(int id) {
		Person person = findById(id);
		entityManager.remove(person);
	}
	
	public List<Person> findAll() {
		TypedQuery<Person> namedQuery = entityManager.createNamedQuery("find_all_persons", Person.class);
		return namedQuery.getResultList();
	}

}
```

---

##### /src/main/java/com/imh/spring/database/databasedemo/entity/Person.java

```java
package com.imh.spring.database.databasedemo.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery (name="find_all_persons", query="select p from Person p")
public class Person {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String location;
	private Date birthDate;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(int id, String name, String location, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
	
	//Necessary to use JPA-Hibernate
	public Person(String name, String location, Date birthDate) {
		super();
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
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", location=" + location + ", birthDate=" + birthDate + "]";
	}
}
```