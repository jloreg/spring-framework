package com.imh.spring.basics.springindepth.cdi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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