package me.mfazio.cbbbracketrandomizer.ratings;

/**
 * Created by MFazio on 2015-03-05.
 */
public class BPIRating extends Rating {

    private final double pva;
    private final double rawRating;

    public BPIRating(final String teamName, final double rating, final double pva, final double rawRating) {
        super(rating, teamName);
        this.pva = pva;
        this.rawRating = rawRating;
    }

    public double getPva() {
        return pva;
    }

    public double getRawRating() {
        return rawRating;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BPIRatings{");
        sb.append("pva=").append(pva);
        sb.append(", rawRating=").append(rawRating);
        sb.append(", BPI=").append(super.rating);
        sb.append(", TeamName=").append(super.schoolName);
        sb.append('}');
        return sb.toString();
    }
}
