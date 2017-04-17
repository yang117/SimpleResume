package com.yangcao.simpleresume;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yangcao.simpleresume.model.BasicInfo;
import com.yangcao.simpleresume.util.ImageUtils;
import com.yangcao.simpleresume.util.PermissionUtils;

/**
 * Created by Rainie on 4/8/17.
 */

public class BasicInfoEditActivity extends EditBaseActivity <BasicInfo> {

    public static final String KEY_BASIC_INFO = "basic_info";
    private static final int REQ_CODE_PICK_IMAGE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                showImage(imageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtils.REQ_CODE_WRITE_EXTERNAL_STORAGE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info_edit;
    }

    @Override
    protected BasicInfo initializeData() {
        return getIntent().getParcelableExtra(KEY_BASIC_INFO);
    }

    @Override
    protected void setupUIForEdit(@NonNull BasicInfo data) {
        ((EditText)findViewById(R.id.basic_info_edit_name))
                .setText(data.name);
        ((EditText)findViewById(R.id.basic_info_edit_email))
                .setText(data.email);

        if (data.imageUri != null) {
            showImage(data.imageUri);
        }

        //choose image listener
        findViewById(R.id.basic_info_edit_image_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PermissionUtils.checkPermission(BasicInfoEditActivity.this,
                                                     Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtils.requestReadExternalStoragePermission(BasicInfoEditActivity.this);
                } else {
                    pickImage();
                }
            }
        });

        setTitle("Edit Basic Info");
    }

    @Override
    protected void setupUIForCreate() {}

    @Override
    protected void saveAndExit(@Nullable BasicInfo data) {
        if (data == null) {
            data = new BasicInfo();
        }
        data.name = ((EditText)
                findViewById(R.id.basic_info_edit_name)).getText().toString();
        data.email = ((EditText)
                findViewById(R.id.basic_info_edit_email)).getText().toString();
        data.imageUri = (Uri)findViewById(R.id.basic_info_edit_image).getTag();

        //object -> intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_BASIC_INFO, data);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    private void showImage(@NonNull Uri imageUri) {
        ImageView imageView = (ImageView)findViewById(R.id.basic_info_edit_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setTag(imageUri);
        ImageUtils.loadImage(this, imageUri,imageView);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                REQ_CODE_PICK_IMAGE);
    }


}
