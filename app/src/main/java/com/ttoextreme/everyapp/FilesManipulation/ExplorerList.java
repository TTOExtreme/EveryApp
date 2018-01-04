package com.ttoextreme.everyapp.FilesManipulation;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ttoextreme.everyapp.MainScreen;
import com.ttoextreme.everyapp.R;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class ExplorerList {

    MainScreen context;
    List<ListFiles> bts = new ArrayList<ListFiles>();
    public byte FOLDER=0;
    public byte FILE=1;


    public void init(MainScreen ct){
        context = ct;


    }

    public void addList(String name, byte type, String Description){
        ListFiles lf = new ListFiles();
        lf.Name= name;
        lf.Type = type;
        lf.Description = Description;
        bts.add(lf);
    }

    public View GetExplorerView(String path){//generates an listview
        bts = new ArrayList<ListFiles>();
        GetFileList(path);

        RelativeLayout RL = new RelativeLayout(context);
        ScrollView SV = new ScrollView(context);
        RelativeLayout.LayoutParams rl;

        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenX = size.x;
        int screenY = size.y;
        int screenAdapterY = screenY / 15;

        for (int i = 0; i < bts.size(); i++)
        {
            rl = new RelativeLayout.LayoutParams(screenX-screenAdapterY-10, screenAdapterY-10);
            rl.topMargin = (  10 +(i * (screenAdapterY + 5)));
            rl.leftMargin = screenAdapterY + 5;
            Button bt = new Button(context);
            bt.setText(bts.get(i).Name);
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bts.get(finalI).Type==FOLDER) {
                        context.ExplorerPath = bts.get(finalI).Description;
                        context.UpdateExplorer(context.ExplorerPath);
                    }else{
                        context.OpenFile(bts.get(finalI).Description);
                    }
                }
            });
            bt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(bts.get(finalI).Type==FOLDER) {
                        context.ExplorerPath = bts.get(finalI).Description;
                        context.UpdateExplorer(context.ExplorerPath);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("File Name");

                        builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.OpenFile(bts.get(finalI).Description);
                            }
                        });
                        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.OpenEditor(bts.get(finalI).Description);
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
            ImageView im = new ImageView(context);
            Drawable d;
            if(bts.get(i).Type==FOLDER){
                d = ContextCompat.getDrawable(context, R.drawable.foldericon);
            }else {
                d = ContextCompat.getDrawable(context, R.drawable.fileicon);
            }
            im.setImageDrawable(d);
            RL.addView(im, rl);
        }
        SV.addView(RL);
        return SV;
    }

    private void GetFileList(String path) {//gets the list of files in path
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
            context.Lua.DoLine(context.Lua.Refer.PrintDev+"(Error Loading Folder: "+ path + "\n"+ e.getMessage() +")");
        }

    }

}