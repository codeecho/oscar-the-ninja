package uk.co.codeecho.gdx.ninja.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import uk.co.codeecho.gdx.forge.box2d.util.Box2DUtils;
import uk.co.codeecho.gdx.forge.library.component.builder.RectangularComponentBuilder;
import uk.co.codeecho.gdx.forge.util.CoordinateUtils;

public class PlayerComponentBuilder extends RectangularComponentBuilder<PlayerComponentBuilder, PlayerComponent> {

    private final World world;
    private final SpriteBatch spriteBatch;

    public PlayerComponentBuilder(World world, SpriteBatch spriteBatch) {
        this.world = world;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public PlayerComponent build() {
        CoordinateUtils.recalculateRectangleAroundCentralCoordinates(bounds);
        PlayerConfiguration configuration = new PlayerConfiguration();
        PlayerViewModel viewModel = new PlayerViewModel(world, bounds.x, bounds.y, bounds.width, bounds.height);
        PlayerView view = new PlayerView(viewModel, spriteBatch);
        PlayerComponent component = new PlayerComponent(configuration, viewModel, view);
        Box2DUtils.setComponent(viewModel.getBody(), component);
        return component;
    }

}
