package com.stern.Asigurare;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

public class YTOUtils {
	
	public final static boolean verificaCNP(String cnp) {	    
		if (cnp.length() != 13) {			
			return false;
			}
		String cod[] = cnp.split("");
		int suma = Integer.parseInt(cod[1])*2+Integer.parseInt(cod[2])*7+Integer.parseInt(cod[3])*9+Integer.parseInt(cod[4])*1+Integer.parseInt(cod[5])*4+Integer.parseInt(cod[6])*6+Integer.parseInt(cod[7])*3+Integer.parseInt(cod[8])*5+Integer.parseInt(cod[9])*8+Integer.parseInt(cod[10])*2+Integer.parseInt(cod[11])*7+Integer.parseInt(cod[12])*9;
		int rest = suma%11;
		int last = Integer.parseInt(cod[13]);
		if (!((rest<10) && (rest == last) ||
				(rest == 10) && (last == 1))) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String replaceInEmail (String str)
	 {
		 String [] listGmail = {"@gmail.coml",
        "@gmal.com", "@gmail.cim", "@gmail.col", "@gmail.con","@gmai.com", "@gma.com", "@gmail.vom","@gmail.cpm"};
		 String [] listYahoo = {"@yahoo.con",
                "@yagoo.com", "@yaahoo.com", "@yahoo.cim", "@yah00.com", "@yahoo.rom", "@yakoo.com",
                "@yahoo.cm", "@yahoo..com", "@yah.com", "@yahu.com", "@yaho.coj", "@yaoo.com", "@yahoo.om", "@yhoo.com",
                "@ya.com", "@yaho.com", "@yahho.com", "@yahoo.vom", "@yahop.com", "@yahh.com", "@yah00.vcom", "@yahol.com", "yahoo.cpm"};
		 if (str.indexOf('@')!=-1){
			 String mail = "";
			 mail = str.substring(str.indexOf('@'));
			 for (int i=0;i<listGmail.length;i++)
				 if (mail.equals(listGmail[i]))
						 {
					 		mail="@gmail.com";
					 		break;
						 }
			 for (int i=0;i<listYahoo.length;i++)
				 if (mail.equals(listYahoo[i]))
						 {
					 		mail="@yahoo.com";
					 		break;
						 }
			 str = str.replace (str.substring((str.indexOf("@"))),mail);
		 }
		 return str;
	 }

	
    public static String replaceInSerieSasiu (String str)
    {
    	str = str.replace('O', '0');
    	str = str.replace('I', '1');
    	return str;
    }
    
    public static String replaceDiacritice (String str)
    {
    	str = str.replace("ă","a");
    	str = str.replace("â","a");
    	str = str.replace("î","i");
    	str = str.replace("ş","s");
    	str = str.replace("ţ","t");
    	
    	str = str.replace("Ă","A");
    	str = str.replace("Â","A");
    	str = str.replace("Î","I");
    	str = str.replace("Ş","S");
    	str = str.replace("Ţ","T");
    	return str;
    }
    
    public static Boolean verifyCUI(String cui) {
    	String constanta = "235712357";
    	if (cui.length() > 10 || cui.length()==0) return false;
    	int suma = 0;
    	cui = new StringBuilder(cui).reverse().toString();
    	for (int  i = 1; i < cui.length(); i++) {
    	int s = (cui.charAt(i)-48) * (constanta.charAt(i-1)-48);
    	suma += s;
    	}
    	int rest = (suma * 10) % 11;
    	if (rest == (cui.charAt(0)-48) || (rest == 10 && (cui.charAt(0)-48) == 0))
    	return true;
    	else
    	return false;
    	}
    
	public static boolean eMailValidation(String emailstring)
    {
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

	public static boolean isTablet (Context context) {
        // TODO: This hacky stuff goes away when we allow users to target devices
        int xlargeBit = 4; // Configuration.SCREENLAYOUT_SIZE_XLARGE;  // upgrade to HC SDK to get this
        Configuration config = context.getResources().getConfiguration();
        return (config.screenLayout & xlargeBit) == xlargeBit;
    }
	
	public static YTOPersoana getPersoanaByIdIntern(String idIntern)
	{
		YTOPersoana persoana = new YTOPersoana();
		for (int i=0;i<GetObiecte.persoane.size();i++)
			if (GetObiecte.persoane.get(i).idIntern.equals(idIntern))
			{
				persoana = GetObiecte.persoane.get(i);
				break;
			}
		return persoana;
	}
	
	public static String getDeviceName() {
		  String manufacturer = Build.MANUFACTURER;
		  String model = Build.MODEL;
		  if (model.startsWith(manufacturer)) {
		    return capitalize(model);
		  } else {
		    return capitalize(manufacturer) + " " + model;
		  }
		}


		private static String capitalize(String s) {
		  if (s == null || s.length() == 0) {
		    return "";
		  }
		  char first = s.charAt(0);
		  if (Character.isUpperCase(first)) {
		    return s;
		  } else {
		    return Character.toUpperCase(first) + s.substring(1);
		  }
		} 
		
		
	public static String setAnPermis (String anPermis)
	{
		String dataPermis = "";
		try{
			if (Integer.parseInt(anPermis) == Calendar.getInstance().get(Calendar.YEAR))
			{
				dataPermis =  (Calendar.getInstance().get(Calendar.MONTH)+1) +"-" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1) + "-" + Calendar.getInstance().get(Calendar.YEAR);				
			}else{
				dataPermis = "01-01-"+anPermis;
			}
		}catch (Exception e)
		{
			dataPermis = "01-01-"+Calendar.getInstance().get(Calendar.YEAR);
		}
		return dataPermis;
	}
	
	public static Boolean isWeekend ()
	{
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(Calendar.DAY_OF_WEEK));
		System.out.println(f.format(c.getTime()));
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		
		Date date = new Date();// given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		
		if (weekDay == 7 || weekDay == 1 )
			return true;
		if (weekDay == 6 && hour >= 16)
			return true;
		if (weekDay == 2 && hour < 6)
			return true;
			
		
		return false;
	}

}
