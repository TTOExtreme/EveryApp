package com.ttoextreme.everyapp.Intetpreter.Statements;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.References;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;

import java.io.File;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class Conditionals {
    private LuaInterpreterJava Lua;
    private Variables Vars;

    public Conditionals(LuaInterpreterJava lua)
    {
        Lua = lua;
        Vars = Lua.Vars;
    }

    public boolean IsTrue(String prog)
    {
        if(Vars == null){Vars = Lua.Vars;}
        if (prog.length() < 1) { return false; }
        String str = Vars.Replace(prog);
        String[] args;

        //#region +++++++++++++++++++++ "Exists" ++++++++++++++++++++++++
        if (str.indexOf("exist") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")") - (str.indexOf("(") + 1))).replace("exist", "#").split("#");
            String comp1 = args[1];

            if (comp1.indexOf(new References().RootDir) > -1)
            {
                String s = ((Vars.Replace("CurrentDir") + "Assets").replace(" ", "") + comp1.replace(" ", "")).replace("\\", "/");
                if (new File(s).exists()) { return true; } else { return false; }
            }
            else
            {
                String s = ((Vars.Replace("CurrentDir") + "Assets" + Vars.Replace("CurrentLocation")).replace(" ", "") + "\\" + comp1.replace(" ", "")).replace("\\", "/");
                if (new File(s).exists()) { return true; } else { return false; }
            }
        }
          
        //#region +++++++++++++++++++++ "==" ++++++++++++++++++++++++
        if (str.indexOf("==") > -1)
        {
            str = Vars.Replace(str);

            String s =str.substring(str.indexOf("(") + 1, (str.indexOf(")")));
            args = s.split("==");
            String comp1 = args[0].replace(" ","");
            String comp2 = args[1].replace(" ","");
            //boolean isTrue = false;

            if (comp1.equals(comp2)) {
                System.out.println("true");
                return true;
            }else {
                System.out.println("False");
                return false;
            }

        }
          
        //#region +++++++++++++++++++++ "!=" ++++++++++++++++++++++++
        if (str.indexOf("!=") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")"))).replace("!=", "#").split("#");
            String comp1 = args[0];
            String comp2 = args[1];
            boolean isTrue = false;

            if (comp1 != comp2 && (comp1 != "" && comp2 != "")) { return true; } else { return false; }

        }
        
        //#region +++++++++++++++++++++ ">" ++++++++++++++++++++++++
        if (str.indexOf(">") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")"))).replace(">", "#").split("#");
            String comp1 = args[0];
            String comp2 = args[1];
            boolean isTrue = false;

            if (Double.valueOf(comp1) > Double.valueOf(comp2) && (comp1 != "" && comp2 != "")) { return true; } else { return false; }

        }
        
        //#region +++++++++++++++++++++ "<" ++++++++++++++++++++++++
        if (str.indexOf("<") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")"))).replace("<", "#").split("#");
            String comp1 = args[0];
            String comp2 = args[1];
            boolean isTrue = false;

            if (Double.valueOf(comp1) < Double.valueOf(comp2) && (comp1 != "" && comp2 != "")) { return true; } else { return false; }

        }
        
        //#region +++++++++++++++++++++ "<=" ++++++++++++++++++++++++
        if (str.indexOf("<=") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")"))).replace("<=", "#").split("#");
            String comp1 = args[0];
            String comp2 = args[1];
            boolean isTrue = false;

            if (Double.valueOf(comp1) <= Double.valueOf(comp2) && (comp1 != "" && comp2 != "")) { return true; } else { return false; }

        }
            
        //#region +++++++++++++++++++++ ">=" ++++++++++++++++++++++++
        if (str.indexOf(">=") > -1)
        {
            str = Vars.Replace(str);

            args = str.substring(str.indexOf("(") + 1, (str.indexOf(")"))).replace(">=", "#").split("#");
            String comp1 = args[0];
            String comp2 = args[1];
            boolean isTrue = false;

            //Console.WriteLine("c1: " + comp1 + " c2: " + comp2);

            if (Double.valueOf(comp1) >= Double.valueOf(comp2) && (comp1 != "" && comp2 != ""))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
     
            //#region +++++++++++++++++++++ "direct boolean" ++++++++++++++++++++++++
        if (str.indexOf("true") > -1)
        {
            return true;
        }
 
        return false;
    }
}
