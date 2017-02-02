package uk.co.codeecho.gdx.ninja;

import uk.co.codeecho.gdx.forge.GdxForgeGame;

public class Game extends GdxForgeGame {

    @Override
    protected float getPixelsPerUnit() {
        return 64f;
    }

    @Override
    protected ScreenFactory createScreenFactory() {
        return new ScreenFactory();
    }

    @Override
    protected String getFirstScreen() {
        return Screens.START;
    }

}
