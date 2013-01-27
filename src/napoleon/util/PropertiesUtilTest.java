package napoleon.util;

import napoleon.model.rule.GameContext;
import napoleon.model.rule.Options;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 13/01/22
 * Time: 8:03
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtilTest {
    @Test
    public void testLoadContextFromXml() throws Exception {
        GameContext.Init();
        Options options = PropertiesUtil.loadContextFromXml().getOptions();
        assertTrue(options.canHideMighty);
        assertTrue(options.areFirstAndLastTurnIgnoredSpecialCards);
        assertTrue(options.isClub3RequiringJoker);
        assertTrue(options.usesJokerAsRequiringTrump);
        assertFalse(options.isLeftBowerStrongerThanSame2);

    }
}
