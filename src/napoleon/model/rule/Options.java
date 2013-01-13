package napoleon.model.rule;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 13/01/07
 * Time: 6:58
 * To change this template use File | Settings | File Templates.
 */
public class Options {
    final boolean canHideMighty;
    final boolean areFirstAndLastTurnIgnoredSpecialCards;
    final boolean isLeftBowerStrongerThanSame2;
    final boolean isClub3RequiringJoker;
    final boolean usesJokerAsRequiringTrump;

    public static Options DEFAULT = Options.Build(new Builder(){{
        canHideMighty = true;
        areFirstAndLastTurnIgnoredSpecialCards = true;
        isClub3RequiringJoker = true;
        usesJokerAsRequiringTrump = true;
    }});

    private static Options Build(Builder builder) {
        return new Options(builder);
    }

    private Options(Builder builder) {
        this.canHideMighty = builder.canHideMighty;
        this.areFirstAndLastTurnIgnoredSpecialCards = builder.areFirstAndLastTurnIgnoredSpecialCards;
        isLeftBowerStrongerThanSame2 = builder.isLeftBowerStrongerThanSame2;
        isClub3RequiringJoker = builder.isClub3RequiringJoker;
        this.usesJokerAsRequiringTrump = builder.usesJokerAsRequiringTrump;
    }

    private static class Builder {
        boolean canHideMighty;
        boolean areFirstAndLastTurnIgnoredSpecialCards;
        boolean isLeftBowerStrongerThanSame2;
        boolean isClub3RequiringJoker;
        boolean usesJokerAsRequiringTrump;
    }
}
