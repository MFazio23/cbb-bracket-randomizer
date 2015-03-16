package me.mfazio.cbbbracketrandomizer.randomizers;

import java.util.Random;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-06.
 */
public class GenericRatingRandomizer implements Randomizer {

    private final Random rand;

    public GenericRatingRandomizer() {

        this.rand = new Random();
    }

    @Override
    public Team getWinner(final Team teamA, final Team teamB, final Rating.RatingType ratingType) {
        final Rating teamARating = teamA.getRating(ratingType);
        final Rating teamBRating = teamB.getRating(ratingType);

        final double ratingTotal = teamARating.getRating() + teamBRating.getRating();
        final double randomVal = this.rand.nextDouble() * ratingTotal;

        //TODO: Simplify this
        if(teamARating.getRating() > teamBRating.getRating()) {
            return teamARating.getRating() >= randomVal ? teamA : teamB;
        } else {
            return teamBRating.getRating() >= randomVal ? teamB : teamA;
        }
    }

}
