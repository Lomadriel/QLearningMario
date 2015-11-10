package mario.entity;

import java.util.List;

import javafx.geometry.Point2D;

public interface AgentBody {
	public List<Entity> getPerception();
	
	public void setPerception(List<Entity> perception);
	
	public double getPerceptionDistance();
	
	public void move(Point2D vector);
	
	public void act(int action);

	public Point2D getWantedMovement();

	public int getWantedAction();
}
