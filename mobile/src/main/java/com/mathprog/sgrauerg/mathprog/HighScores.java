package com.mathprog.sgrauerg.monochromemath;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by scott on 11/13/2015.
 */
public class HighScores implements Serializable {

    private static final String HIGH_SCORES_FILE = "HighScorz";

    List<HighScoreAndName> highScoreList;
    int rankMostRecent;

    private class HighScoreAndName implements Comparable<HighScoreAndName>, Serializable
    {
        public String userName;
        public float gameScore;

        @Override
        public int compareTo(HighScoreAndName inScore)
        {
            if (gameScore < inScore.gameScore)
            {
                return 1;
            }
            if (gameScore > inScore.gameScore)
            {
                return -1;
            }
            return 0;
        }
    }


    public HighScores()
    {
        highScoreList = new LinkedList<HighScoreAndName>();
        rankMostRecent = -1;
    }

    public void addHighScore(String userName, float score)
    {
        HighScoreAndName newScore = new HighScoreAndName();
        newScore.userName = userName;
        newScore.gameScore = score;

        highScoreList.add(newScore);
        Collections.sort(highScoreList);

        rankMostRecent = highScoreList.indexOf(newScore) + 1;
    }

    public int getRankMostRecent()
    {
        return rankMostRecent;
    }

    public int getTotalNumScores()
    {
        return highScoreList.size();
    }

    public String getHighScoresNamesAndScoresString(int numHighScores)
    {
        StringBuilder scoresAndNamesString = new StringBuilder();
        int numScoresToRetrieve = Math.min(highScoreList.size(), numHighScores);
        for (int i=0; i < numScoresToRetrieve; i++)
        {
            scoresAndNamesString.append(highScoreList.get(i).userName + "          " +
                                        highScoreList.get(i).gameScore);

            if (i != (numHighScores - 1))
            {
                scoresAndNamesString.append("\n");
            }

        }

        return scoresAndNamesString.toString();
    }

    public String getHighScoresNamesOnlyString(int numHighScores)
    {
        StringBuilder scoresAndNamesString = new StringBuilder();
        int numScoresToRetrieve = Math.min(highScoreList.size(), numHighScores);
        for (int i=0; i < numScoresToRetrieve; i++)
        {
            scoresAndNamesString.append(highScoreList.get(i).userName);

            if (i != (numHighScores - 1))
            {
                scoresAndNamesString.append("\n");
            }

        }

        return scoresAndNamesString.toString();
    }

    public String getHighScoresScoresOnlyString(int numHighScores)
    {
        StringBuilder scoresAndNamesString = new StringBuilder();
        int numScoresToRetrieve = Math.min(highScoreList.size(), numHighScores);
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (int i=0; i < numScoresToRetrieve; i++)
        {
            scoresAndNamesString.append(formatter.format(highScoreList.get(i).gameScore));

            if (i != (numHighScores - 1))
            {
                scoresAndNamesString.append("\n");
            }

        }

        return scoresAndNamesString.toString();
    }


    public static void saveHighScoresToFile(HighScores highScoresToSave, String filePath)
    {
        try{
            FileOutputStream fout = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(highScoresToSave);
            oos.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static HighScores loadHighScoresFromFile(String filePath)
    {
        HighScores loadedHighScores = null;

        File fileCheck = new File(filePath);
        if(fileCheck.exists())
        {
            //deserialize the quarks.ser file
            try {
                InputStream file = new FileInputStream(filePath);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                //deserialize the high score file
                loadedHighScores = (HighScores)input.readObject();
            }
            catch (Exception e)
            {
                Log.v("HighScores", "Error opening high score file");
            }

        }

        return loadedHighScores;
    }
}
