package com.ttoextreme.everyapp.Intetpreter.Statements;

import android.os.Handler;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;

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

        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        FOR = Lua.FOR;
        //WHILE = Lua.WHILE;
        Vars = Lua.Vars;
    }

    public void WhileStaement(List<String> Lines)
    {
        Func = Lua.Func;
        Cond = Lua.Cond;
        IF = Lua.IF;
        FOR = Lua.FOR;
        //WHILE = Lua.WHILE;
        Vars = Lua.Vars;

        List<String> prog = new ArrayList<>();

        String openclose = "";
        int count = 0;

        int DelayTime = 50;

        List<String> program = new ArrayList<>();

        String[] args = Lines.get(0).substring(Lines.get(0).indexOf("(") + 1, Lines.get(0).lastIndexOf(")") - 1 - Lines.get(0).indexOf("(")).split(";");
        while (Cond.IsTrue("("+args[0]+")"))
        {
            //Task.Delay(10);

            if (prog.get(count).indexOf(Refer.Delay)>-1){DelayTime += Integer.parseInt(Lua.ExtractArgs(prog.get(0))[0]); }
            if (prog.get(count).indexOf(Refer.Do) > -1) { openclose += "{"; }

            //prog.RemoveAt(0);
            count++;
            if (prog.get(count).indexOf(Refer.Do) > -1) { openclose += "{"; }
            List<String> _prog = new ArrayList<>();
            for (String s : prog) { _prog.add(s); }
            while ((prog.get(count).indexOf(Refer.Then) == -1 && prog.get(count).indexOf(Refer.Do) == -1) && openclose != "{")
            {
                if (_prog.get(0).indexOf(Refer.End) > -1) { _prog.set(count,_prog.get(count).replace(Refer.End, " ")); openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }

                if (prog.get(0).indexOf(Refer.Return) > -1) { return; }
                Lua.DoLine(_prog.get(count));
                _prog.remove(count);
                if (_prog.size() - count == 1) { break; }
            }
            Lua.DoFile(program.toArray(new String[0]));

            count =0;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WhileStaement(Lines);

                }
            }, DelayTime);
            return;

        }
        for (; count > 0; count--)
        {
            prog.remove(0);
        }
    }
}
