package com.ttoextreme.everyapp.Intetpreter.StorageValues.Methods;

import java.util.function.BiFunction;

/**
 * Created by TTOExtreme on 18/01/2018.
 */
class MethodItem {
    public String Name;
    public String Calls;
    BiFunction<String[],String,String> Method;

    MethodItem(String name,String calls,BiFunction<String[],String,String> method){Name = name; Calls = calls; Method = method;}
}
