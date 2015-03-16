package mfazio.me.cbbbracketrandomizer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import mfazio.me.cbbbracketrandomizer.CBBBracketRandomizerApplication;
import mfazio.me.cbbbracketrandomizer.R;
import mfazio.me.cbbbracketrandomizer.adapters.BracketAdapter;

/**
 * Created by MFazio on 2015-03-13.
 */
public class BracketActivity extends ActionBarActivity {

    private BracketRandomizer bracketRandomizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_bracket);

        this.bracketRandomizer = ((CBBBracketRandomizerApplication) this.getApplication()).getBracketRandomizer();

        final ListView bracketList = (ListView) this.findViewById(R.id.bracket_list);
        final BracketAdapter bracketAdapter = new BracketAdapter(
            this,
            R.layout.game_list_item,
            this.bracketRandomizer.getCurrentBracket()
        );

        bracketList.setAdapter(bracketAdapter);

        bracketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bracketRandomizer.playGame(position);
                bracketAdapter.notifyDataSetChanged();
            }
        });
    }

}
