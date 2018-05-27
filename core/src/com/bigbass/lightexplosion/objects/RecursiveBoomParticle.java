package com.bigbass.lightexplosion.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bigbass.lightexplosion.SRand;
import com.bigbass.lightexplosion.SimOptions;

public class RecursiveBoomParticle extends Particle {

	private Vector2 vel;
	
	private float size;
	
	private Color color;

	private int ticks;
	private Color tmpColor;
	
	public RecursiveBoomParticle(ParticleFactory pfac, float x, float y, float vx, float vy, float size, Color color){
		super(pfac, 100, x, y);
		
		this.vel = new Vector2(vx, vy);
		this.size = size;
		this.color = color;
		this.color.a = 1;
	}

	@Override
	public void render(ShapeRenderer sr) {
		sr.setColor(color);
		sr.rect(pos.x + 1f, pos.y, size, size);
	}

	@Override
	public void update(float delta) {
		if(SimOptions.op().particleTrail){
			if(ticks > 5 && life > 0 && color.a > 0){
				ticks = 0;
				
				tmpColor = color.cpy();
				tmpColor.a = Math.max(0, tmpColor.a - 0.1f);
				pfac.addParticle(new TrailParticle(pfac, pos.x, pos.y, vel.x * 0.5f, vel.y * 0.5f, tmpColor.cpy()));
			}
		}
		
		color.a -= 0.5 * delta;
		if(color.a < 0){
			color.a = 0;
		}
		
		vel.y += -10 * delta;
		//vel.y *= 0.85f;
		
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		
		if(color.a <= 0){
			life = 0;
			boom();
		}

		ticks += 1;
	}
	
	private void boom(){
		for(int theta = 0; theta < 360; theta += 45){
			float x = MathUtils.cosDeg(SRand.getR().nextFloat() * 360) * SimOptions.op().boomSecondaryStrength;
			float y = MathUtils.sinDeg(SRand.getR().nextFloat() * 360) * SimOptions.op().boomSecondaryStrength;
			
			pfac.addParticle(new BoomParticle(pfac, pos.x, pos.y, vel.x + x, vel.y + y, size - 1, color.cpy()));
		}
	}
}
