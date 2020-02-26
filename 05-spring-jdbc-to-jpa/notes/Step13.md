## Step 13 - Quick introduction to JPA 

In the previous steps, we talked about Spring Jdbc, we saw how Spring Jdbc is more advantageous than Jdbc, and we understood how to do basic operations using Spring Jdbc. What we did was we implemented a PersonJdbcDao, where we implemented the findAll, findById, deleteById, insert and update methods.

Let's switch our attention to JPA. JPA stands for Java persistence API. The way Jdbc works, whether it's Spring Jdbc or the normal Jdbc, is your write a query first. That's what you would do in anything that you would do with JDBC. Right?
You write a query and map the values to it, **this is called mapping the values**. To execute the queries you need a set of parameters, so you would pass them using mapping.

And also what we would do **on the reserve side is we would actually do something called row mapping**. So once I get the results from the database, I would need to map them back to the beans. So I'm mapping it back to the Person bean in here. **The complexity with Jdbc or Spring Jdbc is the fact that writing these queries is not a simple task**. In the examples which we have these are really simples queries, but when we talk about really big applications they would have hundreds and hundreds of tables. A couple of applications I have worked with have about 300 tables, and the other one had about 500 tables. I mean these are huge databases, and what would happen in those circumstances, is your query starts becoming complex and the amount of skills that you would need to maintain those queries become multifold. 

From a Java expert expecting that kind of knowledge is very difficult. So then what would happen is these queries are written by database experts and that would make it very difficult to maintain the application. That's where JPA comes in. JPA says why do you map a query and try and map the values and get the data back? Why don't you map the entities? So why don't you map directly an object to a row in the table? What we will do with JPA is we would define something called an Entity. We will define the relationships between entities as well. So we'll define an Entity called Person and we would map it to a row in the table called Person. We will map the fields from here on the Person class to the columns in the table.

```java
package com.imh.spring.database.databasedemo.entity;

public class Person {

	private int id;
	private String name;
	private String location;
	private Date birthDate;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
}
```

So, you can say this Person maps to a table Person. This *private Date birthDate;* here maps to a field called *birth_date timestamp,*. And you can also define relationships, if a Person can have an address and if let's say a Person can have multiple addresses, then you can define the relationship between the Person and the addresses as well. Once you define the entities and the relationships, the JPA implementation would take care of identifying the entities and creating the right queries for you, based on the operations you would want to perform.

So the job of writing the queries shifts from you, from the developer to a framework which is called JPA implementation. JPA is like an interface, it defines a set of annotation, and a set of interfaces. Hibernate is the most implementations of JPA. So you can create the JPA as an interface and hibernate as the class. So hibernate implements JPA. 
 
To make a long story short Hibernate was actually present even before JPA, Hibernate was the most popular ORM framework in the last decade, and JPA came in after seeing the success of Hibernate. After seeing the success of hibernate, JAVA EE said, "Ok! Why don't we have a standard of how to do ORM?", and that's where JPA came in. JPA is the standard of doing object-relational mapping (ORM). What does that mean? 
 
Object is an instance of this Person class. So I'm mapping an Object to a relation. Relation is a table. So I'm mapping an Object to a Relation. I'm mapping this object defined on Person class, to a something called table or relation. So this is what it's called object-relational mapping. JPA kinds of standardizes the ORM, and hibernate implements JPA. Actually hibernate has a lot more features than JPA itself, but what we'll do here is to restrict ourselves to JPA because once you use JPA then that means you have the option of switching away to a different JPA implementation at the later point in time. You will not just be restricted to hibernate, but if some popular ORM framework comes in the future then you can easily shift to that. 
 
In this short step, what we were doing was to get a high-level big picture of JPA. 
In the next step, we would start by creating a parallel JPA thing for the PersonJdbcDao.