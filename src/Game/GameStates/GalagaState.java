package Game.GameStates;

import Game.Galaga.Entities.EnemyAlien;
import Game.Galaga.Entities.EnemyBee;
import Game.Galaga.Entities.EntityManager;
import Game.Galaga.Entities.PlayerShip;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class GalagaState extends State {

	public EntityManager entityManager;
	public String Mode = "Menu";
	private Animation titleAnimation;
	public int selectPlayers = 1;
	public int startCooldown = 60*7;//seven seconds for the music to finish
	public int tickcounter = 0;
	public int spawnTime = 120;
	public boolean[][] beeFilled = new boolean [2][8]; //added boolean arrays for spawning enemies in their position. recommendation by Ta's
	boolean[][] enemyFilled = new boolean [2][4];
	boolean firstSpawn=true;
	public Integer spawnAgain[][]= new Integer[8][5]; 
    boolean spawnAgainCheck=true;
	

	public GalagaState(Handler handler){
		super(handler);
		refresh();
		entityManager = new EntityManager(new PlayerShip(handler.getWidth()/2-64,handler.getHeight()- handler.getHeight()/7,64,64,Images.galagaPlayer[0],handler));
		titleAnimation = new Animation(256,Images.galagaLogo);
	}


	@Override
	public void tick() {
		
		tickcounter++;
		if(tickcounter > 60*2){  //first prototype... spawned bees from time to time automatically, stopped to prevent overlapping
			spawnTime++;
			tickcounter=0;
		}
		if(spawnTime >= 1) {
			Random random = new Random(); //BEE SPAWN
			spawnTime = 0;
             for (int i = 0; i < 2; i++) {
                for(int k =0; k < 8;k++) {
                	//this if is to check if every row and column is empty
                	if(beeFilled[i][k]!=true) {
                	handler.getGalagaState().entityManager.entities.add(new EnemyBee(0, 0, 32, 32, handler, i +3, k)); //enemy spawns correctly but it does not spawn automatically without overlapping
                	//this is to stop the overlapping
                	beeFilled[i][k]=true;
                    handler.getGalagaState().entityManager.entities.add(new EnemyBee(0, 0, 32, 32, handler, i +3, k));
                	}
                }
            	
             }
            
             for (int i = 0; i < 2; i++) {
                 for(int k =0; k < 4;k++) {
                 	//this if is to check if every row and column is empty
                 	if(enemyFilled[i][k]!=true) {
                 	handler.getGalagaState().entityManager.entities.add(new EnemyAlien(0, 0, 32, 32, handler, i+1 , k+2)); // spawns new enemy
                 	//this is to stop the overlapping
                 	enemyFilled[i][k]=true;
                 	}
                 }
             	
              }

		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P) && handler.DEBUG){ //spawns enemy bee, if full. overlaps
			
			handler.getGalagaState().entityManager.entities.add(new EnemyBee(0, 0, 32, 32, handler,new Random().nextInt(2)+3 ,new Random().nextInt(8)));
		}
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_O) && handler.DEBUG){ // spawns enemy alien, if full. overlaps
			
			handler.getGalagaState().entityManager.entities.add(new EnemyAlien(0, 0, 32, 32, handler,new Random().nextInt(2)+1,new Random().nextInt()+2));
		}
		
		if (Mode.equals("Stage")){
			if (startCooldown<=0) {
				entityManager.tick(); 
			}else{
				startCooldown--;
			}
			
		}else{

			titleAnimation.tick();
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
				selectPlayers=1;
			}else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
				selectPlayers=2;
			}
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
				Mode = "Stage";
				handler.getMusicHandler().playEffect("Galaga.wav");

			}

		}

		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)) { //goes into debug mode for cheats
			handler.DEBUG = !handler.DEBUG;
		}

        if(handler.getScoreManager().getGalagaCurrentScore()>handler.getScoreManager().getGalagaHighScore()) {
       	 handler.getScoreManager().setGalagaHighScore(handler.getScoreManager().getGalagaCurrentScore()); //adds score
	}
 
}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0,0,handler.getWidth(),handler.getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(handler.getWidth()/4,0,handler.getWidth()/2,handler.getHeight());

		if (handler.DEBUG) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
			g.setColor(Color.YELLOW);
			g.drawString("debug mode",handler.getWidth()/2-handler.getWidth()/4,32);
		}else {

		}
		Random random = new Random(System.nanoTime());

		for (int j = 1;j < random.nextInt(15)+60;j++) {
			switch (random.nextInt(6)) {
			case 0:
				g.setColor(Color.RED);
				break;
			case 1:
				g.setColor(Color.BLUE);
				break;
			case 2:
				g.setColor(Color.YELLOW);
				break;
			case 3:
				g.setColor(Color.GREEN);
				break;
			case 4:
				g.setColor(Color.MAGENTA); //added colors
				break;
			case 5:
				g.setColor(Color.WHITE);
			}
			int randX = random.nextInt(handler.getWidth() - handler.getWidth() / 2) + handler.getWidth() / 4;
			int randY = random.nextInt(handler.getHeight());
			g.fillRect(randX, randY, 2, 2);



		}

		if (Mode.equals("Stage")) {
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 62));
			g.drawString("HIGH",handler.getWidth()-handler.getWidth()/4,handler.getHeight()/16);
			g.drawString("SCORE",handler.getWidth()-handler.getWidth()/4+handler.getWidth()/48,handler.getHeight()/8);
			g.setColor(Color.WHITE);//added score
			g.drawString(String.valueOf(handler.getScoreManager().getGalagaHighScore()),handler.getWidth()-handler.getWidth()/4+handler.getWidth()/48,handler.getHeight()/5);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.setColor(Color.WHITE);
			g.drawString("SCORE:",handler.getWidth()/2-handler.getWidth()/18,32);
			g.drawString(String.valueOf(handler.getScoreManager().getGalagaHighScore()),handler.getWidth()/2-32,64);
			
			for (int i = 0; i< entityManager.playerShip.getHealth();i++) {
				g.drawImage(Images.galagaPlayer[0], (handler.getWidth() - handler.getWidth() / 4 + handler.getWidth() / 48) + ((entityManager.playerShip.width*2)*i), handler.getHeight()-handler.getHeight()/4, handler.getWidth() / 18, handler.getHeight() / 18, null);
			}
			if (startCooldown<=0) {
				entityManager.render(g);
			}else{
				g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
				g.setColor(Color.MAGENTA);
				g.drawString("Start",handler.getWidth()/2-handler.getWidth()/18,handler.getHeight()/2);
			}
		}else{

			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));

			g.setColor(Color.MAGENTA);
			g.drawString("SCORE:",handler.getWidth()/2-handler.getWidth()/18,32);

			g.setColor(Color.WHITE);
			
			g.drawString(String.valueOf(handler.getScoreManager().getGalagaHighScore()),handler.getWidth()/2-32,64);

			g.drawImage(titleAnimation.getCurrentFrame(),handler.getWidth()/2-(handler.getWidth()/12),handler.getHeight()/2-handler.getHeight()/3,handler.getWidth()/6,handler.getHeight()/7,null);

			g.drawImage(Images.galagaCopyright,handler.getWidth()/2-(handler.getWidth()/8),handler.getHeight()/2 + handler.getHeight()/3,handler.getWidth()/4,handler.getHeight()/8,null);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
			g.drawString("1   PLAYER",handler.getWidth()/2-handler.getWidth()/16,handler.getHeight()/2);
			g.drawString("2   PLAYER",handler.getWidth()/2-handler.getWidth()/16,handler.getHeight()/2+handler.getHeight()/12);
			if (selectPlayers == 1){
				g.drawImage(Images.galagaSelect,handler.getWidth()/2-handler.getWidth()/12,handler.getHeight()/2-handler.getHeight()/32,32,32,null);
			}else{
				g.drawImage(Images.galagaSelect,handler.getWidth()/2-handler.getWidth()/12,handler.getHeight()/2+handler.getHeight()/18,32,32,null);
			}
			 
   

		}            
	}
 public void firstSpawn() { //first spawn
	 for(int i =0; i<8; i++) {
		 for(int k=0;k<5;k++) {
			 if(k>=3) {
				 handler.getGalagaState().entityManager.entities.add(new EnemyBee(0,0,32,32,handler,k,i));
			 }else {
				 handler.getGalagaState().entityManager.entities.add(new EnemyAlien(0,0,32,32,handler,k,i));
			 }
		 }
	 }
	  }
	 
	 public void spawnAgainCheck() { //checks if enemy spawned. for random spawn after death
		 for (int i =0; i<8;i++) {
			 for(int k=0;k<5;k++) {
				 if(spawnAgain[i][k] != null) {
					 if(spawnAgain[i][k] >= 60*(new Random().nextInt(4)+5)) {
						 spawnAgain[i][k]=null;
						 if(k>=3) {
							 handler.getGalagaState().entityManager.entities.add(new EnemyBee(0,0,32,32,handler,k,i));
						 }else {
							 handler.getGalagaState().entityManager.entities.add(new EnemyAlien(0,0,32,32,handler,k,i));
							 
						 }
					 }else { 
						 spawnAgain[i][k]++;
					 }
				 }
			 }
		 }
	 
	 
	 
	 
	 
	 
 }
	
	
	@Override
	public void refresh() {

	}
	
	
	
	              
	
	
	
	
	
}
