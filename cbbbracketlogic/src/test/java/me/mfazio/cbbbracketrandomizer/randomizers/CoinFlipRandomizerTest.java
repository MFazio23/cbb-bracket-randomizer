package me.mfazio.cbbbracketrandomizer.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

public class CoinFlipRandomizerTest {

    private static final int TEST_COUNT = 10000000;
    private static final double ERROR_THRESHOLD = .0001;

    private CoinFlipRandomizer randomizer;

    @Before
    public void setUp() throws Exception {
        this.randomizer = new CoinFlipRandomizer();
    }

    @Test
    public void testGetWinner() throws Exception {

        final Team teamA = new Team.Builder()
            .setSchoolName("Test Team A")
            .setSeed(3)
            .build();
        final Team teamB = new Team.Builder()
            .setSchoolName("Test Team B")
            .setSeed(8)
            .build();

        final Map<Team, Double> results = new HashMap<Team, Double>() {{
            this.put(teamA, 0d);
            this.put(teamB, 0d);
        }};

        for(int x=0;x<TEST_COUNT;x++) {
            final Team winner = this.randomizer.getWinner(teamA, teamB, Rating.RatingType.CoinFlip);

            results.put(winner, results.get(winner) + 1d);
        }

        Assert.assertTrue("The random result count for Team A are out of the threshold range", this.withinThreshold(results.get(teamA)));
        Assert.assertTrue("The random result count for Team B are out of the threshold range", this.withinThreshold(results.get(teamB)));
    }

    private boolean withinThreshold(final double count) {
        final double pct = count / (TEST_COUNT/2);

        final double diff = Math.abs(1d - pct);

        return diff < ERROR_THRESHOLD;
    }
}