package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class GameInputHandler extends ApplicationAdapter implements InputProcessor {

    GameController control;

    public GameInputHandler(GameController control){
        this.control = control;

    }
    public boolean keyDown(int keycode) {
        control.processInput("keyDown",new int[]{keycode});
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        control.processInput("keyUp",new int[]{keycode});
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
