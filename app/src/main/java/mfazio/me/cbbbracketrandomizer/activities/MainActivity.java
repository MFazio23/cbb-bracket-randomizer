package mfazio.me.cbbbracketrandomizer.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.dataloaders.KimonoDataLoader;
import me.mfazio.cbbbracketrandomizer.randomizers.CoinFlipRandomizer;
import me.mfazio.cbbbracketrandomizer.randomizers.GenericRatingRandomizer;
import me.mfazio.cbbbracketrandomizer.randomizers.Randomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.ratingsmappers.RatingsMapper;
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.CBBBracketRandomizerApplication;
import mfazio.me.cbbbracketrandomizer.R;
import mfazio.me.cbbbracketrandomizer.adapters.RandomizerAdapter;
import mfazio.me.cbbbracketrandomizer.tasks.GameLoaderTask;
import mfazio.me.cbbbracketrandomizer.tasks.RatingsLoaderTask;
import mfazio.me.cbbbracketrandomizer.tasks.TeamLoaderTask;
import mfazio.me.cbbbracketrandomizer.types.RandomizerInfo;


public class MainActivity extends ActionBarActivity implements DataLoaderActivity {

    private CBBBracketRandomizerApplication app;
    private TextView loadingView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = ((CBBBracketRandomizerApplication) this.getApplication());

        this.progressBar = (ProgressBar) this.findViewById(R.id.main_progress_bar);

        this.loadingView = (TextView) this.findViewById(R.id.loading_view);

        if (!this.app.gamesAndTeamsLoaded()) {
            this.loadData();
        }

        final ListView listView = (ListView) this.findViewById(R.id.randomizer_list_view);

        listView.setAdapter(
            new RandomizerAdapter(
                this,
                R.layout.randomizer_list_item,
                new ArrayList<RandomizerInfo>() {{
                    add(new RandomizerInfo(Rating.RatingType.CoinFlip, "Coin Flip", "50/50 chance for all teams.", Randomizer.UpsetChance.VERY_HIGH));
                    add(new RandomizerInfo(Rating.RatingType.Seed, "Seeded", "Random results based on seeding.", Randomizer.UpsetChance.MEDIUM));
                    //add(new RandomizerInfo(Rating.RatingType.RPI, "RPI", "Random results based on RPI ranking.", Randomizer.UpsetChance.HIGH));
                }}
            )
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    app.getBracketRandomizer().configureRandomizerAndBracket(
                        ((RandomizerInfo) listView.getAdapter().getItem(position)).getRatingType()
                    );

                    final Intent intent = new Intent(getApplicationContext(), BracketActivity.class);

                    startActivity(intent);
                } catch (IOException e) {
                    Log.e("Randomizer", "The randomizer/bracket cannot be configured.", e);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            final Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setGlobalTeams(List<Team> teams) {
        this.app.setTeams(teams);
    }

    @Override
    public void setGlobalGames(List<JsonObject> jsonObjects) {
        this.app.setJsonGames(jsonObjects);
    }

    @Override
    public void setGlobalRatings(List<Rating> ratings) {
        this.app.setRpiRatings(ratings);
    }

    public void loadData() {
        if (!app.getBracketRandomizer().hasTeams())
            new TeamLoaderTask(app.getBracketRandomizer(), this).execute();
        if (!app.getBracketRandomizer().hasGames())
            new GameLoaderTask(app.getBracketRandomizer(), this, this.progressBar).execute();
        if (!app.getBracketRandomizer().hasRatings())
            new RatingsLoaderTask(app.getBracketRandomizer(), this).execute();
    }

    @Override
    public boolean loadingComplete() {

        this.findViewById(R.id.loading_view).setVisibility(View.INVISIBLE);

        return app.gamesAndTeamsLoaded();
    }

    @Override
    public void onLoadingStarted() {
        if (this.progressBar != null) this.progressBar.setVisibility(View.VISIBLE);
        if (this.loadingView != null) this.loadingView.setVisibility(View.VISIBLE);

        this.findViewById(R.id.retry_layout).setVisibility(View.INVISIBLE);
        this.findViewById(R.id.randomizer_list_view).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoadingComplete() {

        try {
            app.getBracketRandomizer().configureRandomizerAndBracket(Rating.RatingType.Seed);
        } catch (IOException e) {
            Log.e("Main", "The bracket cannot be set up.", e);
        }

        this.findViewById(R.id.randomizer_list_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed() {
        this.findViewById(R.id.retry_layout).setVisibility(View.VISIBLE);
    }


    public void retryLoading(View view) {
        this.loadData();
    }
}
