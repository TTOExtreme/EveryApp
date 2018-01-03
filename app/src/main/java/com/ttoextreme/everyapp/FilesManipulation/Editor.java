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
    private Activity Main;
    private EditText terminal;
    private Button Save;
    private Button SaveNew;
    public List<String> Text = new ArrayList<String>();
    public int Bg = Color.BLACK;
    public int Tx = Color.WHITE;
    public float TexSize = 12;


    RelativeLayout RL;
    ScrollView SV;

    public Editor(Activity act){
        Main=act;
        terminal = new EditText(Main);
        Save = new Button(Main);
        SaveNew = new Button(Main);
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);
    }

    public void Edit(String path){
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
                System.err.print(e);
            }

        }
    }

    public void SetOnkeyListerner(BiFunction<String[],String,String> keylistener){
        Save.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    keylistener.apply(((EditText)v).getText().toString().split(";"),"");
                    return true;
                }
                return false;
            }
        });
    }


    public View getTerm(){

        RL.removeAllViews();
        SV.removeAllViews();
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;

        terminal.setText(Text.toString());

        Save.setTextColor(Color.BLACK);
        Save.setBackgroundColor(Color.GRAY);

        for (String s:Text) { terminal.setText(terminal.getText() + "\n" + s); }
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);
        terminal.setGravity(Gravity.TOP);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);

        rl.height = screenY/15;
        rl.width = (screenX-20)/2;
        rl.topMargin = 0;
        rl.leftMargin = ((screenX/2)*0)+5;
        RL.addView(Save,rl);

        rl.height = screenY/15;
        rl.width = (screenX-20)/2;
        rl.topMargin = 0;
        rl.leftMargin = ((screenX/2)+5)*1;
        RL.addView(SaveNew,rl);


        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY;
        rl.width = screenX;
        rl.topMargin = screenY/15;
        rl.leftMargin = 0;
        RL.addView(terminal,rl);

        SV.addView(RL);
        return SV;
    }

    public void Update(){
        terminal.setText("");

        for (String s:Text) { terminal.setText(terminal.getText() + "\n" + s); }
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);
    }
}
