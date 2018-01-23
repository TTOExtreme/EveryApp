package com.ttoextreme.everyapp.ActivityManipulation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables.Variables;
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
    Variables Vars;

    private int Screen = 0;
    public List<ScreenRefer> Screens;

    public ActProcessor(MainScreen act){
        Main = act;
        Main.DebugAct.Append("[Init] Initialize Activity Process Class");
        Vars = Main.Lua.Vars;
        Screens = new ArrayList<ScreenRefer>();
        ScreenRefer main = new ScreenRefer();
        main.Name="Main";
        AddScreen(main);
    }

    public void AddScreen(ScreenRefer sr){
        for (int i=0;i<Screens.size();i++){
            if(sr.Name.equals(Screens.get(i).Name)){
                Screens.set(i,sr);
                return;
            }
        }
        Screens.add(sr);
    }
    public void AddToScreen(ViewStruct vs,String Name){
        for (int i=0;i<Screens.size();i++){
            if(Name.equals(Screens.get(i).Name)){

                ScreenRefer sc = Screens.get(i);
                if(sc.Views==null){sc.Views = new ArrayList<ViewStruct>();}
                sc.Views.add(vs);
                Screens.set(i,sc);
                return;
            }
        }
    }

    public View getView(){
        Main.DebugAct.Append("[Event] Creates Activity View");
        RL = new RelativeLayout(Main);
        SV = new ScrollView(Main);

        ScreenRefer Scr = Screens.get(Screen);

        if(Scr.Views!=null) {
            for (ViewStruct View : Scr.Views) {
                RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(View.Width, View.Height);
                if (View.Type == BUTTON) {
                    Button bot = new Button(Main);
                    bot.setText(Vars.Replace(View.Text).replace("\"",""));
                    bot.setBackgroundColor(View.Background);
                    bot.setTextColor(View.TextColor);
                    bot.setTransitionName(View.OnClick);
                    bot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Main.Lua.DoLine(v.getTransitionName());
                            Update();
                        }
                    });
                    rl.leftMargin = View.marginleft;
                    rl.rightMargin = View.marginright;
                    rl.topMargin = View.margintop;
                    rl.bottomMargin = View.marginbottom;
                    RL.addView(bot, rl);
                }
                if (View.Type == LABEL) {
                    TextView bot = new TextView(Main);
                    bot.setText(View.Text);
                    bot.setBackgroundColor(View.Background);
                    bot.setTextColor(View.TextColor);
                    bot.setTransitionName(View.OnClick);
                    bot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Main.Lua.DoLine(v.getTransitionName());
                            Update();
                        }
                    });
                    rl.leftMargin = View.marginleft;
                    rl.rightMargin = View.marginright;
                    rl.topMargin = View.margintop;
                    rl.bottomMargin = View.marginbottom;
                    RL.addView(bot, rl);
                }
                if (View.Type == EDITTEXT) {
                    EditText bot = new EditText(Main);
                    bot.setText(View.Text);
                    bot.setBackgroundColor(View.Background);
                    bot.setTextColor(View.TextColor);
                    bot.setTransitionName(View.Name);
                    bot.addTextChangedListener(new TextWatcher() {
                        public void afterTextChanged(Editable s) {
                            Main.ActMeth.ViewSetText(new String[]{s.toString()},View.Name+ ".setText("+s.toString()+");");
                            Main.Lua.Compo.VarSet(View.Name+".text",s.toString());
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                    });
                    rl.leftMargin = View.marginleft;
                    rl.rightMargin = View.marginright;
                    rl.topMargin = View.margintop;
                    rl.bottomMargin = View.marginbottom;
                    RL.addView(bot, rl);
                }

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


