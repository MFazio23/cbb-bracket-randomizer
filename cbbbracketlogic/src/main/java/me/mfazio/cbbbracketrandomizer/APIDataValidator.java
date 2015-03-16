package me.mfazio.cbbbracketrandomizer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import me.mfazio.cbbbracketrandomizer.dataloaders.GoogleSheetLoader;
import me.mfazio.cbbbracketrandomizer.randomizers.GenericRatingRandomizer;
import me.mfazio.cbbbracketrandomizer.ratingsmappers.RatingsMapper;
import me.mfazio.cbbbracketrandomizer.types.Bracket;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-06.
 */
public class APIDataValidator {

    public static final int TEST_COUNT = 10000000;

    public APIDataValidator() throws Exception {
        final List<Team> teams = new GoogleSheetLoader<Team>(GoogleSheetLoader.TEAMS_SHEET_NAME).loadData(GoogleSheetLoader.teamListType);
        //final List<Rating> ratings = new KimonoDataLoader<Rating>(KimonoDataLoader.RPI_API_ID).loadData(KimonoDataLoader.ratingListType);
        final List<Team> ratedTeams = new RatingsMapper().mapSeedsAsRatingsToTeams(teams);

        final List<JsonObject> jsonGames = new GoogleSheetLoader<JsonObject>(GoogleSheetLoader.BRACKET_SHEET_NAME).loadData(GoogleSheetLoader.jsonObjectListType);

        final GenericRatingRandomizer randomizer = new GenericRatingRandomizer();

        final Map<Team, Integer> winners = new HashMap<>();

        for(Team team : teams ) {
            winners.put(team, 0);
        }

        for(int x=0;x<TEST_COUNT;x++) {
            if(x%100000 == 0) System.out.println((x/100000) + "% done");

            final Team winner = new Bracket().createBracketFromJson(jsonGames, ratedTeams).playFullBracket(randomizer);

            winners.put(winner, winners.get(winner) + 1);
        }

        for(Map.Entry<Team, Integer> teamEntry : winners.entrySet()) {
            final Team team = teamEntry.getKey();
            System.out.println(team.getSchoolName() + "\t" + teamEntry.getValue() + "\t" + team.getSeed());
        }
    }

    public static void main(String[] args) throws Exception {
        new APIDataValidator();
    }
}
