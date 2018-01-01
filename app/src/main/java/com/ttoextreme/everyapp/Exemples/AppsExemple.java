package com.ttoextreme.everyapp.Exemples;

import com.ttoextreme.everyapp.Intetpreter.Statements.Conditionals;
import com.ttoextreme.everyapp.Intetpreter.Statements.ForStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class AppsExemple {
    public static String HelloWorld = "print(\"HelloWorld\"); \ndelay(3000);\n  print(\"HelloWorld\");";
    public static String IfCondition = "print(\"Condition Test\"); \nif(1==1)then\nprint(\"run this if true \");\nelse\nprint(\"run this if false \");\nend";
    public static String ForCondition = "print(\"For Test\"); \nint i=0; \nfor(i;0;10)do\nprint(\"Num\" i);\nend";
    public List<AppExStruct> Exemples = new ArrayList<>();

    public AppsExemple(){
        Exemples.add(new AppExStruct("HelloWorld",HelloWorld));
        Exemples.add(new AppExStruct("IfConditional",IfCondition));
        Exemples.add(new AppExStruct("ForConditional", ForCondition));
    }
}

