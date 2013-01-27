package napoleon.model.rule;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 13/01/07
 * Time: 6:58
 * To change this template use File | Settings | File Templates.
 */
public class Options {
    public final boolean canHideMighty;
    public final boolean areFirstAndLastTurnIgnoredSpecialCards;
    public final boolean isLeftBowerStrongerThanSame2;
    public final boolean isClub3RequiringJoker;
    public final boolean usesJokerAsRequiringTrump;

    public static Options DEFAULT = Options.Build(new Builder(){{
        canHideMighty = true;
        areFirstAndLastTurnIgnoredSpecialCards = true;
        isClub3RequiringJoker = true;
        usesJokerAsRequiringTrump = true;
    }});

    private static Options Build(Builder builder) {
        return new Options(builder);
    }

    public Options(Builder builder) {
        this.canHideMighty = builder.canHideMighty;
        this.areFirstAndLastTurnIgnoredSpecialCards = builder.areFirstAndLastTurnIgnoredSpecialCards;
        isLeftBowerStrongerThanSame2 = builder.isLeftBowerStrongerThanSame2;
        isClub3RequiringJoker = builder.isClub3RequiringJoker;
        this.usesJokerAsRequiringTrump = builder.usesJokerAsRequiringTrump;
    }

    public static class Builder {
        public boolean canHideMighty;
        public boolean areFirstAndLastTurnIgnoredSpecialCards;
        public boolean isLeftBowerStrongerThanSame2;
        public boolean isClub3RequiringJoker;
        public boolean usesJokerAsRequiringTrump;
    }
}
