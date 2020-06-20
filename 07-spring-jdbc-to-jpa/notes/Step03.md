## Step 03 - Creating a Database Table in H2

In this step, we'll try and create a simple database table and we´ll see if we can see the data in H2 web console. We've already added in JDBC, JPA, and also H2 as dependencies in our pom.xml. 

In Eclipse we get a feature through auto-configuration called Wizard. That's basically what you can define in the /src/main/resources, you can define a SQL file through the Eclipse Wizard. So I go to New > Other... > and I create a SQL file with the name data.sql.

If you create a data.sql file in the src/main/resources, when you launch up the application, this file gets called. All the data in the data.sql is used to initialize the database, so we'll use that feature. This is one of the auto-configuration features that is provided in Spring Boot. So we'll make use of that to add some data to our database. So we'll first create a table and then add some data to the database.

What we want to do, is we want to create a 'Person' table with a few columns. I would want to create a column called 'id', and I would want to give a 'name' to the person. I would wan to give him a 'location', so a 'name', a 'location', and also, let's say I would want to track his 'birth_date'. Let's say this is the table I would  want to be able to create. A 'Person' table with all these columns present.

```
person
(
id
name
location
birth_date
)
```

The table in SQL language.

 ```sql
create table person
(
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);
``

Be very careful with this syntax. Everything here should be exactly what exactly as what I have typed in here. So you need to have an open bracket '(', a close bracket ')', a semi-colon ';', and all the columns separated by commas. There should not be any comma after the *primary key(id)* instruction. So make sure that you are exactly typing it is in here.

 Let's now stop the application and start it again. Later go to the H2 [web console](http://localhost:8080/h2-console/), and you would see the table PERSON. If you'll do a query 'SELECT * FROM PERSON', you would see that there is no data which is present in here.

In this small step, we have created a table in the database with a few columns which are present in there. In the next step, we would populate some data into it.

## Complete Code Example

##### /src/main/resources/data.sql

```java
create table person
(
   id integer not null,
   name varchar(255) not null,
   location varchar(255),
   birth_date timestamp,
   primary key(id)
);
```