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

public class IfStatement {

    private References Refer = new References();
    private LuaInterpreterJava Lua;
    private Functions Func;
    private Conditionals Cond;
    //private IfStatement IF;
    private ForStatement FOR;
    private Variables Vars;
    private WhileStatement WHILE;


    public IfStatement(LuaInterpreterJava luaInterpreterJava) {

        Lua = luaInterpreterJava;

        Func = Lua.Func;
        Cond = Lua.Cond;
        //IF = Lua.IF;
        FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;
    }

    // recursive if func
    public List<String> IfStatement(List<String> prog)
    {

        Func = Lua.Func;
        Cond = Lua.Cond;
        //IF = Lua.IF;
        FOR = Lua.FOR;
        WHILE = Lua.WHILE;
        Vars = Lua.Vars;

        String openclose = "";
        if (Cond.IsTrue(prog.get(0)))
        {
            if (prog.get(0).indexOf(Refer.Then) > -1 || prog.get(0).indexOf(Refer.Else) > -1 || prog.get(0).indexOf(Refer.Do) > -1) { openclose += "{"; }

            prog.remove(0);
            if (prog.get(0).indexOf(Refer.Then) > -1 || prog.get(0).indexOf(Refer.Else) > -1 || prog.get(0).indexOf(Refer.Do) > -1) { openclose += "{"; }

            List<String> program = new ArrayList<>();

            while ( openclose != "")
            {
                if (prog.get(0).indexOf(Refer.Then) > -1 || prog.get(0).indexOf(Refer.Do) > -1) { openclose += "{"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                if (prog.get(0).indexOf(Refer.End) > -1 || prog.get(0).indexOf(Refer.Else) > -1) { openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                if (prog.get(0).indexOf(Refer.Return) > -1) { return prog; }

                program.add(prog.get(0));
                //Lua.DoLine(prog.get(0));
                prog.remove(0);
                if (prog.size() == 0) { break; }
            }
            if (prog.get(0).indexOf(Refer.Else) > -1)
            {
                while (prog.get(0).indexOf(Refer.End) < 0 && prog.get(0).indexOf(Refer.Then) < 0 && prog.get(0).indexOf(Refer.Do) < 0)
                {
                    prog.remove(0);
                    if (prog.size() == 0) { return prog; }
                }
            }

            Lua.DoFile(program.toArray(new String[0]));

            Vars.BGExecution+="}";
            return prog;
        }
        else
        {
            prog.remove(0);
            List<String> program = new ArrayList<>();

            while ( openclose != "")
            {
                if (prog.get(0).indexOf(Refer.Then) > -1 || prog.get(0).indexOf(Refer.Do) > -1) { openclose += "{"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                if (prog.get(0).indexOf(Refer.End) > -1 || prog.get(0).indexOf(Refer.Else) > -1) { openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                if (prog.get(0).indexOf(Refer.Return) > -1) { return prog; }

                //Lua.DoLine(prog.get(0));
                prog.remove(0);
                if (prog.size() == 0) { return prog; }
            }
            if (prog.get(0).indexOf(Refer.Else) > -1)
            {
                openclose = "{";
                while (openclose != "")
                {
                    if (prog.get(0).indexOf(Refer.Then) > -1 || prog.get(0).indexOf(Refer.Do) > -1) { openclose += "{"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                    if (prog.get(0).indexOf(Refer.End) > -1 || prog.get(0).indexOf(Refer.Else) > -1) { openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                    if (prog.get(0).indexOf(Refer.Return) > -1) { return prog; }

                    program.add(prog.get(0));
                    //Lua.DoLine(prog.get(0));
                    prog.remove(0);
                    if (prog.size() == 0) { break; }
                }

                Lua.DoFile(program.toArray(new String[0]));
                Vars.BGExecution+="}";
                return prog;
            }
        }
        Vars.BGExecution+="}";
        return prog;
    }
}
