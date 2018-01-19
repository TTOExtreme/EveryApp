package com.ttoextreme.everyapp.Intetpreter.StorageValues.Variables;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class VariablesStruct
{
    public int GLOBAL = 0;
    public int LOCAL = 1;

    public String Name = "";
    public int Type = 0;
    public Object Value;

    public VariablesStruct() { }

    public VariablesStruct(String name, int type, Object value)
    {
        this.Name = name;
        this.Type = type;
        this.Value = value;
    }
}
