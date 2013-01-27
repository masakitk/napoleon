package napoleon.util;

import napoleon.model.rule.GameContext;
import napoleon.model.rule.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: masakitk
 * Date: 13/01/22
 * Time: 7:29
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtil {

    public static final String NAPOLEON_CONF = "napoleon/util/napoleon.xml";

    static GameContext loadContextFromXml() {
        return loadContextFromXml(NAPOLEON_CONF);
    }

    static GameContext loadContextFromXml(String napoleonConf) {
        Options options = createOptionsFromXml(napoleonConf);
        GameContext.getCurrent().initOptions(options);
        return GameContext.getCurrent();
    }

    private static Options createOptionsFromXml(String napoleonConf) {
        Options.Builder builder = new Options.Builder();
        builder.canHideMighty = Boolean.parseBoolean(loadFromXml(napoleonConf).getProperty("canHideMighty"));
        builder.usesJokerAsRequiringTrump = Boolean.parseBoolean(loadFromXml(napoleonConf).getProperty("usesJokerAsRequiringTrump"));
        builder.isClub3RequiringJoker = Boolean.parseBoolean(loadFromXml(napoleonConf).getProperty("isClub3RequiringJoker"));
        builder.areFirstAndLastTurnIgnoredSpecialCards = Boolean.parseBoolean(loadFromXml(napoleonConf).getProperty("areFirstAndLastTurnIgnoredSpecialCards"));
        builder.isLeftBowerStrongerThanSame2 = Boolean.parseBoolean(loadFromXml(napoleonConf).getProperty("isLeftBowerStrongerThanSame2"));
        return new Options(builder);
    }

    private static Properties loadFromXml(String napoleonConf) {
        Properties properties = new Properties();
        try {
            properties.loadFromXML(PropertiesUtil.class.getClassLoader().getResourceAsStream(napoleonConf));
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(PropertiesUtil.class.getClass());
            logger.error("caught when load properties", e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return properties;
    }
}
