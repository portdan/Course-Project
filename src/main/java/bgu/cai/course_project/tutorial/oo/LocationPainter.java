package bgu.cai.course_project.tutorial.oo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.visualizer.ObjectPainter;

import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.VAR_X;
import static bgu.cai.course_project.tutorial.oo.ExampleOOGridWorld.VAR_Y;

public class LocationPainter implements ObjectPainter {

	protected float fWidth,fHeight;

	public LocationPainter(int[][] map) {

		//set up floats for the width and height of our domain
		this.fWidth = map.length;
		this.fHeight = map[0].length;
	}
	
	@Override
	public void paintObject(Graphics2D g2, OOState s, ObjectInstance ob,
							float cWidth, float cHeight) {

		//agent will be filled in blue
		g2.setColor(Color.BLUE);

		//determine the width of a single cell on our canvas
		//such that the whole map can be painted
		float width = cWidth / fWidth;
		float height = cHeight / fHeight;

		int ax = (Integer)ob.get(VAR_X);
		int ay = (Integer)ob.get(VAR_Y);

		//left coordinate of cell on our canvas
		float rx = ax*width;

		//top coordinate of cell on our canvas
		//coordinate system adjustment because the java canvas
		//origin is in the top left instead of the bottom right
		float ry = cHeight - height - ay*height;

		//paint the rectangle
		g2.fill(new Rectangle2D.Float(rx, ry, width, height));


	}



}
