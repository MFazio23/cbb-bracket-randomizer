package me.mfazio.cbbbracketrandomizer;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.dataloaders.GoogleSheetLoader;
import me.mfazio.cbbbracketrandomizer.dataloaders.KimonoDataLoader;
import me.mfazio.cbbbracketrandomizer.randomizers.CoinFlipRandomizer;
import me.mfazio.cbbbracketrandomizer.randomizers.GenericRatingRandomizer;
import me.mfazio.cbbbracketrandomizer.randomizers.Randomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.ratingsmappers.RatingsMapper;
import me.mfazio.cbbbracketrandomizer.types.Bracket;
import me.mfazio.cbbbracketrandomizer.types.Game;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-12.
 */
public class BracketRandomizer {

    private Bracket currentBracket;
    private Randomizer randomizer;
    private Rating.RatingType ratingType;
    private List<Team> teams;
    private List<JsonObject> jsonGames;
    private List<Rating> ratings;


    public void initialize() throws IOException {
        this.teams = this.loadTeams();
        this.jsonGames = this.loadJsonGames();
    }

    public void configureRandomizerAndBracket(final Rating.RatingType ratingType) throws IOException {
        this.ratingType = ratingType;
        switch(this.ratingType) {
            case CoinFlip:
                this.randomizer = new CoinFlipRandomizer();
                break;
            case Seed:
                this.randomizer = new GenericRatingRandomizer();
                this.teams = new RatingsMapper().mapSeedsAsRatingsToTeams(this.teams);
                break;
            case RPI:
                this.randomizer = new GenericRatingRandomizer();
                this.teams = new RatingsMapper().mapRatingsToTeams(this.teams, this.ratings, this.ratingType);
                break;
        }

        this.currentBracket = new Bracket().createBracketFromJson(this.jsonGames, this.teams);
    }

    public Bracket getCurrentBracket() {
        return this.currentBracket;
    }

    public Team playFullBracket() {
        return this.currentBracket.playFullBracket(this.randomizer);
    }

    public boolean setTeams(final List<Team> teams) {
        this.teams = teams;
        return !this.teams.isEmpty();
    }

    public List<Team> loadTeams() throws IOException {
        return new GoogleSheetLoader<Team>(GoogleSheetLoader.TEAMS_SHEET_NAME).loadData(GoogleSheetLoader.teamListType);
    }

    public boolean setJsonGames(final List<JsonObject> jsonGames) {
        this.jsonGames = jsonGames;
        return !this.jsonGames.isEmpty();
    }

    public List<JsonObject> loadJsonGames() throws IOException {
        return new GoogleSheetLoader<JsonObject>(GoogleSheetLoader.BRACKET_SHEET_NAME).loadData(GoogleSheetLoader.jsonObjectListType);
    }

    public boolean setRatings(List<Rating> ratings) {
        this.ratings = ratings;
        return !this.ratings.isEmpty();
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Rating> loadRatings() throws IOException {
        return new KimonoDataLoader<Rating>(KimonoDataLoader.RPI_API_ID).loadData(KimonoDataLoader.ratingListType);
    }

    public boolean hasTeams() {
        return this.teams != null && !this.teams.isEmpty();
    }

    public boolean hasGames() {
        return this.teams != null &&  !this.jsonGames.isEmpty();
    }

    public List<Game> getGames() {
        return this.getCurrentBracket().getGames();
    }


    public boolean hasRatings() {
        return this.ratings != null && !this.ratings.isEmpty();
    }

    public void playGame(final int position) {
        this.currentBracket.playGame(position, this.randomizer, this.ratingType);
    }
}
