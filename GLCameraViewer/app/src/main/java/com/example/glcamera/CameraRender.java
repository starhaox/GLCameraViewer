package com.example.glcamera;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import com.example.glcamera.filter.CameraFilter;
import com.example.glcamera.utils.CameraHelper;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {
    private CameraView cameraView;
    private CameraHelper cameraHelper;
    private SurfaceTexture mCameraTexure;
    private int[] textures;
    float[] mtx = new float[16];
    private CameraFilter screenFilter;
    public CameraRender(CameraView cameraView) {
        this.cameraView = cameraView;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
        // 打开摄像头
        cameraHelper = new CameraHelper(lifecycleOwner, this);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        textures = new int[1];
        //让 SurfaceTexture与 Gpu  共享一个数据源
        mCameraTexure.attachToGLContext(textures[0]);
        //监听摄像头数据回调，
        mCameraTexure.setOnFrameAvailableListener(this);
        screenFilter = new CameraFilter(cameraView.getContext());

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        screenFilter.setSize(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 摄像头的数据给gpu
        mCameraTexure.updateTexImage();
        mCameraTexure.getTransformMatrix(mtx);
        //screenFilter.setTransformMatrix(mtx);
        screenFilter.onDraw(mtx, textures[0]);
    }

    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        //摄像头预览到的数据 在这里
        mCameraTexure=output.getSurfaceTexture();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        cameraView.requestRender();
    }
}
