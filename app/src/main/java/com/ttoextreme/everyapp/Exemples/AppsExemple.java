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
    public static String IfCondition = "print(\"Condition Test\"); \nif(1==1)then\nprint(\"run this if true \");\ndelay(3000);\n  print(\"True Delayed\");\nelse\nprint(\"run this if false \");\nend";
    public static String ForCondition = "print(\"For Test\"); \ni=0; \nfor(i;0;10)do\nprint(\"Num\" i);\n delay(1000);\nend";
    public static String WhileCondition = "print(\"While Test\"); \ni = 0; \nwhile(i < 10)do\nprint(\"Num\" i);\n delay(500);\ni++;\nend";
    public static String Function = "print(\"Functions\");\n\nfunction func1()\nprint(\"this is in func1\");\nend\n\nfunction func2()\nprint(\"this is in func2\");\nend\n\nfunction func3()\nprint(\"this is in func3\");\nend\n\nfunc2();\nfunc3();\nfunc1();";
    public static String Button = "act.init;\ncreateact(scr1);\ncreateview(button,bot1);\nscr1.addview(bot1);\nbot1.setBgColor(0,0,255);\nbot1.setTextColor(50,255,50);\nact.start(scr1);";
    public List<AppExStruct> Exemples = new ArrayList<>();

    public AppsExemple(){
        Exemples.add(new AppExStruct("01-HelloWorld",HelloWorld));
        Exemples.add(new AppExStruct("02-IfConditional",IfCondition));
        Exemples.add(new AppExStruct("03-ForConditional", ForCondition));
        Exemples.add(new AppExStruct("04-WhileConditional", WhileCondition));
        Exemples.add(new AppExStruct("05-Function", Function));
        Exemples.add(new AppExStruct("06-Button", Button));
    }
}

