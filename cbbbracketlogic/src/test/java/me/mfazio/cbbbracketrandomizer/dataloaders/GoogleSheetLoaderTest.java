package me.mfazio.cbbbracketrandomizer.dataloaders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import me.mfazio.cbbbracketrandomizer.randomizers.CoinFlipRandomizer;
import me.mfazio.cbbbracketrandomizer.randomizers.GenericRatingRandomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Bracket;
import me.mfazio.cbbbracketrandomizer.types.Game;
import me.mfazio.cbbbracketrandomizer.types.Team;

public class GoogleSheetLoaderTest {

    @Test
    public void testLoadTeamData() throws Exception {
        final List<Team> teams = new GoogleSheetLoader<Team>(GoogleSheetLoader.TEAMS_SHEET_NAME).loadData(GoogleSheetLoader.teamListType);

        Assert.assertEquals("The number of returned teams is incorrect.", 64, teams.size());
    }

    @Test
    public void testLoadBracketData() throws Exception {
        final List<JsonObject> jsonGames = new GoogleSheetLoader<JsonObject>(GoogleSheetLoader.BRACKET_SHEET_NAME).loadData(GoogleSheetLoader.jsonObjectListType);

        Assert.assertEquals("The number of returned games is incorrect.", 32, jsonGames.size());
    }
}