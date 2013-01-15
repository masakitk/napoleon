package napoleon.model.resource;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 12/12/27
 * Time: 8:03
 * To change this template use File | Settings | File Templates.
 */
public class Messages extends ListResourceBundle {
    public static final String CARDS_GAINED = "cardsGained";
    public static final String CARDS_HAVING = "cardsHaving";
    public static final String NAPOLEON_FIXED = "napoleonFixed";
    public static final String YOUR_CARDS = "yourCards";
    public static final String SELECT_UNUSED_CARDS = "selectUnusedCards";
    public static final String INPUT_UNUSED_CARDS_WITH_CONSOLE = "inputUnusedCardsWithConsole";
    public static final String YOU_HAVE_NOT_THE_CARD = "youHaveNotTheCard";
    public static final String INPUT_ADJUTANT_CARD = "inputAdjutantCard";
    public static final String UNUSED_CARDS = "unusedCards";
    public static final String NAPOLEON_GAINED_CARDS = "napoleonGainedCards";
    public static final String PLAYER_GAINED_CARDS = "playerGainedCards";
    public static final String TURN_WINNER = "turnWinner";
    public static final String CARD_OF_ADJUTANT = "adjutantDetail";
    public static final String PLAYER_OF_ADJUTANT = "playerOfAdjutant";
    public static final String WINNER_DETAIL = "winnerDetail";
    public static final String CARDS_OF_THIS_TURN_AND_DECLARATION = "cardsOfThisTurnAndDeclaration";
    public static final String YOU_MUST_OPEN_TRUMP = "youMustOpenTrump";
    public static final String YOU_MUST_OPEN_JOKER = "youMustOpenJoker";
    public static final String YOU_MUST_OPEN_LEAD_SUIT = "youMustOpenLeadSuit";
    public static final String ALLIED_FORCES_TEAM = "AlliedForcesTeam";
    public static final String NAPOLEON_TEAM = "NapoleonTeam";
    public static final String ADJUTANT_NAME_WHEN_NAPOLEON_ALONE = "AdjutantNameWhenNapoleonAlone";
    public static final String INPUT_DECLARATION = "inputDeclaration";
    public static final String INPUT_CARD = "inputCard";
    public static final String CALLED_GO_ADJUTANT = "calledGoAdjutant";
    public static final String EXTRA_CARDS = "extraCards";
    public static final String INPUT_CARD_OR_GO_ADJUTANT = "inputCardOrGoAdjutant";

    public static ResourceBundle RESOURCE = ResourceBundle.getBundle("napoleon.model.resource.Messages",
            Locale.JAPANESE,
            new ResourceBundle.Control() {
                @Override
                public long getTimeToLive(String aBaseName, Locale aLocale) {
                    // キャッシュを即死させます♪
                    return TTL_DONT_CACHE;
                }});
    private final static String[][] resources = {
            {CARDS_GAINED, "/ gained:%s%n"},
            {CARDS_HAVING, "/ having:%s%n"},
            {NAPOLEON_FIXED, "★napoleon fixed:%s, %s"},
            {YOUR_CARDS, "you have :[%s]"},
            {SELECT_UNUSED_CARDS, "select %d unused cards"},
            {INPUT_UNUSED_CARDS_WITH_CONSOLE, "input cards to be unused, as [C3,C4,C5...]"},
            {YOU_HAVE_NOT_THE_CARD, "you don't have the card."},
            {INPUT_ADJUTANT_CARD, "input card to adjutant"},
            {UNUSED_CARDS, "table cards are %s"},
            {NAPOLEON_GAINED_CARDS, "napoleon gained %s"},
            {PLAYER_GAINED_CARDS, "player %s gained %s"},
            {TURN_WINNER, "★turn[%d], winner[%s]: cards[%s]"},
            {CARD_OF_ADJUTANT, "Adjutant is who having %s"},
            {PLAYER_OF_ADJUTANT, "Adjutant is %s"},
            {WINNER_DETAIL, "NapoleonTeam gained %s, winner is %s"},
            {CARDS_OF_THIS_TURN_AND_DECLARATION, "This turn opened %s, declaration is %s"},
            {YOU_MUST_OPEN_TRUMP, "Trump is required, you must open trump (or max number card)."},
            {YOU_MUST_OPEN_JOKER,"Joker is required, you must open Joker"},
            {YOU_MUST_OPEN_LEAD_SUIT, "Lead suit is required, you must open lead suit"},
            {ALLIED_FORCES_TEAM, "AlliedForcesTeam"},
            {NAPOLEON_TEAM, "NapoleonTeam"},
            {ADJUTANT_NAME_WHEN_NAPOLEON_ALONE, "nobody: only napoleon;"},
            {INPUT_DECLARATION, "input declaration(Ex. S13:♠13、H15:♥15、Pass etc..)"},
            {INPUT_CARD, "input card(Ex. S1:♠A、H13:♥13 etc...)"},
            {INPUT_CARD_OR_GO_ADJUTANT, "input card(Ex. S1:♠A、H13:♥13 etc..  Napoleon can tell to go adjutant card as input \"GO\".)"},
            {EXTRA_CARDS, "extra cards are %s"},
            {CALLED_GO_ADJUTANT, "go Adjutant!!"},
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
