package me.mfazio.cbbbracketrandomizer.dataloaders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import me.mfazio.cbbbracketrandomizer.types.Game;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-06.
 */
public class GoogleSheetLoader<T> extends DataLoader<T>{

    public static final String SHEET_BASE_URL
        = "https://script.google.com/macros/s/AKfycbxN6xTdM-6myhUUxHBREDiBNh8WWNk24h6D36vWJtwd8xpctGwr/exec?id=1rGWICV6-gCFUDyi1wpo5PBoxrZVd7aqhCal-f46hlRY&sheet=";

    public static final String TEAMS_SHEET_NAME = "2015-Teams";
    public static final String BRACKET_SHEET_NAME = "2015-Bracket";
    public static final String ALT_NAME_SHEET_NAME = "Alternate-Names";

    public static final Type jsonObjectListType = new TypeToken<List<JsonObject>>(){}.getType();
    public static final Type teamListType = new TypeToken<List<Team>>(){}.getType();

    protected final String url;
    protected final String sheetName;

    public GoogleSheetLoader(final String sheetName) {
        this(SHEET_BASE_URL, sheetName);
    }

    public GoogleSheetLoader(final String url, final String sheetName) {
        this.url = url;
        this.sheetName = sheetName;
    }

    public String getUrl() {
        return url;
    }

    public String getSheetName() {
        return sheetName;
    }

    @Override
    public List<T> loadData(final Type type) throws IOException {
        final String jsonString = this.loadJSONStringFromURL(this.url + this.sheetName);

        return this.gson.fromJson(
            jsonString,
            type
        );
    }
}
