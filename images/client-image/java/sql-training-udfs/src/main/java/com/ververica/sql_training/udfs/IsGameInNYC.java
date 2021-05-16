package com.ververica.sql_training.udfs;

import com.ververica.sql_training.udfs.util.GameUtils;
import org.apache.flink.table.functions.ScalarFunction;

public class IsGameInNYC extends ScalarFunction {
    public boolean eval(Long gameId){
        return GameUtils.isInNYC(gameId);
    }
}
