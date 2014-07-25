package com.stern.Asigurare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class GetObiecte {
	
	public static ArrayList<YTOPersoana> persoane;
	public static ArrayList<YTOLocuinta> locuinte;
	public static ArrayList<YTOPersoana> persoaneNu;
	public static ArrayList<YTOAutovehicul> autovehicule;
	public static ArrayList<YTOOferta> asigurari;
	public static ArrayList<YTOAlerta> alerte;
	public static ArrayList<YTOAlerta> alertebyid;
	
	static String link = "http://rc.i-crm.ro/maasigurapi/";//rc

	//static String link = "http://beta.i-crm.ro/maasigurapi/";//beta
	//static String link = "http://192.168.1.103:8082/"; // Edi
	
	static String SENDER_ID = "1074787013996";
	static String deviceToken = "";
	static int numberOfNewNotification = 0;
	
	static YTOPersoana persoanaFizica = new YTOPersoana();
	static YTOPersoana persoanaJuridica = new YTOPersoana();
	
	public static void getDate (DatabaseConnector dbConnector)
	{
		dbConnector.loadJudete();
		dbConnector.loadMarcaAuto();
		dbConnector.loadCodCaen();
	}
	
	public static void getPersoane (DatabaseConnector dbConnector)
	{
		dbConnector.checkCursor(1);
	if (DatabaseConnector.de)
	{
	try{						
		dbConnector.loadObiecte();
	}
	catch(Exception ex) {
		ex.printStackTrace();
		//dbConnector.close();
	}
	persoane = new ArrayList<YTOPersoana>();
	persoane.clear();
	persoaneNu = new ArrayList<YTOPersoana>();
	persoaneNu.clear();
	int j=-1;//numara numarul de persoane care trebuiesc adaugate
	for (int i= 0; i<DatabaseConnector.idIntern.size(); i++)
		{
		if (DatabaseConnector.tipObject.get(i).equals("1"))
			{
				j++;//se incrementeaza cu 1 in cazul in care gaseste o persoana
				YTOPersoana pers = new YTOPersoana();
				pers = pers.persoanaFromJSON(pers, DatabaseConnector.JSONtext.get(i));
				pers.idIntern = DatabaseConnector.idIntern.get(i);
				pers.isDirty=true;
				persoane.add(pers);
				if (GetObiecte.persoane.get(j).proprietar.equals("nu"))
				{
					persoaneNu.add(persoane.get(j));
				}
				else 
				{
					if (persoane.get(j).tipPersoana.equals("fizica"))
					{
						persoanaFizica=persoane.get(j);
					}
						
					else 
					{
						persoanaJuridica=persoane.get(j);
					}
				}
				}
		}
	}
	else 
	{
		persoane = new ArrayList<YTOPersoana>();
		persoaneNu = new ArrayList<YTOPersoana>();
	}
}
	
	public static void getLocuinte (DatabaseConnector dbConnector)
	{
		dbConnector.checkCursor(3);
	if (DatabaseConnector.de)
	{
	try{						
		dbConnector.loadObiecte();
	}
	catch(Exception ex) {
		ex.printStackTrace();
		//dbConnector.close();
	}
	locuinte = new ArrayList<YTOLocuinta>();
	locuinte.clear();
	int j=-1;//numara numarul de locuinte care trebuiesc adaugate
	for (int i= 0; i<DatabaseConnector.idIntern.size(); i++)
		{
		if (DatabaseConnector.tipObject.get(i).equals("3"))
		{
			j++;//se incrementeaza cu 1 in cazul in care gaseste o locuinta si trebuie adaugata in lista,incepe de la 0
			YTOLocuinta locuinta = new YTOLocuinta();
			locuinta = locuinta.locuintaFromJSON(locuinta, DatabaseConnector.JSONtext.get(i));
			locuinta.idIntern = DatabaseConnector.idIntern.get(i);
			locuinta.isDirty=true;
			locuinte.add(locuinta);
		}
		}
	}
		
	else 
	{
		locuinte = new ArrayList<YTOLocuinta>();
	}
}
	
	
	public static void getAutovehicule (DatabaseConnector dbConnector)
	{
		dbConnector.checkCursor(2);
	if (DatabaseConnector.de)
	{
	try{						
		dbConnector.loadObiecte();
	}
	catch(Exception ex) {
		ex.printStackTrace();
		//dbConnector.close();
	}
	autovehicule = new ArrayList<YTOAutovehicul>();
	autovehicule.clear();
	int j=-1;//numara numarul de masini care trebuiesc adaugate
	for (int i= 0; i<DatabaseConnector.idIntern.size(); i++)
		{
		if (DatabaseConnector.tipObject.get(i).equals("2"))
			{
				j++;//se incrementeaza cu 1 in cazul in care gaseste o masina si trebuie adaugata in lista,incepe de la 0
				YTOAutovehicul autovehicul = new YTOAutovehicul();
				autovehicul = autovehicul.autovehiculFromJSON(autovehicul, DatabaseConnector.JSONtext.get(i));
				autovehicul.idIntern = DatabaseConnector.idIntern.get(i);
				autovehicul.isDirty=true;
				autovehicule.add(autovehicul);
			}
		}
	}	
	else 
	{
		autovehicule = new ArrayList<YTOAutovehicul>();
	}
	
}
	public static void getAsigurari (DatabaseConnector dbConnector)
	{
		dbConnector.checkCursor(4);
	if (DatabaseConnector.de)
	{
	try{						
		dbConnector.loadObiecte();
	}
	catch(Exception ex) {
		ex.printStackTrace();
		//dbConnector.close();
	}
	asigurari = new ArrayList<YTOOferta>();
	asigurari.clear();
	int j=-1;//numara numarul de asigurari care trebuiesc adaugate
	for (int i= 0; i<DatabaseConnector.idIntern.size(); i++)
		{
		if (DatabaseConnector.tipObject.get(i).equals("4"))
		{
			j++;//se incrementeaza cu 1 in cazul in care gaseste o asigurare si trebuie adaugata in lista,incepe de la 0
			YTOOferta asigurare = new YTOOferta();
			asigurare = asigurare.ofertaFromJSON(DatabaseConnector.JSONtext.get(i));
			asigurare.idIntern = DatabaseConnector.idIntern.get(i);
			asigurari.add(asigurare);
		}
		}
	}
		
	else 
	{
		asigurari = new ArrayList<YTOOferta>();
	}
}
	
	public static void getAlerte (DatabaseConnector dbConnector)
	{
		dbConnector.checkCursor(5);
	if (DatabaseConnector.de)
	{
	try{						
		dbConnector.loadObiecte();
	}
	catch(Exception ex) {
		ex.printStackTrace();
		//dbConnector.close();
	}
	alerte = new ArrayList<YTOAlerta>();
	alerte.clear();
	if (alertebyid!=null)
		alertebyid.clear();
	int j=-1;//numara numarul de alerte care trebuiesc adaugate
	for (int i= 0; i<DatabaseConnector.idIntern.size(); i++)
		{
		if (DatabaseConnector.tipObject.get(i).equals("5"))
		{
			j++;//se incrementeaza cu 1 in cazul in care gaseste o alerta si trebuie adaugata in lista,incepe de la 0
			YTOAlerta alerta = new YTOAlerta();
			alerta = alerta.alertaFromJSON(DatabaseConnector.JSONtext.get(i));
			alerta.idIntern = DatabaseConnector.idIntern.get(i);
			alerte.add(alerta);
		}
		}
	Collections.sort(alerte, new Comparator<YTOAlerta>(){							 
		@Override
		public int compare(YTOAlerta object1, YTOAlerta object2) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Calendar c = Calendar.getInstance();
			//String dataSetata = sdf.format(mDay+"."+mMonth+"."+mYear);
			try {
				object1.date = sdf.parse(object1.dataAlerta);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				object2.date = sdf.parse(object2.dataAlerta);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            return  object1.date.compareTo(object2.date);								
		}				 
    });
	}
		
	else 
	{
		alerte = new ArrayList<YTOAlerta>();
	}
}
	
	public static void getAlerteByIdObiect(String idObiect)
	{
		alertebyid = new ArrayList<YTOAlerta>();
		if (alerte.size()!=0)
		{
			for (int i=0;i<alerte.size();i++)
				if (alerte.get(i).idObiect.equals(idObiect))
					alertebyid.add(alerte.get(i));
		}
	}
	
	public static int getNrAlerteFromInterval()
	{
		int nr=0;
		boolean setScroll = false;
		if (alerte==null) return 0;
		if (alerte.size()==0) return 0;
		for (int i= 0; i<GetObiecte.alerte.size(); i++)
		{
			YTOAlerta alerta = new YTOAlerta();
			alerta = GetObiecte.alerte.get(i);
			Date date1=null,date2 = null,date3=null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Calendar c = Calendar.getInstance();
			String dataCurenta = sdf.format(c.getTime());
			
			c.add(Calendar.DATE, -15);  // number of days to add
			String dataMinima = sdf.format(c.getTime());
			c.add(Calendar.DATE, 30);
			String dataMaxima = sdf.format(c.getTime());
			try {
				date1 = sdf.parse(dataMinima);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				date2 = sdf.parse(alerta.dataAlerta);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				date3 = sdf.parse(dataMaxima);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (date1.before(date2) && date2.before(date3)) {
				nr++;
				if (!setScroll){
					VeziAlerte.scrollToHere = i;
					setScroll = true;
				}
			}
		}
		return nr;
	}

	

}
