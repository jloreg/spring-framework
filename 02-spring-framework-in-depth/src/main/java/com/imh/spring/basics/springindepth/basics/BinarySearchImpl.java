package com.imh.spring.basics.springindepth.basics;

//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)	//by default
public class BinarySearchImpl {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("bubble")
	private SortAlgorithm sortAlgorithm;
	
	public int binarySearch (int[] numbers, int numberTo) {
		int[] sortedNumbers = sortAlgorithm.sort(numbers);
		System.out.println(sortAlgorithm);
		//Search the array
		
		return 3;
	}
	
//	@PostConstruct
//	public void postConstruct() {
//		logger.info("postConstruct");
//	}
//
//	@PreDestroy
//	public void preDestroy() {
//		logger.info("preDestroy");
//	}
}	