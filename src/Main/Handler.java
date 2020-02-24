package Main;

import Display.DisplayScreen;
import Game.GameStates.*;
import Input.KeyManager;
import Input.MouseManager;
import Resources.MusicHandler;
import Resources.ScoreManager;

import javax.sound.sampled.Clip;
import java.util.ArrayList;


/**
 * Created by AlexVR on 1/24/2020.
 */

public class Handler {

    private GameSetUp game;
    private DisplayScreen screen;
    private ScoreManager scoreManager;
    private boolean fullScreen = false,mute=false;
    private Clip backgroundMusic;
    private MusicHandler musicHandler;
    private ArrayList<Clip> effects;
    private State lastState;
    public static boolean DEBUG = false;

    public Handler(GameSetUp game){
        this.game = game;
    }

    public int getWidth(){
        return getDisplayScreen().getCanvas().getWidth();
    }

    public int getHeight(){

        return getDisplayScreen().getCanvas().getHeight();
    }

    public GameSetUp getGameProperties() {
        return game;
    }

    public void setGameProperties(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

    public DisplayScreen getDisplayScreen(){return screen;}

    public void setDisplayScreen(DisplayScreen screen){this.screen=screen;}

    public GameState getGameState (){
        return (GameState)getGameProperties().gameState;
    }

    public MenuState getMenuState (){
        return (MenuState) getGameProperties().menuState;
    }

    public PauseState getPauseState (){
        return (PauseState) getGameProperties().pauseState;
    }

    public GalagaState getGalagaState (){
        return (GalagaState)getGameProperties().galagaState;
    }

    public void changeState(State state){
        State.setState(state);
    }

    public State getState(){
        return State.getState();
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public void setScoreManager(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public Clip getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(Clip backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public MusicHandler getMusicHandler() {
        return musicHandler;
    }

    public void setMusicHandler(MusicHandler musicHandler) {
        this.musicHandler = musicHandler;
    }

    public ArrayList<Clip> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Clip> effects) {
        this.effects = effects;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }
}
