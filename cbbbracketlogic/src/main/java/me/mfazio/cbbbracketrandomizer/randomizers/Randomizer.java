package me.mfazio.cbbbracketrandomizer.randomizers;

import java.io.Serializable;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-05.
 */
public interface Randomizer extends Serializable {

    Team getWinner(final Team teamA, final Team teamB, final Rating.RatingType ratingType);

    enum UpsetChance {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        VERY_HIGH("Very High"),
        UNKNOWN("Unknown");

        private final String displayText;

        private UpsetChance(final String displayText) {
            this.displayText = displayText;
        }

        public String getDisplayText() {
            return displayText;
        }
    }

}
