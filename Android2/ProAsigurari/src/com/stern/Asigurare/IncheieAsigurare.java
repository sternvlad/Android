package com.stern.Asigurare;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class IncheieAsigurare extends Activity {

	AppSettings appSett;
	int width;
	ListView listNotificari;
	ArrayList<YTONotificare> alerte = new ArrayList<YTONotificare>();
	private SimpleCursorAdapter conAdapter;
	DatabaseConnector dbConnector;
	Button trimiteBtn, btn_nu, btn_da;
	String mesajOK = null;
	String mesaj = null;
	ProgressDialog dlg;
	Dialog dialog;
	WebView wv;
	String operator = "";
	int nrNotificari;
	TextView tvTermeni;
	TextView tvContinut;
	ImageView imgTitlu;
	ImageView imzero;// se afiseaza in cazul in care nu exista nicio
						// inregistrare
	TextView tvzero;// se afiseaza in cazul in care nu exista nicio inregistrare
	int nrAlerte;// numarul de alerte
	int i = 1;
	Button asigRca, asigLocuinta, asigCalatorie;

	@SuppressLint("SimpleDateFormat")
	public void onCreate(Bundle savedInstanceState) {
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incheie_asigurare);

		MainController.tvTitlu.setText(getString(R.string.i436));
		// MainActivity.buttonNotificari.setVisibility(View.VISIBLE);
		appSett = AppSettings.get(this);
		appSett.updateTitluGroup2(getString(R.string.i436));
		nrNotificari = appSett.getNumberOfNewNotifications();

		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface
				.create(th, Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface
				.create(th, Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface
				.create(th, Typeface.BOLD));

		asigRca = (Button) findViewById(R.id.asigurare_rca);
		asigRca.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MainActivity.buttonNotificari.setVisibility(View.GONE);
				Intent asigRCA = new Intent(getParent(), AsigurareRca.class);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("AsigurareRca", asigRCA);

			}
		});
	
		asigLocuinta = (Button) findViewById(R.id.asigurare_locuinta);
		asigLocuinta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MainActivity.buttonNotificari.setVisibility(View.GONE);
				Intent asigLoc = new Intent(getParent(),
						AsigurareLocuinta.class);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("AsigurareLocuinta", asigLoc);

			}
		});

		asigCalatorie = (Button) findViewById(R.id.asigurare_calatorie);
		asigCalatorie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MainActivity.buttonNotificari.setVisibility(View.GONE);
				AltePersoaneCalatorie.calatori.clear();
				Intent asigCal = new Intent(getParent(),
						AsigurareCalatorie.class);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity
						.startChildActivity("AsigurareCalatorie", asigCal);

			}
		});

		if (appSett.getLanguage().equals("hu") && YTOUtils.isTablet(this)) {
			((TextView) findViewById(R.id.text_header3)).setTextSize(27);
		}

	}

	public void onResume() {
		super.onResume();
		MainController.tvTitlu.setText(getString(R.string.i436));
		appSett.updateTitluGroup2(getString(R.string.i436));
	}

	@Override
	public void onBackPressed() {
		exitDialog();
	}

	public void exitDialog() {
		final Dialog dialog = new Dialog(getParent());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);

		((TextView) dialog.findViewById(R.id.text_titlu)).setText("INFO");
		((TextView) dialog.findViewById(R.id.text_continut))
				.setText(getString(R.string.i435));
		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		TextView textHeader = (TextView) dialog
				.findViewById(R.id.text_vrei_sa_stergi);
		textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
		textHeader.setText(getString(R.string.i804));
		textHeader.setTypeface(Typeface.create(th, Typeface.BOLD));

		Button btn_nu = (Button) dialog.findViewById(R.id.button_nu);
		btn_nu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button btn_da = (Button) dialog.findViewById(R.id.button_da);
		btn_da.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				appSett.updateLanguageWasChanged("false");
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
	public PendingIntent existAlarm() {
		Intent intent = new Intent(this, GCMIntentService.class);
		intent.setAction(Intent.ACTION_VIEW);
		PendingIntent test = PendingIntent.getBroadcast(this, 0 + 0, intent,
				PendingIntent.FLAG_NO_CREATE);
		return test;
	}

	public void errorDialog(String mesaj) { // eroare
		final Dialog dialog = new Dialog(getParent());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_error);
		TextView tvContinut;
		// ImageView imgContinut;
		Button btn_ok = (Button) dialog.findViewById(R.id.button_ok);
		tvContinut = (TextView) dialog.findViewById(R.id.text_error);
		if (mesaj == null)
			tvContinut.setText(getString(R.string.i473));
		else if (!mesaj.equals(""))
			tvContinut.setText(mesaj);
		TextView textHeader;
		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		textHeader = (TextView) dialog.findViewById(R.id.text_eroare_header);
		textHeader.setTypeface(Typeface.create(th, Typeface.BOLD));
		if (!isOnline()) {
			textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
			textHeader.setText(getString(R.string.i799));
			tvContinut.setTextSize(12);
		}

		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {

			return true;
		}
		return false;
	}
}
