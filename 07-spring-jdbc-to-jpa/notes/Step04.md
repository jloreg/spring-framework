## Step 04 - Populate data into Person Table

In the last step, we created a database table. Now we want to populate some data into that table.

```sql
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
```

One of the options is I can keep running this query at the start of the application to populate some data. How do we automate it? we already use a specific option to create the table, why don't we use the same thing to populate some data in as well ?. Let's do that right now, so what I'll do is, in the data.sql, I can create some more queries.

So I'll not just insert one person, I will initiate the database inserting three persons. Make sure that you are having different IDs, and you are using a big 'Id' value; **at a later point in time you will use JPA and will auto-generate these IDs and we don't want there to be a conflict**. 'ID' is the primary key. So you need to have different values for the ID.

```sql
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate());
```

If you launch up the Spring Database Application and H2 console again, you'll see the Person table and three rows in it; to see the rows you must do a query like *SELECT * FROM PERSON*.

So this is kind of initial data that we'd be using for our JDBC section. So now we have a table and some initial data to play with. What we'll do is we will start using that data to query and put information out.

In the next few steps, you will really start by playing around with JDBC. In the last few steps, what we have done is we initialized our database, we created a table, we created some data into our table, and now we would start playing with JDBC.

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

INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10001,  'Ranga', 'Hyderabad',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10002,  'James', 'New York',sysdate());
INSERT INTO PERSON (ID, NAME, LOCATION, BIRTH_DATE ) 
VALUES(10003,  'Pieter', 'Amsterdam',sysdate());
```