package com.gamedemo;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class GameDemoRenderer implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final Context context;

    private int program;

    private static final int COLOR_COMPONENT_COUNT = 3;

    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final String A_COLOR = "a_Color";
    private int aColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private final FloatBuffer vertexData;

    float[] tableTriangleVertices = {
            // Order of coordinates: X, Y, R, G, B
            0f, 0f, 1f, 1f, 1f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,

            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f
    };

    public GameDemoRenderer(Context context) {
        this.context = context;
        vertexData = ByteBuffer
                .allocateDirect(tableTriangleVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableTriangleVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShaderSource =
                TextResourseReader.readTextFileFromResource(context, R.raw.vertex_shader);
        String fragmentShaderSource =
                TextResourseReader.readTextFileFromResource(context, R.raw.fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        aColorLocation = glGetAttribLocation(program, A_COLOR);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);

        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);

        glEnableVertexAttribArray(aPositionLocation);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, vertexData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glDrawArrays(GL_LINES, 6, 2);
        glDrawArrays(GL_POINTS, 8, 1);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
