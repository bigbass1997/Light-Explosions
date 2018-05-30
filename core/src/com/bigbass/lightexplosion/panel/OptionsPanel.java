package com.bigbass.lightexplosion.panel;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass.lightexplosion.Main;
import com.bigbass.lightexplosion.SimOptions;
import com.bigbass.lightexplosion.gui.SliderGroup;
import com.bigbass.lightexplosion.skins.SkinManager;

public class OptionsPanel extends Panel {
	
	private OrthographicCamera cam;
	private Stage stage;
	private ShapeRenderer sr;
	
	private Label header;
	private Label warning;
	
	private List<Actor> guiElements;
	
	public OptionsPanel() {
		super();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		
		stage = new Stage();
		Main.inputMultiplexer.addProcessor(stage);
		
		header = new Label("OPTIONS", SkinManager.getSkin("fonts/computer.ttf", 36));
		header.setPosition(80, Gdx.graphics.getHeight() - 47);
		stage.addActor(header);
		
		warning = new Label("Trails may cause lag spikes.\n  But they look good!", SkinManager.getSkin("fonts/computer.ttf", 20));
		warning.setPosition(20, 500);
		warning.setVisible(false);
		warning.setColor(1, 0.2f, 0.2f, 1);
		stage.addActor(warning);
		
		sr = new ShapeRenderer(500000);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		
		this.isVisible(false);
		this.isActive(false);
		
		guiElements = new ArrayList<Actor>();
		SimOptions op = SimOptions.op();
		
		// GUI ELEMENT CREATION | Also sets/grabs initial SimOption values \\
		// Explosion Type
		SliderGroup tmp1 = new SliderGroup(0, 2, 1, 25, 795, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp1.header.setText("Explosion Type");
		guiElements.add(tmp1);
		stage.addActor(tmp1);
		op.typeSelection = 0;
		op.typeSelectionRandom = false;
		
		// Explosion Strength - Primary
		SliderGroup tmp2 = new SliderGroup(0, 400, 1, 25, 740, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp2.header.setText("Explosion Strength");
		tmp2.slider.setValue(op.boomPrimaryStrength);
		guiElements.add(tmp2);
		stage.addActor(tmp2);
		
		// Explosion Strength - Secondary
		SliderGroup tmp3 = new SliderGroup(0, 400, 1, 25, 705, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp3.slider.setValue(op.boomSecondaryStrength);
		guiElements.add(tmp3);
		stage.addActor(tmp3);
		
		// Explosion Angles - Min
		SliderGroup tmp4 = new SliderGroup(0, 360, 1, 25, 650, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp4.header.setText("Explosion Angles");
		tmp4.slider.setValue(op.sparkMinTheta);
		guiElements.add(tmp4);
		stage.addActor(tmp4);

		// Explosion Angles - Max
		SliderGroup tmp5 = new SliderGroup(0, 360, 1, 25, 615, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp5.slider.setValue(op.sparkMaxTheta);
		guiElements.add(tmp5);
		stage.addActor(tmp5);

		// Explosion Angles - Step
		SliderGroup tmp6 = new SliderGroup(1, 360, 1, 25, 580, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp6.slider.setValue(op.sparkThetaInteration);
		guiElements.add(tmp6);
		stage.addActor(tmp6);
		
		SliderGroup tmp7 = new SliderGroup(0, 1, 1, 25, 525, 200, SkinManager.getSkin("fonts/computer.ttf", 20));
		tmp7.header.setText("Particle Trails");
		tmp7.slider.setValue(0);
		op.particleTrail = false;
		guiElements.add(tmp7);
		stage.addActor(tmp7);
	}
	
	@Override
	public void render() {
		sr.begin(ShapeType.Filled);
		sr.setColor(0.5f, 0.5f, 0.5f, 0.4f);
		sr.rect(0, 20, 250, Gdx.graphics.getHeight() - 40);

		sr.setColor(0.7f, 0.7f, 0.7f, 0.4f);
		sr.rect(0, Gdx.graphics.getHeight() - 50, 250, 30);
		
		if(insideHeader()){
			sr.rect(0, Gdx.graphics.getHeight() - 50, 250, 30);
		}
		
		sr.end();
		
		panelGroup.render();
		
		stage.draw();
		
		/*sr.begin(ShapeType.Filled);
		sr.setColor(Color.FIREBRICK);
		renderDebug(sr);
		sr.end();*/
	}

	@Override
	public void update(float delta) {
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && insideHeader()){
			this.isVisible(false);
			this.isActive(false);
		}
		
		stage.act(delta);
		
		updateSimOptions();
		
		panelGroup.update(delta);
	}
	
	private void updateSimOptions(){
		SimOptions op = SimOptions.op();
		
		// Explosion Type
		SliderGroup type = (SliderGroup) guiElements.get(0);
		int exType = (int) type.slider.getValue();
		switch(exType){
		case 0:
			type.label.setText("Single Boom");
			op.typeSelection = 0;
			op.typeSelectionRandom = false;
			break;
		case 1:
			type.label.setText("Double Boom");
			op.typeSelection = 1;
			op.typeSelectionRandom = false;
			break;
		case 2:
			type.label.setText("Random");
			op.typeSelectionRandom = true;
			break;
		}
		
		// Explosion Strength - Primary
		SliderGroup priS = (SliderGroup) guiElements.get(1); // Gets slider group from list
		priS.label.setText(String.format("Primary: %.0f", priS.slider.getValue())); // Updates the label
		op.boomPrimaryStrength = priS.slider.getValue(); // Updates the respective SimOptions variable

		// Explosion Strength - Secondary
		SliderGroup secS = (SliderGroup) guiElements.get(2);
		secS.label.setText(String.format("Secondary: %.0f", secS.slider.getValue()));
		op.boomSecondaryStrength = secS.slider.getValue();
		
		// Explosion Angles - Min & Max
		SliderGroup angMin = (SliderGroup) guiElements.get(3);
		SliderGroup angMax = (SliderGroup) guiElements.get(4);
		
		// Prevents min from being larger than max, and the opposite
		if(angMax.slider.getValue() < angMin.slider.getValue()){
			angMin.slider.setValue(angMax.slider.getValue());
		} else if(angMin.slider.getValue() > angMax.slider.getValue()){
			angMax.slider.setValue(angMin.slider.getValue());
		}
		
		angMin.label.setText(String.format("Min: %.0f", angMin.slider.getValue()));
		op.sparkMinTheta = (int) angMin.slider.getValue();
		
		angMax.label.setText(String.format("Max: %.0f", angMax.slider.getValue()));
		op.sparkMaxTheta = (int) angMax.slider.getValue();
		

		// Explosion Angles - Step
		SliderGroup angStep = (SliderGroup) guiElements.get(5);
		angStep.label.setText(String.format("Step: %.0f", angStep.slider.getValue()));
		op.sparkThetaInteration = (int) angStep.slider.getValue();
		
		// Particle Trails
		SliderGroup trails = (SliderGroup) guiElements.get(6);
		if(trails.slider.getValue() == 0){
			trails.label.setText("Off");
			op.particleTrail = false;
			warning.setVisible(false);
		} else {
			trails.label.setText("On");
			op.particleTrail = true;
			warning.setVisible(true);
		}
	}
	
	private boolean insideHeader(){
		float mx = Gdx.input.getX();
		float my = -Gdx.input.getY() + Gdx.graphics.getHeight();
		
		return (mx > 0 && mx < 250 && my > Gdx.graphics.getHeight() - 50 && my < Gdx.graphics.getHeight() - 20);
	}
	
	public void dispose(){
		stage.dispose();
		sr.dispose();
		panelGroup.dispose();
	}
}
