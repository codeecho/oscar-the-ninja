package uk.co.codeecho.gdx.ninja;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import uk.co.codeecho.gdx.forge.GameManager;
import uk.co.codeecho.gdx.forge.box2d.collision.event.CollisionStartEventFilter;
import uk.co.codeecho.gdx.forge.box2d.collision.event.PreCollisionEvent;
import uk.co.codeecho.gdx.forge.box2d.collision.event.PreCollisionEventListener;
import uk.co.codeecho.gdx.forge.box2d.debug.Box2DDebugRenderer;
import uk.co.codeecho.gdx.forge.box2d.fixture.filter.BodyTypeFixtureFilter;
import uk.co.codeecho.gdx.forge.box2d.fixture.filter.ComponentFixtureFilter;
import uk.co.codeecho.gdx.forge.box2d.world.WorldBuilder;
import uk.co.codeecho.gdx.forge.box2d.world.WorldStepAction;
import uk.co.codeecho.gdx.forge.component.builder.ComponentBuilderFactory;
import uk.co.codeecho.gdx.forge.input.KeyDownEvent;
import uk.co.codeecho.gdx.forge.input.KeyDownEventListener;
import uk.co.codeecho.gdx.forge.input.KeyPressedEvent;
import uk.co.codeecho.gdx.forge.input.KeyPressedEventListener;
import uk.co.codeecho.gdx.forge.input.KeyUpEvent;
import uk.co.codeecho.gdx.forge.input.KeyUpEventListener;
import uk.co.codeecho.gdx.forge.library.action.ChangeScreenAction;
import uk.co.codeecho.gdx.forge.library.action.UpdateCameraAction;
import uk.co.codeecho.gdx.forge.library.camera.Box2DBoundedTrackingCamera;
import uk.co.codeecho.gdx.forge.library.event.listener.SpriteBatchEventListener;
import uk.co.codeecho.gdx.forge.library.parrallax.ParrallaxBackground;
import uk.co.codeecho.gdx.ninja.player.PlayerComponent;
import uk.co.codeecho.gdx.forge.screen.Screen;
import uk.co.codeecho.gdx.forge.screen.ScreenLayer;
import uk.co.codeecho.gdx.forge.tmx.TMXMapBuilder;
import uk.co.codeecho.gdx.forge.tmx.TMXMapBuilderImpl;
import uk.co.codeecho.gdx.forge.tmx.TMXMapProperties;
import uk.co.codeecho.gdx.forge.tmx.TMXMapUtils;

public class LevelScreen extends Screen {

    private final Box2DBoundedTrackingCamera camera;

    private final PlayerComponent player;

    public LevelScreen() {
        SpriteBatch spriteBatch = new SpriteBatch();
        World world = new WorldBuilder().setGravity(new Vector2(0, -20f)).build();

        addAction(new WorldStepAction(world));

        TiledMap map = new TmxMapLoader().load("platformer-demo.tmx");
        TMXMapProperties mapProperties = TMXMapUtils.getMapProperties(map);

        GameManager gameManager = GameManager.getInstance();

        camera = new Box2DBoundedTrackingCamera(gameManager.getDisplayWidthInUnits()*2, gameManager.getDisplayHeightInUnits()*2, 0, mapProperties.getWidth(), 0, mapProperties.getHeight(), 1, null);

        ScreenLayer parralaxLayer = new ScreenLayer("backgrounds");
        parralaxLayer.addComponent(new ParrallaxBackground(camera, spriteBatch, new Texture("bg3.png"), mapProperties.getWidth(), mapProperties.getHeight(), 0.5f, false, true));
        addLayer(parralaxLayer);

        ComponentBuilderFactory componentBuilderFactory = new uk.co.codeecho.gdx.ninja.ComponentBuilderFactory(spriteBatch, world);
        TMXMapBuilder mapBuilder = new TMXMapBuilderImpl(map, componentBuilderFactory, camera, spriteBatch);
        addLayers(mapBuilder.build());

        addComponent(new SpriteBatchEventListener(spriteBatch, camera));
        
        if(gameManager.isDebugMode()){
            addComponent(new Box2DDebugRenderer(world, camera, spriteBatch));
        }

        player = (PlayerComponent) getComponent("player");

        camera.setTarget(player.getBody());
        
        camera.update();

        addAction(new UpdateCameraAction(camera));

        addEventListeners();
    }

    private void addEventListeners() {
        addEventListener(new KeyPressedEventListener() {

            @Override
            public void doHandle(KeyPressedEvent event) {
                int key = event.getKey();
                if (key == Input.Keys.RIGHT) {
                    player.moveRight();
                } else if (key == Input.Keys.LEFT) {
                    player.moveLeft();
                }
            }
        });
        addEventListener(new KeyDownEventListener(Input.Keys.UP) {

            @Override
            public void doHandle(KeyDownEvent event) {
                player.jump();
            }
        });
        addEventListener(new KeyUpEventListener(Input.Keys.LEFT, Input.Keys.RIGHT) {

            @Override
            public void doHandle(KeyUpEvent event) {
                player.idle();
            }
        });
        addEventListener(new KeyUpEventListener(Input.Keys.UP) {

            @Override
            public void doHandle(KeyUpEvent event) {
                player.stopJumping();
            }
        });
        addEventListener(new PreCollisionEventListener(new ComponentFixtureFilter(player), new BodyTypeFixtureFilter("platform")) {

            @Override
            public void doHandle(PreCollisionEvent event) {
                if (player.isJumping()) {
                    event.getCollision().cancel();
                }
            }
        });
        addEventListener(new CollisionStartEventFilter(new ComponentFixtureFilter(player), new BodyTypeFixtureFilter("end")), new ChangeScreenAction(Screens.GAME_COMPLETE));
        addEventListener(new CollisionStartEventFilter(new ComponentFixtureFilter(player), new BodyTypeFixtureFilter("death")), new ChangeScreenAction(Screens.GAME_OVER));
    }

}
