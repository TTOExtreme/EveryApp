package com.ttoextreme.everyapp.RenderEngine.Obj;

import java.nio.FloatBuffer;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class GL_Obj_Struct {
    public String name;

    public FloatBuffer vertex;//generated from vertices and indices

    public float[] vertices = {};

    public float[][] colors = {};

    public byte[] indices = {};
    public float[] rotation = {1.0f,0.5f,0.0f};
    public float rotation_speed = 0.0f;
    public float angle = 0.0f;
    public int faces = 0;

    public float[] scale = {1.0f,1.0f,1.0f};
    public float[] Translate = {0.0f,0.0f,-10.0f};

    public GL_Obj_Struct(){}
    public GL_Obj_Struct(float[] Vertices, float[][] Colors, byte[] Indices, int Faces){vertices = Vertices;colors = Colors;indices=Indices; faces=Faces;}

}
