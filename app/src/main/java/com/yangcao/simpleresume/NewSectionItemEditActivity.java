package com.yangcao.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yangcao.simpleresume.model.NewSection;
import com.yangcao.simpleresume.model.NewSectionItem;
import com.yangcao.simpleresume.util.DateUtils;


public class NewSectionItemEditActivity extends EditBaseActivity <NewSectionItem> {

    public static final String KEY_NEW_SECTION_ITEM = "new_section_item";
    public static final String KEY_NEW_SECTION_ITEM_ID = "new_section_item_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_section_edit;
    }

    @Override
    protected NewSectionItem initializeData() {
        return getIntent().getParcelableExtra(KEY_NEW_SECTION_ITEM);
    }

    @Override
    protected void setupUIForEdit(@NonNull final NewSectionItem data) {
        ((EditText)findViewById(R.id.new_section_edit_title)).setText(data.title);
        ((EditText)findViewById(R.id.new_section_edit_date)).setText(DateUtils.dateToString(data.date));
        ((EditText)findViewById(R.id.new_section_edit_content)).setText(data.content);

        setTitle("Edit");

        //设置delete点击事件
        findViewById(R.id.new_section_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_NEW_SECTION_ITEM_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.new_section_edit_delete).setVisibility(View.GONE);
        setTitle("Add");
    }

    @Override
    protected void saveAndExit(@Nullable NewSectionItem data) {
        if (data == null) {
            data = new NewSectionItem();
            data.sectionNameId = getIntent().getStringExtra(
                    NewSectionNameEditActivity.KEY_NEW_SECITON_NAME_ID);
        }

        data.title = ((EditText)
                findViewById(R.id.new_section_edit_title)).getText().toString();
        data.date = DateUtils.stringToDate(((EditText)
                findViewById(R.id.new_section_edit_date)).getText().toString());
        data.content = ((EditText)
                findViewById(R.id.new_section_edit_content)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_NEW_SECTION_ITEM, data);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }
}
