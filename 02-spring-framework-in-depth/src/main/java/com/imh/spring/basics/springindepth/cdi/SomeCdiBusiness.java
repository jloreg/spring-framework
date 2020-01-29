package com.imh.spring.basics.springindepth.cdi;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

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
		int[] data = someCdiDao.getData();
		for (int value : data) {
			if (value > greatest) {
				greatest = value;
			}
		}
		return greatest;
	}
}