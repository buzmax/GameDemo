package com.gamedemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by macuser on 6/11/15.
 */
public class GameSurfaceView extends GLSurfaceView {

    public GameSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new GameDemoRenderer(context));
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(new GameDemoRenderer(context));
    }
}
