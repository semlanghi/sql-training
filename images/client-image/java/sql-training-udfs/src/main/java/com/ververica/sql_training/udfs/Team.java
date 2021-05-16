package com.ververica.sql_training.udfs;

import com.ververica.sql_training.udfs.util.GameUtils;
import org.apache.flink.table.functions.ScalarFunction;

public class Team extends ScalarFunction {
    public String eval(Long playerId){
        return GameUtils.team(playerId);
    }
}
