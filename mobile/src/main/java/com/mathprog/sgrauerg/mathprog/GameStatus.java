package com.mathprog.sgrauerg.monochromemath;

import java.util.Calendar;
import android.util.Log;
import android.widget.TextView;
import android.view.*;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by sgrauerg on 11/5/15.
 */
public class GameStatus {

    public enum probResult { CORRECT, WRONG};
    private class ProbResults
    {
        public ProbResults(MathProblem inProbToAnswer, int inGivenAnswer, float inTimeToAnswerInSecs)
        {
            problemToAnswer = inProbToAnswer;
            givenAnswer = inGivenAnswer;
            timeToAnswerInSecs = inTimeToAnswerInSecs;
        }

        public int givenAnswer;
        public float timeToAnswerInSecs;
        public MathProblem problemToAnswer;
    }

    private LinkedList<ProbResults> currentResults;
    private int numCorrect;
    private int numWrong;
    private float currScore;
    private long timeAtQuestionStart;

    public GameStatus()
    {
        currentResults = new LinkedList<ProbResults>();
    }


    public int getNumCorrect() {
        return numCorrect;
    }

    public void incrNumCorrect() {
        numCorrect++;
    }

    public int getNumWrong() {
        return numWrong;
    }

    public void incrNumWrong() {
        numWrong++;
    }

    public float getCurrScore() {
        return currScore;
    }

    public void processResult(MathProblem lastMathProb, int givenAnswer, long timeAtAnswer)
    {
        probResult lastProbResult = probResult.CORRECT;
        if (lastMathProb.correctAnswer() == givenAnswer)
        {
            incrNumCorrect();
            lastProbResult = probResult.CORRECT;
        }
        else
        {
            incrNumWrong();
            lastProbResult = probResult.WRONG;
        }

        //compute how long it took to get answer
        long timeAfterStartMillsecs = (timeAtAnswer - timeAtQuestionStart);
        float timeToAnswerQuestion = timeAfterStartMillsecs / 1000.0f;
        switch (lastProbResult)
        {
            case CORRECT:
                currScore += 10;
                if (timeToAnswerQuestion < 2.5f)
                {
                    currScore += (2.5f - timeToAnswerQuestion) * 2;
                }
                break;
            case WRONG:
                currScore -= 10;
                break;
        }

        ProbResults p = new ProbResults(new MathProblem(lastMathProb), givenAnswer, timeToAnswerQuestion);
        currentResults.addFirst(p);
    }


    public void startGame()
    {
        numCorrect = 0;
        numWrong = 0;
        currScore = 0.0f;
        currentResults.clear();
    }

    public void startQuestion()
    {
        Calendar c = Calendar.getInstance();
        timeAtQuestionStart = c.getTimeInMillis();
    }

    public String getAllResultsString()
    {
        StringBuilder results = new StringBuilder();
        for (ProbResults result : currentResults)
        {
            if (result.givenAnswer == result.problemToAnswer.correctAnswer())
                results.append("CORRECT: ");
            else
                results.append("INCORRECT: ");

            results.append(result.problemToAnswer.getLeftSideStringRep());
            results.append(result.problemToAnswer.getOpStringRep());
            results.append(result.problemToAnswer.getRightSideStringRep());
            results.append("=");
            results.append(result.problemToAnswer.correctAnswer());
            if (result.givenAnswer == result.problemToAnswer.correctAnswer())
            {
                results.append(" answered in ");
                results.append(String.format("%.2f", result.timeToAnswerInSecs));
                results.append("s");
            }
            else
            {
                results.append(" NOT ");
                results.append(result.givenAnswer);
            }
            results.append("\n");
        }

        return results.toString();
    }
}
