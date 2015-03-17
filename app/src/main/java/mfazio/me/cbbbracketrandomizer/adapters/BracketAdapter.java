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
import me.mfazio.cbbbracketrandomizer.types.Team;
import mfazio.me.cbbbracketrandomizer.R;

/**
 * Created by MFazio on 2015-03-13.
 */
public class BracketAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final int headerResource;
    private final Bracket bracket;
    private final LayoutInflater inflater;

    public BracketAdapter(final Context context, final int resource, final int headerResource, final Bracket bracket) {
        this.context = context;
        this.resource = resource;
        this.headerResource = headerResource;
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
        final String roundName = this.getRoundName(position);

        final View view =
            this.inflater.inflate(
                StringUtils.isNotEmpty(roundName) ? this.headerResource : this.resource,
                parent,
                false);

        final Game game = this.getItem(position);

        if (StringUtils.isNotEmpty(roundName)) {
            final TextView headerTextView = (TextView) view.findViewById(R.id.round_text_view);
            headerTextView.setText(roundName);
        }

        final TextView teamAName = (TextView) view.findViewById(R.id.team_a_name);
        final TextView teamASeed = (TextView) view.findViewById(R.id.team_a_seed);
        final TextView teamBName = (TextView) view.findViewById(R.id.team_b_name);
        final TextView teamBSeed = (TextView) view.findViewById(R.id.team_b_seed);

        final int baseTextColor = context.getResources().getColor(R.color.bb_default_text_color);
        teamAName.setTextColor(baseTextColor);
        teamBName.setTextColor(baseTextColor);

        if (game != null && game.isGamePlayed()) {
            if (game.getTeamA().equals(game.getWinner())) {
                teamAName.setTextColor(Color.parseColor(game.getTeamA().getColorHex()));
            } else {
                teamBName.setTextColor(Color.parseColor(game.getTeamB().getColorHex()));
            }
        }

        if (game != null && game.getTeamA() != null) {
            final Team teamA = game.getTeamA();
            teamAName.setText(teamA.getSchoolName());
            teamASeed.setText(teamA.getSeed() + " seed");
        } else {
            teamAName.setText("");
            teamASeed.setText("");
        }
        if (game != null && game.getTeamB() != null) {
            final Team teamB = game.getTeamB();
            teamBName.setText(teamB.getSchoolName());
            teamBSeed.setText(teamB.getSeed() + " seed");
        } else {
            teamBName.setText("");
            teamBSeed.setText("");
        }
        if (position == 63) {
            final Team champion = game.getTeamA();
            if (champion != null) {
                view.findViewById(R.id.game_list_item).setVisibility(View.GONE);

                final LinearLayout championLayout = (LinearLayout) view.findViewById(R.id.champion_layout);
                championLayout.setVisibility(View.VISIBLE);

                final TextView championName = (TextView) championLayout.findViewById(R.id.champion_name);
                championName.setText(champion.getSchoolName());
                championName.setTextColor(Color.parseColor(game.getTeamA().getColorHex()));

                final TextView championSeed = (TextView) view.findViewById(R.id.champion_seed);
                championSeed.setText(champion.getSeed() + " seed");

                Log.wtf("Champion", champion.toString());
            }
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
            case 63:
                return "NATIONAL CHAMPION";
            default:
                return null;
        }
    }
}
