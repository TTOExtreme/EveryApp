package com.ttoextreme.everyapp.RenderEngine;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.RenderEngine.Obj.GL_Obj_Struct;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class GLSurface implements GLSurfaceView.Renderer{
    private MainScreen Main;
    private Render Engine;


    private static float angleCube = 0;    // Rotational angle in degree for cube (NEW)
    private static float speedCube = -1.5f;   // Rotational speed for cube (NEW)

    public GLSurface(MainScreen main, Render render){
        Main=main;
        Engine = render;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);gl.glLoadIdentity();                // Reset the model-view matrix
        if(Engine.Objects!=null) {
            for (int i = 0; i < Engine.Objects.size(); i++) {
                gl.glLoadIdentity();                // Reset the model-view matrix
                gl.glTranslatef(Engine.Objects.get(i).Translate[0], Engine.Objects.get(i).Translate[1], Engine.Objects.get(i).Translate[2]); // Translate right and into the screen
                gl.glScalef(Engine.Objects.get(i).scale[0], Engine.Objects.get(i).scale[1], Engine.Objects.get(i).scale[2]);      // Scale down (NEW)
                gl.glRotatef(Engine.Objects.get(i).angle, Engine.Objects.get(i).rotation[0], Engine.Objects.get(i).rotation[1], Engine.Objects.get(i).rotation[2]); // rotate about the axis (1,1,1) (NEW)
                Draw(gl, Engine.Objects.get(i));
                Engine.Objects.get(i).angle += Engine.Objects.get(i).rotation_speed;
            }
        }
    }

    public void Draw(GL10 gl, GL_Obj_Struct gl_obj_struct){
        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        //gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        if(gl_obj_struct.vertex==null){gl_obj_struct.vertex=GetVertexBuffer(gl_obj_struct);}
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, gl_obj_struct.vertex);

        // Render all the faces
        for (int face = 0; face < gl_obj_struct.faces; face++) {
            // Set the color for each of the faces
            gl.glColor4f(gl_obj_struct.colors[face][0], gl_obj_struct.colors[face][1], gl_obj_struct.colors[face][2], gl_obj_struct.colors[face][3]);
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }

    public FloatBuffer GetVertexBuffer(GL_Obj_Struct gl_obj_struct){
        FloatBuffer vertexBuffer;

        ByteBuffer vbb = ByteBuffer.allocateDirect(getVertices(gl_obj_struct).length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(getVertices(gl_obj_struct));         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
        return vertexBuffer;
    }


    public float[] getVertices(GL_Obj_Struct gl_obj_struct){
        float[] vetices = new float[gl_obj_struct.indices.length*4];
        int x=0;
        for (int i:gl_obj_struct.indices) {
            vetices[x*3+0]=gl_obj_struct.vertices[i*3+0];
            vetices[x*3+1]=gl_obj_struct.vertices[i*3+1];
            vetices[x*3+2]=gl_obj_struct.vertices[i*3+2];
            x++;
        }
        return vetices;
    }
}
