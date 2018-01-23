package com.ttoextreme.everyapp.RenderEngine;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.RenderEngine.Obj.GL_Cube;
import com.ttoextreme.everyapp.RenderEngine.Obj.GL_Obj_Struct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class Render {
    private MainScreen Main;
    private RelativeLayout RL;
    private GLSurface Engine;
    private GLSurfaceView GL;

    public List<GL_Obj_Struct> Objects = new ArrayList<>();

    public Render(MainScreen mainScreen) {
        Main=mainScreen;
        GL=new GLSurfaceView(Main);
        Engine=new GLSurface(Main,this);

        GL.setRenderer(Engine);
    }

    public View GetView(){
        if(RL==null){ RL = new RelativeLayout(Main); }
        RL.removeAllViews();

        RL.addView(GL,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        Button bot1 = new Button(Main);
        bot1.setText("Press me!");
        bot1.setBackgroundColor(Color.TRANSPARENT);
        bot1.setTextColor(Color.TRANSPARENT);
        RL.addView(bot1,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));

        return RL;
    }

    public void Add(String name){
        for (int i=0;i<Objects.size();i++){
            if(Objects.get(i).name.equals(name)){
                GL_Obj_Struct s = Objects.get(i);
                //Objects.remove(i);
                //Objects.set(i,s);
                return;
            }
        }
    }

    public void AddCube(String name){
        name=Main.Lua.Vars.Replace(name);
        GL_Obj_Struct s;
        if(Objects.size()>0) {
            for (int i = 0; i < Objects.size(); i++) {
                s = Objects.get(i);
                if (Objects.get(i).name.equals(name)) {
                    Objects.remove(i);
                    Objects.set(i, s);
                    return;
                }
            }
        }
        s = new GL_Obj_Struct(new GL_Cube().vertices, new GL_Cube().colors, new GL_Cube().indices, new GL_Cube().faces);
        s.name=name;
        Objects.add(s);
    }

    public void SetPos(String name, String s[]) {
        name=Main.Lua.Vars.Replace(name);
        for (int i=0;i<Objects.size();i++){
            if(Objects.get(i).name.equals(name)){
                GL_Obj_Struct struct = Objects.get(i);
                Objects.remove(i);
                struct.Translate=new float[]{Float.parseFloat(s[0]),Float.parseFloat(s[1]),Float.parseFloat(s[2])};
                Objects.add(struct);
                return;
            }
        }
    }

    public void SetRotationSpeed(String name, String s[]) {
        name=Main.Lua.Vars.Replace(name);
        for (int i=0;i<Objects.size();i++){
            if(Objects.get(i).name.equals(name)){
                GL_Obj_Struct struct = Objects.get(i);
                Objects.remove(i);
                struct.rotation_speed=Float.parseFloat(s[0]);
                Objects.add(struct);
                return;
            }
        }
    }

    public void SetAlpha(String name, String s[]) {
        name=Main.Lua.Vars.Replace(name);
        for (int i=0;i<Objects.size();i++){
            if(Objects.get(i).name.equals(name)){
                GL_Obj_Struct struct = Objects.get(i);
                //Objects.remove(i);
                for (int j=0;j<struct.colors.length;j++){
                    struct.colors[j][3]=Float.parseFloat(s[0]);
                }
                Objects.add(struct);
                return;
            }
        }
    }
}
