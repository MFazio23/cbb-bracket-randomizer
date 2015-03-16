package mfazio.me.cbbbracketrandomizer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import me.mfazio.cbbbracketrandomizer.types.Bracket;
import me.mfazio.cbbbracketrandomizer.types.Game;
import mfazio.me.cbbbracketrandomizer.R;

/**
 * Created by MFazio on 2015-03-13.
 */
public class BracketAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final Bracket bracket;
    private final LayoutInflater inflater;

    public BracketAdapter(final Context context, final int resource, final Bracket bracket) {
        this.context = context;
        this.resource = resource;
        this.bracket = bracket;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.bracket.getGames().size();
    }

    @Override
    public Game getItem(int position) {
        return this.bracket.getGames().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = convertView != null ? convertView : this.inflater.inflate(this.resource, parent, false);

        final String roundName = this.getRoundName(position);

        if (StringUtils.isNotEmpty(roundName)) {
            final LinearLayout headerLayout = (LinearLayout) this.inflater.inflate(R.layout.game_list_item_header, parent, false);
            final TextView headerTextView = (TextView) headerLayout.findViewById(R.id.round_text_view);
            headerTextView.setText(roundName);
        }

        final Game game = this.getItem(position);

        final TextView teamAName = (TextView) view.findViewById(R.id.team_a_name);
        final TextView teamASeed = (TextView) view.findViewById(R.id.team_a_seed);
        final TextView teamBName = (TextView) view.findViewById(R.id.team_b_name);
        final TextView teamBSeed = (TextView) view.findViewById(R.id.team_b_seed);

        final int baseTextColor = context.getResources().getColor(R.color.bb_default_text_color);
        teamAName.setTextColor(baseTextColor);
        teamBName.setTextColor(baseTextColor);

        if (game.isGamePlayed()) {
            if (game.getTeamA().equals(game.getWinner())) {
                teamAName.setTextColor(Color.parseColor(game.getTeamA().getColorHex()));
            } else {
                teamBName.setTextColor(Color.parseColor(game.getTeamB().getColorHex()));
            }
        }

        teamAName.setText(game.getTeamA().getSchoolName());
        teamASeed.setText(game.getTeamA().getSeed() + " seed");

        if(game.getTeamB() != null) {
            teamBName.setText(game.getTeamB().getSchoolName());
            teamBSeed.setText(game.getTeamB().getSeed() + " seed");
        } else {
            teamBName.setText("");
            teamBSeed.setText("");
        }

        return view;
    }

    private String getRoundName(final int game) {
        switch (game) {
            case 0:
                return "Second Round";
            case 32:
                return "Third Round";
            case 48:
                return "Regional Semifinals";
            case 56:
                return "Regional Finals";
            case 60:
                return "National Semifinals";
            case 62:
                return "National Championship";
        }

        return null;
    }
}
