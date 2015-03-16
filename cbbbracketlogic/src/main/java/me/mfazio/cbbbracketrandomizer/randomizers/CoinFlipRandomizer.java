package me.mfazio.cbbbracketrandomizer.randomizers;

import java.util.Random;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

/**
 * Created by MFazio on 2015-03-05.
 */
public class CoinFlipRandomizer implements Randomizer {

    private final Random rand;

    public CoinFlipRandomizer() {
        this.rand = new Random();
    }

    @Override
    public Team getWinner(Team teamA, Team teamB, final Rating.RatingType ratingType) {
        return (this.rand.nextInt(2) == 1) ? teamA : teamB;
    }
}
