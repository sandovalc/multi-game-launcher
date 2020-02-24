package Main;

import Display.DisplayScreen;
import Game.GameStates.*;
import Input.KeyManager;
import Input.MouseManager;
import Resources.Images;
import Resources.MusicHandler;
import Resources.ScoreManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;


/**
 * Created by AlexVR on 1/24/2020.
 */

public class GameSetUp implements Runnable {
    private DisplayScreen display;
    private int width, height;
    private String title;

    private boolean running = false;
    private Thread thread;


    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //Handler
    private Handler handler;

    //States
    public State gameState;
    public State menuState;
    public State pauseState;
    public State galagaState;




    GameSetUp(String title, int width, int height){

        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

    }

    private void init(){
        display = new DisplayScreen(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        //checks for all images to exist
        Images img = new Images();


        handler = new Handler(this);
        handler.setDisplayScreen(display);

        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        pauseState = new PauseState(handler);
        galagaState = new GalagaState(handler);

        handler.setScoreManager(new ScoreManager(handler));
        handler.setMusicHandler(new MusicHandler(handler));
        handler.getMusicHandler().startMusic("nature.wav");

        handler.changeState(menuState);

    }


    public void reStart(boolean clearScore){
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        pauseState = new PauseState(handler);
        if (clearScore){
            handler.setScoreManager(new ScoreManager(handler));
        }
        handler.changeState(menuState);

    }

     synchronized void start(){
        if(running)
            return;
        running = true;
        //this runs the run method in this  class
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        //initiallizes everything in order to run without breaking
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            //makes sure the games runs smoothly at 60 FPS
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                //re-renders and ticks the game around 60 times per second
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
        }

        stop();

    }

    private void tick(){
        //checks for key types and manages them
        keyManager.tick();


        if (keyManager.keyJustPressed(KeyEvent.VK_F11)){
            handler.setFullScreen(display.flipFullScreen());
        }

        if (keyManager.keyJustPressed(KeyEvent.VK_R)){
            handler.getState().refresh();
        }

        if (keyManager.keyJustPressed(KeyEvent.VK_M)){
            if (handler.isMute()){
                handler.getMusicHandler().resumeMusic();
            }else{
                handler.getMusicHandler().pauseMusic();
            }
        }


        //game states are the menus
        if(State.getState() != null)
            State.getState().tick();
    }

    private void render(){
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3); 
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);

        //Draw Here!
        if(State.getState() != null) {
            State.getState().render(g);
        }
        if (handler.isMute()){
            g.drawImage(Images.muteIcon,12,12,64,64,null);
        }

        //End Drawing!
        bs.show();
        g.dispose();
    }

    private synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     KeyManager getKeyManager(){
        return keyManager;
    }

    MouseManager getMouseManager(){
        return mouseManager;
    }

}

