package com.yangcao.simpleresume;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Rainie on 4/10/17.
 */

public abstract class EditBaseActivity <T> extends AppCompatActivity {

    private T data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = initializeData();
        if (data != null) {
            setupUIForEdit(data);
        } else {
            setupUIForCreate();
        }
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
                saveAndExit(data);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutId();

    protected abstract T initializeData();

    protected abstract void setupUIForEdit(@NonNull T data);

    protected abstract void setupUIForCreate();

    protected abstract void saveAndExit(@Nullable T data);

}
