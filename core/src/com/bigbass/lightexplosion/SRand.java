package com.bigbass.lightexplosion;

import java.util.Random;

/**
 * Small holder class for a static Random variable.
 * Was tired of creating a Random object in every class.
 */
public class SRand {
	
	private static Random rand;
	
	private SRand(){}
	
	public static Random getR(){
		if(rand == null){
			rand = new Random();
		}
		
		return rand;
	}
}
