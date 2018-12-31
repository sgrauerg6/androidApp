package com.mathprog.sgrauerg.monochromemath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sgrauerg on 11/6/15.
 */
public class GameOptions implements Serializable {

    public static final int GAME_TIME = 15;
    public enum probType { PLUS, MINUS, MULT, DIVIDE};

    public class probSettings
    {
        public probType currProbType;
        public boolean probTypeEnabled;
    }

    public int gameTime;
    public Set<probSettings> probTypeSettings;
    public List<probType> activeProbTypes;

    public GameOptions()
    {
        gameTime = GAME_TIME;
        probTypeSettings = new HashSet<probSettings>();
        for (probType currProbType : probType.values())
        {
            probSettings currOpSettings = new probSettings();
            currOpSettings.currProbType = currProbType;
            currOpSettings.probTypeEnabled = true;

            probTypeSettings.add(currOpSettings);
        }

        activeProbTypes = new ArrayList<probType>();
        for (probSettings currProbSettings : probTypeSettings)
        {
            if (currProbSettings.probTypeEnabled == true)
            {
                activeProbTypes.add(currProbSettings.currProbType);
            }
        }
    }

}
