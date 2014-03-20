package com.nicklase.bilteori;

import java.util.List;

public class Question{
    private  String catName;
    private  String formulation;
    private  List <String> alternatives;
    private String rightAnswer;

    public Question(String catName, String formulation, List alternatives, String rightAnswer) {
        this.catName = catName;
        this.formulation = formulation;
        this.alternatives = alternatives;
        this.rightAnswer=rightAnswer;
    }

	public String getCatName() {
		return catName;
	}

	public String getFormulation() {
		return formulation;
	}

	public  List<String> getAlternatives() {
		return alternatives;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}
}