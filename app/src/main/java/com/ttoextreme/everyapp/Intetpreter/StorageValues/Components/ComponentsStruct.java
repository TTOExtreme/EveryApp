package com.ttoextreme.everyapp.Intetpreter.StorageValues.Components;

/**
 * Created by TTOExtreme on 23/01/2018.
 */

public class ComponentsStruct {
    public String Name = "";
    public Object Value;

    public ComponentsStruct() { }

    public ComponentsStruct(String name, Object value)
    {
        this.Name = name;
        this.Value = value;
    }
}
