package me.mfazio.cbbbracketrandomizer.dataloaders;

import org.junit.Test;

import java.util.List;

import me.mfazio.cbbbracketrandomizer.ratings.Rating;

public class KimonoDataLoaderTest {

    @Test
    public void testLoadRPIData() throws Exception {
        final KimonoDataLoader<Rating> loader = new KimonoDataLoader<>("bwb2rtte");

        final List<Rating> ratings = loader.loadData(KimonoDataLoader.ratingListType);

        System.out.println(ratings);
    }
}