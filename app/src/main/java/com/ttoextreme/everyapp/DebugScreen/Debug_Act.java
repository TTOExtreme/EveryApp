package com.ttoextreme.everyapp.DebugScreen;

import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.R;

/**
 * Created by TTOExtreme on 18/01/2018.
 */

public class Debug_Act {

    private MainScreen Main;
    private TextView Term;
    private RelativeLayout RL;
    private ScrollView SV;
    public String Text = "";

    public Debug_Act(MainScreen main){
        Main=main;
        if(RL==null){ RL = new RelativeLayout(Main); }
        if(SV==null){ SV = new ScrollView(Main); }
        if(Term==null){ Term = new TextView(Main); }
        Append("Initialize Debug Screen");
    }

    public View GetView(){
        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;

        if(RL!=null){ RL.removeAllViews(); SV.removeAllViews();}
        Term.setText(Text);
        Term.setBackgroundColor(Color.BLACK);
        Term.setTextColor(Color.WHITE);
        Term.setTextSize(18);
        Term.setGravity(Gravity.BOTTOM);
        RL.addView(Term,new RelativeLayout.LayoutParams(screenX,RelativeLayout.LayoutParams.WRAP_CONTENT));
        RL.setBackgroundColor(Color.BLACK);

        SV.addView(RL);
        SV.setBackgroundColor(Color.BLACK);
        //*/
        return SV;
    }

    public void Update(){
        Term.setText(Text);
        SV.setScrollY(SV.getMaxScrollAmount());
    }

    public void Append(String s){
        Text+="\n"+s;
        if (!(Term==null)){Update();}
    }
}
