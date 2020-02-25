package Game.GameStates;

import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

import java.awt.*;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class GameState extends State {

    private UIManager uiManager;

    public GameState(Handler handler){
        super(handler);
        refresh();
    }


    @Override
    public void tick() {

        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.selectionBackground,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);
        g.drawString(String.valueOf(handler.getScoreManager().getGalagaHighScore()),handler.getWidth()/18,32);
    }

    @Override
    public void refresh() {
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton((handler.getWidth() / 2) - (handler.getWidth() /3) + 24, (handler.getHeight() /2)-(handler.getHeight() /32), handler.getWidth()/7, handler.getHeight()/6, Images.galagaLogo, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getMusicHandler().triggerGalaga();
                State.setState(handler.getGalagaState());
                handler.changeState(handler.getGalagaState());
                
            }
        }));
    }
}
