package com.ttoextreme.everyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ttoextreme.everyapp.Exemples.AppExStruct;
import com.ttoextreme.everyapp.Exemples.AppsExemple;
import com.ttoextreme.everyapp.FilesManipulation.ExplorerList;
import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Terminal.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String MainFolderPath = "/sdcard"+"/EveryApp_Applications/";
    public  String ExplorerPath = MainFolderPath;
    private RelativeLayout MainScreen;
    private ExplorerList Explorer;

    private LuaInterpreterJava Lua;
    private Terminal Term;
    private References ReferencesClass = new References();

    private AppsExemple AppEx = new AppsExemple();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        MainScreen = findViewById(R.id.Act_MainScreen);

        Lua = new LuaInterpreterJava(this);
        Term = new Terminal(this);
        Term.SetOnkeyListerner(this::InputTerminal);

        Explorer = new ExplorerList();
        Explorer.init(this);

        CreateMethods();

        //ask permition

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
                },1);


        //Creates the Example files
        for (AppExStruct s : AppEx.Exemples) {
            File Exp = new File(ExplorerPath + "/Exemples/");
            if(!Exp.exists()){
                Exp.mkdir();
            }
            Exp = new File(ExplorerPath + "/Exemples/" + s.Name + ".eapp");
            if (!Exp.exists()) {
                try {
                    FileWriter writer = new FileWriter(Exp);
                    writer.append(s.Code);
                    writer.flush();
                    writer.close();

                    /*
                    Exp.createNewFile();
                    OutputStream fo = new FileOutputStream(Exp);
                    String hw = s.Code;
                    fo.write(hw.getBytes());
                    fo.close();
                    //*/
                    System.out.println("file created: " + Exp);
                } catch (IOException e) {
                    System.out.println("Error " + e);
                }

            }
        }


        UpdateExplorer(MainFolderPath);






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//deletes the solid color sobreposition on icons

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    UpdateExplorer(MainFolderPath);
                } else {

                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void CreateMethods(){
        Lua.AddMethod(ReferencesClass.StartTerminal,ReferencesClass.StartTerminal,this::StartTerminal);

        Lua.AddMethod(ReferencesClass.Print, ReferencesClass.Print, this::Print);
        Lua.AddMethod(ReferencesClass.Write, ReferencesClass.Write, this::Write);
        Lua.AddMethod(ReferencesClass.Clear, ReferencesClass.Clear, this::Clear);
    }

    public String StartTerminal(String[] s1,String s2){ MainScreen.removeAllViews(); MainScreen.addView(Term.getTerm(),0);  return "";}

    public String Print(String[] s1,String s2){String str = s1[0].replace("\"",""); Term.Text.add(str); System.out.println(str); Term.Update(); return "";}
    public String Write(String[] s1,String s2){String str = s1[0].replace("\"",""); Term.Text.set(Term.Text.size()-1,Term.Text.get(Term.Text.size()-1)+str); Term.Update(); return "";}
    public String Clear(String[] s1,String s2){ Term.Text = new ArrayList<String>(); Term.Update(); return "";}

    public void UpdateExplorer(String path){
        ExplorerPath = path;
        MainScreen.removeAllViews();
        MainScreen.addView(Explorer.GetExplorerView(ExplorerPath),0);
    }

    public String InputTerminal(String[] s1,String s2){
        Lua.DoFile(s1);
        return "";
    }

    public void OpenFile(String path){

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));

            String str;

            List<String> list = new ArrayList<String>();

            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            Lua.DoFile(list.toArray(new String[0]));
            StartTerminal(new String[]{}, " ");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            MainScreen.removeAllViews();
        } else if (id == R.id.bot_NewFile) {

        } else if (id == R.id.bot_OpenFile) {
            UpdateExplorer(MainFolderPath);
        } else if (id == R.id.bot_Explorer) {
            UpdateExplorer(ExplorerPath);
        } else if (id == R.id.bot_Terminal){
            StartTerminal(new String[]{}, " ");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
