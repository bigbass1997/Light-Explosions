package com.bigbass.lightexplosion.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bigbass.lightexplosion.skins.SkinManager;

public class RotatedLabel extends Group {
	
	private Label label;
	
	public RotatedLabel(String text, float theta, int fontSize){
		label = new Label(text, SkinManager.getSkin("fonts/computer.ttf", fontSize));
		this.addActor(label);
		this.addAction(Actions.rotateBy(theta));
	}
}
