package com.ttoextreme.everyapp.Menus;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 02/01/2018.
 */

public class Pressets {

    private MainScreen Main;

    public int TextSize = 12;
    public boolean DarkTheme = false;
    public boolean DevMode = false;
    public boolean Reset = false;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String path = "";

    public Pressets(MainScreen act){
        Main=act;
        Main.DebugAct.Append("[Init] Initialize Settings Database");
        path= act.getFilesDir().getAbsolutePath()+"/prefs";
        File f = new File(path);
        if(!f.exists()){ Write();}
    }

    private void Write(){
        Main.DebugAct.Append("[Event] Writing to Settings Database");
        String s = "";
        File f = new File(path);
        if(DarkTheme){s+="1"+"\n";}else{s+="0"+"\n";}
        if(DevMode){s+="1"+"\n";}else{s+="0"+"\n";}
        if(Reset){s+="1"+"\n";}else{s+="0"+"\n";}

        try {
            FileWriter writer = new FileWriter(f);
            writer.append(s);
            writer.flush();
            writer.close();
            Main.DebugAct.Append("[Info] Saving Settings");
        } catch (Exception e) {
            Main.DebugAct.Append("[Error] Saving Settings: \n"+ e.getMessage());
            Main.Exit();
        }
    }

    public void FirstLoad(){
        Main.DebugAct.Append("[Event] Loading From Settings Database");
        try {
            Main.DebugAct.Append("[Info] Loading Settings");
            BufferedReader in = new BufferedReader(new FileReader(path));
            List<String> list = new ArrayList<String>();
            String str;
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            Main.DebugAct.Append("[Info] Loading DarkTheme Setting");
            if(list.get(0).equals("1")){DarkTheme=true; Main.DebugAct.Append("[Info] DarkTheme: True;");}else{DarkTheme = false; Main.DebugAct.Append("[Info] DarkTheme: False;");}
            Main.DebugAct.Append("[Info] Loading DevMode Setting");
            if(list.get(1).equals("1")){DevMode=true; Main.DebugAct.Append("[Info] DevMode: True;");}else{DevMode=false; Main.DebugAct.Append("[Info] DevMode: False;");}
            Main.DebugAct.Append("[Info] Loading Reset Setting");
            if(list.get(2).equals("1")){Reset=true; Main.DebugAct.Append("[Info] Reset: True;");}else{Reset=false; Main.DebugAct.Append("[Info] Reset: False;");}
            Main.DebugAct.Append("[Info] Loaded Settings");
        } catch (Exception e) {
            Main.DebugAct.Append("[Error] Failed to Load Settings Aborting \n" + e.toString());
            Main.Exit();
        }
    }

    public void setDarkTheme(boolean b){
        DarkTheme = b;
        Write();
    }
    public void setReset(boolean b){
        Reset = b;
        Write();
    }
    public void setTextSize(int b){
        TextSize = b;
        Write();
    }
    public void setDevMode(boolean b){
        DevMode = b;
        Write();
    }
}
