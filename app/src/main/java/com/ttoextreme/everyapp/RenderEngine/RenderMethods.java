package com.ttoextreme.everyapp.RenderEngine;

import com.ttoextreme.everyapp.MainScreen;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class RenderMethods {
    private MainScreen Main;
    private Render Engine;
    private GL_Refer Refer=new GL_Refer();

    public RenderMethods(MainScreen mainScreen) {
        Main=mainScreen;
        Engine = Main.GLRender;
    }

    public void CreateMethods() {
        Main.Lua.AddMethod("GL_Init",Refer.Init,Main::StartEngine);
        Main.Lua.AddMethod("GL_AddCube",Refer.AddCube,this::AddCube);
        Main.Lua.AddMethod("GL_SetPos",Refer.SetPos,this::SetPos);
        Main.Lua.AddMethod("GL_SetAlpha",Refer.Settransparency,this::SetAlpha);
        Main.Lua.AddMethod("GL_SetRotationSpeed",Refer.SetRotationSpeed,this::SetRotationSpeed);
    }

    public String AddCube(String[] s1,String s2){
        Engine.AddCube(s1[0].replace(" ","").replace(".","").replace("\"",""));
        return "";
    }

    public String SetPos(String[] s1,String s2){
        String Obj = s2.substring(0,s2.indexOf(Refer.SetPos)).replace(" ","").replace(".","").replace("\"","");
        if(s1.length!=3){Main.Exit();}
        Engine.SetPos(Obj,s1);
        return "";
    }
    public String SetAlpha(String[] s1,String s2){
        String Obj = s2.substring(0,s2.indexOf(Refer.Settransparency)).replace(" ","").replace(".","").replace("\"","");
        if(s1.length!=1){Main.Exit();}
        Engine.SetAlpha(Obj,s1);
        return "";
    }
    public String SetRotationSpeed(String[] s1,String s2){
        String Obj = s2.substring(0,s2.indexOf(Refer.SetRotationSpeed)).replace(" ","").replace(".","").replace("\"","");
        if(s1.length!=1){Main.Exit();}
        Engine.SetRotationSpeed(Obj,s1);
        return "";
    }
}
