package Game.Galaga.Entities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by AlexVR on 1/25/2020
 */
public class EntityManager {

    
	public ArrayList<BaseEntity> entities;
    public PlayerShip playerShip;
	public ArrayList<BaseEntity>Add = new ArrayList<>(); //received this tip and used it, worked well.
    

    public EntityManager(PlayerShip playerShip) {
        entities = new ArrayList<>();
        this.playerShip = playerShip;
    }

    public void tick(){
        playerShip.tick();
        ArrayList<BaseEntity> toRemove = new ArrayList<>();
        for (BaseEntity entity: entities){
            if (entity.remove){
                toRemove.add(entity);
                continue;
            }
            entity.tick();
            if (entity.bounds.intersects(playerShip.bounds)){
                playerShip.damage(entity);
            }
        }
        for (BaseEntity toErase:toRemove){
            entities.remove(toErase);
        }
        for (BaseEntity toAdd: Add){ //added toAdd to have both (remove and add)
            entities.add(toAdd);
        }
        Add.clear();
    }

    public void render(Graphics g){
        for (BaseEntity entity: entities){
            entity.render(g);
        }
        playerShip.render(g);

    }

	public void add(AlienLaser playEffect) {
		// TODO Auto-generated method stub
		
	}

}
