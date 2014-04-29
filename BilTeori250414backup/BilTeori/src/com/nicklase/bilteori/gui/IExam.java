package com.nicklase.bilteori.gui;

interface IExam {
	public void setUpButtons();
	public void createRadioButton(); 
	public void removeRadioButton();
	public void updateProgress();
	public void randomizeQuestions();
	public void setQuestion();
	public void getQuestionList();
}
