package com.ttoextreme.everyapp.Menus;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    RelativeLayout RL;
    ScrollView SV;

    Switch swt1;
    Switch swt2;



    List<String> Options;

    public Settings(MainScreen act){
        Main=act;
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);

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
        rl.topMargin = (screenY/15)*0 + 10;
        rl.leftMargin = 10;
        Lab.setText("Dark Theme");
        RL.addView(Lab,rl);
        swt1.setChecked(Main.Presset.DarkTheme);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*0 +10;
        RL.addView(swt1,rl);

        Lab = new TextView(Main);
        Lab.setTextSize(20);
        swt2 = new Switch(Main);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*1;
        rl.leftMargin = 10;
        Lab.setText("Dev Mode");
        RL.addView(Lab,rl);
        swt2.setChecked(Main.Presset.DevMode);
        rl = new RelativeLayout.LayoutParams(screenX, screenY);
        rl.height = screenY/15;
        rl.width = screenX/2;
        rl.topMargin = ((screenY/15)+10)*1;
        RL.addView(swt2,rl);


        SV.addView(RL);

        Update();
        return SV;
    }


    private void Update() {
        Main.Presset.setDarkTheme(swt1.isChecked());
        Main.Presset.setDevMode(swt2.isChecked());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Update();
            }
        }, 500);

    }
}
