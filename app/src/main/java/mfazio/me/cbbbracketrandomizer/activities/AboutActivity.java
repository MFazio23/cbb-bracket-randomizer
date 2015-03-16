package mfazio.me.cbbbracketrandomizer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import mfazio.me.cbbbracketrandomizer.R;

/**
 * Created by MFazio on 2015-03-13.
 */
public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
