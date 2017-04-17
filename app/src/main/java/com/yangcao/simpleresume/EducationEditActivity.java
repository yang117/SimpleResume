package com.yangcao.simpleresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yangcao.simpleresume.model.Education;
import com.yangcao.simpleresume.util.DateUtils;
import java.util.Arrays;

/**
 * Created by Rainie on 4/5/17.
 */

@SuppressWarnings("ConstantConditions")
public class EducationEditActivity extends EditBaseActivity <Education>{

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected Education initializeData() {  //取得传入的初值
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Education data) {
        ((TextView) findViewById(R.id.education_edit_school))
                .setText(data.school);
        ((TextView)findViewById(R.id.education_edit_major))
                .setText(data.major);
        ((TextView)findViewById(R.id.education_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((TextView)findViewById(R.id.education_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((TextView)findViewById(R.id.education_edit_courses))
                .setText(TextUtils.join("\n", data.courses)); //拼接字符串

        setTitle("Edit Education");

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
        setTitle("New Education");
    }

    @Override
    protected void saveAndExit(@Nullable Education data) {
        //save data from UI to an object
        if (data == null) {
            data = new Education();
        } //不为空则继续使用之前的edu对象,id不变

        data.school = ((EditText)
                findViewById(R.id.education_edit_school)).getText().toString();
        data.major = ((EditText)
                findViewById(R.id.education_edit_major)).getText().toString();
        data.startDate = DateUtils.stringToDate(((EditText)
                findViewById(R.id.education_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(((EditText)
                findViewById(R.id.education_edit_end_date)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(((EditText)
                findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        //convert the object to a result(intent)
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

}





