package com.nicklase.bilteori.logic;

import java.util.List;

public class Question{
    private  String catName;
    private  String formulation;
    private String  image;
    private  List <String> alternatives;
    private String rightAnswer;

    public Question(String catName, String formulation, List<String> alternatives, String rightAnswer, String image) {
        this.catName = catName;
        this.formulation = formulation;
        this.alternatives = alternatives;
        this.rightAnswer=rightAnswer;
        this.image=image;
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
	public String getImage(){
		return image;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
}