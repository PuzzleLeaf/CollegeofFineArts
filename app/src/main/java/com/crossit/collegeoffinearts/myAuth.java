package com.crossit.collegeoffinearts;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class myAuth {
    public static FirebaseUser mUser = null;
    public static FirebaseAuth mAuth = null;

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static String userId = "non";
    public static String userEmail = null;
    public static String userName = null;

//    public static ArrayList<BoardObject> listData = new ArrayList<>();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReferenceFromUrl("gs://test-e18ed.appspot.com");



}
