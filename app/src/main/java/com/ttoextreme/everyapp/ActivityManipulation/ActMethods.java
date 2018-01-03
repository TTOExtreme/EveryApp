package com.ttoextreme.everyapp.ActivityManipulation;

import android.graphics.Color;

import com.ttoextreme.everyapp.MainScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 02/01/2018.
 */

public class ActMethods {

    private ActRefer ref = new ActRefer();
    private MainScreen Main;
    List<ViewStruct> Views;

    public ActMethods(MainScreen act){
        Main = act;
        Main.Lua.AddMethod(ref.Init,ref.Init,this::Init);
        Main.Lua.AddMethod(ref.Add,ref.Add,this::AddView);
        Main.Lua.AddMethod(ref.StartAct,ref.StartAct,this::Start);
        Main.Lua.AddMethod(ref.SetClick,ref.SetClick,this::ViewSetClick);
        Main.Lua.AddMethod(ref.CreateView,ref.CreateView,this::CreateView);
        Main.Lua.AddMethod(ref.CreateAct,ref.CreateAct,this::CreateAct);
        Main.Lua.AddMethod(ref.SetText,ref.SetText,this::ViewSetText);
        Main.Lua.AddMethod(ref.SetBgColor,ref.SetBgColor,this::ViewSetBgColor);
        Main.Lua.AddMethod(ref.SetTextColor,ref.SetTextColor,this::ViewSetTextColor);
        Main.Lua.AddMethod(ref.SetMarginTop,ref.SetMarginTop,this::ViewSetMarginTop);
        Main.Lua.AddMethod(ref.SetMarginBottom,ref.SetMarginBottom,this::ViewSetMarginBottom);
        Main.Lua.AddMethod(ref.SetMarginLeft,ref.SetMarginLeft,this::ViewSetMarginLeft);
        Main.Lua.AddMethod(ref.SetMarginRight,ref.SetMarginRight,this::ViewSetMarginRight);
        Views = new ArrayList<ViewStruct>();
    }

    public String Init(String[] s1,String s2){
        Main.StartAct(s1,s2);
        return "";
    }
    public String CreateView(String[] s1,String s2){
        ViewStruct view = new ViewStruct();
        if(s1[0].equals(ref.Button)){view.Type=Main.ActP.BUTTON;}
        if(s1[0].equals(ref.Label)){view.Type=Main.ActP.LABEL;}
        if(s1[0].equals(ref.EditText)){view.Type=Main.ActP.EDITTEXT;}
        view.Name=s1[1];
        Views.add(view);
        return "";
    }
    public String CreateAct(String[] s1,String s2){
        ScreenRefer act = new ScreenRefer();
        act.Name=s1[0];
        Main.ActP.AddScreen(act);
        return "";
    }
    public String AddView(String[] s1,String s2){
        String scr = s2.substring(0,s2.indexOf(ref.Add)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(s1[0])){
                Main.ActP.AddToScreen(Views.get(i),scr);
            }
        }
        return "";
    }
    public String ViewSetText(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetText)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.Text=s1[0];
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetBgColor(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetText)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.Background = Color.rgb(Integer.parseInt(s1[0]),Integer.parseInt(s1[1]),Integer.parseInt(s1[2]));
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetTextColor(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetText)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.Text=s1[0];
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetMarginTop(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetMarginTop)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.margintop=Integer.parseInt(s1[0]);
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetMarginBottom(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetMarginBottom)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.marginbottom=Integer.parseInt(s1[0]);
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetMarginLeft(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetMarginLeft)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.marginleft=Integer.parseInt(s1[0]);
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetMarginRight(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetMarginRight)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.marginright=Integer.parseInt(s1[0]);
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String ViewSetClick(String[] s1,String s2){
        String View = s2.substring(0,s2.indexOf(ref.SetMarginRight)).replace(" ","").replace(".","");
        for(int i=0;i<Views.size();i++){
            if(Views.get(i).Name.equals(View)){
                ViewStruct vs = Views.get(i);
                vs.OnClick = s1[0];
                Views.set(i,vs);
            }
        }
        return "";
    }
    public String Start(String[] s1,String s2){
        Main.ActP.StartScreen(s2);
        return "";
    }
}
