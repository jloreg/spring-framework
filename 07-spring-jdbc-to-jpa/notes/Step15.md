## Step 15 - Implementing findById JPA Repository Method

In the previous step, we have defined an entity called Person, and in this step, we'll create a new PersonJpaRepository class in a package com.imh.spring.database.databasedemo.jpa to manage the Entity. It's called a Repository. Let's get started.

One of the things I would need to say is this PersonJpaRepository class, is a Repository, that's the number one. One of the other things that you would need to also say is we would do TransactionManagement in here. So whenever we insert rows and delete whenever we do an update to the database, transactions become very important. When you do three or four steps, or three or four updates in a single transaction you would want all of them to be successful, or all of them to fail together. For now, we would implement transactions at the level of a Repository. Ideally, transactions have to be implemented around your business services, but for this example, we'll restrict our view to the repository. So we'll need to say this is the repository. We would need to have transactions around this. 

The other thing is we need to have some way to connect to the database. How do we connect to the database? That's the other thing that we would need to finalize. If you remember, in Spring Jdbc we used a Jdbc template, Jdbc template was autowired with a data source connection, and we used that to fire our queries. So what do we use in here in the JPA repository? That's another thing which we need to work on. 

After that, the typical thing that we would want to do is to create our method to do the retrieval of the data. What we'll do is we'll start with findById method, so I'll copy the one from our PersonJdbcDao. So here in *public Person findById (int id)* I would want to return the Person for that specific Id. 

```java
//Repository
//TransactionManager
public class PersonJpaRepository {
	
	//connect to the database
		
	public Person findById(int id) {
		return; //JPA
	}

}
```
However, here I didn't want to use Jdbc. I'd want to get it by using JPA. Let's get started now. The way you can say this is a Repository is by @Repository annotation, and the way you can define transactions here is just by saying @Transactional. Now I would want to be able to connect to the database for that, there is something called an entity manager, EntityManager manages entities. All the operations that you are performing in a specific session, are all stored inside your EntityManager. All the operations are not really stored in the EntityManager but in something called @PersistenceContext. EntityManager is the interface to the PersistenceContext. All operations have to be going through the EntityManager, let's import the EntityManager and the PersistenceContext. 

Now I want to do a findById. How do I do that? There are a number of find methods inside the EntityManager. The one which we will be using is *EntityManager.find(Person.class, id);*, the first parameter is a Person entity, and what is the primary key? the second parameter id is the primary key, that's it. You are now ready to get rocking and rolling.

```java
@Repository
@TransactionManager
public class PersonJpaRepository {
	
	//connect to the database
	@PersistenceContext
	EntityManager entityManager;
	
	public Person findById(int id) {
		return entityManager.find(Person.class, id);	//JPA
	}

}
```

One of the things I will do right now is I'll do a copy of SpringDatabaseApplication and rename it to SpringJpaApplication, and later I'll rename SpringDatabaseApplication to SpringJdbcApplication. This would help us have a clear separation between the application that uses Jdbc and the application that uses JPA. One of the things that Spring Boot do is automatic components scan on this package com.imh.spring.database.databasedemo as well. So SpringJpaApplication and SpringJdbcApplication will also get triggered, and I don' t want that to happen, so I'll comment the annotation *@SpringBootApplication* on SpringJdbcApplication to make sure that the Jdbc code does not get fired.

Now I'll go to the SpringJpaApplication class and now we want to do the same operations than on SpringJdbcApplication. However, what we want to use is repository. Which repository? Person JPA repository.

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
	}

}
```

So we have enough. A simple *repository.findById(10001))* method which is running using JPA. If I run the SpringJpaApplication, the Person object is being printed here.

```
Console output:

2020-02-27 11:06:23.123  INFO 6965 --- [  restartedMain] c.i.s.d.d.SpringJpaApplication           : Started SpringJpaApplication in 3.978 seconds (JVM running for 4.386)
2020-02-27 11:06:23.203  INFO 6965 --- [  restartedMain] ication$$EnhancerBySpringCGLIB$$6d396808 : User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@3d9cfe96
```

Now the Person object is being printed here. So I see person Id 10001, but I'm not able to see the query which is being executed. I would rather want to be able to see the query, so that I'm sure the query which went to the database is right; you must go to src/main/resources/data.sql and verify that there is a row in data.sql with the values *VALUES(10001,  'Ranga', 'Hyderabad',sysdate());*. How do I do that? I can do that updating /src/main/resources/application.properties to spring.jpa.show-sql=true. Let's fire the SpringJpaApplication again and see the results.

```
Console output:
Hibernate: drop table person if exists
Hibernate: drop sequence if exists hibernate_sequence
Hibernate: create sequence hibernate_sequence start with 1 increment by 1
Hibernate: create table person (id integer not null, birth_date timestamp, location varchar(255), name varchar(255), primary key (id))
2020-02-27 11:16:35.820  INFO 6965 --- [  restartedMain] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2020-02-27 11:16:35.820  INFO 6965 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2020-02-27 11:16:35.824  INFO 6965 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2020-02-27 11:16:35.867  WARN 6965 --- [  restartedMain] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2020-02-27 11:16:35.904  INFO 6965 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-02-27 11:16:35.999  INFO 6965 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-02-27 11:16:36.000  INFO 6965 --- [  restartedMain] c.i.s.d.d.SpringJpaApplication           : Started SpringJpaApplication in 0.701 seconds (JVM running for 617.263)
Hibernate: select person0_.id as id1_0_0_, person0_.birth_date as birth_da2_0_0_, person0_.location as location3_0_0_, person0_.name as name4_0_0_ from person person0_ where person0_.id=?
2020-02-27 11:16:36.007  INFO 6965 --- [  restartedMain] ication$$EnhancerBySpringCGLIB$$6d396808 : User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@32fb80fe
```

Now you'd be able to see the table definition in here as well. You can see that Hibernate is creating the table definition for us. This is triggered by SpringBoot AutoConfiguration. The other thing you can also see in here, is the query is getting executed in here. You can see that there's a *select from person*. Person with an ID is being executed in here.

In this step, we implemented a simple method to retrieve a Person by Id and also we enable spring.jpa.show-sql=true for JPA. Other than that, we had to actually remove the definition of the person table from our data.sql, because this is directly initialized by schema update of Hiberante which is triggered by Spring Boot Auto Configuration.

---

**Important.** If you run the SpringJpaAplication and gets the exception orh.h2.jdbc.JdbcSQLException: Table "PERSON" already exists, is due to interference between the Spring Boot AutoConfiguration and Hibernate functionality.

So, where are we creating the table Person ? we are creating in data SQL, right? So in /src/main/resources/data.sql we are creating the table in here.

```sql
create table person
( 
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);
```
One of the magic of Spring Boot AutoConfiguration is the fact that it knows that we are using in-memory database, and it knows that JPA is in the classpath. It knows that I'm defining entities as well because I put the @Entity in Person class, **so what it does is it triggers something called a schema update which is one of the hibernate features**. So what it does is, when we are using an in-memory database, it automatically creates this schema for us with the annotations (@Id and @GeneratedValue in Person class). So from now on, you don't really need to define the table. So I don't need to create the table now on, because the table would be created for me by ... who created the table for me? **The table would be created for me by the schema update. Schema update is triggered by Spring Boot AutoConfiguration and it's one of the features in Hibernate**.

So whenever we're connecting to an embedded database, schema gets automatically created. All that you need to do is insert the data in. Let's kill the application and run it again.

```sql
/* 
create table person
(
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);
*/
```

## Complete Code Example

##### /src/main/resources/application.properties
```
spring.h2.console.enabled=true
spring.jpa.show-sql=true
#logging.level.root=INFO
```

##### /src/main/resources/data.sql
```
/* Only comment the create table instruction
create table person
(
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);
*/

INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate());

```

##### /src/main/java/com/imh/spring/database/databasedemo/SpringJdbcApplication.java

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

//@SpringBootApplication
public class SpringJdbcApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJdbcDao dao;

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcApplication.class, args);
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

##### /src/main/java/com/imh/spring/database/databasedemo/SpringJpaApplication.java

```java
package com.imh.spring.database.databasedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		
		/* When the next methods are implemented, uncomment this
		logger.info("All users -> {}", repository.findAll());
		
		logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
				repository.deleteById(10002));
		
		logger.info("Inserting 10004 -> {}", 
				repository.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				repository.update(new Person(10003, "Pieter", "Utrecht", new Date())));
		
		logger.info("All users -> {}", repository.findAll());
		*/
	}

}

```

##### /src/main/java/com/imh/spring/database/databasedemo/jpa/PersonJpaRepository.java

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

}
```