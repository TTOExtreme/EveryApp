package com.ttoextreme.everyapp.Intetpreter.StorageValues;

import com.ttoextreme.everyapp.Intetpreter.References;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/9/17.
 */

public class Variables {

    public List<VariablesStruct> LV = new ArrayList<>();
    public List<VariablesStruct> GV = new ArrayList<>();
    public String BGExecution = "";

    public String Replace(String command)
    {

        boolean brea = false;
        for (VariablesStruct v : LV)
        {
            if (command.indexOf(v.Name) > -1)
            {
                String[] st = new References().Replaceble;

                for (String s1 : st)
                {
                    for (String s2 : st)
                    {
                        if (command.indexOf(s2 + v.Name + s1) > -1) { command = command.replace(s2 + v.Name + s1, s2 + v.Value.toString() + s1); brea = true; }
                        if (command.indexOf(s1 + v.Name + s2) > -1) { command = command.replace(s1 + v.Name + s2, s1 + v.Value.toString() + s2); brea = true; }
                        if (command.indexOf(v.Name + s1) == 0) { command = command.replace(v.Name + s1, v.Value.toString() + s1); }
                        if (command.indexOf(s1 + v.Name) == 0) { command = command.replace(s1 + v.Name, s1 + v.Value.toString()); }
                        if (command == v.Name) { command = command.replace(v.Name, v.Value.toString()); brea = true; }
                    }
                    if (brea) {
                        //break;
                    }
                }
            }
        }

        for (VariablesStruct v : GV)
        {
            if (command.indexOf(v.Name) > -1)
            {
                String[] st = new References().Replaceble;

                for (String s1 : st)
                {
                    for (String s2 : st)
                    {
                        if (command.indexOf(s2 + v.Name + s1) > -1) { command = command.replace(s2 + v.Name + s1, s2 + v.Value.toString() + s1); }
                        if (command.indexOf(s1 + v.Name + s2) > -1) { command = command.replace(s1 + v.Name + s2, s1 + v.Value.toString() + s2); }
                        if (command.indexOf(v.Name + s1) == 0) { command = command.replace(v.Name + s1, v.Value.toString() + s1); }
                        if (command.indexOf(s1 + v.Name) == 0) { command = command.replace(s1 + v.Name, s1 + v.Value.toString()); }
                        if (command == v.Name) { command = command.replace(v.Name, v.Value.toString()); }
                    }
                }
            }
        }
        return command;
    }

    public boolean  Exists(String var){
        boolean r = false;
        for (VariablesStruct v : LV)
        {
            if (v.Name.equals(var))
            {
                r = true;
            }
        }
        for (VariablesStruct v : GV)
        {
            if (v.Name.equals(var))
            {
                r = true;
            }
        }

        return r;
    }

    public void VarSum(String var, int val)
    {
        if (var.indexOf(" ") > -1) { var = var.replace(" ", ""); }
        for (VariablesStruct v : LV)
        {
            if (v.Name.equals(var))
            {
                //if (v.Type == "int")
                try
                {
                    v.Value = Integer.parseInt(v.Value.toString()) + val;
                    VarSet(v.Name,v.Type,v.Value);
                }
                catch(Exception e)
                {
                    System.err.println(e);
                }
            }
        }
    }
    public void VarSub(String var, int val)
    {
        if (var.indexOf(" ") > -1) { var = var.replace(" ", ""); }
        for (VariablesStruct v : LV)
        {
            if (v.Name.equals(var))
            {
                //if (v.Type == "int")
                try
                {
                    v.Value = Integer.parseInt(v.Value.toString()) - val;
                    VarSet(v.Name,v.Type,v.Value);
                }
                catch(Exception e)
                {
                    System.err.println(e);
                }
            }
        }
    }


    //insert new variable or rewrite
    public void VarAdd(VariablesStruct v)
    {
        if (v.Value.toString() == "NaN" || v.Value.toString() == "") { v.Value = "null"; }
        if (v.Name != "" && v.Name != " ")
        {
            if (v.Type == new VariablesStruct().LOCAL)
            {
                for (int i = 0; i < LV.size(); i++)
                {
                    VariablesStruct va = LV.get(i);
                    if (va.Name.equals(v.Name)) { va = v; LV.set(i,va);  return; }
                }
                LV.add(v);
            }
            else
            {
                for (int i = 0; i < GV.size(); i++)
                {
                    VariablesStruct va = GV.get(i);
                    if (va.Name.equals(v.Name)) { va = v; GV.set(i,va);  return; }
                }
                GV.add(v);
            }
        }
    }

    //variable replace
    public void VarSet(String name, int type, Object value)
    {
        if (type == new VariablesStruct().LOCAL)
        {
            for (int i = 0; i < LV.size(); i++)
            {
                VariablesStruct va = LV.get(i);
                if (va.Name == name) { va = new VariablesStruct(name,type,value); return; }
                LV.set(i,va);
            }
            LV.add(new VariablesStruct(name, type, value));
        }
        else
        {
            for (int i = 0; i < GV.size(); i++)
            {
                VariablesStruct va = GV.get(i);
                if (va.Name == name) { va = new VariablesStruct(name,type,value); return; }
                GV.set(i,va);
            }
            GV.add(new VariablesStruct(name, type, value));
        }
    }

    //delete variable
    public void VarRem(String name)
    {
        for (int i = 0; i < LV.size(); i++)
        {
            VariablesStruct va = LV.get(i);
            if (va.Name.equals(name)) {
                if(LV.remove(va)){
                    System.out.print("removed");
                }else{
                    System.out.print("Error removing");
                }
                return;
            }
        }
        for (int i = 0; i < GV.size(); i++)
        {
            VariablesStruct va = GV.get(i);
            if (va.Name.equals(name)) { GV.remove(va); return; }
        }
    }
}
