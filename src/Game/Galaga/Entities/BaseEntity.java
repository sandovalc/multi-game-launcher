package Game.Galaga.Entities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by AlexVR on 1/25/2020
 */
public class BaseEntity {
    public int x,y,width,height;
    public BufferedImage sprite;
    public Rectangle bounds;
    Handler handler;
    Random random;
    Rectangle arena;
    Animation enemyDeath;
    boolean remove = false;


    public BaseEntity(int x, int y, int width, int height, BufferedImage sprite, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.bounds = new Rectangle(x,y,width,height);
        this.handler = handler;
        random = new Random();
        arena = new Rectangle(handler.getWidth()/4,0,handler.getWidth()-handler.getWidth()/2,handler.getHeight());
        enemyDeath = new Animation(256,Images.galagaEnemyDeath);
    }

    public void tick(){

    }

    public void render(Graphics g) {
        if (arena.contains(bounds) && !remove) {
            g.drawImage(sprite, x, y, width, height, null);
            if (Handler.DEBUG) {
                g.setColor(Color.red);
                ((Graphics2D) g).draw(bounds);
            }
        }
    }

    public void damage(BaseEntity damageSource) {
    	
    	
    	
    }

}
