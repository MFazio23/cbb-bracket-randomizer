package mfazio.me.cbbbracketrandomizer.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import mfazio.me.cbbbracketrandomizer.CBBBracketRandomizerApplication;
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

        ((TextView)this.findViewById(R.id.about_logo)).setMovementMethod(LinkMovementMethod.getInstance());


        final Tracker tracker = ((CBBBracketRandomizerApplication) getApplication()).getTracker();
        tracker.setScreenName("About");
        tracker.send(
            new HitBuilders.ScreenViewBuilder().build()
        );

    }

    public void goToEmail(View view) {
        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "android@mfazio.me");
        intent.setData(Uri.parse("mailto:"));
        startActivity(intent);
    }

    public void goToTwitter(View view) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://twitter.com/FazioDev"));

        this.startActivity(intent);
    }
}
