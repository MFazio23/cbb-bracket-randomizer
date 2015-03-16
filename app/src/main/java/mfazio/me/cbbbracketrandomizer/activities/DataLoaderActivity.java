package mfazio.me.cbbbracketrandomizer.activities;

import com.google.gson.JsonObject;

import java.util.List;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-12.
 */
public interface DataLoaderActivity {

    void setGlobalTeams(final List<Team> teams) ;
    void setGlobalGames(final List<JsonObject> jsonObjects);
    void setGlobalRatings(final List<Rating> ratings);
    boolean loadingComplete();
    void onLoadingStarted();
    void onLoadingComplete();
    void onLoadingFailed();
}
