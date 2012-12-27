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
            {SELECT_UNUSED_CARDS, "不要なカードを%d枚選んで下さい"},
            {INPUT_UNUSED_CARDS_WITH_CONSOLE, "使用しないカードを選んで下さい。Ex.[C3,C4,C5...]"},
            {YOU_HAVE_NOT_THE_CARD, "%sは持っていません"},
            {INPUT_ADJUTANT_CARD, "副官のカードを指定して下さい"},
            {UNUSED_CARDS, "テーブルに残されたカードは %s です。"},
            {NAPOLEON_GAINED_CARDS, "ナポレオン獲得カード %s"},
            {PLAYER_GAINED_CARDS, "プレイヤー [%s] の獲得カード %s"},
            {TURN_WINNER, "★ターン[%d], 勝者[%s]: カード[%s]"},
            {CARD_OF_ADJUTANT, "副官のカードは %s です"},
            {PLAYER_OF_ADJUTANT, "副官は %s です"},
            {WINNER_DETAIL, "ナポレオン軍の獲得カード %s, 勝者は %s です"},
            {CARDS_OF_THIS_TURN_AND_DECLARATION, "このターンのカード %s, 宣言 %s"},
            {YOU_MUST_OPEN_TRUMP, "切り札請求された場合は、切り札をださなければなりません。"},
            {YOU_MUST_OPEN_JOKER,"ジョーカー請求された場合は、ジョーカーをださなければなりません。"},
            {YOU_MUST_OPEN_LEAD_SUIT, "台札がある場合は、台札をださなければなりません。"},
    }                                                                      ;

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}
