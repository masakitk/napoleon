package napoleon.model.resource;

import java.util.ListResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 12/12/27
 * Time: 8:07
 * To change this template use File | Settings | File Templates.
 */
public class Messages_ja extends ListResourceBundle {

    private final static String[][] resources = {
            {"cardsGained", "/ 獲得:%s%n"}
    };

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
