package com.ttoextreme.everyapp.Intetpreter;

import com.ttoextreme.everyapp.Intetpreter.Statements.ForStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.IfStatement;
import com.ttoextreme.everyapp.Intetpreter.Statements.WhileStatement;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/9/17.
 */

public class Compiler {

    private References Refer = new References();
    private LuaInterpreterJava Lua;
    private Functions Func;


    public Compiler(LuaInterpreterJava lua){
        Lua=lua;
        Lua.DebugAppend("[Init] Initialize Compiler");
        Func = Lua.Func;
    }

    public String[] Compile(String[] _Lines)
    {
        //compile
        List<String> Lines = new ArrayList<>();
        for (String s :_Lines) { Lines.add(s); }
        for (int line = 0; line < Lines.size(); line++)
        {
            if (Lines.get(line).indexOf(Refer.Function) > -1)
            {
                String[] args = Lines.get(line).substring(Lines.get(line).indexOf("(") + 1, Lines.get(line).lastIndexOf(")")).split(",");
                String name = Lines.get(line).substring(0, Lines.get(line).indexOf("(")).replace(Refer.Function, "").replace(" ", "");
                boolean ret = false;
                List<String> l = new ArrayList<>();
                String openclose = "";
                Lines.remove(line);
                while (Lines.get(line).indexOf(Refer.FunctionEnd) < 0 || openclose != "")
                {
                    if (Lines.get(line).indexOf(Refer.End) > -1) { Lines.set(line, Lines.get(line).replace(Refer.End, " ")); openclose += "}"; openclose = openclose.replace("{}", ""); if (openclose == "") { break; } }
                    if (Lines.get(line).indexOf(Refer.Return) > -1) { ret = true; }
                    l.add(Lines.get(line));
                    Lines.remove(line);
                }
                //l.Add(Lines.get(line));//add the end line
                Lines.remove(line);
                line--;
                Func.Set(name, l);
            }
        }
        return Lines.toArray(new String[0]);
    }
}
