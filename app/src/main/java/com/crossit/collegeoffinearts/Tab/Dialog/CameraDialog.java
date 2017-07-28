package com.crossit.collegeoffinearts.Tab.Dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;

import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.LinearLayout;


import com.crossit.collegeoffinearts.R;

import java.io.File;


/**
 * Created by cmtyx on 2017-06-22.
 */

public class CameraDialog extends AppCompatActivity {

    final int PICK_FROM_CAMERA = 100, PICK_FROM_ALBUM = 101, CROP_FROM_CAMERA = 102;

    private String imgPath = "";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_camera_dialog);

        externalClick();
        cameraClick();
        galleryClick();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CROP_FROM_CAMERA:
                    bresultData(uri);
                    break;
                case PICK_FROM_ALBUM:
                    Uri uri = data.getData();
                    bresultData(uri);
                    break;
                case PICK_FROM_CAMERA:
                    cropImage();
                    break;

            }
        }
    }


    public void cameraClick() {
        LinearLayout cameraClick = (LinearLayout) findViewById(R.id.camera_select);
        cameraClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
    }

    private void showCamera() {
        uri = getFileUri();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PICK_FROM_CAMERA);

    }

    private Uri getFileUri() {
        File dir = new File(this.getFilesDir(), "img");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, System.currentTimeMillis() + "png");
        imgPath = file.getAbsolutePath();

        return FileProvider.getUriForFile(this, "com.crossit.collegeoffinearts", new File(imgPath));
    }

    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //indicate image type and Uri of image
        cropIntent.setDataAndType(uri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cropIntent, CROP_FROM_CAMERA);
    }


    public void galleryClick() {
        LinearLayout galleryClick = (LinearLayout) findViewById(R.id.gallery_select);
        galleryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_FROM_ALBUM);
                }
            }
        });
    }

    void bresultData(Uri uri) {
        Intent intent = new Intent();
        intent.putExtra("image", uri.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void externalClick() {
        LinearLayout external = (LinearLayout) findViewById(R.id.camera_dialog);
        external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
