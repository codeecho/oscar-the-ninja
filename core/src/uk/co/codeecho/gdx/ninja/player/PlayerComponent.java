package uk.co.codeecho.gdx.ninja.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import uk.co.codeecho.gdx.forge.Renderable;
import uk.co.codeecho.gdx.forge.Updatable;
import uk.co.codeecho.gdx.forge.View;
import uk.co.codeecho.gdx.forge.action.ActionManager;
import uk.co.codeecho.gdx.forge.action.ContinuousAction;
import uk.co.codeecho.gdx.forge.component.IdentifiedComponent;
import uk.co.codeecho.gdx.forge.library.state.Direction;

public class PlayerComponent implements IdentifiedComponent, Renderable, Updatable{
    
    private final PlayerConfiguration configuration;
    private final PlayerViewModel viewModel;
    private final View view;
    private final ActionManager actionManager;

    public PlayerComponent(PlayerConfiguration configuration, PlayerViewModel viewModel, View view) {
        this.configuration = configuration;
        this.viewModel = viewModel;
        this.view = view;
        this.actionManager = new ActionManager();
        addActions();
    }

    private void addActions() {
        actionManager.addAction(new ContinuousAction() {

            @Override
            public void doInvoke(float delta) {
                Vector2 velocity = viewModel.getBody().getLinearVelocity();
                if(Math.abs(velocity.y) < 0.001){
                    velocity.y = 0;
                }
                if (velocity.y < 0) {
                    viewModel.setState(PlayerStates.FALLING);
                    viewModel.isOnGround(false);
                }
                if (!viewModel.isOnGround() && velocity.y == 0) {
                    viewModel.isOnGround(true);
                    if (viewModel.getState().equals(PlayerStates.FALLING)) {
                        viewModel.setState(PlayerStates.IDLE);
                    }
                }
            }
        });
    }
    
    public void moveLeft() {
        if (!viewModel.isJumping()) {
            viewModel.setState(PlayerStates.WALKING);
        }
        viewModel.setDirection(Direction.LEFT);
        Body body = viewModel.getBody();
        if (body.getLinearVelocity().x > - configuration.getSpeed()) {
            viewModel.getBody().applyForceToCenter(new Vector2(-configuration.getAcceleration(), 0), true);
        }
    }

    public void moveRight() {
        if (!viewModel.isJumping()) {
            viewModel.setState(PlayerStates.WALKING);
        }
        viewModel.setDirection(Direction.RIGHT);
        Body body = viewModel.getBody();
        if (body.getLinearVelocity().x < configuration.getSpeed()) {
            viewModel.getBody().applyForceToCenter(new Vector2(configuration.getAcceleration(), 0), true);
        }
    }

    public void idle() {
        if (!viewModel.isJumping()) {
            viewModel.setState(PlayerStates.IDLE);
        }
        viewModel.getBody().setLinearVelocity(0, viewModel.getBody().getLinearVelocity().y);
    }

    public void jump() {
        if (viewModel.canJump()) {
            viewModel.setState(PlayerStates.JUMPING);
            viewModel.isOnGround(false);
            Body body = viewModel.getBody();
            body.applyForceToCenter(new Vector2(0, configuration.getJumpForce()), true);
        }
    }

    public void stopJumping() {
        if (viewModel.isJumping()) {
            if (viewModel.getBody().getLinearVelocity().y > configuration.getMinJumpSpeed()) {
                viewModel.getBody().setLinearVelocity(viewModel.getBody().getLinearVelocity().x, configuration.getMinJumpSpeed());
            }
        }
    }

    @Override
    public String getId() {
        return "player";
    }
    
    @Override
    public void update(float delta) {
        actionManager.update(delta);
        view.update(delta);
    }

    @Override
    public void render() {
        view.render();
    }
    
    public Body getBody(){
        return viewModel.getBody();
    }
    
    public boolean isJumping(){
        return viewModel.isJumping();
    }
    
}
