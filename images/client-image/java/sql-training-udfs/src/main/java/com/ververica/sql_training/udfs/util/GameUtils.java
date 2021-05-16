package com.ververica.sql_training.udfs.util;

import java.util.Arrays;
import java.util.HashSet;

public class GameUtils {

    public static String team(long playerId){

        if(playerId<5 && playerId>=0)
            return "Lakers";
        else if (playerId<10 && playerId>=5)
            return "Clippers";
        else if (playerId<15 && playerId>=10)
            return "Pistons";
        else if (playerId<20 && playerId>=15)
            return "Knicks";
        else return "OtherTeam";
    }

    public static boolean isInNYC(long gameId){
        return gameId == 1L;
    }

}
