package com.example.glcamera.filter;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TEXTURE0;

import com.example.glcamera.R;
import com.example.glcamera.utils.OpenGLUtils;

public class CameraFilter {
    float[] VERTEX = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f
    };

    float[] TEXTURE = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
    };

    float[] TEXTURE_BACK = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    float[] TEXTURE_FRONT = {
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    FloatBuffer vertexBuffer; //顶点坐标
    FloatBuffer textureBuffer; // 纹理坐标

    private int program;
    private int vPosition;
    private int vCoord;
    private int vTexture;
    private int vMatrix;

    private float[] mtx;
    private int mWidth;
    private int mHeight;

    public CameraFilter(Context context) {
        vertexBuffer =  ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.clear();
        vertexBuffer.put(VERTEX);

        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureBuffer.clear();
        textureBuffer.put(TEXTURE);

        String vertexSharder = OpenGLUtils.readRawTextFile(context, R.raw.camera_vert);
        String fragSharder = OpenGLUtils.readRawTextFile(context, R.raw.camera_frag);
        program = OpenGLUtils.loadProgram(vertexSharder, fragSharder);

        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        vCoord = GLES20.glGetAttribLocation(program, "vCoord");
        vTexture = GLES20.glGetUniformLocation(program, "vTexture");
        vMatrix = GLES20.glGetUniformLocation(program, "vMatrix");
    }

    public void setTransformMatrix(float[] mtx) {
        this.mtx = mtx;
    }

    public void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    //渲染
    public void onDraw(float[] mtx, int texture) {
        GLES20.glViewport(0, 0, mWidth, mHeight);
        GLES20.glUseProgram(program);
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(vPosition,2,GL_FLOAT, false,0,vertexBuffer);
        GLES20.glEnableVertexAttribArray(vPosition);

        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(vCoord, 2, GLES20.GL_FLOAT,false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(vCoord);

        //gpu获取读取
        GLES20.glActiveTexture(GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(vTexture, 0);
        GLES20.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }
}
