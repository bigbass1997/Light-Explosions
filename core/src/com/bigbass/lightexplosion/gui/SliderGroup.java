package com.bigbass.lightexplosion.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class SliderGroup extends Group {
	
	public Slider slider;
	public Label label;
	
	public Label header;
	
	public SliderGroup(float min, float max, float step, float x, float y, float width, Skin skin){
		slider = new Slider(min, max, step, false, skin);
		slider.setPosition(x, y);
		slider.setWidth(width);
		this.addActor(slider);
		
		label = new Label("Test", skin);
		label.setPosition(slider.getX() + 2, slider.getY() + 18);
		this.addActor(label);
		
		header = new Label("", skin);
		header.setPosition(slider.getX() - 12, slider.getY() + 40);
		this.addActor(header);
	}
}
