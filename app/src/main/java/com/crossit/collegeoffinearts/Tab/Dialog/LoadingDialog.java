package com.crossit.collegeoffinearts.Tab.Dialog;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class LoadingDialog {
    public static Loading loading;

    public static void loadingShow(){
        if(!loading.isShowing()){
            loading.show();
        }
    }

    public static void loadingDismiss(){
        if(loading.isShowing()){
            loading.dismiss();
        }
    }
}
