## Step 08 - What's in the background? Understanding Spring Boot Autoconfiguration

In the previous steps, we were able to query the data from the database and print it on the log. Then we made use of was *JdbcTemplate jdbcTemplate;* (see #1). What one question we didn't answer earlier was, how is the JDBC template getting auto wired in? How is it knowing about the connection to the in-memory H2 database? Where are the DataSource and all that kind of stuff ?.

```java
@Autowired
JdbcTemplate jdbcTemplate;	//#1
```

In this step, we would try and give you more information about how the whole thing is happening. The entire magic is due to Spring Boot AutoConfiguration. If you go to your /src/main/resources/application.properties, and you can type in *logging.level.root=debug*. So I'm setting the root logging level of the application to debug, and restart the entire app.

```
spring.h2.console.enabled=true
logging.level.root=debug
``` 

So start and stop the application. Once you do that, you can search in your log for "configuration report". So I'm searching for "auto-configuration" or "auto-configuration report", so this is basically what I'm looking for.

When you've run the application in debug mode, you'd see a complete report of AutoConfiguration. AutoConfiguration, as we discussed during Spring Boot section, is a very important feature which is present in Spring Boot. What Spring Boot does is it looks at what our classes are available on the classpath, and it would automatically configure things based on that. If he sees an in-memory database like H2 on the classpath, what it does? it automatically configures a connection to it. If it sees a web application on the classpath, what does it do? It configures a dispatcher servlet. If Spring Boot sees JPA on the classpath, it would automatically configure an entity manager factory and the transaction manager.

What I would recommend you to do is to look through this auto-configuration report, you would see a lot of information which is present in here. It also prints what did not match, wanted to try to AutoConfigure but it will also show you what did not really match. These are call negative matches. 

I'd recommend you to put the application in debug mode, run it. Look at the AutoConfiguration report and you'll get much more understanding of what's happening in the background. What I have done is I've extracted some of the important stuff which is happening and I put it on the bottom of this document. If you go to the bottom of this specific document (see Console output #1 - Jdbc Template AutoConfiguration), you'd see a few notes on the auto-configuration which is related to JDBC template. 

What is happening in here, we already looked H2ConsoleAutoConfiguration the H2 console auto-configuration is matching. It was configured. Why was it configured ? Because it was looking for a web servlet. Once a web servlet class is present then it would try and auto-configure H2. There are other conditions as well. The other conditions is to have this property *spring.h2.console.enabled=true* in the property file.

When we were enabling H2 console, if you remember it well, then you would remember that we configured this property in /src/main/resources/application.properties. So  at the top of the application.properties we have this *spring.h2.console.enabled=true*. H2 console is only auto-configured when there is a class called *org.h2.server.web.WebServlet*, when H2 is in the classpath, and when it's *spring.h2.console.enabled=true*. So only when the conditions are matching the H2ConsoleAutoConfiguration is auto-configured. 

Similar to that, the DataSourceAutoConfiguration. So the data source is auto-configured when there is a *javax.sql.DataSource* or a *org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType* in the classpath. We have embedded database H2. So the data source is auto-configured, and using the data source a *JdbcTemplateAutoConfiguration* is autoconfigured as well. So what happens in the background is, because there's a embedded database in the classpath, a data source gets auto-configured and also a JDBC template using that data source gets autoconfigured. This is all the magic which is happening in the background.

If this is the first time you are looking at AutoConfiguration this might be a little confusing but don't worry about it. As you play around with Spring Boot, you will be understanding auto configuration a lot more and you'll be able to figure out what's happening in the background.

In this step, we took a small break from all our hands-on stuff and looked at what's happening in the background with AutoConfiguration. We looked at how a JDBC template is being auto-configured.

```
Console output #1 - Jdbc Template AutoConfiguration:

=========================
AUTO-CONFIGURATION REPORT
=========================

DataSourceAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'javax.sql.DataSource', 'org.springframework.jdbc.datasource.embedded.E mbeddedDatabaseType'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)

DataSourceTransactionManagerAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'org.springframework.jdbc.core.JdbcTemplate', 'org.springframework.transaction.PlatformTransactionManager'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)

H2ConsoleAutoConfiguration matched:
   - @ConditionalOnClass found required class 'org.h2.server.web.WebServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
   - found ConfigurableWebEnvironment (OnWebApplicationCondition)
   - @ConditionalOnProperty (spring.h2.console.enabled=true) matched (OnPropertyCondition)

JdbcTemplateAutoConfiguration matched:
   - @ConditionalOnClass found required classes 'javax.sql.DataSource', 'org.springframework.jdbc.core.JdbcTemplate'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
   - @ConditionalOnSingleCandidate (types: javax.sql.DataSource; SearchStrategy: all) found a primary bean from beans 'dataSource' (OnBeanCondition)

JdbcTemplateAutoConfiguration.JdbcTemplateConfiguration#jdbcTemplate matched:
   - @ConditionalOnMissingBean (types: org.springframework.jdbc.core.JdbcOperations; SearchStrategy: all) did not find any beans (OnBeanCondition)
```

## Complete Code Example

##### /src/main/resources/application.properties

```
spring.h2.console.enabled=true
logging.level.root=debug
``` 
```