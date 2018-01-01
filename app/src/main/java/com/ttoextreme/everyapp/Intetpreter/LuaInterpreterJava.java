package com.ttoextreme.everyapp.Intetpreter;

import android.app.Activity;
import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.*;
import com.ttoextreme.everyapp.Intetpreter.Statements.Conditionals;
import com.ttoextreme.everyapp.Intetpreter.Statements.ForStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.IfStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.WhileStatement;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Methods;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;
import com.ttoextreme.everyapp.MainScreen;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class LuaInterpreterJava {

    private Activity Main;
    private Methods MethodsClass;
    private References Refer = new References();
    public Compiler Comp;
    public Functions Func;
    public Conditionals Cond;
    public IfStatement IF;
    public ForStatement FOR;
    public WhileStatement WHILE;
    public Variables Vars;

    public LuaInterpreterJava(Activity ms){
        Main=ms;
        MethodsClass = new Methods(this);
        Comp = new Compiler(this);
        Func = new Functions(this);
        IF = new IfStatement(this);
        FOR = new ForStatement(this);
        WHILE = new WhileStatement(this);
        Vars = new Variables();
        Cond = new Conditionals(this);
    }

    public void DoLine(String command){
        DoFile(new String[]{command});
    }

    public void DoFile(String[] program){
        Comp.Compile(program);
        for (int i = 0;i<program.length;i++) {
            if(program[i].indexOf(Refer.Delay)>-1){
                final String[] _program = new String[program.length-i-1];
                for(int j=1;j<program.length-i;j++){
                    _program[j-1]=program[j+i];
                }
                String str = program[i].substring(program[i].indexOf(Refer.Delay));
                if(str.indexOf(";")>-1){str = str.substring(0,str.indexOf(";"));}
                final int DelayTime =Integer.parseInt(ExtractArgs(program[i])[0]);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DoFile(_program);

                    }
                }, DelayTime);
                return;
            }else{
                if(MethodsClass.MethodValid(program[i])){
                    MethodsClass.Get(program[i]).apply(ExtractArgs(program[i]),"");
                }else{
                    //++;
                    if(program[i].indexOf("++")>-1){
                        String v=program[i].replace("++","").replace(";","").replace(" ","");
                        Vars.VarSum(v,1);
                    }
                    if(program[i].indexOf("--")>-1){
                        String v=program[i].replace("++","").replace(";","").replace(" ","");
                        Vars.VarSum(v,1);
                    }

                    //conditionals
                    if(program[i].indexOf(Refer.If)>-1 && program[i].indexOf(Refer.Then)>-1){
                        List<String> p = new ArrayList<>();
                        for (int j = 0;j<program.length-i;j++){p.add(program[i+j]); program[i+j]="";}
                        p=IF.IfStatement(p);
                        for (int j = 0;j<p.size();j++){program[i+j]=p.get(j);}

                    }
                    if(program[i].indexOf(Refer.For)>-1 && program[i].indexOf(Refer.Do)>-1){
                        List<String> p = new ArrayList<>();
                        for (int j = 0;j<program.length-i;j++){p.add(program[i+j]); program[i+j]="";}
                        FOR.FOR(p);
                        for (int j = 0;j<p.size();j++){program[i+j]=p.get(j);}
                    }
                    if(program[i].indexOf(Refer.While)>-1 && program[i].indexOf(Refer.Do)>-1){
                        List<String> p = new ArrayList<>();
                        for (int j = 0;j<program.length-i;j++){p.add(program[i+j]); program[i+j]="";}
                        WHILE.WhileStaement(p);
                        for (int j = 0;j<p.size();j++){program[i+j]=p.get(j);}
                    }
                }
            }
        }
        //MethodsClass.Get(Refer.Print).apply(new String[]{Refer.EndProgram},"");
    }

    public String[] ExtractArgs(String str) {
        if(str.indexOf("(")>-1 || str.indexOf(")")>-1) {
            str = str.substring(str.indexOf("(") + 1);
            str = str.substring(0, str.lastIndexOf(")"));
        }
        return str.split(",");

    }

    public void AddMethod(String name, String caller, BiFunction<String[],String,String> method){
        MethodsClass.AddMethod(name,caller,method);
    }

}
