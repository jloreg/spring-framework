package com.imh.spring.aop.springaop.data;

import org.springframework.stereotype.Repository;

import com.imh.spring.aop.springaop.aspect.TrackTime;

@Repository	//@Repository is something which talk's with the database; @Repository is for Data layer. 
public class Dao1 {

	/* Ideally, when you are naming your Dao methods, I prefer calling them retrieve instead of get. Retrieve is something which indicates that I'm trying 
	 * to retrieve from our database, or from my external thing. When I use get to represent something, it might be confused with a getter, so get or setter,
	 * that kind of stuff.*/
	@TrackTime
	public String retrieveSomething(){
		return "Dao1";
	}

}