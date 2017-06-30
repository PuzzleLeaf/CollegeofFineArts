package com.crossit.collegeoffinearts;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class LoginActivity extends AppCompatActivity {

    String TAG = "qwe";
    private static final int RC_SIGN_IN = 9001;

    private SharedPreferences sharedPref;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private TextView nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        myAuth.mAuth = FirebaseAuth.getInstance();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "네트워크 환경이 불안전 합니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
//                    String userId = user.getUid();
//                    String temp = user.getEmail();
//                    int idx = temp.indexOf("@");
//                    String userEmail = temp.substring(0,idx);
//
//                    sharedPref = getSharedPreferences("key",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString("firebaseKey",userId);
//                    editor.putString("firebaseEmail",userEmail);
//                    editor.putString("firebaseName",nickName.getText().toString());
//                    editor.commit();
//
//                    myAuth.userEmail = userEmail;
//                    myAuth.userId = userId;
                } else {
                   resetData();
                }
            }
        };

        nickName = (TextView)findViewById(R.id.login_nickname);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_btn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        myAuth.mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            myAuth.mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //SharedPreference Data 초기화
    private void resetData()
    {
        sharedPref = getSharedPreferences("key",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firebaseKey", "non");
        editor.putString("firebaseEmail",null);
        editor.putString("firebaseName",null);
        editor.commit();

        myAuth.mUser = null;
        myAuth.userEmail = null;
        myAuth.userId = null;
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        myAuth.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            myAuth.mUser = task.getResult().getUser();
                            sharedPref = getSharedPreferences("key",MODE_PRIVATE);
                            myAuth.userId = sharedPref.getString("firebaseKey", "non");
                            myAuth.userEmail = sharedPref.getString("firebaseEmail",null);
                            myAuth.userName = sharedPref.getString("firebaseName",null);
                            Toast.makeText(getApplicationContext(), "환영합니다.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
            }
        }
    }


}
