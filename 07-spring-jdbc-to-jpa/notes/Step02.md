## Step 02 - Launching up H2 Console

http://localhost:8080/h2-console/

```
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:
```

```java
Console output:
2020-02-21 10:36:41.830  INFO 4929 --- [  restartedMain] c.i.s.d.d.SpringDatabaseApplication      : Starting SpringDatabaseApplication on laptop with PID 4929
2020-02-21 10:36:42.639  INFO 4929 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-02-21 10:36:42.805  INFO 4929 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'
```

**Remind**. H2 web console must be launched first since Eclipse web browser, later if you would want to launch the H2 web console since Firefox or anything else browser, you can do it, but first, you must launch first since Eclipse.


## Complete Code Example

##### /src/main/resources/application.properties

```java
spring.h2.console.enabled=true
```