package mfazio.me.cbbbracketrandomizer.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.activities.DataLoaderActivity;

/**
 * Created by MFazio on 2015-03-12.
 */
public class TeamLoaderTask extends AsyncTask<String, Void, List<Team>> {

    private BracketRandomizer bracketRandomizer;
    private DataLoaderActivity dataLoaderActivity;

    public TeamLoaderTask(final BracketRandomizer bracketRandomizer, final DataLoaderActivity dataLoaderActivity) {
        this.bracketRandomizer = bracketRandomizer;
        this.dataLoaderActivity = dataLoaderActivity;
    }

    @Override
    protected void onPreExecute() {
        this.dataLoaderActivity.onLoadingStarted();
    }

    @Override
    protected List<Team> doInBackground(String... params) {
        List<Team> teams = new ArrayList<>();

        try {
            teams = this.bracketRandomizer.loadTeams();
        } catch (IOException e) {
            Log.e("LoadTeams", "Attempting to load teams failed.", e);
            this.dataLoaderActivity.onLoadingFailed();
        }

        return teams;
    }

    @Override
    protected void onPostExecute(List<Team> teams) {
        super.onPostExecute(teams);

        this.dataLoaderActivity.setGlobalTeams(teams);

        if (this.dataLoaderActivity.loadingComplete()) {
            this.dataLoaderActivity.onLoadingComplete();
        }
    }
}
