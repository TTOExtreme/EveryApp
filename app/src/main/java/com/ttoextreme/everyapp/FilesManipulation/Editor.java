package com.ttoextreme.everyapp.FilesManipulation;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ttoextreme.everyapp.MainScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by TTOExtreme on 02/01/2018.
 */

public class Editor {
    private MainScreen Main;
    private EditText terminal;
    private Button Save;
    private Button SaveNew;
    public String Path;
    public List<String> Text = new ArrayList<String>();
    public int Bg = Color.BLACK;
    public int Tx = Color.WHITE;
    public float TexSize = 12;


    RelativeLayout RL;
    ScrollView SV;

    public Editor(MainScreen act){
        Main=act;
        terminal = new EditText(Main);
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);
    }

    public void Edit(String path){
        Path = path;
        Text = new ArrayList<String>();
        if(new File(path).exists()){
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(path));
                String str;
                while ((str = in.readLine()) != null) {
                    Text.add(str);
                }
            }catch (Exception e){
                Main.Lua.DoLine(Main.Lua.Refer.PrintDev+"(Error Reading File: "+ path + "\n"+ e.getMessage() +")");
            }

        }
    }

    public String GetText(){
        return terminal.getText().toString();
    }

    public View getView(){

        RL.removeAllViews();
        SV.removeAllViews();
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;

        String t ="";
        for (String s:Text) { t+=s+"\n";}
        t=t.substring(0,t.lastIndexOf("\n"));
        terminal.setText(t);
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);
        terminal.setGravity(Gravity.TOP);


        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY;
        rl.width = screenX;
        rl.topMargin = 0;
        rl.leftMargin = 0;
        RL.addView(terminal,rl);

        SV.addView(RL);
        return SV;
    }
}
