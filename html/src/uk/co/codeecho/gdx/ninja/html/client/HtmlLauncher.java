package uk.co.codeecho.gdx.ninja.html.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import uk.co.codeecho.gdx.ninja.Game;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(896, 512);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Game();
        }
}