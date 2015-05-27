package ru.darout.webcom.controllers;

public abstract class AController implements IController{
	protected static final String API_CONTEXT = "angspark1";
	protected static final String WEB_COMM_CONTEXT = "web-comm";
	
	@Override
	public abstract void setupEndpoints();
}
