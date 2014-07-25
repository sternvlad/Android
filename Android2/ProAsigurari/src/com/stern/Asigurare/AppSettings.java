package com.stern.Asigurare;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {

	SharedPreferences prefs;
	Context context;

	public static AppSettings get(Context context) {
		AppSettings sett = new AppSettings(context);
		sett.context = context;
		return sett;
	}

	private AppSettings(Context context) {
		prefs = context.getApplicationContext().getSharedPreferences(
				"AppSettings", Context.MODE_PRIVATE);
	}

	public String getDeviceId() {
		return prefs.getString("DeviceID", "");
	}

	public void updateDeviceID(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("DeviceID", value);
		edit.commit();
	}

	public int getIndexMenu() {
		return prefs.getInt("MenuPressed", 0);
	}

	public void updateIndexMenu(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("MenuPressed", index);
		edit.commit();
	}

	public int getFirstTime() {
		return prefs.getInt("FirstTime", 1);
	}

	public void updateFirstTime(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("FirstTime", index);
		edit.commit();
	}

	public int getNumberOfNewNotifications() {
		return prefs.getInt("NotificationsNumber", 0);
	}

	public void updateNumberOfNewNotifications(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("NotificationsNumber", index);
		edit.commit();
	}

	public int getRatedApp() {
		return prefs.getInt("RatedApp", 0);
	}

	public void updateRatedApp(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("RatedApp", index);
		edit.commit();
	}

	public int getSync() {
		return prefs.getInt("Sync", 1);
	}

	public void updateSync(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("Sync", index);
		edit.commit();
	}

	public int getEventsUntilPrompt() {
		return prefs.getInt("Events", 2);
	}

	public void updateEventsUntilPrompt(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("Events", index);
		edit.commit();
	}

	public int getUsesUntilPrompt() {
		return prefs.getInt("Uses", 2);
	}

	public void updateUsesUntilPrompt(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("Uses", index);
		edit.commit();
	}

	public int getShouldPrompt() {
		return prefs.getInt("ShouldPrompt", 1);
	}

	public void updateShouldPrompt(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("ShouldPrompt", index);
		edit.commit();
	}

	public int getTokenSent() {
		return prefs.getInt("TokenSent", 0);
	}

	public void updateTokenSent(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("TokenSent", index);
		edit.commit();
	}

	public int getOpenNotification() {
		return prefs.getInt("AutoOpen", 0);
	}

	public void updateOpenNotification(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("AutoOpen", index);
		edit.commit();
	}

	public String getString(String key, String value) {
		return prefs.getString(key, value);
	}

	public void updateString(String key, String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public String getTelefonComanda() {
		return prefs.getString("TelefonComanda", "");
	}

	public void updateTelefonComanda(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TelefonComanda", value);
		edit.commit();
	}

	public String LanguageWasChanged() {
		return prefs.getString("LanguageWasChanged", "");
	}

	public void updateLanguageWasChanged(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("LanguageWasChanged", value);
		edit.commit();
	}

	public String getUsername() {
		return prefs.getString("Username", "");
	}

	public void updateUsername(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Username", value);
		edit.commit();
	}

	public String getPassword() {
		return prefs.getString("Password", "");
	}

	public void updatePassword(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Password", value);
		edit.commit();
	}

	public String getPushToken() {
		return prefs.getString("PushToken", "");
	}

	public void updatePushToken(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("PushToken", value);
		edit.commit();
	}

	public String getScopCalatorie() {
		return prefs.getString("ScopCalatorie", "");
	}

	public void updateScopCalatorie(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("ScopCalatorie", value);
		edit.commit();
	}

	public String getTaraDestinatie() {
		return prefs.getString("TaraDestinatie", "Turcia");
	}

	public void updateTaraDestinatie(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TaraDestinatie", value);
		edit.commit();
	}

	public String getSumaAsigurataCalatorie() {
		return prefs.getString("SumaAsigurataCalatorie", "");
	}

	public void updateSumaAsigurataCalatorie(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("SumaAsigurataCalatorie", value);
		edit.commit();
	}

	public int getNrZileCalatorie() {
		return prefs.getInt("NumarZile", 5);
	}

	public void updateNrZileCalatorie(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("NumarZile", index);
		edit.commit();
	}

	public int getRcaNrLuni() {
		return prefs.getInt("RcaNrLuni", 6);
	}

	public void updateRcaNrLuni(int index) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("RcaNrLuni", index);
		edit.commit();
	}

	public String getCodRaspuns() {
		return prefs.getString("CodRaspuns", "");
	}

	public void updateCodRaspuns(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("CodRaspuns", value);
		edit.commit();
	}

	public String getMoneda() {
		return prefs.getString("Moneda", "");
	}

	public void updateMoneda(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Moneda", value);
		edit.commit();
	}

	public String getNume() {
		return prefs.getString("Nume", "");
	}

	public void updateNume(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Nume", value);
		edit.commit();
	}

	public String getTipProdus() {
		return prefs.getString("TipProdus", "");
	}

	public void updateTipProdus(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TipProdus", value);
		edit.commit();
	}

	public String getOperaor() {
		return prefs.getString("Operaor", "");
	}

	public void updateOperaor(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Operaor", value);
		edit.commit();
	}

	public String getTitluGroup1() {
		return prefs.getString("TitluGroup1", "");
	}

	public void updateTitluGroup1(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TitluGroup1", value);
		edit.commit();
	}

	public String getLink() {
		return prefs.getString("Link", "");
	}

	public void updateLink(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Link", value);
		edit.commit();
	}

	public String getTitluGroup2() {
		return prefs.getString("TitluGorup2", "");
	}

	public void updateTitluGroup2(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TitluGorup2", value);
		edit.commit();
	}

	public String getTitluGroup4() {
		return prefs.getString("TitluGroup4", "");
	}

	public void updateTitluGroup4(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TitluGroup4", value);
		edit.commit();
	}

	public String getJudContact() {
		return prefs.getString("JudContact", "");
	}

	public void updateJudContact(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("JudContact", value);
		edit.commit();
	}

	public String getLocalitate() {
		return prefs.getString("Localitate", "");
	}

	public void updateLocalitate(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Localitate", value);
		edit.commit();
	}

	public String getTipPlata() {
		return prefs.getString("TipPlata", "");
	}

	public void updateTipPlata(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("TipPlata", value);
		edit.commit();
	}

	public String getMesajEroare() {
		return prefs.getString("MesajEroare", "");
	}

	public void updateMesajEroare(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("MesajEroare", value);
		edit.commit();
	}

	public String getEmailContact() {
		return prefs.getString("EmailContact", "");
	}

	public void updateEmailContact(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("EmailContact", value);
		edit.commit();
	}

	public String getInstalledDate() {
		return prefs.getString("InstalledDate", "");
	}

	public void updateInstalledDate(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("InstalledDate", value);
		edit.commit();
	}

	public String getStrada() {
		return prefs.getString("Strada", "");
	}

	public void updateStrada(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Strada", value);
		edit.commit();
	}

	public String getPrima() {
		return prefs.getString("Prima", "");
	}

	public void updatePrima(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Prima", value);
		edit.commit();
	}

	public String getCompanie() {
		return prefs.getString("Companie", "");
	}

	public void updateCompanie(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Companie", value);
		edit.commit();
	}

	public String getIdIntern() {
		return prefs.getString("IdIntern", "");
	}

	public void updateIdIntern(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("IdIntern", value);
		edit.commit();
	}

	public String getLanguage() {
		return prefs.getString("Language", "ro");
	}

	public void updateLanguage(String value) {
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("Language", value);
		edit.commit();
	}

}
