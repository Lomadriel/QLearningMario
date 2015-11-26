package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.ArrayList;
import java.util.List;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class Mushroom extends MobileEntity<Mushroom> implements AgentBody, Collectable {
	static final Point2D maxVelocity;
	
	static {
        double x = Double.parseDouble(Locale.getString(Mushroom.class, "max.velocity.x")); //$NON-NLS-1$
        double y = Double.parseDouble(Locale.getString(Mushroom.class, "max.velocity.y")); //$NON-NLS-1$
        maxVelocity = new Point2D(x, y);
	}
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    private boolean isCollected = false;
    private Entity<?> collector;
    private List<Entity<?>> perception = new ArrayList<>();
    private Point2D wantedAcceleration;

    public Mushroom() {
        this.currentHitbox = Mushroom.hitbox;
        setMaxVelocity(Mushroom.maxVelocity);
    }
    
    @Override
    public void collect(Entity<?> entity) {
        this.isCollected = true;
        this.collector = entity;
    }

    @Override
    public boolean isCollected() {
        return this.isCollected;
    }

    @Override
    public Entity<?> getCollector() {
        return this.collector;
    }

    @Override
    public List<Entity<?>> getPerception() {
        return this.perception;
    }

    @Override
    public void setPerception(List<Entity<?>> perception) {
        this.perception = perception;
    }

    @Override
    public double getPerceptionDistance() {
        return 0f;
    }

    @Override
    public void askAcceleration(Point2D vector) {
        this.wantedAcceleration = vector;
    }

    @Override
    public void askAction(int action) {
        // A mushroom doesn't have any brain.
    }

    @Override
    public Point2D getWantedAcceleration() {
        return this.wantedAcceleration;
    }

    @Override
    public int getWantedAction() {
        return 0;
    }

}
