package com.nicklase.bilteori.logic;

//not in use yet, will be implemented when alternatives require pictures.
public class Alternative {

private String  image;
private  String formulation;
public String getImage() {
	return image;
}
public Alternative(String image, String formulation) {
	super();
	this.image = image;
	this.formulation = formulation;
}
public void setImage(String image) {
	this.image = image;
}
public String getFormulation() {
	return formulation;
}
public void setFormulation(String formulation) {
	this.formulation = formulation;
}
}
