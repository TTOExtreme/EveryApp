package com.ttoextreme.everyapp.FilesManipulation;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ExplorerList {

    MainScreen Main;
    List<ListFiles> bts = new ArrayList<ListFiles>();
    public byte FOLDER=0;
    public byte FILE=1;


    public void init(MainScreen ct){
        Main = ct;
        Main.DebugAct.Append("[Init] Initialize Explorer Class");
    }

    public void addList(String name, byte type, String Description){
        ListFiles lf = new ListFiles();
        lf.Name= name;
        lf.Type = type;
        lf.Description = Description;
        bts.add(lf);
    }

    public View GetExplorerView(String path) {//generates an listview
        Main.DebugAct.Append("[Event] Create Explorer View");
        bts = new ArrayList<ListFiles>();
        GetFileList(path);

        RelativeLayout RL = new RelativeLayout(Main);
        ScrollView SV = new ScrollView(Main);
        RelativeLayout.LayoutParams rl;

        Display display = Main.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;
        if (Main.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenX = size.y;
            screenY = size.x;
        }
        int screenAdapterY = screenY / 15;
        for (int i = 0; i < bts.size(); i++)
        {
            rl = new RelativeLayout.LayoutParams(screenX-screenAdapterY-10, screenAdapterY-10);
            rl.topMargin = (  10 +(i * (screenAdapterY + 5)));
            rl.leftMargin = screenAdapterY + 5;
            Button bt = new Button(Main);
            bt.setText(bts.get(i).Name);
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bts.get(finalI).Type==FOLDER) {
                        Main.ExplorerPath = bts.get(finalI).Description;
                        Main.UpdateExplorer(Main.ExplorerPath);
                    }else{
                        Main.OpenFile(bts.get(finalI).Description);
                    }
                }
            });
            bt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(bts.get(finalI).Type==FOLDER) {
                        Main.ExplorerPath = bts.get(finalI).Description;
                        Main.UpdateExplorer(Main.ExplorerPath);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main);
                        builder.setTitle("File Name");

                        builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Main.OpenFile(bts.get(finalI).Description);
                            }
                        });
                        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Main.OpenEditor(bts.get(finalI).Description);
                            }
                        });

                        builder.show();

                    }
                    return false;
                }
            });

            RL.addView(bt, rl);

            rl = new RelativeLayout.LayoutParams(screenAdapterY, screenAdapterY);
            rl.topMargin =  5 +(i * (screenAdapterY + 5));
            rl.leftMargin = 0;
            ImageView im = new ImageView(Main);
            Drawable d;
            if(bts.get(i).Type==FOLDER){
                d = ContextCompat.getDrawable(Main, R.drawable.foldericon);
            }else {
                d = ContextCompat.getDrawable(Main, R.drawable.fileicon);
            }
            im.setImageDrawable(d);
            RL.addView(im, rl);
        }
        SV.addView(RL);
        return SV;
    }

    private void GetFileList(String path) {//gets the list of files in path
        Main.DebugAct.Append("[Info] Pushing Data from Folder: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        try {
            addList("/..", FOLDER, new File(path).getParent());
            for (File f : files) {
                if (f.getAbsolutePath().indexOf(".")>0) {
                    if(f.getAbsolutePath().indexOf(".eapp")>0 || f.getAbsolutePath().indexOf(".lua")>0) {
                        addList(f.getName(), FILE, f.getAbsolutePath());
                    }
                }else{
                    addList(f.getName(), FOLDER, f.getAbsolutePath());
                }

            }
        }catch (Exception e){
            Main.DebugAct.Append("[Error] Loading Folder: "+ path + "\n"+ e.getMessage());
            Main.Lua.DoLine(Main.Lua.Refer.PrintDev+"([Error] Loading Folder: "+ path + "\n"+ e.getMessage() +")");
        }

    }

}