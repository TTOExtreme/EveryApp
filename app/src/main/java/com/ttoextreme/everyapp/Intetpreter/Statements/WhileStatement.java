package com.ttoextreme.everyapp.Intetpreter.Statements;

import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables.Variables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class WhileStatement {

    private References Refer = new References();
    private LuaInterpreterJava Lua;
    private Functions Func;
    private Conditionals Cond;
    private IfStatement IF;
    private ForStatement FOR;
    private Variables Vars;
    //private WhileStatement WHILE;

    public WhileStatement(LuaInterpreterJava luaInterpreterJava) {
        Lua = luaInterpreterJava;
        Lua.DebugAppend("[Init] Initialize While Interpreter Class");

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        FOR = Lua.FOR;
        //WHILE = Lua.WHILE;
        Vars = Lua.Vars;
    }

    public void WhileStatement(List<String> Lines){

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        FOR = Lua.FOR;
        //WHILE = Lua.WHILE;
        Vars = Lua.Vars;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                While(Lines, 0);
            }
        }, 50);

    }

    public void While(List<String> Lines,int at)
    {

        List<String> _prog = new ArrayList<>();

        String openclose = "";
        int count = at;

        List<String> program = new ArrayList<>();

        String[] args = Lines.get(0).substring(Lines.get(0).indexOf("(") + 1, Lines.get(0).lastIndexOf(")")).split(";");
        while (Cond.IsTrue("("+args[0]+")"))
        {
            if(count>Lines.size()-1){count=0;}
            //verify endless
            if (Lines.get(count).indexOf(Refer.Do) > -1 || Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }
            if (Lines.get(count).indexOf(Refer.End) > -1) { openclose += "}"; }
            while (openclose.indexOf("{}")>-1){openclose=openclose.replace("{}","");}


            //prog.RemoveAt(0);
            count++;
            if(count>Lines.size()-1){count=1;}
            if (Lines.get(count).indexOf(Refer.Do) > -1 && Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }
            _prog = new ArrayList<>();
            for (String s : Lines) { _prog.add(s); }

            if(!(Lines.get(count).indexOf(Refer.End)>-1 && openclose=="")) {
                int finalCount = count;

                if (Lines.get(count).indexOf(Refer.Delay) > -1) {
                    final String[] _program = new String[Lines.size() - count - 1];
                    for (int j = 1; j < Lines.size() - count; j++) {
                        _program[j - 1] = Lines.get(j + count);
                    }
                    String str = Lines.get(count).substring(Lines.get(count).indexOf(Refer.Delay));
                    if (str.indexOf(";") > -1) {
                        str = str.substring(0, str.indexOf(";"));
                    }
                    final int DelayTime = Integer.parseInt(Lua.ExtractArgs(Lines.get(count))[0]);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            While(Lines, finalCount );
                        }
                    }, DelayTime);
                    return;
                } else {
                    Lua.DoLine(Lines.get(count));
                }

            }
        }
        if (_prog.size() > 0) { _prog.remove(0); }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Vars.VarRem(args[0]);
            }
        }, 100);
        return;
    }
}
