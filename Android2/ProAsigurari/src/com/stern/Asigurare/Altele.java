package com.stern.Asigurare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Altele extends ActivityGroup {

	int[] images = { R.drawable.valabilitate_rca, R.drawable.contact_iasigurare };
	Button menu2;
	ListView listView;
	String[] labelsTop = new String[2];
	String[] labels = new String[2];

	AppSettings appSett;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altele);
		appSett = AppSettings.get(this);
		listView = (ListView) findViewById(android.R.id.list);

		labelsTop[0] = getString(R.string.i484);
		labelsTop[1] = getString(R.string.i488);

		labels[0] = getString(R.string.i485);
		labels[1] = getString(R.string.i489);

		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		((TextView) findViewById(R.id.text_header1)).setTypeface(Typeface
				.create(th, Typeface.BOLD));
		((TextView) findViewById(R.id.text_header2)).setTypeface(Typeface
				.create(th, Typeface.BOLD));
		((TextView) findViewById(R.id.text_header3)).setTypeface(Typeface
				.create(th, Typeface.BOLD));

		MainController.tvTitlu.setText(getString(R.string.i490));
		appSett.updateTitluGroup4(getString(R.string.i490));

		listView.setAdapter(new ImageAndTextAdapter(Altele.this,
				R.layout.element_lista_altele, null, null, null));
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Intent valabilitate = new Intent(getParent(),
							ValabilitateRCA.class);
					TabGroupActivity parentActivity = (TabGroupActivity) getParent();
					parentActivity.startChildActivity("ValabilitateRCA",
							valabilitate);
				}
				if (position == 1) {
					Intent contact = new Intent(getParent(), Contact.class);
					TabGroupActivity parentActivity = (TabGroupActivity) getParent();
					parentActivity.startChildActivity("ContactAsigurare",
							contact);
				}
			}
		});
	}

	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;
		Typeface tf = Typeface.createFromAsset(getAssets(), "Arial.ttf");

		public int getCount() {
			return labels.length;
		}

		public ImageAndTextAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
			this.adContext = context;
		}

		public View getView(int pos, View inView, ViewGroup parent) {
			View v = inView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) adContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.element_lista_altele, null);
				if (appSett.getLanguage().equals("hu")
						&& !YTOUtils.isTablet(Altele.this)) {
					((TextView) v.findViewById(R.id.text_List)).setTextSize(14);
					((TextView) v.findViewById(R.id.text_listTop))
							.setTextSize(16);
				}

				((ImageView) v.findViewById(R.id.arrow))
						.setVisibility(View.VISIBLE);
				((TextView) v.findViewById(R.id.text_List))
						.setText(labels[pos]);
				((TextView) v.findViewById(R.id.text_listTop))
						.setText(labelsTop[pos]);
				((ImageView) v.findViewById(R.id.left_img))
						.setImageResource(images[pos]);
				((TextView) v.findViewById(R.id.text_List))
						.setTypeface(Typeface.create(tf, Typeface.NORMAL));
				((TextView) v.findViewById(R.id.text_listTop))
						.setTypeface(Typeface.create(tf, Typeface.NORMAL));
			}
			return v;
		}
	}

	public void onResume() {
		super.onResume();
		MainController.tvTitlu.setText(getString(R.string.i490));
		appSett.updateTitluGroup2(getString(R.string.i490));
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
		TextView textHeader;
		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
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

}
