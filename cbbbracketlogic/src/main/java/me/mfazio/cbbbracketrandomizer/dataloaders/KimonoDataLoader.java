package me.mfazio.cbbbracketrandomizer.dataloaders;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-06.
 */
public class KimonoDataLoader<T> extends DataLoader<T> {

    public static final String KIMONO_URL_FORMAT = "https://www.kimonolabs.com/api/%s?apikey=22d3649db54ee3dbcd7c3e5f8e001010&kimmodify=1&dataonly=true";

    public static final String RPI_API_ID = "bwb2rtte";

    public static final Type ratingListType = new TypeToken<List<Rating>>(){}.getType();

    protected final String apiID;

    public KimonoDataLoader(final String apiID) {
        this.apiID = apiID;
    }

    public static String getKimonoURL(final String apiID) {
        return String.format(KIMONO_URL_FORMAT, apiID);
    }

    @Override
    public List<T> loadData(final Type type) throws IOException {
        final String jsonString = this.loadJSONStringFromURL(KimonoDataLoader.getKimonoURL(this.apiID));

        final JsonObject teamObject =
            this.gson.fromJson(
                jsonString,
                JsonObject.class
            );

        return this.gson.fromJson(
            teamObject.getAsJsonArray("teams"),
            type
        );
    }
}
