package battlegrid.setup;

import battlegrid.abstracts.GameEntityInfo;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 26/01/2011 <br/>
 * Time: 18:09:33 <br/>
 */
public class GameProperties {

    public static int getIntProperty(String key) {
        if(key.contains("PLAYER")){
            return 3;
        }
        else if(key.equals("round.time")){
            return 500;
        }
        return GameEntityInfo.IMMORTAL;
    }

    public static String getProperty(String key) {
        return "NORTH";
    }
}
