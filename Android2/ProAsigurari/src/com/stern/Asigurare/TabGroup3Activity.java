package com.stern.Asigurare;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup3Activity extends TabGroupActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("Altele", new Intent(this,Altele.class));
	}
	
	
}