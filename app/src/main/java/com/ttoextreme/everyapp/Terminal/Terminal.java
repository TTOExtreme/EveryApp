package com.ttoextreme.everyapp.Terminal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.inputmethodservice.Keyboard;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ttoextreme.everyapp.MainScreen;

import java.io.Console;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Handler;

/**
 * Created by ttoextreme on 11/9/17.
 */

public class Terminal {
    private MainScreen Main;
    private EditText terminal;
    private EditText terminalDev;
    private EditText input;
    public String Text = "";
    public String TextDev = "";
    public int Bg = Color.BLACK;
    public int Tx = Color.WHITE;
    public float TexSize = 14;


    RelativeLayout MainRL;
    RelativeLayout RL;
    ScrollView SV;

    public Terminal(MainScreen act){
        Main=act;
        terminal = new EditText(Main);
        terminalDev = new EditText(Main);
        input = new EditText(Main);
        MainRL = new RelativeLayout(Main);
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);
        TexSize = Main.Presset.TextSize;
    }

    public void SetOnkeyListerner(BiFunction<String[],String,String> keylistener){
        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        keylistener.apply(((EditText)v).getText().toString().split(";"),"");
                    return true;
                }
                return false;
            }
        });
    }


    public View getView(){

        MainRL.removeAllViews();
        RL.removeAllViews();
        SV.removeAllViews();
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;


        input.setTextColor(Color.BLACK);
        input.setBackgroundColor(Color.DKGRAY);
        input.setMaxLines(1);

        terminal.setText(Text);
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);
        terminal.setGravity(Gravity.TOP);

        terminalDev.setText(TextDev);
        terminalDev.setBackgroundColor(Color.DKGRAY);
        terminalDev.setTextColor(Color.GREEN);
        terminalDev.setTextSize(TexSize);
        terminalDev.setGravity(Gravity.TOP);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX;
        rl.topMargin = 0;
        MainRL.addView(input,rl);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY;
        if(Main.Presset.DevMode){rl.width=screenX/2;}else{rl.width = screenX;}
        rl.topMargin = screenY/15;
        RL.addView(terminal,rl);

        if(Main.Presset.DevMode) {
            rl = new RelativeLayout.LayoutParams(screenX, screenY);
            rl.height = screenY;
            rl.width = screenX/2;
            rl.topMargin = screenY / 15;
            rl.leftMargin = screenX/2;
            RL.addView(terminalDev, rl);
        }
        SV.addView(RL);
        MainRL.addView(SV);
        return MainRL;
    }

    public void Update(){
        TexSize = Main.Presset.TextSize;
        terminal.setText(Text);
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);

        if(Main.Presset.DevMode){
            terminalDev.setText(TextDev);
            terminalDev.setBackgroundColor(Color.DKGRAY);
            terminalDev.setTextColor(Color.GREEN);
            terminalDev.setTextSize(TexSize);
            terminalDev.setGravity(Gravity.TOP);
        }
    }
}
