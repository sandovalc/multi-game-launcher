package Game.Galaga.Entities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Created by AlexVR on 1/25/2020
 */
public class PlayerShip extends BaseEntity{

    private int health = 3,attackCooldown = 30,speed =6,destroyedCoolDown = 60*7;
    private boolean attacking = false, destroyed = false;
    private Animation deathAnimation;
    //private ArrayList<Integer> beePos= new ArrayList<Integer>(20);

     public PlayerShip(int x, int y, int width, int height, BufferedImage sprite, Handler handler) {
        super(x, y, width, height, sprite, handler);

        deathAnimation = new Animation(256,Images.galagaPlayerDeath);

    }

    @Override
    public void tick() {
        super.tick();
        if (destroyed){
            if (destroyedCoolDown<=0){
                destroyedCoolDown=60*7;
                destroyed=false;
                deathAnimation.reset();
                bounds.x=x;
            }else{
                deathAnimation.tick();
                destroyedCoolDown--;
            }
        }else {
        	
            if (attacking) {
                if (attackCooldown <= 0) {
                    attacking = false;
                } else {
                    attackCooldown--;
                }
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && !attacking) {
                handler.getMusicHandler().playEffect("laser.wav");
                attackCooldown = 30;
                attacking = true;
                handler.getGalagaState().entityManager.entities.add(new PlayerLaser(this.x + (width / 2), this.y - 3, width / 5, height / 2, Images.galagaPlayerLaser, handler, handler.getGalagaState().entityManager));

            }
            if (handler.getKeyManager().left && x > handler.getWidth()/4 +speed) {//add bounds
                x -= (speed);
            }
            
            if (handler.getKeyManager().right && x < handler.getWidth()*(.75)-speed-handler.getGalagaState().entityManager.playerShip.width) {
                x += (speed);
            }
            
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N) && handler.DEBUG) { //suicide 
            	health--;
            	destroyed=true;
            	handler.getMusicHandler().playEffect("explosion.wav");
            	
            	bounds.x = -10;
        }
            bounds.x = x;
        
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_L) && health < 3 && handler.DEBUG) { //adds life one by one
    		health ++;}
        }
    @Override
    public void render(Graphics g) {
         if (destroyed){
             if (deathAnimation.end){
                 g.drawString("READY",handler.getWidth()/2-handler.getWidth()/12,handler.getHeight()/2);
             }else {
                 g.drawImage(deathAnimation.getCurrentFrame(), x, y, width, height, null);
             }
         }else {
             super.render(g);
         }
    }

    @Override
    public void damage(BaseEntity damageSource) {
        if (damageSource instanceof PlayerLaser){
            return;
        }
        health--; //substracts health 
        destroyed = true;
        handler.getMusicHandler().playEffect("explosion.wav");
    }
    
    
    public int getHealth() {
    	if (health < 1) //when players run out of lifes, score turns 0 until it receives another life
    		handler.getScoreManager().removeGalagaCurrentScore(handler.getScoreManager().getGalagaCurrentScore()+100);
        	return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

}