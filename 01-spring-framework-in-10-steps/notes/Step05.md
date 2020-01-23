## Step 5 - What is happening in the background?

##### /src/main/resources/logging.properties

Console

```java
2020-01-23 12:47:33.776 DEBUG 8352 --- [           main] .c.l.ClasspathLoggingApplicationListener : Application started with classpath: unknown

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.4.RELEASE)

2020-01-23 12:47:33.813  INFO 8352 --- [           main] c.i.s.b.springin10steps.Application      : Starting Application on laptop with PID 8352 (/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes started by jon in /home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps)
2020-01-23 12:47:33.813  INFO 8352 --- [           main] c.i.s.b.springin10steps.Application      : No active profile set, falling back to default profiles: default

//Spring Aplication Context

2020-01-23 12:47:33.813 DEBUG 8352 --- [           main] o.s.boot.SpringApplication               : Loading source class com.imh.spring.basics.springin10steps.Application
2020-01-23 12:47:33.842 DEBUG 8352 --- [           main] o.s.b.c.c.ConfigFileApplicationListener  : Loaded config file 'file:/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes/application.properties' (classpath:/application.properties)
2020-01-23 12:47:33.843 DEBUG 8352 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@3e8c3cb
2020-01-23 12:47:33.852 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
2020-01-23 12:47:33.858 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory'
2020-01-23 12:47:33.889 DEBUG 8352 --- [           main] o.s.c.a.ClassPathBeanDefinitionScanner   : Identified candidate component class: file [/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes/com/imh/spring/basics/springin10steps/BinarySearchImpl.class]
2020-01-23 12:47:33.889 DEBUG 8352 --- [           main] o.s.c.a.ClassPathBeanDefinitionScanner   : Identified candidate component class: file [/home/jon/spring-workspace/joan/spring-framework/01-spring-framework-in-10-steps/target/classes/com/imh/spring/basics/springin10steps/BubbleSortAlgorithm.class]

//Important lines

2020-01-23 12:47:34.038 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'application'
2020-01-23 12:47:34.042 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'binarySearchImpl'
2020-01-23 12:47:34.045 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'bubbleSortAlgorithm'
2020-01-23 12:47:34.045 DEBUG 8352 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Autowiring by type from bean name 'binarySearchImpl' via constructor to bean named 'bubbleSortAlgorithm'
```

## Complete Code Example

### /src/main/resources/loggin.properties.

```java
logging.level.org.springframework=debug
```