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
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
