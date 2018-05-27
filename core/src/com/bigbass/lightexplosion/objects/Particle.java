package com.bigbass.lightexplosion.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Particle {
	
	protected ParticleFactory pfac;
	
	public float life;
	public Vector2 pos;
	
	public Particle(ParticleFactory pfac, float life){
		this(pfac, life, 0, 0);
	}
	
	public Particle(ParticleFactory pfac, float life, float x, float y){
		this.pfac = pfac;
		this.life = life;
		this.pos = new Vector2(x, y);
	}
	
	public abstract void render(ShapeRenderer sr);
	
	public abstract void update(float delta);
}
