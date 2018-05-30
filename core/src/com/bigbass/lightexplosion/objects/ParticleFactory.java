package com.bigbass.lightexplosion.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ParticleFactory {
	
	private List<Particle> particles;
	private List<Particle> addQueue;
	private HashSet<Particle> removeQueue;
	
	public ParticleFactory(){
		particles = new ArrayList<Particle>();
		addQueue = new ArrayList<Particle>();
		removeQueue = new HashSet<Particle>();
	}
	
	public void addParticle(Particle p){
		if(p != null){
			addQueue.add(p);
		}
	}
	
	public void removeParticle(Particle p){
		if(p != null){
			removeQueue.add(p);
		}
	}
	
	public void removeAllParticles(List<Particle> ps){
		if(ps != null && ps.size() > 0){
			removeQueue.addAll(ps);
		}
	}
	
	private void processQueues(){
		if(addQueue.size() > 0){
			particles.addAll(addQueue);
			addQueue.clear();
		}
		
		if(removeQueue.size() > 0){
			particles.removeAll(removeQueue);
			removeQueue.clear();
		}
	}
	
	public void render(ShapeRenderer sr){
		sr.begin(ShapeType.Filled);
		for(Particle p : particles){
			if(p.life > 0){
				p.render(sr);
			}
		}
		sr.end();
	}
	
	public void update(float delta){
		for(Particle p : particles){
			if(p.life > 0){
				p.update(delta);
			} else {
				removeQueue.add(p);
			}
		}
		
		processQueues();
	}
	
	public int getCount(){
		return particles.size();
	}
}
