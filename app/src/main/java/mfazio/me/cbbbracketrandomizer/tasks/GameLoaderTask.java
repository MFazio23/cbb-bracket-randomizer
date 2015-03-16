package mfazio.me.cbbbracketrandomizer.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.activities.DataLoaderActivity;

/**
 * Created by MFazio on 2015-03-12.
 */
public class GameLoaderTask extends AsyncTask<String, Void, List<JsonObject>> {

    private final BracketRandomizer bracketRandomizer;
    private final DataLoaderActivity dataLoaderActivity;
    private final ProgressBar progressBar;

    public GameLoaderTask(final BracketRandomizer bracketRandomizer, final DataLoaderActivity dataLoaderActivity, final ProgressBar progressBar) {
        this.bracketRandomizer = bracketRandomizer;
        this.dataLoaderActivity = dataLoaderActivity;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        this.dataLoaderActivity.onLoadingStarted();
    }

    @Override
    protected List<JsonObject> doInBackground(String... params) {
        List<JsonObject> games = new ArrayList<>();

        try {
            games = this.bracketRandomizer.loadJsonGames();
        } catch (IOException e) {
            Log.e("LoadTeams", "Attempting to load teams failed.", e);
            dataLoaderActivity.onLoadingFailed();
        }

        return games;
    }

    @Override
    protected void onPostExecute(List<JsonObject> games) {
        super.onPostExecute(games);

        if(this.progressBar != null) {
            this.progressBar.setVisibility(View.INVISIBLE);
        }

        this.dataLoaderActivity.setGlobalGames(games);

        if(this.dataLoaderActivity.loadingComplete()) {
            dataLoaderActivity.onLoadingComplete();
        }

    }
}
