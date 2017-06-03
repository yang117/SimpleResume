package com.yangcao.simpleresume;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.yangcao.simpleresume.model.NewSection;

public class NewSectionNameEditActivity extends EditBaseActivity <NewSection> {

    public static final String KEY_NEW_SECTION_NAME = "new_section_name";
    public static final String KEY_NEW_SECITON_NAME_ID = "new_section_name_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_section_add;
    }

    @Override
    protected NewSection initializeData() {
        return getIntent().getParcelableExtra(KEY_NEW_SECTION_NAME);
    }

    @Override
    protected void setupUIForEdit(@NonNull final NewSection data) {
        ((EditText)findViewById(R.id.new_section_add_name)).setText(data.name);

        setTitle("Edit Section");

        //设置delete的点击事件
        findViewById(R.id.new_section_add_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_NEW_SECITON_NAME_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.new_section_add_delete).setVisibility(View.GONE);
        setTitle("New Section");
    }

    @Override
    protected void saveAndExit(@Nullable NewSection data) {
        //save data
        if (data == null) {
            data = new NewSection();
        }

        data.name = ((EditText)
                findViewById(R.id.new_section_add_name)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_NEW_SECTION_NAME, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
