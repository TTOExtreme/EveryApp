package com.ttoextreme.everyapp.Terminal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.inputmethodservice.Keyboard;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ttoextreme on 11/9/17.
 */

public class Terminal {
    private Activity Main;
    private EditText terminal;
    private EditText input;
    public List<String> Text = new ArrayList<String>();
    public int Bg = Color.BLACK;
    public int Tx = Color.WHITE;
    public float TexSize = 12;


    RelativeLayout RL;
    ScrollView SV;

    public Terminal(Activity act){
        Main=act;
        terminal = new EditText(Main);
        input = new EditText(Main);
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);
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


    public View getTerm(){

        RL.removeAllViews();
        SV.removeAllViews();
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;

        terminal.setText("");

        input.setTextColor(Color.BLACK);
        input.setBackgroundColor(Color.DKGRAY);
        input.setMaxLines(1);

        for (String s:Text) { terminal.setText(terminal.getText() + "\n" + s); }
        terminal.setBackgroundColor(Bg);
        terminal.setTextColor(Tx);
        terminal.setTextSize(TexSize);
        terminal.setGravity(Gravity.TOP);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);

        rl.height = screenY/15;
        rl.width = screenX;
        rl.topMargin = 0;
        RL.addView(input,rl);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY;
        rl.width = screenX;
        rl.topMargin = screenY/15;
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
