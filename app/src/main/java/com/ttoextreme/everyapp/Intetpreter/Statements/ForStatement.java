package com.ttoextreme.everyapp.Intetpreter.Statements;

import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.VariablesStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class ForStatement {

    private References Refer = new References();
    private LuaInterpreterJava Lua;
    private Functions Func;
    private Conditionals Cond;
    private IfStatement IF;
    //private ForStatement FOR;
    private Variables Vars;
    private WhileStatement WHILE;

    public ForStatement(LuaInterpreterJava luaInterpreterJava) {

        Lua = luaInterpreterJava;

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        //FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;
    }

    public void FOR(List<String> Lines)
    {

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        //FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;

        while (!(Lines.get(0).indexOf(Refer.For) > -1) && (Lines.get(0).indexOf(Refer.Do) > -1) && Lines.size() > 0) { Lines.remove(0); }
        String str =Lines.get(0).substring(Lines.get(0).indexOf("(") + 1, Lines.get(0).lastIndexOf(")"));
        String[] args = str.split(";");
        if (args.length != 3) { Lua.DoLine(Refer.Print + "(" + Refer.ErrorHead+Refer.ErrorFor + "" + Refer.Do + ");"); return; }
        //prog.RemoveAt(0);
        if (!Vars.Exists(args[0])){
            Vars.VarAdd(new VariablesStruct(args[0], new VariablesStruct().LOCAL, args[1]));
        } else {
            Vars.VarSet(args[0], new VariablesStruct().LOCAL, args[1]);
        }


        ForCE(Lines,0);

    }

    private void ForCE(List<String> Lines,int at){//continuos execution

        List<String> _prog = new ArrayList<>();

        String openclose = "";
        int count = at;
        while (!(Lines.get(0).indexOf(Refer.For) > -1) && (Lines.get(0).indexOf(Refer.Do) > -1) && Lines.size() > 0) { Lines.remove(0); }
        String str =Lines.get(0).substring(Lines.get(0).indexOf("(") + 1, Lines.get(0).lastIndexOf(")"));
        String[] args = str.split(";");
        if (args.length != 3) { Lua.DoLine(Refer.Print + "(" + Refer.ErrorHead+Refer.ErrorFor + "" + Refer.Do + ");"); return; }
        //prog.RemoveAt(0);
        if (!Vars.Exists(args[0])){ Vars.VarAdd(new VariablesStruct(args[0], new VariablesStruct().LOCAL, args[1])); }
        String sig ="";

        if (Integer.parseInt(args[1]) < Integer.parseInt(args[2])) { sig = " < "; } else { if (Integer.parseInt(args[1]) > Integer.parseInt(args[2])) { sig = " < "; } }


        for (; Cond.IsTrue("(" + args[0].replace(" ", "") + sig + args[2].replace(" ", "") + ")");)
        {
            if(count>=Lines.size()){count=0;}
            if (Lines.get(count).indexOf(Refer.End) > -1) { openclose += "{"; }

            //prog.RemoveAt(0);
            count++;
            if(count>=Lines.size()){count=0;}
            if (Lines.get(count).indexOf(Refer.Do) > -1 && Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }
            _prog = new ArrayList<>();
            for (String s : Lines) { _prog.add(s); }

            int DelayTime = 50;

            Lua.DoLine(Lines.get(count));

            if (Integer.parseInt(args[1]) < Integer.parseInt(args[2])) { Lua.DoLine(args[0] + "++;"); } else { if (Integer.parseInt(args[1]) > Integer.parseInt(args[2])) { Lua.DoLine(args[0] + "--;"); } }

            final Handler handler = new Handler();
            int finalCount = count;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ForCE(Lines, finalCount +1);
                }
            }, DelayTime);
            return;
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


/* 2017 12 23 18.31


            if (Lines.get(count).indexOf(Refer.End) > -1) { openclose += "{"; }

            //prog.RemoveAt(0);
            count++;
            if (Lines.get(count).indexOf(Refer.Do) > -1 && Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }
            _prog = new ArrayList<>();
            for (String s : Lines) { _prog.add(s); }

            int DelayTime = 50;

            List<String> program = new ArrayList<>();
            while ( openclose != "")
            {
                if (_prog.get(count).indexOf(Refer.End) > -1) { _prog.set(count,_prog.get(count).replace(Refer.End, " ")); openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                if (_prog.get(count).indexOf(Refer.Then) > -1 || _prog.get(count).indexOf(Refer.Do) > -1) { openclose += "{"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                //_prog.set(count,_prog.get(count).replace(Refer.Else, "").replace(Refer.If, "").replace(Refer.For, "").replace(Refer.While, "").replace(Refer.Then, "").replace(Refer.Do, ""));

                if (_prog.get(0).indexOf(Refer.Return) > -1) { return; }
                program.add(_prog.get(count));
                if(_prog.get(count).indexOf(Refer.Delay)>-1){DelayTime += Integer.parseInt(Lua.ExtractArgs(_prog.get(0))[0]); }
                //Lua.DoLine(_prog.get(count));
                count++;
                //_prog.RemoveAt(count);
                if (_prog.size() - count == 1)// 1 for the "end" line
                {
                    break;
                }
            }

            Lua.DoFile(program.toArray(new String[0]));

            count =0;
            if (Integer.parseInt(args[1]) < Integer.parseInt(args[2])) { Lua.DoLine(args[0] + "++;"); } else { if (Integer.parseInt(args[1]) > Integer.parseInt(args[2])) { Lua.DoLine(args[0] + "--;"); } }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FOR(Lines);

                }
            }, DelayTime);
            return;
        }
        if (_prog.size() > 0) { _prog.remove(0); }
        Vars.VarRem(args[0]);
        return;
* */