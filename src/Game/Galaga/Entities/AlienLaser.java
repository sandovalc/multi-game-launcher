package Game.Galaga.Entities;

import Main.Handler;

import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 1/25/2020
 */
public class AlienLaser extends BaseEntity {

    EntityManager enemies;
    int speed = 10; //increased speed for difficulty

    public AlienLaser(int x, int y, int width, int height, BufferedImage sprite, Handler handler,EntityManager enemies) {
        super(x, y, width, height, sprite, handler);
        this.enemies=enemies;
    }

    @Override
    public void tick() {
        if (!remove) {
            super.tick();
            y += speed; //CHANGED symbol so it goes down
            bounds.y = y;
            for (BaseEntity enemy : enemies.entities) {
                if (enemy instanceof EnemyAlien || enemy instanceof AlienLaser) {
                    continue;
                }
                if (enemy.bounds.intersects(bounds)) {
                    enemy.damage(this);
                }
            }
        }
    }
}
