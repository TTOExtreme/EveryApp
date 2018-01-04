package com.ttoextreme.everyapp.Intetpreter.Statements;

import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.VariablesStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private List<String> _Lines;
    private int LineAt=0;
    private String BG ="";
    private boolean Err=false;

    public ForStatement(LuaInterpreterJava luaInterpreterJava) {

        Lua = luaInterpreterJava;

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        //FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;
    }

    public void FOR(List<String> Lines,String uuid)
    {

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        //FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;

        while (!(Lines.get(0).indexOf(Refer.For) > -1) && (Lines.get(0).indexOf(Refer.Do) > -1) && Lines.size() > 0) { Lines.remove(0); }
        String str ="";
        if(Lines.get(0).indexOf("(")>-1 && Lines.get(0).indexOf(")")>-1){str=Lines.get(0).substring(Lines.get(0).indexOf("(") + 1, Lines.get(0).lastIndexOf(")"));}
        String[] args = str.split(";");
        if(str.equals("")){
            Lua.BGExecution = Lua.BGExecution.replace(uuid, "");
            return;
        }
        if (args.length != 3) {
            Lua.DoLine(Refer.Print + "(" + Refer.ErrorHead+Refer.ErrorFor + "" + Refer.Do + ");");
            Lua.BGExecution = Lua.BGExecution.replace(uuid, "");
            return;
        }
        //prog.RemoveAt(0);
        if (!Vars.Exists(args[0])){
            Vars.VarAdd(new VariablesStruct(args[0], new VariablesStruct().LOCAL, args[1]));
        } else {
            //Vars.VarSet(args[0], new VariablesStruct().LOCAL, args[1]);
        }

        List<String> _prog = new ArrayList<>();
        String openclose = "";
        int count = 0;
        if(LineAt>0){count=LineAt;}
        String sig ="";

        if (Integer.parseInt(args[1]) < Integer.parseInt(args[2])) { sig = " <= "; } else { if (Integer.parseInt(args[1]) > Integer.parseInt(args[2])) { sig = " >= "; } }
        count++;
        for (; Cond.IsTrue("(" + args[0].replace(" ", "") + sig + args[2].replace(" ", "") + ")");)
        {
            if(count>Lines.size()-1){count=0;}
            //verify endless
            if (Lines.get(count).indexOf(Refer.Do) > -1 || Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }
            if (Lines.get(count).indexOf(Refer.End) > -1) { openclose += "}"; }
            while (openclose.indexOf("{}")>-1){openclose=openclose.replace("{}","");}

            if(count==0 && Lines.size()>1){count = 1;}
            if(count>Lines.size()-1){count=1;}
            if (Lines.get(count).indexOf(Refer.Do) > -1 && Lines.get(count).indexOf(Refer.Then) > -1) { openclose += "{"; }

            if(count==Lines.size()-1) {
                if(sig.indexOf("<=")>-1){_prog.add(args[0]+"++;");}else{_prog.add(args[0]+"--;");}

                String uuid1 = "{" + UUID.randomUUID().toString()+"}";
                Lua.BGExecution += uuid1;
                LineAt = count;
                PauseContinue(uuid1,uuid,Lines);
                Lua.DoFile(_prog.toArray(new String[0]),uuid1);
                _prog = new ArrayList<>();
                return;
            }else{
                _prog.add(Lines.get(count));
            }
            count++;
        }

        Lua.Vars.VarRem(args[0]);
        Lua.BGExecution = Lua.BGExecution.replace(uuid, "");

        return;
    }

    private void PauseContinue(String wait,String uuid,List<String> Lines) {
        if (Lua.BGExecution.indexOf(wait) < 0) {
            FOR(Lines,uuid);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PauseContinue(wait,uuid,Lines);
                }
            }, 50);
        }
    }

}
