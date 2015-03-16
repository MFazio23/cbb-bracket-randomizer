package mfazio.me.cbbbracketrandomizer.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.activities.DataLoaderActivity;

/**
 * Created by MFazio on 2015-03-13.
 */
public class RatingsLoaderTask extends AsyncTask<String, Integer, List<Rating>> {

    private BracketRandomizer bracketRandomizer;
    private DataLoaderActivity dataLoaderActivity;

    public RatingsLoaderTask(final BracketRandomizer bracketRandomizer, final DataLoaderActivity dataLoaderActivity) {
        this.bracketRandomizer = bracketRandomizer;
        this.dataLoaderActivity = dataLoaderActivity;
    }

    @Override
    protected void onPreExecute() {
        this.dataLoaderActivity.onLoadingStarted();
    }

    @Override
    protected List<Rating> doInBackground(String... params) {
        List<Rating> ratings = new ArrayList<>();

        try {
            ratings = this.bracketRandomizer.loadRatings();
        } catch (IOException e) {
            Log.e("Load Ratings", "Attempting to load ratings failed.", e);
            this.dataLoaderActivity.onLoadingFailed();
        }

        return ratings;
    }

    @Override
    protected void onPostExecute(List<Rating> ratings) {
        super.onPostExecute(ratings);

        this.dataLoaderActivity.setGlobalRatings(ratings);

        if (this.dataLoaderActivity.loadingComplete()) {
            this.dataLoaderActivity.onLoadingComplete();
        }
    }
}
