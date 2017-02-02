package uk.co.codeecho.gdx.ninja.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import uk.co.codeecho.gdx.forge.View;
import uk.co.codeecho.gdx.forge.animation.AnimationHandler;
import uk.co.codeecho.gdx.forge.box2d.animation.Box2DModelAnimationFrameRenderer;
import uk.co.codeecho.gdx.forge.library.predicate.DirectionEqualsPredicate;
import uk.co.codeecho.gdx.forge.library.predicate.StatusEqualsPredicate;
import uk.co.codeecho.gdx.forge.library.state.Direction;
import uk.co.codeecho.gdx.forge.predicate.PredicateUtils;
import uk.co.codeecho.gdx.forge.texture.TextureMap;
import uk.co.codeecho.gdx.forge.texture.TextureUtils;

public class PlayerView implements View{

    private final AnimationHandler animationHandler;
    
    public PlayerView(PlayerViewModel viewModel, SpriteBatch spriteBatch) {
        animationHandler = new AnimationHandler(viewModel, new Box2DModelAnimationFrameRenderer(spriteBatch));
        float frameRate = 0.1f;
        TextureMap idleTextureMap = new TextureUtils().split(new Texture("ninja/Idle.png"), 2, 1);
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.IDLE)).and(new DirectionEqualsPredicate(Direction.RIGHT)).build(), new Animation(frameRate, idleTextureMap.get(0, 0)));
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.IDLE)).and(new DirectionEqualsPredicate(Direction.LEFT)).build(), new Animation(frameRate, idleTextureMap.get(1, 0)));
        TextureMap walkTextureMap = new TextureUtils().split(new Texture("ninja/Run.png"), 5, 4);
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.WALKING)).and(new DirectionEqualsPredicate(Direction.RIGHT)).build(), new Animation(frameRate, walkTextureMap.from(0, 2).by(4,1).asArray(), Animation.PlayMode.LOOP));
        Array<TextureRegion> walkLeftFrames = walkTextureMap.from(0, 0).by(4, 1).asArray();
        walkLeftFrames.reverse();
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.WALKING)).and(new DirectionEqualsPredicate(Direction.LEFT)).build(), new Animation(frameRate, walkLeftFrames, Animation.PlayMode.LOOP));
        TextureMap jumpTextureMap = new TextureUtils().split(new Texture("ninja/Jump.png"), 2, 2);
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.JUMPING)).and(new DirectionEqualsPredicate(Direction.RIGHT)).build(), new Animation(frameRate, jumpTextureMap.get(0, 1)));
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.JUMPING)).and(new DirectionEqualsPredicate(Direction.LEFT)).build(), new Animation(frameRate, jumpTextureMap.get(1, 0)));
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.FALLING)).and(new DirectionEqualsPredicate(Direction.RIGHT)).build(), new Animation(frameRate, jumpTextureMap.get(1, 1)));
        animationHandler.addAnimation(PredicateUtils.when(new StatusEqualsPredicate(PlayerStates.FALLING)).and(new DirectionEqualsPredicate(Direction.LEFT)).build(), new Animation(frameRate, jumpTextureMap.get(0, 0)));
        
    }

    @Override
    public void update(float delta) {
        animationHandler.update(delta);
    }

    @Override
    public void render() {
        animationHandler.render();
    }

}
