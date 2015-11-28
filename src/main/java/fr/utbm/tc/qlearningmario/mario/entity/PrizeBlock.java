package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.BlockType;

public class PrizeBlock extends Block<PrizeBlock> implements Damageable {
	private Entity<?> surprise;
	private boolean empty;
	
	public PrizeBlock(Entity<?> surprise) {
		super(BlockType.PrizeBlock);
		assert(surprise != null);
		this.surprise = surprise;
	}
	
	public Entity<?> getSurprise() {
		return this.surprise;
	}
	
	@Override
	public void damage(int amount) {
		this.kill();
	}

	@Override
	public void damage(int amount, Entity<?> source) {
		this.kill();
	}

	@Override
	public void kill() {
		this.empty = true;
	}
	
	@Override
	public boolean isDead() {
		return this.empty;
	}
	
	public boolean isEmpty() {
		return this.empty;
	}

	@Override
	public int getHealth() {
		return this.empty ? 0 : 1;
	}

	@Override
	public void setHealth(int health) {
		// health cannot be changed.
	}

	@Override
	public int getMaxHealth() {
		return 1;
	}

	@Override
	public void setMaxHealth(int maxHealth) {
		// Max health cannot be changed.
	}

	@Override
	public void resetMaxHealth() {
		// Reset max health cannot be changed nor reset.
	}

	@Override
	public void heal(int amount) {
		// A block cannot be healed.
	}

	@Override
	public boolean isInvincible() {
		return false;
	}

	@Override
	public double getNoDamageTimestamp() {
		return 0.f;
	}

	@Override
	public void setNoDamageTimestamp(double timestamp) {
		// A block doesn't have any noDamageTimestamp.
	}
	
	@Override
	public PrizeBlock clone() {
		PrizeBlock clone = super.clone();
		clone.surprise = (Entity<?>) this.surprise.clone();
		return clone;
	}
}
