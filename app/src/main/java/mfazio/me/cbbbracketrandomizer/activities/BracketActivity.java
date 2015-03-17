package mfazio.me.cbbbracketrandomizer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.CBBBracketRandomizerApplication;
import mfazio.me.cbbbracketrandomizer.R;
import mfazio.me.cbbbracketrandomizer.adapters.BracketAdapter;

/**
 * Created by MFazio on 2015-03-13.
 */
public class BracketActivity extends ActionBarActivity {

    private CBBBracketRandomizerApplication app;
    private Tracker tracker;
    private String randomizerType;
    private BracketRandomizer bracketRandomizer;
    private BracketAdapter bracketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_bracket);
        this.app = (CBBBracketRandomizerApplication) this.getApplication();
        this.tracker = this.app.getTracker();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.randomizerType = this.getIntent().getStringExtra("randomizerType");

        this.bracketRandomizer = this.app.getBracketRandomizer();

        ((TextView) this.findViewById(R.id.randomizer_type_label)).setText("Randomizer Type: " + randomizerType);

        final ListView bracketList = (ListView) this.findViewById(R.id.bracket_list);
        this.bracketAdapter = new BracketAdapter(
            this,
            R.layout.game_list_item,
            R.layout.game_list_item_header,
            this.bracketRandomizer.getCurrentBracket()
        );

        bracketList.setAdapter(this.bracketAdapter);

        bracketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Team winner = bracketRandomizer.playGame(position);
                bracketAdapter.notifyDataSetChanged();

                if (tracker != null) {

                    final HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder()
                        .setCategory("Random Games - " + randomizerType)
                        .setAction("Play Single Game");
                    if (winner != null) {
                        eventBuilder.setLabel(winner.getSchoolName());
                    }

                    eventBuilder.setValue(1);
                    tracker.send(
                        eventBuilder.build()
                    );
                }
            }
        });

        if (this.tracker != null) {
            tracker.setScreenName("Bracket");
            tracker.send(
                new HitBuilders.ScreenViewBuilder().build()
            );
        }
    }

    public void playAll(View view) {
        final Team winner = this.bracketRandomizer.playFullBracket();
        this.bracketAdapter.notifyDataSetChanged();

        if (this.tracker != null) {
            this.tracker.send(
                new HitBuilders.EventBuilder()
                    .setCategory("Random Games - " + this.randomizerType)
                    .setAction("Play Full Bracket")
                    .setLabel(winner.getSchoolName())
                    .setValue(1)
                    .build()
            );
        }
    }
}
