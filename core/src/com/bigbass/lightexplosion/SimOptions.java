package com.bigbass.lightexplosion;

public class SimOptions {
	
	private static SimOptions instance;
	
	//Explosion Type
	public int typeSelection = 0;
	public boolean typeSelectionRandom = false;
	
	//Spark Explosion Angle Range
	public int sparkMinTheta = 0;
	public int sparkMaxTheta = 360;
	public int sparkThetaInteration = 16;
	
	//Boom Particle Strength
	public float boomPrimaryStrength = 40;
	public float boomSecondaryStrength = 120;
	
	//Boom Particle Trail
	public boolean particleTrail = false;
	
	private SimOptions(){
		
	}
	
	public static SimOptions op(){
		if(instance == null){
			instance = new SimOptions();
		}
		
		return instance;
	}
}
