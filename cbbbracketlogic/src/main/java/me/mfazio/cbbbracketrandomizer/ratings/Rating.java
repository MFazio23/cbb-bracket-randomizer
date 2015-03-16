package me.mfazio.cbbbracketrandomizer.ratings;

/**
 * Created by MFazio on 2015-03-05.
 */
public class Rating {

    protected final double rating;
    protected final String schoolName;

    public Rating(final double rating, final String schoolName) {
        this.rating = rating;
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public double getRating() {
        return this.rating;
    }

    public enum RatingType {
        CoinFlip,
        Seed,
        RPI,
        BPI,
        Sagarin,
        KenPom,
        SonnyMoore
    }
}
