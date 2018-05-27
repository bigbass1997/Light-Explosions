package com.bigbass.lightexplosion.panel;

import java.util.ArrayList;

public class PanelGroup {
	
	public ArrayList<Panel> panels;
	
	public PanelGroup(){
		panels = new ArrayList<Panel>();
	}
	
	public void render(){
		for(Panel panel : panels){
			if(panel.isVisible()){
				panel.render();
			}
		}
	}
	
	public void update(float delta){
		for(Panel panel : panels){
			if(panel.isActive()){
				panel.update(delta);
			}
		}
	}
	
	public void dispose(){
		for(int i = panels.size() - 1; i >= 0; i--){
			panels.get(i).dispose();
			panels.remove(i);
		}
	}
}
