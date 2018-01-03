package com.ttoextreme.everyapp.ActivityManipulation;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ttoextreme.everyapp.MainScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 02/01/2018.
 */

public class ActProcessor {

    public static int BUTTON = 0;
    public static int LABEL = 1;
    public static int EDITTEXT = 2;

    MainScreen Main;
    RelativeLayout RL;
    ScrollView SV;

    private int Screen = 0;
    public List<ScreenRefer> Screens;

    public ActProcessor(MainScreen act){
        Main = act;
        Screens = new ArrayList<ScreenRefer>();

    }

    public void AddScreen(ScreenRefer sr){
        for (int i=0;i<Screens.size();i++){
            if(sr.Name.equals(Screens.get(i).Name)){
                Screens.set(i,sr);
            }
        }
    }
    public void AddToScreen(ViewStruct vs,String Name){
        for (int i=0;i<Screens.size();i++){
            if(Name.equals(Screens.get(i).Name)){
                ScreenRefer sc = Screens.get(i);
                sc.Views.add(vs);
                Screens.set(i,sc);
            }
        }
    }

    public View getView(){
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);

        ScreenRefer Scr = Screens.get(Screen);

        for (ViewStruct View:Scr.Views) {
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(View.Width,View.Height);
            if(View.Type==BUTTON){
                Button bot = new Button(Main);
                bot.setText(View.Text);
                bot.setBackgroundColor(View.Background);
                bot.setTextColor(View.TextColor);
                bot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Main.Lua.DoLine(View.OnClick);
                    }
                });
                rl.leftMargin=View.marginleft;
                rl.rightMargin=View.marginright;
                rl.topMargin=View.margintop;
                rl.bottomMargin=View.marginbottom;
                RL.addView(bot,rl);
            }
            if(View.Type==LABEL){
                TextView bot = new TextView(Main);
                bot.setText(View.Text);
                bot.setBackgroundColor(View.Background);
                bot.setTextColor(View.TextColor);
                rl.leftMargin=View.marginleft;
                rl.rightMargin=View.marginright;
                rl.topMargin=View.margintop;
                rl.bottomMargin=View.marginbottom;
                RL.addView(bot,rl);
            }

        }


        SV.addView(RL);
        return SV;
    }

    public void Update(){
        Main.StartAct(new String[]{},"");
    }

    public void StartScreen(String Name) {
        for (int i = 0; i < Screens.size(); i++) {
            if (Name.equals(Screens.get(i).Name)) {
                Screen=i;
                Update();
            }
        }
    }
}


