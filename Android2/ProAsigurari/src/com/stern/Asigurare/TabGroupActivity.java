package com.stern.Asigurare;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
* The purpose of this Activity is to manage the activities in a tab.
* Note: Child Activities can handle Key Presses before they are seen here.
*/
public class TabGroupActivity extends ActivityGroup {

    static TextView tvTitluPopup;
	static TextView tvContinut;
    static ImageView imgTitlu;
    static Button btn_nu,btn_da;
	private ArrayList<String> mIdList;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if (mIdList == null) mIdList = new ArrayList<String>();
	}

/**
* This is called when a child activity of this one calls its finish method.
* This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
* and starts the previous activity.
* If the last child activity just called finish(),this activity (the parent),
* calls finish to finish the entire group.
*/
	@Override
	public void finishFromChild(Activity child) 
	{
		LocalActivityManager manager = getLocalActivityManager();
		
		if (mIdList.get(mIdList.size()-1).equals("CalculatieCalatorie") && mIdList.get(mIdList.size()-2).equals("CalculatieCalatorie"))
			while (mIdList.get(mIdList.size()-1).equals("CalculatieCalatorie") && mIdList.get(mIdList.size()-2).equals("CalculatieCalatorie"))
				mIdList.remove(mIdList.size()-1);

		if (mIdList.get(mIdList.size()-1).equals("Locuinta") && LocuinteleMele.tipDate.equals("Asigurare") && !AsigurareLocuinta.skipNextPage)
			mIdList.remove(mIdList.size()-1);
		
		if (mIdList.get(mIdList.size()-1).equals("Masina") && MasinileMele.tipDate.equals("Asigurare")&& !AsigurareRca.skipNextPage)
			mIdList.remove(mIdList.size()-1);
		
		if (mIdList.get(mIdList.size()-1).equals("Persoana") && AltePersoane.tipDate.equals("Asigurare") )
			mIdList.remove(mIdList.size()-1);
		
		int index = mIdList.size()-1;
		
		if (index < 1 ) 
		{
			finish();
			return;
		}
		
		
		manager.destroyActivity(mIdList.get(index), true);
		mIdList.remove(index);
		index--;
		String lastId = mIdList.get(index);
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		Window newWindow = manager.startActivity(lastId, lastIntent);
		setContentView(newWindow.getDecorView());
		
		
	}

/**
* Starts an Activity as a child Activity to this.
* @param Id Unique identifier of the activity to be started.
* @param intent The Intent describing the activity to be started.
* @throws android.content.ActivityNotFoundException.
*/
	public void startChildActivity(String Id, Intent intent) 
	{
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) 
		{
			mIdList.add(Id);
			setContentView(window.getDecorView());
		}
	}

/**
* The primary purpose is to prevent systems before android.os.Build.VERSION_CODES.ECLAIR
* from calling their default KeyEvent.KEYCODE_BACK during onKeyDown.
*/
@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{
			//preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

/**
* Overrides the default implementation for KeyEvent.KEYCODE_BACK
* so that all systems call onBackPressed().
*/
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{
			onBackPressed();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

/**
* If a Child Activity handles KeyEvent.KEYCODE_BACK.
* Simply override and add this method.
*/
	@Override
	public void onBackPressed () {
        int length = mIdList.size();
        if (length > 1) {
                Activity current = getLocalActivityManager().getActivity(
                                mIdList.get(length - 1));
                current.finish();
        }else 
        {
        	exitDialog();
        }

	}
	
	public void exitDialog () {
	     final Dialog dialog=new Dialog(this);
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     dialog.setContentView(R.layout.dialog);

	     ((TextView) dialog.findViewById(R.id.text_titlu)).setText("INFO");
	     ((TextView) dialog.findViewById(R.id.text_continut)).setText(getString(R.string.i435));
	     Typeface th = Typeface.createFromAsset(getAssets(), "NanumPenScript-Regular.ttf");
			
	     TextView textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
	     textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
	     textHeader.setText(getString(R.string.i804));
	     textHeader.setTypeface(Typeface.create(th,Typeface.BOLD));
	     
	     Button btn_nu=(Button)dialog.findViewById(R.id.button_nu);
	     btn_nu.setOnClickListener(new OnClickListener() {
	     @Override
	     public void onClick(View v) {
	        dialog.dismiss();
	     }
	     });

	     
	     Button btn_da=(Button)dialog.findViewById(R.id.button_da);
	     btn_da.setOnClickListener(new OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    		finish();
		    		Intent intent = new Intent(Intent.ACTION_MAIN);
		    		intent.addCategory(Intent.CATEGORY_HOME);
		    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    		startActivity(intent);
		    		android.os.Process.killProcess(android.os.Process.myPid());
	            }	
		     });
	   dialog.show();
	}

}