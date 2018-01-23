package com.ttoextreme.everyapp.RenderEngine.Obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class GL_Cube {

    public float[] vertices = { // 5 vertices of the pyramid in (x,y,z)
            -0.5f, -0.5f,  0.5f,  // 0. left-bottom-front
            0.5f, -0.5f,  0.5f,  // 1. right-bottom-front
            -0.5f,  0.5f,  0.5f,  // 2. left-top-front
            0.5f,  0.5f,  0.5f,  // 3. right-top-front
            -0.5f, -0.5f, -0.5f,  // 4. left-bottom-back
            -0.5f,  0.5f, -0.5f,  // 5. left-top-back
            0.5f, -0.5f, -0.5f,  // 6. right-bottom-back
            0.5f,  0.5f, -0.5f,  // 7. right-top-back
    };

    public float[][] colors = {  // Colors of the 6 faces
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            {1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };

    public byte[] indices = { // Vertex indices of the 4 Triangles
            0, 1, 2, 3,  // front face
            6, 4, 7, 5,  // front face
            4, 0, 5, 2,  // front face
            1, 6, 3, 7,  // front face
            2, 3, 5, 7,  // front face
            4, 6, 0, 1,  // front face
    };
    public int faces = 6;
}
