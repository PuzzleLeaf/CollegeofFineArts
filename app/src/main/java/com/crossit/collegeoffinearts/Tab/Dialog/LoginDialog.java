package com.crossit.collegeoffinearts.Tab.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.crossit.collegeoffinearts.R;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class LoginDialog extends Dialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public LoginDialog(Context context) {
        super(context,R.style.theme_dialog);
    }
}
