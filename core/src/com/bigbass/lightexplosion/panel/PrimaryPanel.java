package com.bigbass.lightexplosion.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass.lightexplosion.Main;
import com.bigbass.lightexplosion.SRand;
import com.bigbass.lightexplosion.gui.RotatedLabel;
import com.bigbass.lightexplosion.objects.ParticleFactory;
import com.bigbass.lightexplosion.objects.SparkParticle;
import com.bigbass.lightexplosion.skins.SkinManager;

public class PrimaryPanel extends Panel {
	
	private OrthographicCamera cam;
	private Stage stage;
	private ShapeRenderer sr;
	
	private Label infoLabel;
	private RotatedLabel optionsLabel;
	private Label instructions;
	
	private ParticleFactory pfac;
	
	private boolean mouseClicked = false;
	
	private int ticks = 0;
	
	public PrimaryPanel() {
		super();
		
		panelGroup.panels.add(new OptionsPanel());
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
		stage = new Stage();
		Main.inputMultiplexer.addProcessor(stage);
		
		infoLabel = new Label("", SkinManager.getSkin("fonts/computer.ttf", 24));
		infoLabel.setColor(Color.WHITE);
		stage.addActor(infoLabel);
		
		optionsLabel = new RotatedLabel("OPTIONS", 90, 38);
		optionsLabel.setPosition(28, Gdx.graphics.getHeight() - 120);
		stage.addActor(optionsLabel);
		
		instructions = new Label("Hold Space, or click with mouse, to create light explosions. Press S to save a screenshot.", SkinManager.getSkin("fonts/computer.ttf", 19));
		instructions.setPosition(270, 5);
		instructions.setColor(0.9f, 0.9f, 0.9f, 1f);
		stage.addActor(instructions);
		
		sr = new ShapeRenderer(500000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		pfac = new ParticleFactory();
	}
	
	public void render() {
		if(!panelGroup.panels.get(0).isVisible()){
			sr.begin(ShapeType.Filled);

			sr.setColor(0.3f, 0.3f, 0.3f, 0.6f);
			sr.rect(0, Gdx.graphics.getHeight() - 140, 30, 120);
			sr.rect(0, 20, 10, Gdx.graphics.getHeight() - 40 - 120);

			sr.setColor(0.7f, 0.7f, 0.7f, 0.4f);
			sr.rect(0, Gdx.graphics.getHeight() - 140, 30, 120);

			float mx = Gdx.input.getX();
			float my = -Gdx.input.getY() + Gdx.graphics.getHeight();
			if(( (mx > 0 && mx < 10 && my > 20 && my < Gdx.graphics.getHeight() - 20) ||
				 (mx > 0 && mx < 30 && my > Gdx.graphics.getHeight() - 140 && my < Gdx.graphics.getHeight() - 20)
				)){

				sr.setColor(0.3f, 0.3f, 0.3f, 0.6f);
				sr.rect(0, Gdx.graphics.getHeight() - 140, 30, 120);
				sr.rect(0, 20, 10, Gdx.graphics.getHeight() - 40 - 120);

				sr.setColor(0.7f, 0.7f, 0.7f, 0.4f);
				sr.rect(0, Gdx.graphics.getHeight() - 140, 30, 120);
			}
			
			sr.end();
		}
		
		pfac.render(sr);
		
		panelGroup.render();
		
		stage.draw();
		
		/*sr.begin(ShapeType.Filled);
		sr.setColor(Color.FIREBRICK);
		renderDebug(sr);
		sr.end();*/
	}
	
	public void update(float delta) {
		float mx = Gdx.input.getX();
		float my = -Gdx.input.getY() + Gdx.graphics.getHeight();
		
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			if(ticks % 5 == 0){
				pfac.addParticle(new SparkParticle(pfac, 100 + (SRand.getR().nextFloat() * (Gdx.graphics.getWidth() - 200)), (SRand.getR().nextFloat() * 20),
						Color.rgba8888(SRand.getR().nextFloat(), SRand.getR().nextFloat(), SRand.getR().nextFloat(), 1)));
			}
			
			ticks += 1;
			if(ticks > 100000){
				ticks = 0;
			}
		}
		
		if(!panelGroup.panels.get(0).isVisible()){
			if(Gdx.input.isButtonPressed(Buttons.LEFT) && !mouseClicked){
				if(mx > 30){
					pfac.addParticle(new SparkParticle(pfac, mx, my,
							Color.rgba8888(Math.max(0.5f, SRand.getR().nextFloat()), Math.max(0.5f, SRand.getR().nextFloat()), Math.max(0.8f, SRand.getR().nextFloat()), 1)));
				}
				
				mouseClicked = true;
			}
			if(!Gdx.input.isButtonPressed(Buttons.LEFT) && mouseClicked) {
				mouseClicked = false;
			}
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && (
					(mx > 0 && mx < 10 && my > 20 && my < Gdx.graphics.getHeight() - 20) ||
					(mx > 0 && mx < 30 && my > Gdx.graphics.getHeight() - 140 && my < Gdx.graphics.getHeight() - 20)
				)){
			panelGroup.panels.get(0).isActive(true);
			panelGroup.panels.get(0).isVisible(true);
			
			mouseClicked = true;
		}
		optionsLabel.setVisible(!panelGroup.panels.get(0).isVisible());
		
		pfac.update(delta);
		
		stage.act(delta);
		
		panelGroup.update(delta);
		
		if(Gdx.input.isKeyPressed(Keys.D)){
			String info = String.format("Data:%n  FPS: %s%n  #Parts: %d",
					Gdx.graphics.getFramesPerSecond(),
					pfac.getCount()
				);
			
			infoLabel.setText(info);
			infoLabel.setPosition(40, Gdx.graphics.getHeight() - (infoLabel.getPrefHeight() / 2) - 5);
		} else {
			infoLabel.setText("");
		}
	}
	
	public void dispose(){
		stage.dispose();
		sr.dispose();
		panelGroup.dispose();
	}
}