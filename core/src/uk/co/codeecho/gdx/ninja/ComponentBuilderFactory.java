package uk.co.codeecho.gdx.ninja;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import uk.co.codeecho.gdx.forge.component.builder.ComponentBuilder;
import uk.co.codeecho.gdx.forge.component.builder.ComponentBuilderFactoryImpl;
import uk.co.codeecho.gdx.forge.component.builder.ComponentBuilderProvider;
import uk.co.codeecho.gdx.forge.library.component.builder.provider.BlockBuilderProvider;
import uk.co.codeecho.gdx.forge.library.component.builder.provider.PlatformBuilderProvider;
import uk.co.codeecho.gdx.forge.library.component.builder.provider.TextureComponentBuilderProvider;
import uk.co.codeecho.gdx.ninja.player.PlayerComponentBuilder;

public class ComponentBuilderFactory extends ComponentBuilderFactoryImpl{

    public ComponentBuilderFactory(final SpriteBatch spriteBatch, final World world) {
        addProvider("logo", new TextureComponentBuilderProvider(new Texture("badlogic.jpg"), spriteBatch));
        
        addProvider("player", new ComponentBuilderProvider() {

            @Override
            public ComponentBuilder getBuilder() {
                return new PlayerComponentBuilder(world, spriteBatch);
            }
        });
        
        addProvider("block", new BlockBuilderProvider(world));
        addProvider("platform", new PlatformBuilderProvider(world));
        addProvider("end", new BlockBuilderProvider(world, "end"));
        addProvider("death", new BlockBuilderProvider(world, "death"));
    }

}
