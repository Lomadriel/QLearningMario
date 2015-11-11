package mario.entity;

import mario.common.Hitbox;

public class Coin extends Entity implements Collectable {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    private boolean isCollected = false;
    private Entity collecter = null;

    public Coin() {
        this.currentHitbox = Coin.hitbox; 
    }
    
    @Override
    public void collect(Entity entity) {
        this.isCollected = true;
        this.collecter = entity;
    }
    
    @Override
    public boolean isCollected() {
        return this.isCollected;
    }
    
    @Override
    public Entity getCollecter() {
        return this.collecter;
    }

}
