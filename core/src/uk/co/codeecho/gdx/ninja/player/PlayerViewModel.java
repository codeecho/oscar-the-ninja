package uk.co.codeecho.gdx.ninja.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import uk.co.codeecho.gdx.forge.GameManager;
import uk.co.codeecho.gdx.forge.box2d.Box2DModel;
import uk.co.codeecho.gdx.forge.box2d.body.builder.BodyBuilder;
import uk.co.codeecho.gdx.forge.box2d.fixture.builder.FixtureBuilder;
import uk.co.codeecho.gdx.forge.library.box2d.BoxShape;
import uk.co.codeecho.gdx.forge.library.state.Direction;
import uk.co.codeecho.gdx.forge.library.state.Directional;
import uk.co.codeecho.gdx.forge.library.state.Stateful;

public class PlayerViewModel implements Box2DModel, Stateful, Directional{
    
    private static Body buildBody(World world, float x, float y, float width, float height) {
        Body body = new BodyBuilder(world, BodyDef.BodyType.DynamicBody)
                .setType("player")
                .setPosition(new Vector2(x, y))
                .build();
        new FixtureBuilder()
                .setId("wrapper")
                .isSensor(true)
                .setShape(new BoxShape(width, height))
                .build(body);
        new FixtureBuilder()
                .setId("boundary")
                .setShape(new BoxShape(width*0.5f, height))
                .build(body);
//        new FixtureBuilder()
//                .setId("foot")
//                .isSensor(true)
//                .setShape(new BoxShape(width/2, GameManager.getInstance().pixelsToUnits(2), new Vector2(0, (-height/2)-GameManager.getInstance().pixelsToUnits(1))))
//                .build(body);
        return body;
    }
    
    private final Body body;
    
    private String state = PlayerStates.IDLE;
    private Direction direction = Direction.RIGHT;
    private boolean isOnGround = false;
    private boolean injured = false;
    
    public PlayerViewModel(World world, float x, float y, float width, float height) {
        this.body = buildBody(world, x, y, width, height);
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public boolean isJumping(){
        return getState().equals(PlayerStates.JUMPING);
    }
    
    public boolean canJump(){
        return isOnGround();
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void isOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean isInjured() {
        return injured;
    }

    public void setInjured(boolean injured) {
        this.injured = injured;
    }
    
}
