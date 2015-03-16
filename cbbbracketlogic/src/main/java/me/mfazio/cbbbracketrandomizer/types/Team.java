package me.mfazio.cbbbracketrandomizer.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;

/**
 * Created by MFazio on 2015-03-05.
 */
public class Team {

    protected final String schoolName;
    protected final int seed;
    protected final int wins;
    protected final int losses;
    protected final int rank;
    protected final String colorHex;
    protected final Map<Rating.RatingType, Rating> ratings;

    public Team() {
        // This is really just here for Gson.  I feel like there should be a better way to do this.
        this.schoolName = null;
        this.seed = 0;
        this.wins = 0;
        this.losses = 0;
        this.rank = 0;
        this.colorHex = null;
        this.ratings = new HashMap<>();
    }

    public Team(final Builder builder) {
        this.schoolName = builder.schoolName;
        this.seed = builder.seed;
        this.wins = builder.wins;
        this.losses = builder.losses;
        this.rank = builder.rank;
        this.colorHex = "#FFFFFF";
        this.ratings = new HashMap<>();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public int getSeed() {
        return seed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getRank() {
        return rank;
    }

    public String getColorHex() {
        return colorHex;
    }

    public Rating getRating(final Rating.RatingType ratingType) {
        return this.ratings.get(ratingType);
    }

    public Rating addRating(final Rating.RatingType ratingType, final Rating rating) {
        return this.ratings.put(ratingType, rating);
    }

    public Rating removeRating(final Rating.RatingType ratingType) {
        return this.ratings.remove(ratingType);
    }

    public Map<Rating.RatingType, Rating> getRatings() {
        return ratings;
    }

    public static Map<String, Team> convertTeamListToTeamMap(final List<Team> teams) {
        final Map<String, Team> teamMap = new HashMap<>();

        for(Team team : teams) {
            teamMap.put(team.getSchoolName(), team);
        }

        return teamMap;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Team{");
        sb.append("schoolName='").append(schoolName).append('\'');
        sb.append(", seed=").append(seed);
        sb.append(", wins=").append(wins);
        sb.append(", losses=").append(losses);
        sb.append(", rank=").append(rank);
        sb.append('}');
        return sb.toString();
    }

    public void addSeedRating() {
        this.ratings.put(Rating.RatingType.Seed, new Rating(17 - this.seed, this.schoolName));
    }

    public static class Builder {
        protected String schoolName;
        protected int seed;
        protected int wins;
        protected int losses;
        protected int rank;

        public String getSchoolName() {
            return schoolName;
        }

        public Builder setSchoolName(String schoolName) {
            this.schoolName = schoolName;
            return this;
        }

        public int getSeed() {
            return seed;
        }

        public Builder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public int getWins() {
            return wins;
        }

        public Builder setWins(int wins) {
            this.wins = wins;
            return this;
        }

        public int getLosses() {
            return losses;
        }

        public Builder setLosses(int losses) {
            this.losses = losses;
            return this;
        }

        public int getRank() {
            return rank;
        }

        public Builder setRank(int rank) {
            this.rank = rank;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
