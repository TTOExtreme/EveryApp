package com.ttoextreme.everyapp.Intetpreter;

/**
 * Created by ttoextreme on 11/8/17.
 */

public class References {
    public String If = "if";
    public String Then = "then";
    public String Else = "else";
    public String End = "end";

    public String While = "while";
    public String For = "for";
    public String Do = "do";

    public String Function = "function";
    public String FunctionEnd = "end";
    public String Return = "return";

    public String Comment = "--";

    public String Print = "print";
    public String Write = "write";
    public String Clear = "clear";
    public String Delay = "delay";

    public String Local = "local";
    public String Global = "Global";

    public String Components = "component";

    public String RootDir ="_root";
    public String CurrentDir = "";

    public String[] Alpha = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "ç","₢" };
    public String[] Numeric = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    public String[] Replaceble = { " ", ")", "(", "]", "[", "{", "}", "+", "-", "/", "#", "%", "!", "=", "*", ",", ".", ";", "&", "_" };
    //==================== ERRORS ====================\\
    public String ErrorHead = "[ERROR] ";
    public String ErrorNFC = "Command Not Found :( \n";
    public String ErrorNFF = "File or Folder Not Found :( \n";
    public String ErrorWA = "Wrong Number of Args accepted, max: ";
    public String ErrorWC = "Wrong Conversion\n";

    public String ErrorFor = " use For(<Variable>;<Initial Value>;<End Value>)";

    public String StartTerminal = "term.show;";
    public String EndProgram = "Program Finished!";
}
