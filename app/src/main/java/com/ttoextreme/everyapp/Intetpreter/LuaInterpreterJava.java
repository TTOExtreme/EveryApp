package com.ttoextreme.everyapp.Intetpreter;

import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.Statements.Conditionals;
import com.ttoextreme.everyapp.Intetpreter.Statements.ForStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.IfStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.WhileStatement;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Methods.Methods;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables.Variables;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables.VariablesStruct;
import com.ttoextreme.everyapp.MainScreen;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class LuaInterpreterJava {

    private MainScreen Main;
    private Methods MethodsClass;
    public References Refer = new References();
    public Compiler Comp;
    public Functions Func;
    public Conditionals Cond;
    public IfStatement IF;
    public ForStatement FOR;
    public WhileStatement WHILE;
    public Variables Vars;

    private int LineAt =0;//pos program
    private String[] Program;

    public String BGExecution = "";

    public LuaInterpreterJava(MainScreen ms){
        Main=ms;
        DebugAppend("[Init] Initialize Lua Interpreter");
        MethodsClass = new Methods(this);
        Func = new Functions(this);
        Comp = new Compiler(this);
        IF = new IfStatement(this);
        FOR = new ForStatement(this);
        WHILE = new WhileStatement(this);
        Vars = new Variables(this);
        Cond = new Conditionals(this);
    }

    public void DebugAppend(String s){
        Main.DebugAct.Append(s);
    }

    public void DoLine(String command){
        String uuid1 = "{" + UUID.randomUUID().toString()+"}";
        //Main.DebugAct.Append("[Info] Execute Line: {" + command + "} UUID: " + uuid1);
        DoFile(new String[]{command},uuid1);
    }

    public void DoFile(String[] program,String uuid){

        Program=program;
        program = Comp.Compile(program);
        for (int i=LineAt;i<program.length;i++) {
            if(program[i].indexOf(Refer.Delay)>-1){

                String str = program[i].substring(program[i].indexOf(Refer.Delay));
                if(str.indexOf(";")>-1){str = str.substring(0,str.indexOf(";"));}
                final int DelayTime = Integer.parseInt(ExtractArgs(program[i])[0]);

                String uuid1 = "{" + UUID.randomUUID().toString()+"}";
                BGExecution = BGExecution + uuid1;
                program[i]="";
                Program = program;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BGExecution = BGExecution.replace(uuid1,"");
                    }
                }, DelayTime);
                PauseContinue(uuid1,uuid);
                return;
                //*/
            }else{
                if(MethodsClass.MethodValid(program[i])){
                    MethodsClass.Get(program[i]).apply(ExtractArgs(Vars.Replace(program[i])),program[i]);
                    program[i]="";
                }else{
                    if(Func.Exist(program[i])){
                        //DoFile(Func.Get(program[i]).toArray(new String[0]));
                    }else {
                        //++; --;
                        if (program[i].indexOf("++") > -1) {
                            String v = program[i].replace("++", "").replace(";", "").replace(" ", "");
                            Vars.VarSum(v, 1);
                        }
                        if (program[i].indexOf("--") > -1) {
                            String v = program[i].replace("--", "").replace(";", "").replace(" ", "");
                            Vars.VarSub(v, 1);
                        }

                        //var creation
                        if (program[i].indexOf(" = ") > -1) {
                            String[] v = program[i].replace(";", "").replace(" ", "").split("=");
                            Vars.VarAdd(new VariablesStruct(v[0], new VariablesStruct().LOCAL, v[1]));
                        }

                        //conditionals
                        if (program[i].indexOf(Refer.If) > -1 && program[i].indexOf(Refer.Then) > -1) {
                            List<String> p = new ArrayList<>();
                            for (int j = 0; j < program.length - i; j++) {
                                p.add(program[i + j]);
                                program[i + j] = "";
                            }
                            p = IF.IfStatement(p);
                            for (int j = 0; j < p.size(); j++) {
                                program[i + j] = p.get(j);
                            }
                        }
                        if (program[i].indexOf(Refer.For) > -1 && program[i].indexOf(Refer.Do) > -1) {
                            List<String> p = new ArrayList<>();
                            String openclose="";
                            for (int j = 0; j < program.length - i; j++) {
                                if (program[i+j].indexOf(Refer.Do) > -1 || program[i+j].indexOf(Refer.Then) > -1) { openclose += "{"; }
                                if (program[i+j].indexOf(Refer.End) > -1) { openclose += "}"; }
                                while (openclose.indexOf("{}")>-1){openclose=openclose.replace("{}","");}
                                p.add(program[i + j]);
                                program[i + j] = "";
                                if(openclose.equals("")){break;}
                            }
                            String uuid1 = "{" + UUID.randomUUID().toString()+"}";
                            BGExecution = BGExecution+uuid1;
                            Program = program;
                            PauseContinue(uuid1,uuid);

                            FOR.FOR(p,uuid1);
                            return;
                            //*/
                        }
                        if (program[i].indexOf(Refer.While) > -1 && program[i].indexOf(Refer.Do) > -1) {
                            List<String> p = new ArrayList<>();
                            for (int j = 0; j < program.length - i; j++) {
                                p.add(program[i + j]);
                                program[i + j] = "";
                            }
                            WHILE.WhileStatement(p);
                            for (int j = 0; j < p.size(); j++) {
                                program[i + j] = p.get(j);
                            }
                            BGExecution = BGExecution + "{";
                            Program = program;
                            //PauseContinue();
                            return;
                        }
                    }
                }
            }
        }
        LineAt=0;
        BGExecution=BGExecution.replace(uuid,"");
        //Main.DebugAct.Append("[Info] Terminates Thread UUID: " + uuid);
        //MethodsClass.Get(Refer.Print).apply(new String[]{Refer.EndProgram},"");
    }

    private void PauseContinue(String wait,String uuid) {
        if (BGExecution.indexOf(wait) < 0) {
            DoFile(Program,uuid);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PauseContinue(wait,uuid);
                }
            }, 50);
        }
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
        Main.DebugAct.Append("[Addon] Add Method: " + name);
    }

}
