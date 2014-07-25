package com.stern.Asigurare;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup2Activity extends TabGroupActivity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("IncheieAsigurare", new Intent(this,IncheieAsigurare.class));
	}
	
	
}