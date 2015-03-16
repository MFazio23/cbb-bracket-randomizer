package me.mfazio.cbbbracketrandomizer.ratings;

/**
 * Created by MFazio on 2015-03-05.
 */
public class SagarinRating extends Rating {

    private final double goldenMean;
    private final double predictor;
    private final double eloScore;

    public SagarinRating(String teamName, double rating, double goldenMean, double predictor, double eloScore) {
        super(rating, teamName);
        this.goldenMean = goldenMean;
        this.predictor = predictor;
        this.eloScore = eloScore;
    }

    public double getGoldenMean() {
        return goldenMean;
    }

    public double getPredictor() {
        return predictor;
    }

    public double getEloScore() {
        return eloScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SagarinRatings{");
        sb.append("goldenMean=").append(goldenMean);
        sb.append(", predictor=").append(predictor);
        sb.append(", eloScore=").append(eloScore);
        sb.append(", rating=").append(rating);
        sb.append(", schoolName=").append(schoolName);
        sb.append('}');
        return sb.toString();
    }
}
