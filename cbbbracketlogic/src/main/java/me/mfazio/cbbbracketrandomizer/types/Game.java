package me.mfazio.cbbbracketrandomizer.types;

/**
 * Created by MFazio on 2015-03-06.
 */
public class Game {

    private Team teamA;
    private Team teamB;
    private Team winner;
    private boolean gamePlayed;


    public Game() {
        this(null, null);
    }

    public Game(final Team teamA, final Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.gamePlayed = false;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public boolean isGamePlayed() {
        return gamePlayed;
    }

    public void setGamePlayed(boolean gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Game{");
        sb.append("teamA=").append(teamA);
        sb.append(", teamB=").append(teamB);
        sb.append(", winner=").append(winner);
        sb.append(", gamePlayed=").append(gamePlayed);
        sb.append('}');
        return sb.toString();
    }
}
