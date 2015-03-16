package me.mfazio.cbbbracketrandomizer.ratingsmappers;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import me.mfazio.cbbbracketrandomizer.dataloaders.GoogleSheetLoader;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-06.
 */
public class RatingsMapper {

    private final Map<String, List<String>> altNames = new HashMap<>();

    public void loadAlternateNames() {

        final GoogleSheetLoader<JsonObject> altNameLoader = new GoogleSheetLoader<>(GoogleSheetLoader.ALT_NAME_SHEET_NAME);

        try {
            final List<JsonObject> altNamesJsonObjectList = altNameLoader.loadData(GoogleSheetLoader.jsonObjectListType);

            for (JsonObject altNameObj : altNamesJsonObjectList) {
                final String baseName = altNameObj.get("schoolName").getAsString();
                final String altName = altNameObj.get("altName").getAsString();

                if (!this.altNames.containsKey(baseName)) {
                    this.altNames.put(baseName, new ArrayList<String>());
                }

                this.altNames.get(baseName).add(altName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Map<String, Rating> convertRatingsToMap(final List<Rating> ratings) {
        final Map<String, Rating> ratingMap = new HashMap<>();

        for (final Rating rating : ratings) {
            ratingMap.put(rating.getSchoolName(), rating);
        }

        return ratingMap;
    }

    public List<Team> mapSeedsAsRatingsToTeams(final List<Team> teams) {
        for(Team team : teams) {
            team.addSeedRating();
        }

        return teams;
    }

    public List<Team> mapRatingsToTeams(final List<Team> teams, final List<Rating> ratings, final Rating.RatingType ratingType) {
        final List<Team> ratedTeams = new ArrayList<>();

        if (this.altNames.isEmpty()) this.loadAlternateNames();

        final Map<String, Rating> ratingsMap = this.convertRatingsToMap(ratings);

        for (Team team : teams) {
            Rating rating = ratingsMap.get(team.getSchoolName());

            if (rating == null && this.altNames.containsKey(team.getSchoolName())) {
                for (String altName : this.altNames.get(team.getSchoolName())) {
                    rating = ratingsMap.get(altName);
                    if (rating != null) break;
                }
            }


            if (rating != null) {
                team.addRating(ratingType, rating);
                ratedTeams.add(team);
            } else {
                System.out.println("Rating missing for " + team.getSchoolName());
            }
        }

        return ratedTeams;
    }

}
