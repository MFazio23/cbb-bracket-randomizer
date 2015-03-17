package mfazio.me.cbbbracketrandomizer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import mfazio.me.cbbbracketrandomizer.CBBBracketRandomizerApplication;
import mfazio.me.cbbbracketrandomizer.R;
import mfazio.me.cbbbracketrandomizer.adapters.BracketAdapter;

/**
 * Created by MFazio on 2015-03-13.
 */
public class BracketActivity extends ActionBarActivity {

    private BracketRandomizer bracketRandomizer;
    private BracketAdapter bracketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_bracket);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String randomizerType = this.getIntent().getStringExtra("randomizerType");

        this.bracketRandomizer = ((CBBBracketRandomizerApplication) this.getApplication()).getBracketRandomizer();

        ((TextView)this.findViewById(R.id.randomizer_type_label)).setText("Randomizer Type: " + randomizerType);

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
                bracketRandomizer.playGame(position);
                bracketAdapter.notifyDataSetChanged();
            }
        });

    }

    public void playAll(View view) {
        this.bracketRandomizer.playFullBracket();
        this.bracketAdapter.notifyDataSetChanged();
    }
}
