package com.ttoextreme.everyapp.Intetpreter.StorageValues.Components;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;
import com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables.VariablesStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class Components {
    private LuaInterpreterJava Lua;
    private List<ComponentsStruct> Comp = new ArrayList<>();

    public Components(LuaInterpreterJava luaInterpreterJava) {
        Lua=luaInterpreterJava;
        Lua.DebugAppend("[Init] Initialize Components Storage");
    }

    public boolean  Exists(String var){
        boolean r = false;
        for (ComponentsStruct v : Comp)
        {
            if (v.Name.equals(var))
            {
                r = true;
            }
        }
        return r;
    }

    public List<ComponentsStruct> GetAll(){
        return Comp;
    }

    //variable replace
    public void VarSet(String name, Object value)
    {
            for (int i = 0; i < Comp.size(); i++)
            {
                ComponentsStruct va = Comp.get(i);
                if (va.Name.equals(name)) {
                    va = new ComponentsStruct(name, value);
                    Comp.remove(i);
                    Comp.add(i, va);
                    return;
                }
            }
        Comp.add(new ComponentsStruct(name, value));
    }

    //delete variable
    public void VarRem(String name)
    {
        for (int i = 0; i < Comp.size(); i++)
        {
            ComponentsStruct va = Comp.get(i);
            if (va.Name.equals(name)) {
                if(Comp.remove(va)){
                    Lua.DoLine(Lua.Refer.PrintDev+"(\"Removed Variable: "+ va.Name +"\")");
                }else{
                    Lua.DebugAppend("[Error] Operation on Variable: Removing \n !!!!!! if you see this message that is a problem !!!!!!!\n");
                    Lua.DoLine(Lua.Refer.PrintDev+"(\"Error Removing Variable: "+ va.Name +"\n !!!!!!if you see this message that is a problem!!!!!!!\")");
                }
                return;
            }
        }
    }
}
