package com.mathprog.sgrauerg.mathprog;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sgrauerg on 11/5/15.
 */
public class MathProblem implements Serializable
{
    private GameOptions.probType currProbType;

    private int leftNum;
    private int rightNum;
    private int userAnswer;

    public MathProblem()
    {
    }

    public MathProblem(MathProblem mathProblemToCopy)
    {
        leftNum = mathProblemToCopy.getLeftNum();
        rightNum = mathProblemToCopy.getRightNum();
        currProbType = mathProblemToCopy.getCurrProbType();
    }

    public GameOptions.probType getCurrProbType() {
        return currProbType;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public int getRightNum() {
        return rightNum;
    }

    public void genNewProblem(GameOptions gameOptions)
    {
        int numPossProbTypes = gameOptions.activeProbTypes.size();
        int selectedProbTypeNum = (int)Math.floor(Math.random() * numPossProbTypes);

        //get problem type (only PLUS for now)
        currProbType = gameOptions.activeProbTypes.get(selectedProbTypeNum);

        switch (currProbType)
        {
            case PLUS:
                //generate values for the left and right sides
                leftNum = (int)Math.floor(Math.random() * 20);
                rightNum = (int)Math.floor(Math.random() * 20);
                break;
            case MINUS:
                //generate values for the left and right sides
                leftNum = (int)Math.floor(Math.random() * 20);
                rightNum = (int)Math.floor(Math.random() * 20);
                break;
            case MULT:
                //generate values for the left and right sides
                leftNum = (int)Math.floor(Math.random() * 15);
                rightNum = (int)Math.floor(Math.random() * 15);
                break;
            case DIVIDE:
                //first gen multiply
                int leftNumMult = (int)Math.floor(Math.random() * 14) + 1;
                int rightNumMult = (int)Math.floor(Math.random() * 14) + 1;
                leftNum = leftNumMult * rightNumMult;
                rightNum = rightNumMult;
                break;
        }

    }

    public int correctAnswer()
    {
        switch (currProbType)
        {
            case PLUS:
                return leftNum + rightNum;
            case MINUS:
                return leftNum - rightNum;
            case MULT:
                return leftNum * rightNum;
            case DIVIDE:
                return leftNum / rightNum;
        }

        return -1;
    }

    public List<Integer> genPossAnswers(int numPossAnswers)
    {
        List<Integer> possAnswers = new ArrayList<Integer>();
        possAnswers.add(correctAnswer());

        for (int i=0; i<(numPossAnswers-1); i++) {
            int possAnswerToAdd;
            do
            {
                possAnswerToAdd = correctAnswer() + (int) ((Math.random() * 20) - 10);
            } while (possAnswers.contains(possAnswerToAdd));

            possAnswers.add(possAnswerToAdd);
        }

        Collections.sort(possAnswers);
        return possAnswers;
    }

    public String getLeftSideStringRep()
    {
        return Integer.toString(leftNum);
    }

    public String getRightSideStringRep()
    {
        return Integer.toString(rightNum);
    }

    public String getOpStringRep()
    {
        switch (currProbType)
        {
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case MULT:
                return "*";
            case DIVIDE:
                return "/";
        }
        return "";
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
}
