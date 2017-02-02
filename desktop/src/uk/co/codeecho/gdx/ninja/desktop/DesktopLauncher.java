package uk.co.codeecho.gdx.ninja.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import uk.co.codeecho.gdx.ninja.Game;

public class DesktopLauncher {

    // TODO: Fix html package
    // TODO: Use gdx-forge v0.1.0 release
    // TODO: Push to github
    // TODO: Deploy html to gh-pages
    // TODO: Parameterize level screen
    
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 896;
        config.height = 512;
        new LwjglApplication(new Game(), config);
    }
}
