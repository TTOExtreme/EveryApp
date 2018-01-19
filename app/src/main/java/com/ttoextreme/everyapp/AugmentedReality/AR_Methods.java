package com.ttoextreme.everyapp.AugmentedReality;

import com.ttoextreme.everyapp.MainScreen;

/**
 * Created by TTOExtreme on 19/01/2018.
 */

public class AR_Methods {
    private MainScreen Main;
    private AR_Refer Refer = new AR_Refer();

    public AR_Methods(MainScreen main){
        Main = main;
    }

    public void CreateMethods(){
        Main.Lua.AddMethod(Refer.StartAR,Refer.StartAR, Main::StartAR);
    }

}
