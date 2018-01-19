package com.ttoextreme.everyapp.Intetpreter.StorageValues.Functions;

import android.app.Activity;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class Functions {

    private List<FunctionStruct> Fs = new ArrayList<>();


    private LuaInterpreterJava Lua;

    public Functions(LuaInterpreterJava lua)
    {
        Lua = lua;
        Lua.DebugAppend("[Init] Initialize Functions Class");
    }

    public List<String> Get(String name)
    {
        for (FunctionStruct f : Fs)
        {
            if (name.indexOf(f.Name)>-1) { return f.Lines; }
        }
        return new ArrayList<String>();
    }
    public List<String> GetAll()
    {
        List<String> l = new ArrayList<>();
        for (FunctionStruct f : Fs)
        {
            l.add(f.Name);
        }
        return l;
    }

    public boolean Exist(String name)
    {
        for (FunctionStruct f : Fs)
        {
            if (name.indexOf(f.Name)>-1) { return true; }
        }
        return false;
    }

    public void Set(String name, List<String> Lines)
    {
        FunctionStruct fu = new FunctionStruct();
        fu.Name = name;
        fu.Lines = Lines;
        for (int i = 0; i < Fs.size(); i++)
        {
            if (Fs.get(i).Name == name) { Fs.set(i,fu); return; }
        }
        Fs.add(fu);
    }

    public void Set(String name, List<String> Lines,boolean Return)
    {
        FunctionStruct fu = new FunctionStruct();
        fu.Name = name;
        fu.Lines = Lines;
        fu.Return = Return;
        for (int i = 0; i < Fs.size(); i++)
        {
            if (Fs.get(i).Name == name) { Fs.set(i,fu); return; }
        }
        Fs.add(fu);
    }
}
