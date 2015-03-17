package mfazio.me.cbbbracketrandomizer;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.JsonObject;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import me.mfazio.cbbbracketrandomizer.BracketRandomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-12.
 */
public class CBBBracketRandomizerApplication extends Application {

    private BracketRandomizer bracketRandomizer;
    private List<Team> teams;
    private List<JsonObject> jsonGames;
    private List<Rating> rpiRatings;
    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        this.bracketRandomizer = new BracketRandomizer();
    }

    public synchronized Tracker getTracker() {
        if(this.tracker == null) {
            final GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            this.tracker = analytics.newTracker("UA-52217136-2");
        }

        return this.tracker;
    }

    public BracketRandomizer getBracketRandomizer() {
        return bracketRandomizer;
    }

    public void setBracketRandomizer(BracketRandomizer bracketRandomizer) {
        this.bracketRandomizer = bracketRandomizer;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        this.bracketRandomizer.setTeams(teams);
    }

    public List<JsonObject> getJsonGames() {
        return jsonGames;
    }

    public void setJsonGames(List<JsonObject> jsonGames) {
        this.jsonGames = jsonGames;
        this.bracketRandomizer.setJsonGames(jsonGames);
    }

    public List<Rating> getRpiRatings() {
        return rpiRatings;
    }

    public void setRpiRatings(List<Rating> rpiRatings) {
        this.rpiRatings = rpiRatings;
    }

    public boolean gamesAndTeamsLoaded() {
        return
            CollectionUtils.isNotEmpty(this.teams) &&
            CollectionUtils.isNotEmpty(this.jsonGames) &&
            CollectionUtils.isNotEmpty(this.rpiRatings);
    }
}
