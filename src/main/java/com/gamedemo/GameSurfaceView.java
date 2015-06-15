package com.gamedemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GameSurfaceView extends GLSurfaceView {

    private Context context;
    GameDemoRenderer renderer;

    public GameSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        this.context = context;
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        this.context = context;
    }

    public void createGameRenderer() {
        renderer = new GameDemoRenderer(context);
        setRenderer(renderer);
    }

    public GameDemoRenderer getRenderer() {
        return renderer;
    }
}
