package com.example.glcamera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class CameraView extends GLSurfaceView {

    private  CameraRender renderer;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        renderer = new CameraRender(this);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
