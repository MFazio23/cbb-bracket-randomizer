package mfazio.me.cbbbracketrandomizer.types;

import me.mfazio.cbbbracketrandomizer.randomizers.Randomizer;
import me.mfazio.cbbbracketrandomizer.ratings.Rating;

/**
 * Created by MFazio on 2015-03-13.
 */
public class RandomizerInfo {

    private final Rating.RatingType ratingType;
    private final String name;
    private final String description;
    private final Randomizer.UpsetChance upsetChance;

    public RandomizerInfo(Rating.RatingType ratingType, String name, String description, Randomizer.UpsetChance upsetChance) {
        this.ratingType = ratingType;
        this.name = name;
        this.description = description;
        this.upsetChance = upsetChance;
    }

    public Rating.RatingType getRatingType() {
        return ratingType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Randomizer.UpsetChance getUpsetChance() {
        return upsetChance;
    }
}
