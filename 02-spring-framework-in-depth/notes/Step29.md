## Step 29 - Spring Unit Testing with Mockito

```java
@Named		//Instead of @Component it's called @Named
@Singleton	//Instead of @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) it's called @Singleton
public class SomeCdiBusiness {

	@Inject	//Instead of @Autowired it's called @Inject
	SomeCdiDao someCdiDao;

	public SomeCdiDao getSomeCDIDAO() {
		return someCdiDao;
	}

	public void setSomeCDIDAO(SomeCdiDao someCdiDao) {
		this.someCdiDao = someCdiDao;
	}

	public int findGreatest() {
		int greatest = Integer.MIN_VALUE;
		int[] data = someCdiDao.getData();	//someCdiDao it's a data layer dependency 
		for (int value : data) {
			if (value > greatest) {
				greatest = value;
			}
		}
		return greatest;
	}
}
```

```java
@Named		//Instead of @Component it's called @Named
@Singleton	//Instead of @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) it's called @Singleton
public class SomeCdiDao {
	
	public int[] getData() {
		return new int[] {5, 89,100};
	}

}
```

In this specific test SomeCdiBusinessTest, actually we are depending on the data layer SomeCdiDao, we are trying to get the data from the data layer. This is not really a good practice.

```java
@RunWith(MockitoJUnitRunner.class)
public class SomeCdiBusinessTest {
	
	//Inject the mock in the SomeCdiBusiness object 
	@InjectMocks
	SomeCdiBusiness someCdibusiness;
	@Mock
	//Create Mock
	SomeCdiDao daoMock; 	
	
	@Test
	public void testBasicScenario() {
		//scenario: when daoMock.getData() method is called, return int[]{2,4}
		Mockito.when(daoMock.getData()).thenReturn(new int[] {2,4});
		int actualResult = someCdibusiness.findGreatest();
		//check if the result is the expected (first argument = expected value)
		assertEquals(100, actualResult);	//100 is the expected value that must return the data layer SomeCdiDao
	}
}
```

Now, what we are doing is going across layers, so from the business layer we are going to the data layer and getting the data from the database. So typically this may not be coded by this way, there must be something which is coming from the database, which would mean if the data in the database changes or if somebody else modifies the data, or if the database is down, then your unit test would start failing. That's the kind of dependency that you don't want; the benefit of using Mockito to void hard dependencies and to reduce coupling, will be discussed extensively during the Mockito section. Mockito helps in avoiding that kind of issues creating mocks and using mocks to write effective unit test.

So, what we want to do here in the SomeCdiBusiness, is test the CdiBusiness with different kinds of data (SomeCdiDao). How do this SomeCdiBusiness reacts to different kinds of data. So one of the options is to create multiple versions of SomeCdiDao, but that's not a good option, so what will do is create mocks of SomeCdiDao. So I'll create mocks of SomeCdiDao and write a lot of unit test, to test the SomeCdiBusiness with that. 

## Complete Code Example

##### /src/test/java/com/imh/spring/basics/springindepth/cdi/SomeCdiBusinessTest.java

```java
package com.imh.spring.basics.springindepth.cdi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

//When we run the test with Mockito, we don't really need a Spring Context. All that you need to do, is use a the MockitoJUnitRunner class. 
@RunWith(MockitoJUnitRunner.class)
public class SomeCdiBusinessTest {
	
	//Inject the mock in the SomeCdiBusiness object 
	@InjectMocks
	SomeCdiBusiness someCdibusiness;
	@Mock
	//Create Mock
	SomeCdiDao daoMock; 
	
	@Test
	public void testBasicScenario() {
		//scenario: when daoMock.getData() method is called, return int[]{2,4}
	    Mockito.when(daoMock.getData()).thenReturn(new int[] {2,4});
		int actualResult = someCdibusiness.findGreatest();
		//check if the result is the expected (first argument = expected value)
		assertEquals(100, actualResult);
	}
	
	@Test
	public void testBasicScenario_NoElements() {
		//scenario: when daoMock.getData() method is called without parameters, return int[]{}
		Mockito.when(daoMock.getData()).thenReturn(new int[] {});
		int actualResult = someCdibusiness.findGreatest();
		assertEquals(Integer.MIN_VALUE, actualResult);
	}
	
	@Test
	public void testBasicScenario_EqualElements() {
		//scenario: when daoMock.getData() method is called without parameters, return int[2]{}
		Mockito.when(daoMock.getData()).thenReturn(new int[] {2,2});
		int actualResult = someCdibusiness.findGreatest();
		assertEquals(2, actualResult);
	}
}
```