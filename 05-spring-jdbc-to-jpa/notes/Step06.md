## Step 06 - Execute the findAll method using CommandLineRunner

In the previous step, we created a PersonJdbcDao, and we defined a simple findAll method in PersonJdbcDao class by using a JDBC template to fire a query against the database, and we used the BeanPropertyRowMapper to map the Person class for it.

```java
package com.imh.spring.database.databasedemo.jdbc;

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

Now we would want to be able to fire this query *jdbcTemplate.query("select * from person", new BeanPropertyRowMapper(Person.class))* at the startup of the application, so what we'll do is in the SpringDatabaseApplication, we'll implement a CommandLineRunner. So CommandLineRunner is one of the interfaces which is present in Spring Boot. This would be launching up when I implement a CommandLineRunner, and then this code which we write in a specific *public void run(String... args) throws Exception* method that would be launched up, as soon as the application context is ready. So when this application context launches up the code in the CommandLine Runner gets executed.

```java
package com.imh.spring.database.databasedemo;

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

		//This would return the list of values back, and print that to the console.
		logger.info("All users -> {}", dao.findAll());
	}

}
```

What does the logger do? It takes its value *dao.findAll()* and replace the first occurrence of this variable template *"All users -> {}"*. So let's format the code a little bit and stop and start the application, let's see what would happen (I would expect a NotSuchMethod exception to be thrown).

```
Console output:

[2m2020-02-24 09:36:11.651[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 2.818 seconds (JVM running for 3.358)
[2m2020-02-24 09:36:11.662[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mConditionEvaluationReportLoggingListener[0;39m [2m:[0;39m 

Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
[2m2020-02-24 09:36:11.668[0;39m [31mERROR[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.boot.SpringApplication              [0;39m [2m:[0;39m Application run failed

java.lang.IllegalStateException: Failed to execute CommandLineRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:787) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:768) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:322) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1215) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	at com.imh.spring.database.databasedemo.SpringDatabaseApplication.main(SpringDatabaseApplication.java:22) ~[classes/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.boot.devtools.restart.RestartLauncher.run(RestartLauncher.java:49) ~[spring-boot-devtools-2.2.4.RELEASE.jar:2.2.4.RELEASE]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.imh.spring.database.databasedemo.entity.Person]: No default constructor found; nested exception is java.lang.NoSuchMethodException: com.imh.spring.database.databasedemo.entity.Person.<init>()
	at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:145) ~[spring-beans-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.BeanPropertyRowMapper.mapRow(BeanPropertyRowMapper.java:285) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.RowMapperResultSetExtractor.extractData(RowMapperResultSetExtractor.java:94) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.RowMapperResultSetExtractor.extractData(RowMapperResultSetExtractor.java:61) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.JdbcTemplate$1QueryStatementCallback.doInStatement(JdbcTemplate.java:440) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:376) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.JdbcTemplate.query(JdbcTemplate.java:452) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.jdbc.core.JdbcTemplate.query(JdbcTemplate.java:462) ~[spring-jdbc-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at com.imh.spring.database.databasedemo.jdbc.PersonJdbcDao.findAll(PersonJdbcDao.java:22) ~[classes/:na]
	at com.imh.spring.database.databasedemo.jdbc.PersonJdbcDao$$FastClassBySpringCGLIB$$7abacdf6.invoke(<generated>) ~[classes/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139) ~[spring-tx-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689) ~[spring-aop-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	at com.imh.spring.database.databasedemo.jdbc.PersonJdbcDao$$EnhancerBySpringCGLIB$$9af3ba09.findAll(<generated>) ~[classes/:na]
	at com.imh.spring.database.databasedemo.SpringDatabaseApplication.run(SpringDatabaseApplication.java:27) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:784) ~[spring-boot-2.2.4.RELEASE.jar:2.2.4.RELEASE]
	... 10 common frames omitted
Caused by: java.lang.NoSuchMethodException: com.imh.spring.database.databasedemo.entity.Person.<init>()
	at java.base/java.lang.Class.getConstructor0(Class.java:3349) ~[na:na]
	at java.base/java.lang.Class.getDeclaredConstructor(Class.java:2553) ~[na:na]
	at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:138) ~[spring-beans-5.2.3.RELEASE.jar:5.2.3.RELEASE]
	... 30 common frames omitted

[2m2020-02-24 09:36:11.671[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.s.concurrent.ThreadPoolTaskExecutor [0;39m [2m:[0;39m Shutting down ExecutorService 'applicationTaskExecutor'
[2m2020-02-24 09:36:11.671[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mj.LocalContainerEntityManagerFactoryBean[0;39m [2m:[0;39m Closing JPA EntityManagerFactory for persistence unit 'default'
[2m2020-02-24 09:36:11.671[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36m.SchemaDropperImpl$DelayedDropActionImpl[0;39m [2m:[0;39m HHH000477: Starting delayed evictData of schema as part of SessionFactory shut-down'
[2m2020-02-24 09:36:11.875[0;39m [33m WARN[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mo.s.b.f.support.DisposableBeanAdapter   [0;39m [2m:[0;39m Invocation of destroy method failed on bean with name 'inMemoryDatabaseShutdownExecutor': org.h2.jdbc.JdbcSQLNonTransientConnectionException: La base de datos ya esta cerrada (para des-habilitar el cerrado automatico durante el shutdown de la VM, agregue ";DB_CLOSE_ON_EXIT=FALSE" a la URL de conexión)
Database is already closed (to disable automatic closing at VM shutdown, add ";DB_CLOSE_ON_EXIT=FALSE" to the db URL) [90121-200]
[2m2020-02-24 09:36:11.876[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mcom.zaxxer.hikari.HikariDataSource      [0;39m [2m:[0;39m HikariPool-1 - Shutdown initiated...
[2m2020-02-24 09:36:11.880[0;39m [32m INFO[0;39m [35m3712[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mcom.zaxxer.hikari.HikariDataSource      [0;39m [2m:[0;39m HikariPool-1 - Shutdown completed.
```

So let's find out. There's an exception that is being thrown and if you look at it, it says NoSuchMethodException and it says *Caused by java.lang.NoSuchMethodException: com.imh.spring.database.databasedemo.entity.Person.<init>()*. So actually, when I'm using a BeanRowMapper, what we are using in the JDBC is a BeanPropertyRowMapper. And whenever I use a BeanPropertyRowMapper, **the Bean on which the PropertyRowMapper is defined, should have a no argument constructor**. So I need to create a simple no-argument constructor.

```java
package com.imh.spring.database.databasedemo.entity;

public class Person {

	private int id;
	private String name;
	private String location;
	private Date birthDate;
	
	//Default constructor (simple no argument constructor)
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
```

One of the things is, this is the default one which is provided by Java. So if this constructor *public Person()* was not there, this is provided by default, so I don't need to provide this at all. But since we provided this constructor *public Person(int id, String name, String location, Date birthDate)*, Java will no longer provide the default *public Person()* no-argument constructor. **So I need to provide that right now**. So I'm just initializing a default no-argument constructor and restart the application again. Let's what would happen now, start the application again, because last time the started failed.

```
Console output:
[2m2020-02-24 09:48:29.302[0;39m [32m INFO[0;39m [35m3864[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 1.979 seconds (JVM running for 2.458)
[2m2020-02-24 09:48:29.314[0;39m [32m INFO[0;39m [35m3864[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$22b19d6e[0;39m [2m:[0;39m All users -> [
 Person [id=10001, name=Ranga, location=Hyderabad, birthDate=2020-02-24], 
 Person [id=10002, name=James, location=New York, birthDate=2020-02-24], 
 Person [id=10003, name=Pieter, location=Amsterdam, birthDate=2020-02-24]]
```
Application started up, and when I look at whatever is printed, it says all users and it's printing the Person hash code stuff. So there are three persons printed, but it's printing the hash codes of it. How can avoid that ? I'd want to see the real values of Person, not the hash code stuff. How do I do that ? All that I need to do is generate the toString() method. 

```java
package com.imh.spring.database.databasedemo.entity;

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", location=" + location + ", birthDate=" + birthDate + "]";
	}
```

So this would generate a Person, but each Person I would want to be printed in a new line. So I'll say "\n Person", and this would make sure that the details are printed on a new line, and it's easily more visible in the log. Now let's stop and restart the application.

```
Console output:
[2m2020-02-24 08:12:54.591[0;39m [32m INFO[0;39m [35m7380[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mc.i.s.d.d.SpringDatabaseApplication     [0;39m [2m:[0;39m Started SpringDatabaseApplication in 1.956 seconds (JVM running for 2.458)
[2m2020-02-24 08:12:54.603[0;39m [32m INFO[0;39m [35m7380[0;39m [2m---[0;39m [2m[  restartedMain][0;39m [36mication$$EnhancerBySpringCGLIB$$db5248b3[0;39m [2m:[0;39m All users -> [
 Person [id=10001, name=Ranga, location=Hyderabad, birthDate=2020-02-24], 
 Person [id=10002, name=James, location=New York, birthDate=2020-02-24], 
 Person [id=10003, name=Pieter, location=Amsterdam, birthDate=2020-02-24]]
```

So now all the persons are printed on a new line, so you can see all the details that are populated for the Person. You can see that the data is retrieved from the database, and I'm able to see the data which is present in here.

So in this step, what we have done is we were able to use the *public List<Person> findAll()* method inside our Dao to query for all the Persons, and we printed the values that we loaded in for Person in the log. That's your fist JDBC stuff working. So what we did in this example i swe use Spring JDBC to execute a query and print the results of that query. We'll do a lot more in the subsequent steps.

## Complete Code Example

##### /src/main/java/com/imh/spring/database/databasedemo/entity/Person.java

```java
package com.imh.spring.database.databasedemo.entity;

import java.sql.Date;

public class Person {

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
		return "\n Person [id=" + id + ", name=" + name + ", location=" + location + ", birthDate=" + birthDate + "]";
	}

}
```