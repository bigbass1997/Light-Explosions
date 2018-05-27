package com.bigbass.lightexplosion.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bigbass.lightexplosion.SRand;
import com.bigbass.lightexplosion.SimOptions;

public class SparkParticle extends Particle {
	
	private Color color;
	private Color tmpColor;
	private Vector2 vel;
	
	private float ticks;
	
	public SparkParticle(ParticleFactory pfac, float x, float y, int c){
		super(pfac, 100, x, y);
		
		color = new Color(c);
		tmpColor = new Color();
		vel = new Vector2(0, 300 + (SRand.getR().nextFloat() * 180));
		
		ticks = 0;
	}

	@Override
	public void render(ShapeRenderer sr) {
		sr.setColor(color);
		sr.rect(pos.x, pos.y, 5, 5);
	}

	@Override
	public void update(float delta) {
		if(ticks > 5 && life > 0){
			ticks = 0;
			
			tmpColor = color.cpy();
			tmpColor.a -= 0.2f;
			pfac.addParticle(new TrailParticle(pfac, pos.x, pos.y, vel.x, vel.y * 0.5f, tmpColor.cpy()));
		}
		
		vel.y += -150 * delta;
		
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		
		if(vel.y <= 0){
			life = 0;
			boom();
		}
		
		ticks += 1;
	}
	
	private void boom(){
		int type = -1;
		
		if(SimOptions.op().typeSelectionRandom){
			type = SRand.getR().nextInt(2);
		} else {
			type = SimOptions.op().typeSelection;
		}
		
		if(type == 0 || type == 1){
			for(int theta = SimOptions.op().sparkMinTheta - 90; theta < SimOptions.op().sparkMaxTheta - 90; theta += SimOptions.op().sparkThetaInteration){
				float x = MathUtils.cosDeg(theta) * SimOptions.op().boomPrimaryStrength;
				float y = MathUtils.sinDeg(theta) * SimOptions.op().boomPrimaryStrength;
				
				switch(type){
				case 0:
					pfac.addParticle(new BoomParticle(pfac, pos.x, pos.y, vel.x + x, vel.y + y, 4, color.cpy()));
					break;
				case 1:
					pfac.addParticle(new RecursiveBoomParticle(pfac, pos.x, pos.y, vel.x + x, vel.y + y, 4, color.cpy()));
					break;
				}
			}
		}
	}
}
