## Step 16 - Implementing insert and update JPA Repository Methods

In the previous step, we implemented the findById method. Now let's go ahead and implement methods to be able to update and delete a person. The method which we want to define to update a Person is *public Person update(Person person)*. What we want to do in the update method is return an *entityManager.merge(person);*

```java
@Repository
@Transactional
public class PersonJpaRepository {

	// connect to the database
	@PersistenceContext
	EntityManager entityManager;

	public Person findById(int id) {
		return entityManager.find(Person.class, id);// JPA
	}
									//#1
	public Person update(Person person) {
		return entityManager.merge(person);
	}
									//#1
	public Person insert(Person person) {
		return entityManager.merge(person);
	}

}
```

So whether you want to update or insert, you need to call the merge method. What that merge method does is it knows whether the Id is set inside the **person table **or not. There's an Id in the person which is already set, then it will try and update that person. If there is no Id set, then it would insert it in,  as simple as that. 

So let's add another method. Let's call that *public Person insert(Person person)*. Actually, you don't really need two methods because the logic here is really the same. There is no difference between an update and then insert as far as an EntityManager is concerned. We have update and insert methods present in here (see #1).

Let's go to our SpringJpaApplication class. What we want to do now is bring the insert and update into the picture. Let's organise our imports, and do an insert and an update. So let's see if that gets fired properly. Let's run the application again.

```java
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

		//#2. With Hibernate, we don't pass the Id as parameter
		logger.info("Inserting 10004 -> {}", 
				repository.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				repository.update(new Person(10003, "Pieter", "Utrecht", new Date())));

	}

}
```

```
Console output:

[2m2020-02-28 10:20:50.315[0;39m [32m INFO[0;39m [35m4705[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringJpaApplication          [0;39m [2m:[0;39m Started SpringJpaApplication in 2.222 seconds (JVM running for 2.828)
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
[2m2020-02-28 10:20:50.369[0;39m [32m INFO[0;39m [35m4705[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3b590539[0;39m [2m:[0;39m User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@6e1fcc24
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
Hibernate: call next value for hibernate_sequence
Hibernate: insert into person (birth_date, location, name, id) values (?, ?, ?, ?)
[2m2020-02-28 10:20:50.381[0;39m [32m INFO[0;39m [35m4705[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3b590539[0;39m [2m:[0;39m Inserting 10004 -> com.imh.spring.database.databasedemo.entity.Person@378f87c6
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
Hibernate: update person set birth_date=?, location=?, name=? where id=?
[2m2020-02-28 10:20:50.385[0;39m [32m INFO[0;39m [35m4705[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3b590539[0;39m [2m:[0;39m Update 10003 -> com.imh.spring.database.databasedemo.entity.Person@5baf63fb
```

You can see the queries getting written down here as well. So you see a select being written, after the select, what we are doing ? we're doing an insert into person. You can see that Hibernate is calculating the value for us. So Hibernate is really taking the person Id and it's calculating the value for us in here *Hibernate: insert into person (birth_date, location, name, id) values (?, ?, ?, ?)*, it's inserting a new Person in. After that what is happening here *Hibernate: update person set birth_date=?, location=?, name=? where id=?* is the update. So update person is happening for that specific Id. So here Hibernate *Hibernate: call next value for hibernate_sequence* is using a sequence before inserting it in. 

So now let's go to the *localhost:8080/h2-console* to see the data. Let's refresh so we can get a new connection to it. Let's do a SELECT * FROM PERSON.

```
Database output:

SELECT * FROM PERSON;
ID  	BIRTH_DATE  	LOCATION  	NAME
1	2020-02-28 10:20:50.369	Berlin	Tara
10001	2020-02-28 00:00:00	Hyderabad	Ranga
10002	2020-02-28 00:00:00	New York	James
10003	2020-02-28 10:20:50.381	Utrecht	Pieter
(4 rows, 4 ms)
```

You would see that Hibernate assigns an Id of 1 to the insert. So what ever we have inserted in, Hibernate assigns a value of 1  to it. The best way to do the insert is by not even passing a id in (see #2 above on SpringJpaApplication). So we don't assign the Id, we would let hibernate assing the Id, because Hibernate would ignore what ever Id I'm passsing. This is the best way to do that, I'm not even passing in the Id and I would let hibernate dictate the Id for me. Now when you run the application again you would see that Tara would still have the Id of one, that's the Id which is being assigned to it by Hibernate. Let's refresh the localhost:8080/h2-console.

```
Database output:

SELECT * FROM PERSON;
ID  	BIRTH_DATE  	LOCATION  	NAME
1	2020-02-28 10:20:50.369	Berlin	Tara
10001	2020-02-28 00:00:00	Hyderabad	Ranga
10002	2020-02-28 00:00:00	New York	James
10003	2020-02-28 10:20:50.381	Utrecht	Pieter
(4 rows, 4 ms)
```

You can see that Tara is having Id one. If you insert further rows into that table, you would see that you would get Ids two, three, four, five and so on.
Hibernate would directly assign the Ids based on a sequence it creates. So here in *localhost:8080/h2-console*, if you see there is a tab called *Sequences* which is being called Hibernate sequence. It will use the sequence to assign the Ids to different things.

In this step what we did is we defined a method to be able to insert and update a person. 

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
		
		/*When the next methods are implemented, uncomment this
		logger.info("All users -> {}", repository.findAll());
		
		logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
				repository.deleteById(10002));
		
		logger.info("All users -> {}", repository.findAll());
		*/
	}

}
```

---

##### /src/main/java/com/imh/spring/database/databasedemo/jdbc/PersonJpaRepository.java

```java
package com.imh.spring.database.databasedemo.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
```