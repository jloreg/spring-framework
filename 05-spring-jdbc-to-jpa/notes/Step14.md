## Step 14 - Defining Person Entity

In this step, we would start with defining our first JPA Entity. We would want to make this Person class an Entity. Something which maps these beans or this class to a table. In a pom.xml, we already add in the *starter-data-jpa* dependency. So because of that, you get a JPA API. This JPA API if you look at it *hibernate-jpa-X.X.X.Final.jar*, defines a lot of different annotations. One of the annotations which is present in here is @Entity, this is one of the annotations which you'll be making use of in this specific step. The other annotation which is also defined in here are things like @Column. You have an annotation called @Table and lot of such annotations. So this the API and who implements this API? Who implements the JPA API? The Hibernate code. Hibernate is the implementation of that API. So all the code that you would have in here would be a lot of code, it will give functionality to whatever is defined in the APIs. 

So, let's get started with defining our Entity. How do I define an Entity? The easiest way is to just say @Entity and import javax.persistence.Entity package. So this *@Entity public class Person* now maps Person class. One of the things is JPA provides defaults. This Person class maps to a table called person (person table in lowercase). So even though there's an annotation called @Table, and you can put again person in here as the name of the table @Table(name="person"). So you can use @Table(name="person") to say this is a table that it maps to Person class, but I don't really need to do because the table name matches with the name of the class.

```java
package com.imh.spring.database.databasedemo.entity;

@Entity
//@Table(name="person")	//Not necessary
public class Person {

}
```

Other things which you can define in here are column mappings as well so you can put an annotation saying @Column, the name of the column, you can put it in here and say @Column(name="name"). Over here *private String name* the names match, so I don't really need a column as well. 
    
```java
package com.imh.spring.database.databasedemo.entity;

@Entity
public class Person {

	private int id;
//	@Column(name="name").	//Not necessary
	private String name;
	private String location;
	private Date birthDate;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
}
```

The only thing that I would need to define on this specific thing is something called @Id *private int id;*. It indicates this is a primary key. One of the things I would want to do is, I would want hibernate to take control of generating the Id for me. I don't want to manually set the Id. Whenever I need to insert a new row, I would want hibernate to be able to create the Id for me. How can I do that ? I can do that by adding something called @GeneratedValue. I would import generated value annotation in, this would make sure that based on the database, appropriate mechanism is used to populate the Id. Typically, what hibernate does is it creates a sequence in the database and it uses this sequence to populate the value for this specific Id. 

```java
package com.imh.spring.database.databasedemo.entity;

@Entity
public class Person {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private String location;
	private Date birthDate;
	
	public Person() {	//#1
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Necessary for use JPA-Hibernate		#2
	public Person(String name, String location, Date birthDate) {
		super();
		this.name = name;
		this.location = location;
		this.birthDate = birthDate;
	}
}
```

So, now we have defined an Entity called Person. On the Id column, we've made it a primary key by putting and @Id, and with @GeneratedValue we said, "Hibernate! You generate the whole thing for us".The other important thing that you would need to always remember is you need to have a constructor. So you need to have a no-argument constructor (see #1).

You have another constructor with the Id, name, location, and birthDate, **but with Hibernate I don't want to create the Id. So I'll create another constructor where I don't pass the *int id* at all**. So what we are doing is creating a constructor with just three arguments (see #2). This would allow us to create a Person and we'll allow hibernate to assign a value to that. That's it!

That's all you would need to define an entity with hibernate. In the next step, let' see how to create a repository to maintain this Entity.

---

**Important**. If you have problems running the SpringDatabaseAplicattion with JPA-Hibernate annotations, you would need to do the next steps. 

1. On the project, go to properties > Maven > uncheck the option "Resolve dependencies from Workspace projects" and erase the name "pom.xml" from the textbox "Active Maven Profiles". Press Apply and Close button.
2. On the project, go to Run as > Maven clean, and later Restart Eclipse.
3. Every time that you launch SpringDatatabaseApplication, Spring Boot try to create another H2 in-memory database; only it's necessary when we run the application the first time. Comment these lines in the /src/main/resources/data.sql.

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

INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate()); 
```
4. Launch SringDatabaseApplicattion with SpringBoot and review the console output in search of exceptions. 
5. If all run right, repeat the step number one, unless this time you must check the option "Resolve dependencies from Workspace projects" and type "pom.xml" inside the textbox "Active Maven Profiles". Press Apply and Close button.


```
Console output:

[2m2020-02-26 13:07:30.306[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 2.574 seconds (JVM running for 3.106)
[2m2020-02-26 13:07:30.314[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m All users -> [com.imh.spring.database.databasedemo.entity.Person@daa2eef, com.imh.spring.database.databasedemo.entity.Person@63279311, com.imh.spring.database.databasedemo.entity.Person@7ef16f4]
[2m2020-02-26 13:07:30.321[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m User id 10001 -> com.imh.spring.database.databasedemo.entity.Person@481a48a4
[2m2020-02-26 13:07:30.323[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m Deleting 10002 -> No of Rows Deleted - 1
[2m2020-02-26 13:07:30.324[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m Inserting 10004 -> 1
[2m2020-02-26 13:07:30.325[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m Update 10003 -> 1
[2m2020-02-26 13:07:30.325[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$3f9d61fa[0;39m [2m:[0;39m All users -> [com.imh.spring.database.databasedemo.entity.Person@2fb2428b, com.imh.spring.database.databasedemo.entity.Person@db80db5, com.imh.spring.database.databasedemo.entity.Person@769caca6]
[2m2020-02-26 13:07:54.327[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.a.c.c.C.[Tomcat].[localhost].[/]      [0;39m [2m:[0;39m Initializing Spring DispatcherServlet 'dispatcherServlet'
[2m2020-02-26 13:07:54.327[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Initializing Servlet 'dispatcherServlet'
[2m2020-02-26 13:07:54.332[0;39m [32m INFO[0;39m [35m10749[0;39m [2m---[0;39m [2m[nio-8080-exec-1][0;39m [36mo.s.web.servlet.DispatcherServlet       [0;39m [2m:[0;39m Completed initialization in 5 ms
```

```
Database output on http://localhost:8080/h2-console/:

SELECT * FROM PERSON;
ID  	BIRTH_DATE  	LOCATION  	NAME
10001	2020-02-26 00:00:00	Hyderabad	Ranga
10003	2020-02-26 13:07:30.324	Utrecht	Pieter
10004	2020-02-26 13:07:30.323	Berlin	Tara
(3 rows, 4 ms)

```

## Complete Code Example

##### /src/main/resources/data.sql

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

INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate());
```

##### /src/main/java/com/imh/spring/database/databasedemo/entity/Person.java

```java
package com.imh.spring.database.databasedemo.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
}
```