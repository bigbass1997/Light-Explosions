package com.bigbass.lightexplosion.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TrailParticle extends Particle {

	private Vector2 vel;
	
	private Color color;
	
	public TrailParticle(ParticleFactory pfac, float x, float y, float vx, float vy, Color color){
		super(pfac, 100, x, y);
		
		this.vel = new Vector2(vx, vy);
		this.color = color;
		this.color.a -= 0.1f;
		if(this.color.a < 0){
			this.color.a = 0;
		}
	}

	@Override
	public void render(ShapeRenderer sr) {
		sr.setColor(color);
		sr.rect(pos.x + 1f, pos.y, 3, 3);
	}

	@Override
	public void update(float delta) {
		color.a -= 0.5 * delta;
		if(color.a < 0){
			color.a = 0;
		}
		
		vel.y += -40 * delta;
		
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		
		if(vel.y <= -1000 || color.a <= 0){
			life = 0;
		}
	}
}
