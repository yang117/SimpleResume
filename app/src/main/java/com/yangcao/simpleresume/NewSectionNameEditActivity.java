package com.yangcao.simpleresume;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yangcao.simpleresume.model.NewSection;

public class NewSectionNameEditActivity extends AppCompatActivity {

    public static final String KEY_NEW_SECTION_NAME = "new_section_name";
    public static final String KEY_NEW_SECITON_NAME_ID = "new_section_name_id";

    private  NewSection sectionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_section_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sectionName = getIntent().getParcelableExtra(KEY_NEW_SECTION_NAME);
        if (sectionName != null) {
            ((EditText)findViewById(R.id.new_section_add_name)).setText(sectionName.name);
        }

        //设置delete的点击事件
        findViewById(R.id.new_section_add_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_NEW_SECITON_NAME_ID, sectionName.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        //save data
        if (sectionName == null) {
            sectionName = new NewSection();
        }

        sectionName.name = ((EditText)
                findViewById(R.id.new_section_add_name)).getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_NEW_SECTION_NAME, sectionName);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
