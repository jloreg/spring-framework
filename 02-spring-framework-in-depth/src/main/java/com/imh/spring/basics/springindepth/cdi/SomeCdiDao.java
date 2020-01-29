package com.imh.spring.basics.springindepth.cdi;

import javax.inject.Named;
import javax.inject.Singleton;

@Named		//Instead of @Component it's called @Named
@Singleton	//Instead of @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) it's called @Singleton
public class SomeCdiDao {
	
	public int[] getData() {
		return new int[] {5, 89,100};
	}

}
