package bgu.cai.course_project.TwoAgent.Visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import burlap.mdp.core.state.State;
import burlap.visualizer.StatePainter;

import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_X2;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y1;
import static bgu.cai.course_project.TwoAgent.TwoAgentGridWorld.VAR_Y2;

public class TwoAgentAgentPainter implements StatePainter {

	protected float fWidth,fHeight;

	public TwoAgentAgentPainter(int[][] map) {

		//set up floats for the width and height of our domain
		this.fWidth = map.length;
		this.fHeight = map[0].length;
	}

	@Override
	public void paint(Graphics2D g2, State s,float cWidth, float cHeight) {

		//determine the width of a single cell on our canvas
		//such that the whole map can be painted
		float width = cWidth / fWidth;
		float height = cHeight / fHeight;

		int ax1 = (Integer)s.get(VAR_X1);
		int ay1 = (Integer)s.get(VAR_Y1);
		int ax2 = (Integer)s.get(VAR_X2);
		int ay2 = (Integer)s.get(VAR_Y2);

		//left coordinate of cell on our canvas
		float rx1 = ax1*width;
		float rx2 = ax2*width;

		//top coordinate of cell on our canvas
		//coordinate system adjustment because the java canvas
		//origin is in the top left instead of the bottom right
		float ry1 = cHeight - height - ay1*height;
		float ry2 = cHeight - height - ay2*height;

		//agent1 will be filled in green
		g2.setColor(Color.GREEN);

		//paint the rectangle
		g2.fill(new Ellipse2D.Float(rx1, ry1, width, height));

		//agent1 will be filled in blue
		g2.setColor(Color.BLUE);

		//paint the rectangle
		g2.fill(new Ellipse2D.Float(rx2, ry2, width, height));
	}

}
