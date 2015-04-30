package ru.darout.webcom.controllers;

public abstract class AController implements IController{
	protected static final String API_CONTEXT = "angspark1";
	
	@Override
	public abstract void setupEndpoints();
}
