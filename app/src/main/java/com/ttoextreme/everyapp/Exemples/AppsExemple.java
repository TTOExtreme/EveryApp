package com.ttoextreme.everyapp.Exemples;

import com.ttoextreme.everyapp.Intetpreter.Statements.Conditionals;
import com.ttoextreme.everyapp.Intetpreter.Statements.ForStatement;
import com.ttoextreme.everyapp.MainScreen;

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
    public static String Button = "act.init;\ncreateAct(scr1);\ncreateView(button,bot1);\nbot1.setText(\"button\");\nbot1.setBgColor(0,0,255);\nbot1.setTextColor(50,255,50);\nbot1.setWidth(300);\nbot1.setHeight(300);\nbot1.setMarginTop(60);\nbot1.setMarginBottom(60);\nbot1.setMarginLeft(60);\nbot1.setMarginRight(60);\nscr1.addView(bot1);\nact.start(scr1);";
    public static String OpenAct = "act.init;\ncreateAct(scr1);\ncreateView(button,bot1);\nbot1.setText(\"Open Other Activity\");\nbot1.setBgColor(0,0,255);\nbot1.setTextColor(50,255,50);\nbot1.setWidth(300);\nbot1.setHeight(300);\nbot1.setMarginTop(60);\nbot1.setMarginBottom(60);\nbot1.setMarginLeft(60);\nbot1.setMarginRight(60);\nbot1.setClick(\"act.start(scr1);\");\nMain.addView(bot1);\n\n"+
                                    "\ncreateView(button,bot2);\nbot2.setText(\"OpenMain\");\nbot2.setBgColor(0,0,255);\nbot2.setTextColor(50,255,50);\nbot2.setWidth(300);\nbot2.setHeight(300);\nbot2.setMarginTop(60);\nbot2.setMarginBottom(60);\nbot2.setMarginLeft(60);\nbot2.setMarginRight(60);\nbot2.setClick(\"act.start(Main);\");\nscr1.addView(bot2);\nact.start(Main);";
    public static String AugReality = "AR.init();";

    public List<AppExStruct> Exemples = new ArrayList<>();

    public AppsExemple(MainScreen Main){
        Main.DebugAct.Append("[Init] Initialize Exemples Class");
        Exemples.add(new AppExStruct("01-HelloWorld",HelloWorld));
        Exemples.add(new AppExStruct("02-If_Conditional",IfCondition));
        Exemples.add(new AppExStruct("03-For_Conditional", ForCondition));
        Exemples.add(new AppExStruct("04-While_Conditional", WhileCondition));
        Exemples.add(new AppExStruct("05-Function", Function));
        Exemples.add(new AppExStruct("06-Button", Button));
        Exemples.add(new AppExStruct("07-Open_Another_Activity", OpenAct));
        Exemples.add(new AppExStruct("08-Augmented_Reality", AugReality));
    }
}

