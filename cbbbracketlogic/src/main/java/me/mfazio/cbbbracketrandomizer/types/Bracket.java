package me.mfazio.cbbbracketrandomizer.types;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mfazio.cbbbracketrandomizer.randomizers.Randomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;

/**
 * Created by MFazio on 2015-03-07.
 */
public class Bracket {

    private final List<Game> games;

    public Bracket() {
        this.games = new ArrayList<>(64);
    }

    public Game getGame(final int gameNumber) {
        return this.games.get(gameNumber);
    }

    public List<Game> getGames() {
        return this.games;
    }

    public Bracket createBracketFromJson(final List<JsonObject> jsonGames, final List<Team> teams) {
        final Map<String, Team> teamMap = Team.convertTeamListToTeamMap(teams);

        for (JsonObject jsonGame : jsonGames) {

            this.games.add(
                jsonGame.get("game").getAsInt(),
                new Game(
                    teamMap.get(jsonGame.get("teamAName").getAsString()),
                    teamMap.get(jsonGame.get("teamBName").getAsString())
                )
            );
        }

        return this;
    }

    public Game addToGame(final int gameNumber, final Team team) {
        if (this.games.size() <= gameNumber || this.games.get(gameNumber) == null) {
            this.games.add(gameNumber, new Game(team, null));
        } else {
            this.games.set(gameNumber, new Game(this.games.get(gameNumber).getTeamA(), team));
        }

        return this.games.get(gameNumber);
    }

    public void playGame(final int gameNumber, final Randomizer randomizer, final Rating.RatingType ratingType) {
        final Game game = this.games.get(gameNumber);
        final Team winner = randomizer.getWinner(game.getTeamA(), game.getTeamB(), ratingType);

        game.setGamePlayed(true);
        game.setWinner(winner);
        this.games.set(gameNumber, game);

        this.addToGame(this.getNextGameNumber(gameNumber), winner);
    }

    public Team playFullBracket(final Randomizer randomizer) {
        for (int g = 0; g < 63; g++) {
            this.playGame(g, randomizer, Rating.RatingType.Seed);
        }

        return this.games.get(63).getTeamA();
    }

    public int getNextGameNumber(final int currentGameNumber) {
        return (int) (Math.floor((double) currentGameNumber / 2.0)) + 32;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bracket{");
        sb.append("games=").append(games);
        sb.append('}');
        return sb.toString();
    }
}
