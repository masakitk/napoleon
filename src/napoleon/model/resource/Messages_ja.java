package napoleon.model.resource;

import java.util.ListResourceBundle;

import static napoleon.model.resource.Messages.*;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 12/12/27
 * Time: 8:07
 * To change this template use File | Settings | File Templates.
 */
public class Messages_ja extends ListResourceBundle {

    private final static String[][] resources = {
            {CARDS_GAINED, "/ 獲得済カード:%s%n"},
            {CARDS_HAVING, "/ 手札:%s%n"},
            {NAPOLEON_FIXED, "ナポレオンは:%s, %s"},
            {YOUR_CARDS, "あなたの手札:[%s]"},
    }
    ;

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
