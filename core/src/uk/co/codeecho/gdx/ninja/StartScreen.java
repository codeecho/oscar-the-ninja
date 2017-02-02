package uk.co.codeecho.gdx.ninja;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import uk.co.codeecho.gdx.forge.GameManager;
import uk.co.codeecho.gdx.forge.camera.StandardCamera;
import uk.co.codeecho.gdx.forge.event.Event;
import uk.co.codeecho.gdx.forge.event.EventHandler;
import uk.co.codeecho.gdx.forge.input.KeyDownEvent;
import uk.co.codeecho.gdx.forge.input.KeyDownEventListener;
import uk.co.codeecho.gdx.forge.library.action.ChangeScreenAction;
import uk.co.codeecho.gdx.forge.library.event.listener.SpriteBatchEventListener;
import uk.co.codeecho.gdx.forge.library.component.TextureComponent;
import uk.co.codeecho.gdx.forge.screen.Screen;

public class StartScreen extends Screen{

    public StartScreen() {
        OrthographicCamera camera = new StandardCamera();
        
        SpriteBatch spriteBatch = new SpriteBatch();
        addComponent(new SpriteBatchEventListener(spriteBatch, camera));
        
        Texture texture = new Texture("oscar-the-ninja.png");
        Rectangle displayBounds = GameManager.getInstance().getDisplayBoundsInUnits();
        addComponent(new TextureComponent(displayBounds, texture, spriteBatch));
        
        addEventListener(KeyDownEvent.class, new ChangeScreenAction(Screens.LEVEL1));
    }

}
