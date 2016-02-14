package com.mathprog.sgrauerg.mathprog;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by scott on 11/14/2015.
 */
public class OverallStats implements Serializable{

    private int numAnswersCorrect;
    private int numAnswersTotal;
    private List<MathProblem> allProblemsAndAnswers;

    public OverallStats()
    {
        numAnswersCorrect = 0;
        numAnswersTotal = 0;
        allProblemsAndAnswers = new LinkedList<MathProblem>();
    }

    public int getNumAnswersTotal() {
        return numAnswersTotal;
    }

    public void setNumAnswersTotal(int numAnswersTotal) {
        this.numAnswersTotal = numAnswersTotal;
    }

    public List<MathProblem> getAllProblemsAndAnswers() {
        return allProblemsAndAnswers;
    }

    public void setAllProblemsAndAnswers(List<MathProblem> allProblemsAndAnswers) {
        this.allProblemsAndAnswers = allProblemsAndAnswers;
    }

    public int getNumAnswersCorrect() {
        return numAnswersCorrect;
    }

    public void setNumAnswersCorrect(int numAnswersCorrect) {
        this.numAnswersCorrect = numAnswersCorrect;
    }

    public void addMathProblemResult(MathProblem problemToAdd)
    {
        allProblemsAndAnswers.add(problemToAdd);
    }

    public void addMultipleMathProblemsResult(List<MathProblem> problemsToAdd)
    {
        allProblemsAndAnswers.addAll(problemsToAdd);
    }
}
