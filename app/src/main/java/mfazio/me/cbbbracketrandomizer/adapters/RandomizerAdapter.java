package mfazio.me.cbbbracketrandomizer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import mfazio.me.cbbbracketrandomizer.R;
import mfazio.me.cbbbracketrandomizer.types.RandomizerInfo;

/**
 * Created by MFazio on 2015-03-13.
 */
public class RandomizerAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final List<RandomizerInfo> randomizerInfo;
    private final LayoutInflater inflater;

    public RandomizerAdapter(Context context, int resource, List<RandomizerInfo> randomizerInfo) {
        this.context = context;
        this.resource = resource;
        this.randomizerInfo = randomizerInfo;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.randomizerInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return this.randomizerInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = convertView != null ? convertView : this.inflater.inflate(this.resource, parent, false);

        final TextView header = (TextView) view.findViewById(R.id.header);
        final TextView description = (TextView) view.findViewById(R.id.description);
        final TextView upsetLevel = (TextView) view.findViewById(R.id.upset_level);

        final RandomizerInfo randomizerInfo = (RandomizerInfo) this.getItem(position);

        header.setText(randomizerInfo.getName());
        description.setText(randomizerInfo.getDescription());
        upsetLevel.setText("Upset Chance: " + StringUtils.upperCase(randomizerInfo.getUpsetChance().getDisplayText()));

        return view;
    }
}
