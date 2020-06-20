## Step 20 - Connecting to Other Databases

Until now, we have been using an in-memory database. One of the things is you might want to connect to a MySQL database, a SQL server database, or an Oracle database. The detailed instructions of how you can do that for that are present in the home page of this specific section.

So if you go into the folder of this specific section, there is documentation on how to connect to MySQL and other databases. Spring Boot really makes it very easy to connect to other databases, so you can switch from one database to another just by defining a couple of properties in src/main/resources/application.properties, and switching a couple of dependencies. That's it, that's all you would need to do to switch the database.

```xml
<dependency>
	<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

So if you want to connect to other databases you have to remove H2 out of the pom.xml and add in the specific driver for your database. So for example, for MySQL, the dependency that needs to be added is in groupId *mysql*, and artefactId *mysql-connector-java*.

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

Once you do that, you can go in and configure the Spring DataSource URL. So in the /source/main/resources/application.properties, you can configure the data source URL for your thing, and also the user name, and the password to connect to your data source.

```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/person_example
spring.datasource.username=personuser
spring.datasource.password=YOUR_PASSWORD
```

By default, over here *spring.jpa.hibernate.ddl-auto=none* we are setting the hibernate ddl auto to *none*. What this does, is it does not make any changes to your database tables. If you would want hibernate to directly create the schema for you without you worrying about creating it yourself, then you can use the value of *create*. So over here *spring.jpa.hibernate.ddl-auto=none* instead of none, if you put *create* then hibernate would look at your entities and would automatically create the schema for you.

However, if you want to create the data and the Schema manually, then the scripts are present in here. So this is the schema that you would need for this example. 

```
Table

create table person
(
	id integer not null,
	birth_date timestamp,
	location varchar(255),
	name varchar(255),
	primary key (id)
);

Data

INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10003,  'Pieter', 'Amsterdam',sysdate())
```

This is the data that you would need for this example, and the MySQL scripts are present in here as well. So MySQL, how can you install? How can you start the server ? all the documentation is present here, as well as how you can create a database and a user as well.

```
* Install MySQL https://dev.mysql.com/doc/en/installing.html
	More details - http://www.mysqltutorial.org/install-mysql/
        Trouble Shooting - https://dev.mysql.com/doc/refman/en/problems.html
* Startup the Server (as a service)
* Go to command prompt (or terminal)
        Execute following commands to create a database and a user:

		mysql --user=user_name --password db_name
		create database person_example;
		create user 'personuser'@'localhost' identified by 'YOUR_PASSWORD';
		grant all on person_example.* to 'personuser'@'localhost';
		
* Execute following sql queries if you would want to create the table and insert the data manually (not with hibernate) 

		Table

		create table person
		(
			id integer not null,
			birth_date timestamp,
			location varchar(255),
			name varchar(255),
			primary key (id)
		);

		Data

		INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
		INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10002,  'James', 'New York',sysdate());
		INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) VALUES(10003,  'Pieter', 'Amsterdam',sysdate())
```

So all the documentation for MySQL is in here. All that you need to do is install that, change the dependency, change the properties, restart your app and then you'll be connecting to your MySQL database. The most important thing for you to understand is the fact that this is a very easy thing to do. Any application can be switched from an embedded database to a real database in a very small amount of time.