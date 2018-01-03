package com.ttoextreme.everyapp.Intetpreter.StorageValues;

import com.ttoextreme.everyapp.Intetpreter.LuaInterpreterJava;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class Methods {

    LuaInterpreterJava Main;
    private List<MethodItem> MethodsList = new ArrayList<MethodItem>();

    public Methods(LuaInterpreterJava main){
        Main=main;
    }

    public void AddMethod(String name, String caller, BiFunction<String[],String,String> method){
        MethodsList.add(new MethodItem(name,caller,method));
    }

    public boolean MethodValid(String in){
        for(int i=0;i<MethodsList.size();i++){
            if(in.indexOf("(")>-1){in=in.substring(0,in.indexOf("(")+1);}
            if(in.indexOf(MethodsList.get(i).Calls)>-1){return true;}
        }
        return false;
    }

    public BiFunction<String[],String,String> Get(String in){
        for(int i=0;i<MethodsList.size();i++){
            if(in.indexOf("(")>-1){in=in.substring(0,in.indexOf("(")+1);}
            if(in.indexOf(MethodsList.get(i).Calls)>-1){return MethodsList.get(i).Method;}
        }
        return null;
    }


}
class MethodItem {
    public String Name;
    public String Calls;
    BiFunction<String[],String,String> Method;

    MethodItem(String name,String calls,BiFunction<String[],String,String> method){Name = name; Calls = calls; Method = method;}
}
