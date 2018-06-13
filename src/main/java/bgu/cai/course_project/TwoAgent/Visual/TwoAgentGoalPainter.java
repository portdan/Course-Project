package bgu.cai.course_project.TwoAgent.Visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import burlap.mdp.core.state.State;
import burlap.visualizer.StatePainter;

public class TwoAgentGoalPainter implements StatePainter {

	protected int goalX1,goalY1,goalX2,goalY2;
	protected float fWidth,fHeight;

	public TwoAgentGoalPainter(int[][] map, int goalX1, int goalY1, int goalX2, int goalY2) {
		this.goalX1 = goalX1;
		this.goalY1 = goalY1;
		this.goalX2 = goalX2;
		this.goalY2 = goalY2;

		//set up floats for the width and height of our domain
		this.fWidth = map.length;
		this.fHeight = map[0].length;
	}

	@Override
	public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {

		//determine the width of a single cell on our canvas
		//such that the whole map can be painted
		float width = cWidth / fWidth;
		float height = cHeight / fHeight;

		//left coordinate of cell on our canvas
		float rx1 = goalX1*width;
		float rx2 = goalX2*width;

		//top coordinate of cell on our canvas
		//coordinate system adjustment because the java canvas
		//origin is in the top left instead of the bottom right
		float ry1 = cHeight - height - goalY1*height;
		float ry2 = cHeight - height - goalY2*height;

		//agent1 will be filled in green
		g2.setColor(Color.GREEN);

		//paint the rectangle
		g2.fill(new Rectangle2D.Float(rx1, ry1, width, height));

		//agent1 will be filled in blue
		g2.setColor(Color.BLUE);

		//paint the rectangle
		g2.fill(new Rectangle2D.Float(rx2, ry2, width, height));
	}

}
