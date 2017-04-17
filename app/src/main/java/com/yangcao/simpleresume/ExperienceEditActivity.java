package com.yangcao.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yangcao.simpleresume.model.Experience;
import com.yangcao.simpleresume.util.DateUtils;

import java.util.Arrays;

/**
 * Created by Rainie on 4/7/17.
 */

@SuppressWarnings("ConstantConditions")
public class ExperienceEditActivity extends EditBaseActivity <Experience> {

    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_experience_edit;
    }

    @Override
    protected Experience initializeData() {
        return getIntent().getParcelableExtra(KEY_EXPERIENCE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Experience data) {
        ((TextView)findViewById(R.id.experience_edit_company))
                .setText(data.company);
        ((TextView)findViewById(R.id.experience_edit_title))
                .setText(data.title);
        ((TextView)findViewById(R.id.experience_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((TextView)findViewById(R.id.experience_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((TextView)findViewById(R.id.experience_edit_work))
                .setText(TextUtils.join("\n", data.work));

        setTitle("Edit Experience");

        //set delete btn click
        findViewById(R.id.experience_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EXPERIENCE_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.experience_edit_delete).setVisibility(View.GONE);
        setTitle("New Experience");

    }

    @Override
    protected void saveAndExit(@Nullable Experience data) {
        //save data to object
        if (data == null) {
            data = new Experience();
        }
        data.company = ((EditText)
                findViewById(R.id.experience_edit_company)).getText().toString();
        data.title =((EditText)
                findViewById(R.id.experience_edit_title)).getText().toString();
        data.startDate = DateUtils.stringToDate(((EditText)
                findViewById(R.id.experience_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(((EditText)
                findViewById(R.id.experience_edit_end_date)).getText().toString());
        data.work = Arrays.asList(TextUtils.split(((EditText)
                findViewById(R.id.experience_edit_work)).getText().toString(), "\n"));

        //convert object to intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXPERIENCE, data);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

}
