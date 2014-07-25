package com.stern.Asigurare;

import org.json.JSONException;
import org.json.JSONObject;

public class YTOAutovehicul {
	String idIntern;
	String judet;
	String localitate;
	int	   categorieAuto;
	String subcategorieAuto;
	String nrInmatriculare;
	String serieSasiu;
	String marcaAuto;
	String modelAuto;
	int	   cm3;
	int    nrLocuri;
	int    masaMaxima;
	int    putere;
	int    anFabricatie;
	String destinatieAuto;
	int    marimeParc;
	String serieCiv;
	int    dauneInUltimulAn;
	int    aniFaraDaune;
	String culoare;
	String combustibil;
	String tipInregistrare;
	String autoNouInregistrat;
	String inLeasing;
	String firmaLeasing;
	int    nrKm;
	String cascoLa;
	String idFirmaLeasing;
	String idProprietar;
	String idImage;
	String _dataCreare;
	Boolean isDirty;
	
	public YTOAutovehicul () {
		this.idIntern="";
		this.judet="";
		this.localitate="";
		this.categorieAuto=-1;
		this.subcategorieAuto="";
		this.nrInmatriculare="";
		this.serieSasiu="";
		this.marcaAuto="";
		this.modelAuto="";
		this.cm3=-1;
		this.nrLocuri=-1;
		this.masaMaxima=-1;
		this.putere=-1;
		this.destinatieAuto="";
		this.marimeParc=-1;
		this.serieCiv="";
		this.anFabricatie=-1;
		this.dauneInUltimulAn=-1;
		this.aniFaraDaune=-1;
		this.culoare="";
		this.combustibil="";
		this.tipInregistrare="";
		this.autoNouInregistrat="";
		this.inLeasing="";
		this.firmaLeasing="";
		this.nrKm=0;
		this.cascoLa="";
		this.idFirmaLeasing="";
		this.idProprietar="";
		this.idImage="";
		this._dataCreare="";
		this.isDirty=false;
	}
	
	public String autovehiculToJSON (YTOAutovehicul autovehicul)
	{
		JSONObject object = new JSONObject();
		try {
			object.put("judet",(autovehicul.judet !=null ? autovehicul.judet : ""));
			object.put("localitate",(autovehicul.localitate !=null ? autovehicul.localitate : ""));
			object.put("categorie_auto",(autovehicul.categorieAuto !=-1 ? autovehicul.categorieAuto : "-1"));
			object.put("subcategorie_auto",(autovehicul.subcategorieAuto !=null ? autovehicul.subcategorieAuto : ""));
			object.put("nr_inmatriculare",(autovehicul.nrInmatriculare !=null ? autovehicul.nrInmatriculare : ""));
			object.put("serie_sasiu",(autovehicul.serieSasiu !=null ? autovehicul.serieSasiu : ""));
			object.put("marca_auto",(autovehicul.marcaAuto !=null ? autovehicul.marcaAuto : ""));
			object.put("model_auto",(autovehicul.modelAuto !=null ? autovehicul.modelAuto : ""));
			object.put("cm3",(autovehicul.cm3 !=-1 ? autovehicul.cm3 : "-1"));
			object.put("nr_locuri",(autovehicul.nrLocuri !=-1 ? autovehicul.nrLocuri : "-1"));
			object.put("masa_maxima",(autovehicul.masaMaxima !=-1 ? autovehicul.masaMaxima : "-1"));
			object.put("putere",(autovehicul.putere !=-1 ? autovehicul.putere : "-1"));
			object.put("an_fabricatie",(autovehicul.anFabricatie !=-1 ? autovehicul.anFabricatie : "-1"));
			object.put("destinatie_auto",(autovehicul.destinatieAuto !=null ? autovehicul.destinatieAuto : ""));
			object.put("marime_parc",(autovehicul.marimeParc !=-1 ? autovehicul.marimeParc : "-1"));
			object.put("serie_civ",(autovehicul.serieCiv !=null ? autovehicul.serieCiv : ""));
			object.put("daune_ultimul_an",(autovehicul.dauneInUltimulAn !=-1 ? autovehicul.dauneInUltimulAn : "-1"));
			object.put("ani_fara_daune",(autovehicul.aniFaraDaune !=-1 ? autovehicul.aniFaraDaune : "-1"));
			object.put("culoare",(autovehicul.culoare !=null ? autovehicul.culoare : ""));
			object.put("combustibil",(autovehicul.combustibil !=null ? autovehicul.combustibil : ""));
			object.put("tip_inregistrare",(autovehicul.tipInregistrare !=null ? autovehicul.tipInregistrare : ""));
			object.put("auto_nou_inregistrat",(autovehicul.autoNouInregistrat !=null ? autovehicul.autoNouInregistrat : ""));
			object.put("in_leasing",(autovehicul.inLeasing !=null ? autovehicul.inLeasing : ""));
			object.put("firma_leasing",(autovehicul.firmaLeasing !=null ? autovehicul.firmaLeasing : ""));
			object.put("nr_km",(autovehicul.nrKm !=-1 ? autovehicul.nrKm : "-1"));
			object.put("casco",(autovehicul.cascoLa !=null ? autovehicul.cascoLa : ""));
			object.put("id_firma_leasing",(autovehicul.idFirmaLeasing !=null ? autovehicul.idFirmaLeasing : ""));
			object.put("id_proprietar",(autovehicul.idProprietar !=null ? autovehicul.idProprietar : ""));
			object.put("id_image",(autovehicul.idImage !=null ? autovehicul.idImage : ""));
			object.put("data_creare",(autovehicul._dataCreare !=null ? autovehicul._dataCreare : ""));
			
			
		} catch (JSONException e) {
	e.printStackTrace();
	}
		return object.toString();
	}

	public YTOAutovehicul autovehiculFromJSON (YTOAutovehicul autovehicul, String JSONText)
	{
		JSONObject mainObject;
		try {
			mainObject = new JSONObject(JSONText);
			autovehicul.judet = mainObject.getString("judet");
			autovehicul.localitate = mainObject.getString("localitate");
			autovehicul.categorieAuto = Integer.parseInt(mainObject.getString("categorie_auto"));
			autovehicul.subcategorieAuto = mainObject.getString("subcategorie_auto");
			autovehicul.nrInmatriculare = mainObject.getString("nr_inmatriculare");
			autovehicul.serieSasiu = mainObject.getString("serie_sasiu");
			autovehicul.marcaAuto = mainObject.getString("marca_auto");
			autovehicul.modelAuto = mainObject.getString("model_auto");
			autovehicul.cm3 =  Integer.parseInt(mainObject.getString("cm3"));
			autovehicul.nrLocuri = Integer.parseInt(mainObject.getString("nr_locuri"));
			autovehicul.masaMaxima = Integer.parseInt(mainObject.getString("masa_maxima"));
			autovehicul.putere = Integer.parseInt(mainObject.getString("putere"));
			autovehicul.anFabricatie = Integer.parseInt(mainObject.getString("an_fabricatie"));
			autovehicul.destinatieAuto = mainObject.getString("destinatie_auto");
			autovehicul.marimeParc = Integer.parseInt(mainObject.getString("marime_parc"));
			autovehicul.serieCiv = mainObject.getString("serie_civ");
			autovehicul.dauneInUltimulAn = Integer.parseInt(mainObject.getString("daune_ultimul_an"));
			autovehicul.aniFaraDaune = Integer.parseInt(mainObject.getString("ani_fara_daune"));
			autovehicul.culoare = mainObject.getString("culoare");
			autovehicul.combustibil = mainObject.getString("combustibil");
			autovehicul.tipInregistrare = mainObject.getString("tip_inregistrare");
			autovehicul.autoNouInregistrat = mainObject.getString("auto_nou_inregistrat");
			autovehicul.inLeasing = mainObject.getString("in_leasing");
			autovehicul.firmaLeasing = mainObject.getString("firma_leasing");
			autovehicul.nrKm = Integer.parseInt(mainObject.getString("nr_km"));
			autovehicul.cascoLa = mainObject.getString("casco");
			autovehicul.idFirmaLeasing = mainObject.getString("id_firma_leasing");
			autovehicul.idProprietar = mainObject.getString("id_proprietar");
			autovehicul.idImage = mainObject.getString("id_image");
			autovehicul._dataCreare = mainObject.getString("data_creare");
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return autovehicul;

	}
	
	public boolean isValidForRCA ()
	{
		boolean valid=true;
		if (YTOAutovehicul.this.judet.equals(""))
			valid = false;
		if (YTOAutovehicul.this.localitate.equals(""))
			valid = false;
		if (YTOAutovehicul.this.categorieAuto == -1 ||YTOAutovehicul.this.categorieAuto == 0)
			valid = false;
		if (YTOAutovehicul.this.subcategorieAuto.equals(""))
			valid = false;
		if (YTOAutovehicul.this.nrInmatriculare.equals(""))
			valid = false;
		if (YTOAutovehicul.this.serieSasiu.equals("") || YTOAutovehicul.this.serieSasiu.length()<3 || YTOAutovehicul.this.serieSasiu.length()>17)
			valid = false;
		if (YTOAutovehicul.this.marcaAuto.equals(""))
			valid = false;
		if (YTOAutovehicul.this.categorieAuto == -1 || (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.cm3<=0))
			valid = false;
		if (YTOAutovehicul.this.categorieAuto == -1 || YTOAutovehicul.this.categorieAuto == 0 || (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.nrLocuri==0))
			valid = false;
		if (YTOAutovehicul.this.masaMaxima == -1 || YTOAutovehicul.this.categorieAuto == 0)
			valid = false;
		if (YTOAutovehicul.this.putere == -1 || (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.putere<=0))
			valid = false;
		if (YTOAutovehicul.this.anFabricatie == -1 || YTOAutovehicul.this.anFabricatie==0)
			valid = false;
			return valid;
	}
	
	public float completedPercent ()
	{
		int nrCampuri = 13;
		float campuriCompletate = 0;
		if (YTOAutovehicul.this.judet.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.localitate.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.categorieAuto>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.subcategorieAuto.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.nrInmatriculare.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.serieSasiu.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.marcaAuto.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.modelAuto.length()>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.cm3>0)
			campuriCompletate++;
		else 
			campuriCompletate++;
		if (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.nrLocuri>0)
			campuriCompletate++;
		else 
			campuriCompletate++;
		if (YTOAutovehicul.this.masaMaxima>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.anFabricatie>0)
			campuriCompletate++;
		if (YTOAutovehicul.this.categorieAuto!=8 && YTOAutovehicul.this.putere>0)
			campuriCompletate++;
		else campuriCompletate++;
		if (YTOAutovehicul.this.inLeasing.equals("da"))
			{
				nrCampuri = 14;
				if (YTOAutovehicul.this.firmaLeasing.length()>0)
					campuriCompletate++;
			}
		return campuriCompletate/nrCampuri;
	}
	
	
}
