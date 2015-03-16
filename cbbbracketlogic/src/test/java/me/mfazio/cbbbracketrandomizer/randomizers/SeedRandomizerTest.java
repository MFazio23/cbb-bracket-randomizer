package me.mfazio.cbbbracketrandomizer.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;
import me.mfazio.cbbbracketrandomizer.types.Team;

import static org.junit.Assert.*;

public class SeedRandomizerTest {

    private static final int TEST_COUNT = 10000000;
    private static final double ERROR_THRESHOLD = .005;

    private GenericRatingRandomizer randomizer;

    @Before
    public void setUp() throws Exception {
        this.randomizer = new GenericRatingRandomizer();
    }

    @Test
    public void testGetWinner() throws Exception {
        this.testSeeds(1, 16);
        this.testSeeds(2, 15);
        this.testSeeds(3, 14);
        this.testSeeds(4, 13);
        this.testSeeds(5, 12);
        this.testSeeds(6, 11);
        this.testSeeds(7, 10);
        this.testSeeds(8, 9);

        this.testSeeds(1, 1);
        this.testSeeds(1, 2);
        this.testSeeds(1, 3);
        this.testSeeds(2, 3);

    }

    private void testSeeds(final int seedA, final int seedB) {
        final Team teamA = new Team.Builder().setSchoolName("Team A").setSeed(seedA).build();
        final Team teamB = new Team.Builder().setSchoolName("Team B").setSeed(seedB).build();
        final int seedTotal = seedA + seedB;

        final Map<Team, Double> results = new HashMap<Team, Double>() {{
            put(teamA, 0d);
            put(teamB, 0d);
        }};
        for(int x=0;x<TEST_COUNT;x++) {
            final Team winner = this.randomizer.getWinner(teamA, teamB, Rating.RatingType.Seed);

            results.put(winner, results.get(winner) + 1d);
        }

        Assert.assertTrue("The random result count for Team A are out of the threshold range", this.withinThreshold(results.get(teamA), ((double)teamA.getSeed())/seedTotal));
        Assert.assertTrue("The random result count for Team B are out of the threshold range", this.withinThreshold(results.get(teamB), ((double)teamB.getSeed())/seedTotal));
    }

    private boolean withinThreshold(final double count, final double odds) {
        final double pct = count / (TEST_COUNT * odds);

        final double diff = Math.abs(1d - pct);

        return diff < ERROR_THRESHOLD;
    }
}