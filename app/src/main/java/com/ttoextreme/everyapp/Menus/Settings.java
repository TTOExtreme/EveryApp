package com.ttoextreme.everyapp.Menus;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.ttoextreme.everyapp.MainScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 02/01/2018.
 */

public class Settings {

    private MainScreen Main;
    private RelativeLayout RL;
    private ScrollView SV;

    public boolean stop = false;

    private EditText txs;
    private Switch swt1;
    private Switch swt2;
    private Switch swt3;

    public Settings(MainScreen act){
        Main=act;
        Main.DebugAct.Append("[Init] Initialize Settings View");
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);
    }



    public View getView(){
        Main.DebugAct.Append("[Event] Create Settings View");
        int count=0;
        RL.removeAllViews();
        SV.removeAllViews();
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;
        TextView Lab = new TextView(Main);

        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        Lab.setTextSize(20);
        rl.height = screenY/15;
        rl.width = screenX/2;

        Lab = new TextView(Main);
        Lab.setTextSize(20);
        swt1 = new Switch(Main);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count + 10;
        rl.leftMargin = 10;
        Lab.setText("Dark Theme");
        RL.addView(Lab,rl);
        swt1.setChecked(Main.Presset.DarkTheme);
        swt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Update();
            }
        });
        rl = new RelativeLayout.LayoutParams(screenY/15, screenY/15);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count +10;
        rl.leftMargin = screenX-screenY/15;
        RL.addView(swt1,rl);
        count++;

        Lab = new TextView(Main);
        Lab.setTextSize(20);
        txs = new EditText(Main);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = screenX-screenY/15;
        rl.leftMargin = 10;
        Lab.setText("Dev Mode");
        RL.addView(Lab,rl);
        int val=Main.Presset.TextSize;
        txs.setText(String.valueOf(val));
        txs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Update();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rl = new RelativeLayout.LayoutParams(screenY/15, screenY/15);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = screenX-screenY/15;
        RL.addView(txs,rl);
        count++;

        Lab = new TextView(Main);
        Lab.setTextSize(20);
        swt2 = new Switch(Main);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = screenX-screenY/15;
        rl.leftMargin = 10;
        Lab.setText("Dev Mode");
        RL.addView(Lab,rl);
        swt2.setChecked(Main.Presset.DevMode);
        swt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Update();
            }
        });
        rl = new RelativeLayout.LayoutParams(screenY/15, screenY/15);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = screenX-screenY/15;
        RL.addView(swt2,rl);
        count++;

        Lab = new TextView(Main);
        Lab.setTextSize(20);
        swt3 = new Switch(Main);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = 10;
        Lab.setText("Reset on Start");
        RL.addView(Lab,rl);
        swt3.setChecked(Main.Presset.Reset);
        swt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Update();
            }
        });
        rl = new RelativeLayout.LayoutParams(screenY/15, screenY/15);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*count;
        rl.leftMargin = screenX-screenY/15;
        RL.addView(swt3,rl);
        count++;

        SV.addView(RL);

        Update();
        return SV;
    }


    private void Update() {
        Main.DebugAct.Append("[Event] Update Settings View");
        Main.Presset.setDarkTheme(swt1.isChecked());
        Main.Presset.setDevMode(swt2.isChecked());
        Main.Presset.setReset(swt3.isChecked());
        Main.Presset.setTextSize(Integer.parseInt(txs.getText().toString()));
/*
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(stop) {
                    stop=false;
                    return;
                }
                Update();
            }
        }, 500);//*/

    }
}
