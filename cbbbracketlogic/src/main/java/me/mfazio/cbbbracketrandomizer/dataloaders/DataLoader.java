package me.mfazio.cbbbracketrandomizer.dataloaders;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by MFazio on 2015-03-06.
 */
public abstract class DataLoader<T> {

    protected final OkHttpClient client;
    protected final Gson gson;

    public DataLoader() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public String loadJSONStringFromURL(final String url) throws IOException {
        final Request request = new Request.Builder()
            .url(url)
            .build();

        final Response response = this.client.newCall(request).execute();

        if(!response.isSuccessful()) {
            throw new IOException("The API call to " + url + " was not successful.");
        }

        return response.body().string();
    }

    public abstract List<T> loadData(final Type type) throws IOException;

}
