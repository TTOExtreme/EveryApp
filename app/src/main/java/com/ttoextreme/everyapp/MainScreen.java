package com.ttoextreme.everyapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ttoextreme.everyapp.ActivityManipulation.ActMethods;
import com.ttoextreme.everyapp.ActivityManipulation.ActProcessor;
import com.ttoextreme.everyapp.AugmentedReality.AR_Main;
import com.ttoextreme.everyapp.AugmentedReality.AR_Methods;
import com.ttoextreme.everyapp.DebugScreen.Debug_Act;
import com.ttoextreme.everyapp.Exemples.AppExStruct;
import com.ttoextreme.everyapp.Exemples.AppsExemple;
import com.ttoextreme.everyapp.FilesManipulation.Editor;
import com.ttoextreme.everyapp.FilesManipulation.ExplorerList;
import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Menus.Menus;
import com.ttoextreme.everyapp.Menus.Pressets;
import com.ttoextreme.everyapp.Menus.Settings;
import com.ttoextreme.everyapp.Terminal.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String MainFolderPath = "/sdcard"+"/EveryApp_Applications/";
    public  String ExplorerPath = MainFolderPath;
    private RelativeLayout MainScreen;
    private DrawerLayout MainView;
    private ExplorerList Explorer;

    public LuaInterpreterJava Lua;
    private Terminal Term;
    private Settings Setting;
    private Editor EditScreen;
    public ActProcessor ActP;
    public ActMethods ActMeth;
    public Pressets Presset;
    private References ReferencesClass = new References();
    private AppsExemple AppEx;
    public Debug_Act DebugAct;
    public AR_Main ARMain;
    public AR_Methods ARMethods;

    private String Executing = "";

    private int MenuNum;

    private Handler MainThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_screen);
        MainScreen = findViewById(R.id.Act_MainScreen);
        DebugAct = new Debug_Act(this);
        Presset = new Pressets(this);
        Setting = new Settings(this);
        Presset.FirstLoad();
        OpenMain();

        if(Presset.DevMode){
            StartDebug(new String[0],"");
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PostLoad();
                if(Presset.DevMode){
                    MainScreen.setBackgroundColor(Color.BLACK);
                    StartDebug(new String[0],"");

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DebugAct.Append("\n\nPress Back to go to main screen");
                            MainScreen.setBackgroundColor(Color.WHITE);
                        };
                    }, 3000);
                }
            }
        }, 100);

    }
    private void PostLoad(){
        AppEx = new AppsExemple(this);
        MenuNum = new Menus().MAIN;

        Lua = new LuaInterpreterJava(this);
        Term = new Terminal(this);
        Term.SetOnkeyListerner(this::InputTerminal);
        ActP = new ActProcessor(this);
        ActMeth = new ActMethods(this);
        Explorer = new ExplorerList();
        Explorer.init(this);
        EditScreen = new Editor(this);
        ARMethods = new AR_Methods(this);
        ARMain = new AR_Main(this);


        CreateMethods();

        //ask permition

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},1);

        //Creates the Example files
        if(Presset.Reset) {
            File p = new File(ExplorerPath);
            p.delete();
            p.mkdir();
            for (AppExStruct s : AppEx.Exemples) {
                File Exp = new File(ExplorerPath + "/Exemples/");
                if (!Exp.exists()) {
                    Exp.mkdir();
                }
                Exp = new File(ExplorerPath + "/Exemples/" + s.Name + ".eapp");
                if (Exp.exists() && Presset.DevMode) {
                    Exp.delete();
                }
                if (!Exp.exists()) {
                    DebugAct.Append("[Info] Creating Exemple: " + Exp.getName());
                    SaveFile(Exp.getPath(), s.Code);
                }
            }
        }

        if(Presset.DevMode){
            StartDebug(new String[0],"");
        }
    }

    public void Exit(){
        try {
            Thread.sleep(3000);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //UpdateExplorer(MainFolderPath);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void CreateMethods(){
        Lua.AddMethod(ReferencesClass.StartTerminal,ReferencesClass.StartTerminal,this::StartTerminal);

        Lua.AddMethod(ReferencesClass.Print, ReferencesClass.Print, this::Print);
        Lua.AddMethod(ReferencesClass.PrintDev, ReferencesClass.PrintDev, this::PrintDev);
        Lua.AddMethod(ReferencesClass.Write, ReferencesClass.Write, this::Write);
        Lua.AddMethod(ReferencesClass.Clear, ReferencesClass.Clear, this::Clear);

    }

    public String StartTerminal(String[] s1,String s2){ MainScreen = findViewById(R.id.Act_MainScreen); MainScreen.removeAllViews(); MainScreen.addView(Term.getView(),0);  return "";}
    public String StartSettings(String[] s1,String s2){ MainScreen = findViewById(R.id.Act_MainScreen); MainScreen.removeAllViews(); MainScreen.addView(Setting.getView(),0);  return "";}
    public String StartAct(String[] s1,String s2){ MainScreen = findViewById(R.id.Act_MainScreen); MainScreen.removeAllViews(); MainScreen.addView(ActP.getView(),0);  return "";}
    public String StartDebug(String[] s1,String s2){ MainScreen = findViewById(R.id.Act_MainScreen); MainScreen.removeAllViews(); MainScreen.addView(DebugAct.GetView(),0);  return "";}
    public String StartAR(String[] s1,String s2){ MainScreen = findViewById(R.id.Act_MainScreen); MainScreen.removeAllViews(); MainScreen.addView(ARMain.GetView(),0);  return "";}

    public String Print(String[] s1,String s2){String str = s1[0].replace("\"","");Term.Text+="\n"+str;Term.Update();return "";}
    public String PrintDev(String[] s1,String s2){String str = s1[0].replace("\"","");Term.TextDev+="\n"+str;Term.Update();return "";}
    public String Write(String[] s1,String s2){String str = s1[0].replace("\"",""); Term.Text+=str; return "";}
    public String Clear(String[] s1,String s2){ Term.Text = ""; return "";}

    public void StartMain(){
        MainScreen = findViewById(R.id.Act_MainScreen);
        MainScreen.removeAllViews();
    }

    public void UpdateExplorer(String path){
        ExplorerPath = path;
        MainScreen.removeAllViews();
        MainScreen.addView(Explorer.GetExplorerView(ExplorerPath),0);
    }

    public void OpenEditor(String path){
        MenuNum = new Menus().EDITOR;
        EditScreen.Edit(path);
        MainScreen.removeAllViews();
        MainScreen.addView(EditScreen.getView(),0);
    }

    public String InputTerminal(String[] s1,String s2){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String uuid1 = "{" + UUID.randomUUID().toString()+"}";
                Lua.DoFile(s1,uuid1);
            }
        }, 50);
        return "";
    }

    public void OpenFile(String path){
        MenuNum = new Menus().TERMINAL;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
            Executing = path;

            String str;

            List<String> list = new ArrayList<String>();

            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            StartTerminal(new String[]{}, " ");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String uuid1 = "{" + UUID.randomUUID().toString()+"}";
                    Lua.DoLine(Lua.Refer.PrintDev+"(\"Opening App: "+ path +"\")");
                    Lua.DoFile(list.toArray(new String[0]),uuid1);
                }
            }, 50);
        } catch (IOException e) {

            Lua.DoLine(Lua.Refer.PrintDev+"(Error Loading App: "+ path +"\n\n" + e.getMessage() + ")");
            e.printStackTrace();
        }

    }

    public void SaveFile(String path,String Code){
        File Exp = new File(path);
        try {
            FileWriter writer = new FileWriter(Exp);
            writer.append(Code);
            writer.flush();
            writer.close();
            Lua.DoLine(Lua.Refer.PrintDev+"(file created: " + Exp +")");
        } catch (IOException e) {
            Lua.DoLine(Lua.Refer.PrintDev+"(Error " + e.getMessage() +")");
        }
    }

    @Override
    public void onBackPressed() {
        StartMain();
        return;
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(MenuNum==new Menus().MAIN){
            Setting.stop=true;
            getMenuInflater().inflate(R.menu.main_screen, menu);
        }
        if(MenuNum==new Menus().EXPLORER){
            Setting.stop=true;
            getMenuInflater().inflate(R.menu.explorer_screen, menu);
        }
        if(MenuNum==new Menus().TERMINAL){
            Setting.stop=true;
            getMenuInflater().inflate(R.menu.terminal_screen, menu);
        }
        if(MenuNum==new Menus().EDITOR){
            Setting.stop=true;
            getMenuInflater().inflate(R.menu.editor_screen, menu);
        }
        //return super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            StartSettings(new String[]{},"");
            return true;
        }
        if (id == R.id.action_new) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("File Name");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = input.getText().toString();
                    OpenEditor(ExplorerPath+name+".eapp");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        }
        if (id == R.id.action_restart) {
            if(Executing!=""){OpenFile(Executing);}
            return true;
        }
        if (id == R.id.action_save) {
            SaveFile(EditScreen.Path,EditScreen.GetText());
            return true;
        }
        if (id == R.id.action_savenew) {
            return true;
        }
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bot_Home) {
            Setting.stop=true;
            MainScreen.removeAllViews();
            MenuNum = new Menus().MAIN;
        } else if (id == R.id.bot_App) {
            Setting.stop=true;
            //MenuNum = new Menus().EDITOR;
        } else if (id == R.id.bot_OpenFile) {
            Setting.stop=true;
            MenuNum = new Menus().EXPLORER;
            UpdateExplorer(MainFolderPath);
        } else if (id == R.id.bot_Explorer) {
            Setting.stop=true;
            MenuNum = new Menus().EXPLORER;
            UpdateExplorer(ExplorerPath);
        } else if (id == R.id.bot_Terminal){
            Setting.stop=true;
            MenuNum = new Menus().TERMINAL;
            StartTerminal(new String[]{}, " ");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenMain(){
        StartMain();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//deletes the solid color sobreposition on icons

    }

}
