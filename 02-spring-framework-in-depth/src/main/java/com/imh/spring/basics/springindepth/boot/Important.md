If you want to run the examples contained in this package, you must uncomment in the pom.xml file, the lines corresponding to the Spring Boot dependencies: org.springframework.boot. See below:

```xml
<!-- To be able to run the Spring main applications (package com.imh.spring.basics.springindepth), you must comment the lines below. 
	 CAUTION: if you comment these lines, Spring Boot main application (package com.imh.spring.basics.springindepth.boot), can't be able to run.
	 
		<dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-test</artifactId>
	        <scope>test</scope>
	    </dependency>
	    
	    <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-test</artifactId>
	        <scope>test</scope>
	        <exclusions>
	            <exclusion>
	                <groupId>org.junit.vintage</groupId>
	                <artifactId>junit-vintage-engine</artifactId>
	            </exclusion>
	        </exclusions>
	    </dependency>
-->
```