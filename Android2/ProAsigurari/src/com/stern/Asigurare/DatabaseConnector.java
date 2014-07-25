package com.stern.Asigurare;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector extends SQLiteOpenHelper {

	private static final String DB_NAME = "vreau_asigurare.sqlite";
	public static String DB_PATH = "/data/data/com.stern.Asigurare/databases/";
	private SQLiteDatabase vreau_rca;
	public Cursor cursor;
	private final Context myContext;
	public static boolean de;

	public DatabaseConnector(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[2048];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void open() throws Exception {
		boolean dbExist = checkDataBase();

		if (dbExist) {
			vreau_rca = getWritableDatabase();
		} else {
			String myPath = DB_PATH + DB_NAME;
			vreau_rca = getWritableDatabase();
			copyDataBase();
			vreau_rca = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		}
	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	@Override
	public synchronized void close() {
		if (vreau_rca != null)
			vreau_rca.close();
		if (cursor != null)
			cursor.close();
		super.close();
	}

	public void insertObiectAsigurat(String IdIntern, String TipObiect,
			String JSONText) {
		// insert ObiectAsigurat in database
		ContentValues newCon = new ContentValues();
		newCon.put("IdIntern", IdIntern);
		newCon.put("TipObiect", TipObiect);
		newCon.put("JSONText", JSONText);

		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vreau_rca.insert("ObiectAsigurat", null, newCon);
		close();
	}

	public void updateObiectAsigurat(String IdIntern, String TipObiect,
			String JSONText) {
		ContentValues editCon = new ContentValues();
		editCon.put("TipObiect", TipObiect);
		editCon.put("JSONText", JSONText);

		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vreau_rca.update("ObiectAsigurat", editCon, "IdIntern=\"" + IdIntern
				+ "\"", null);
		close();
	}

	public void checkCursor(int tipObiect) {
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cursor = vreau_rca.rawQuery(
				"SELECT IdIntern FROM ObiectAsigurat where TipObiect="
						+ tipObiect, null);
		if (cursor.moveToFirst())
			de = true;
		else
			de = false;
		cursor.close();
		close();
	}

	static ArrayList<String> numeJud;
	static ArrayList<Integer> idJud;

	public void loadJudete() {
		numeJud = new ArrayList<String>();
		idJud = new ArrayList<Integer>();
		numeJud.add("Bucuresti");
		idJud.add(43);
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cursor = vreau_rca
					.rawQuery(
							"select Id, Nume from Judete where Nume!='Bucuresti'",
							null);
			cursor.moveToFirst();
			do {
				int id = Integer.parseInt(cursor.getString(0));
				String cls = cursor.getString(1);
				idJud.add(id);
				numeJud.add(cls);

			} while (cursor.moveToNext());
			cursor.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();
	}

	static ArrayList<String> textCaen;
	static ArrayList<String> codCaen;

	public void loadCodCaen() {
		textCaen = new ArrayList<String>();
		codCaen = new ArrayList<String>();
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cursor = vreau_rca.rawQuery("select CodCaen, Nume from CodCaen", null);
			cursor.moveToFirst();
			do {
				String id = cursor.getString(0);
				String cls = cursor.getString(1);
				codCaen.add(id);
				textCaen.add(cls);

			} while (cursor.moveToNext());
			cursor.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();
	}

	static ArrayList<String> marcaAuto;

	public void loadMarcaAuto() {
		marcaAuto = new ArrayList<String>();

		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cursor = vreau_rca
					.rawQuery(
							"Select Nume from vehicule_marci",
							null);
			cursor.moveToFirst();
			do {
				String cls = cursor.getString(0);
				marcaAuto.add(cls);

			} while (cursor.moveToNext());
			cursor.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();
	}

	static ArrayList<String> localitati;

	public void getLocalitatiById(int id) {
		localitati = new ArrayList<String>();
		localitati.clear();
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cursor = vreau_rca.rawQuery(
					"select localitate from localitati where cod_judet=" + id
							+ " order by localitate", null);
			cursor.moveToFirst();
			do {
				String cls = cursor.getString(0);
				localitati.add(cls);
			} while (cursor.moveToNext());
			cursor.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();

	}

	ArrayList<YTOObiectAsigurat> object;
	static ArrayList<String> idIntern;
	static ArrayList<String> tipObject;
	static ArrayList<String> JSONtext;

	public void loadObiecte() {
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		object = new ArrayList<YTOObiectAsigurat>();
		object.clear();
		try {
			cursor = vreau_rca
					.rawQuery(
							"SELECT IdIntern , TipObiect , JSONText FROM ObiectAsigurat ;",
							null);
			cursor.moveToFirst();
			do {
				YTOObiectAsigurat cls = new YTOObiectAsigurat(cursor.getString(0),
						cursor.getString(1), cursor.getString(2));
				object.add(cls);
			} while (cursor.moveToNext());
			cursor.close();
			idIntern = YTOObiectAsigurat.getIdIntern(object);
			tipObject = YTOObiectAsigurat.getTipObiect(object);
			JSONtext = YTOObiectAsigurat.getJSONText(object);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		close();
	}

	public void deleteObiectAsigurat(String idIntern) {
		try {
			open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vreau_rca.delete("ObiectAsigurat", "IdIntern=\"" + idIntern + "\"",
				null);
		close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
