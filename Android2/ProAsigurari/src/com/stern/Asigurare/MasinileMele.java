package com.stern.Asigurare;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class MasinileMele extends ListActivity {

	public static String tipDate = "";
	public static YTOAutovehicul autovehiculCurent = null;
	Button btn_adauga;
	ImageView imzero;
	TextView tvzero;
	int nrAuto = 0;
	String contUser = "";
	String contParola = "";
	String limba = "ro";
	String versiune = "";
	AppSettings sett;
	public static int positionId;
	private SimpleCursorAdapter conAdapter;
	DatabaseConnector dbConnector;

	public void onCreate(Bundle savedInstanceState) {
		// Display display = ((WindowManager)
		// this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		// int width = display.getWidth();
		Window window = getWindow();
		// if (YTOUtils.isTablet(this)) {
		// window.setGravity(Gravity.CENTER);
		// window.setLayout(320, 480);
		// }
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masinile_mele);

		MainController.tvTitlu.setText(getString(R.string.i264));
		sett = AppSettings.get(this);
		if (tipDate.equals("Asigurare")) {
			sett.updateTitluGroup2(MainController.tvTitlu.getText().toString());
		} else {
			sett.updateTitluGroup1(MainController.tvTitlu.getText().toString());
		}

		imzero = (ImageView) findViewById(R.id.iv_zero_masini);
		tvzero = (TextView) findViewById(R.id.tv_zero_masini);
		String text = "";
		if (sett.getLanguage().equals("hu"))
			text = "Nincsenek elmentett autók.<br> Egy új autó hozzáadásához nyomd meg az gombot<br> <font color=\"#259c5a\"> \"Autó hozzáadása \"</font>";
		else if (sett.getLanguage().equals("en"))
			text = "There aren't any cars saved in the list.<br> To add a new car,<br> press the button above<br> <font color=\"#259c5a\"> \"Add vehicle\"</font>";
		else
			text = "Nu exista masini salvate.<br> Pentru a adauga o noua masina,<br> apasa butonul de mai sus <br> <font color=\"#259c5a\">\"Adauga autovehicul\"</font>";
		tvzero.setText(Html.fromHtml(text), BufferType.SPANNABLE);
		if (GetObiecte.autovehicule != null)
			nrAuto = GetObiecte.autovehicule.size();
		if (nrAuto != 0) {
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
			conAdapter = (new ImageAndTextAdapter(MasinileMele.this,
					R.layout.meniu_lista_element, null, null, null));
			setListAdapter(conAdapter);
		} else {
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}

		btn_adauga = (Button) findViewById(R.id.adauga_masina);
		btn_adauga.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InfoMasina.tipLoad = "add";
				Intent masina = new Intent(getParent(), Masina.class);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("Masina", masina);

			}
		});

		final ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				positionId = position;
				if (tipDate.equals("Asigurare")) {
					autovehiculCurent = new YTOAutovehicul();
					autovehiculCurent = GetObiecte.autovehicule.get(positionId);
					if (autovehiculCurent.completedPercent() != 1
							|| !autovehiculCurent.isValidForRCA()) {
						InfoMasina.tipLoad = "view";
						Intent masina = new Intent(getParent(), Masina.class);
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						parentActivity.startChildActivity("Masina", masina);
					} else
						onBackPressed();
				} else if (tipDate.equals("Valabilitate")) {
					autovehiculCurent = new YTOAutovehicul();
					autovehiculCurent = GetObiecte.autovehicule.get(positionId);
					if (autovehiculCurent.nrInmatriculare.equals("")
							&& autovehiculCurent.serieSasiu.equals("")) {
						InfoMasina.tipLoad = "view";
						Intent masina = new Intent(getParent(), Masina.class);
						TabGroupActivity parentActivity = (TabGroupActivity) getParent();
						parentActivity.startChildActivity("Masina", masina);
					} else
						onBackPressed();
				} else {
					InfoMasina.tipLoad = "view";
					Intent masina = new Intent(getParent(), Masina.class);
					TabGroupActivity parentActivity = (TabGroupActivity) getParent();
					parentActivity.startChildActivity("Masina", masina);
				}
			}

		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteDialog(GetObiecte.autovehicule.get(position).idIntern);
				return true;
			}
		});

		limba = sett.getLanguage();
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		versiune = pInfo.versionName;
		contUser = sett.getUsername();
		contParola = sett.getPassword();

	}

	public void onResume() {
		super.onResume();
		if (GetObiecte.autovehicule != null)
			nrAuto = GetObiecte.autovehicule.size();
		if (nrAuto != 0) {
			imzero.setVisibility(View.GONE);
			tvzero.setVisibility(View.GONE);
			conAdapter = (new ImageAndTextAdapter(MasinileMele.this,
					R.layout.meniu_lista_element, null, null, null));
			setListAdapter(conAdapter);
		} else {
			imzero.setVisibility(View.VISIBLE);
			tvzero.setVisibility(View.VISIBLE);
		}

	}

	private class ImageAndTextAdapter extends SimpleCursorAdapter {
		private Context adContext;
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"ArialRoundedMT.ttf");

		public int getCount() {
			return nrAuto;
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
				v = inflater.inflate(R.layout.element_lista_items, null);
			}

			((TextView) v.findViewById(R.id.textListTopElem))
					.setText(GetObiecte.autovehicule.get(pos).marcaAuto);
			((TextView) v.findViewById(R.id.textListTopElem))
					.setTypeface(Typeface.create(tf, Typeface.BOLD));
			if (!GetObiecte.autovehicule.get(pos).modelAuto.equals(""))
				((TextView) v.findViewById(R.id.textListElem))
						.setText(GetObiecte.autovehicule.get(pos).modelAuto
								+ ","
								+ GetObiecte.autovehicule.get(pos).nrInmatriculare);
			else
				((TextView) v.findViewById(R.id.textListElem)).setText("");
			((ImageView) v.findViewById(R.id.imgListElem))
					.setImageResource(R.drawable.icon_foto);
			if (GetObiecte.autovehicule.get(pos).completedPercent() == 1
					&& GetObiecte.autovehicule.get(pos).isValidForRCA())
				((ImageView) v.findViewById(R.id.image_right))
						.setVisibility(View.GONE);
			else
				((ImageView) v.findViewById(R.id.image_right))
						.setVisibility(View.VISIBLE);
			return v;
		}
	}

	public void deleteDialog(final String idIntern) {
		final Dialog dialog = new Dialog(getParent());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		TextView textHeader;
		Typeface th = Typeface.createFromAsset(getAssets(),
				"NanumPenScript-Regular.ttf");

		textHeader = (TextView) dialog.findViewById(R.id.text_vrei_sa_stergi);
		textHeader.setTextColor(getResources().getColor(R.color.rosu_ter));
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
				dbConnector = new DatabaseConnector(MasinileMele.this);
				dbConnector.deleteObiectAsigurat(idIntern);
				GetObiecte.getAlerteByIdObiect(idIntern);
				new DeleteMasinaWebService(idIntern, contUser, contParola,
						limba, versiune).execute(null, null, null);
				for (YTOAlerta alerta : GetObiecte.alertebyid) {
					dbConnector.deleteObiectAsigurat(alerta.idIntern);
					new StergeAlerta(alerta, idIntern)
							.execute(null, null, null);
				}
				if (autovehiculCurent.isDirty)
					if (autovehiculCurent.idIntern.equals(idIntern))
						autovehiculCurent.isDirty = false;
				GetObiecte.getAutovehicule(dbConnector);
				GetObiecte.getAlerte(dbConnector);
				MainController.setBadge();
				onResume();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private class StergeAlerta extends AsyncTask<Void, Void, Void> {
		YTOAlerta alerta = new YTOAlerta();
		String idIntern = "";

		public StergeAlerta(YTOAlerta alerta, String idIntern) {
			// TODO Auto-generated constructor stub
			this.idIntern = idIntern;
			this.alerta = alerta;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			String xml = "<soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"
					+ "<soap:Body>"
					+ "<DisableAlert xmlns='http://tempuri.org/'>"
					+ "<user>vreaurca</user>"
					+ "<password>123</password>"
					+ "<udid>"
					+ sett.getDeviceId()
					+ "</udid>"
					+ "<id_intern>"
					+ idIntern
					+ "</id_intern>"
					+ "<tip_alerta>"
					+ alerta.tipAlerta
					+ "</tip_alerta>"
					+ "</DisableAlert>"
					+ "</soap:Body>" + "</soap:Envelope>";

			String url = GetObiecte.link + "sync.asmx";
			String soapAction = "http://tempuri.org/DisableAlert";
			String resp = HttpHelper.callWebService(url, soapAction, xml);
			return null;
		}

		@Override
		protected void onPostExecute(final Void unused) {
			super.onPostExecute(null);
		}
	}

}
