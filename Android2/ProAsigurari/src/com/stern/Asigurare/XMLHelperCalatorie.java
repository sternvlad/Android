package com.stern.Asigurare;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperCalatorie extends DefaultHandler {
	String currentValue = null;	
	static StringBuilder tempSB = new StringBuilder();	
	private RaspunsCalatorie myOffer = null;
	private ArrayList<RaspunsCalatorie> oferteCalatorie = new ArrayList<RaspunsCalatorie>();
	public ArrayList<RaspunsCalatorie> getParsedData() {
        return this.oferteCalatorie;
	}
	
	@Override
	public void startDocument() throws SAXException {
		this.myOffer = new RaspunsCalatorie();
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {		
			currentValue = new String();
		} else if (localName.equals("Body")) {			
			currentValue = new String();
		} else if (localName.equals("CallCalculTravelResponse")) {	
			currentValue = new String();
		} else if (localName.equals("CallCalculTravelResult")) {	
			currentValue = new String();
		} else if (localName.equals("ResponsePrima")) {	
			currentValue = new String();
			myOffer = new RaspunsCalatorie();
		} else if (localName.equals("Eroare_ws")) {	
			currentValue = new String();
		} else if (localName.equals("ImageUrl")) {	
			currentValue = new String();
		} else if (localName.equals("Prima")) {	
			 tempSB.delete(0, tempSB.length());
		} else if (localName.equals("PrimaRON")) {	
			currentValue = new String();
		} else if (localName.equals("Companie")) {	
			currentValue = new String();
		} else if (localName.equals("Cod")) {	
			currentValue = new String();
		} else if (localName.equals("Reducere")) {	
			currentValue = new String();
		} else if (localName.equals("PrimaReducere")) {	
			 tempSB.delete(0, tempSB.length());
		} else if (localName.equals("ProcentReducere")) {	
			currentValue = new String();
		} else if (localName.equals("SumaAsigurata")) {	
			currentValue = new String();
		} else if (localName.equals("Moneda")) {	
			currentValue = new String();
		} else if (localName.equals("Fransiza")) {	
			currentValue = new String();
		} else if (localName.equals("TipProdus")) {	
			currentValue = new String();
		} else if (localName.equals("SABagaje")) {	
			currentValue = new String();
		} else if (localName.equals("SAEei")) {	
			currentValue = new String();
		} else if (localName.equals("AcoperireSportAgrement")) {	
			currentValue = new String();
		} else if (localName.equals("LinkConditii")) {	
			currentValue = new String();
		} else if (localName.equals("Informatii")) {	
			currentValue = new String();
		} else if (localName.equals("ConditiiHint")) {	
			tempSB.delete(0, tempSB.length());
		} else if (localName.equals("IdReducere")) {
			currentValue = new String();
		} else if (localName.equals("PromoVodafone")) {
			currentValue = new String();
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {			
		} else if (localName.equals("Body")) {			
		} else if (localName.equals("CallCalculTravelResponse")) {		
		} else if (localName.equals("CallCalculTravelResult")) {	
		} else if (localName.equals("ResponsePrima")) {
			oferteCalatorie.add(myOffer);
		} else if (localName.equals("Eroare_ws")) {	
			myOffer.setEroare_ws(currentValue);
		} else if (localName.equals("ImageUrl")) {	
		} else if (localName.equals("Prima")) {
			myOffer.setPrima(Double.parseDouble(tempSB.toString()));
		} else if (localName.equals("PrimaRON")) {
			myOffer.setPrimaRON(currentValue);
		} else if (localName.equals("Companie")) {
			myOffer.setCompanie(currentValue);
		} else if (localName.equals("Cod")) {
			myOffer.setCodOferta(currentValue);
		}  else if (localName.equals("Reducere")) {
			if (currentValue.equals("true"))  myOffer.setReducere(true);
			else myOffer.setReducere(false);
		} else if (localName.equals("PrimaReducere")) {
			myOffer.setPrimaReducere(Double.parseDouble(tempSB.toString()));
		} else if (localName.equals("ProcentReducere")) {
			myOffer.setProcentReducere(currentValue);
		} else if (localName.equals("SumaAsigurata")) {
			myOffer.setSumaAsigurata(currentValue);
		} else if (localName.equals("Moneda")) {
			myOffer.setMoneda(currentValue);
		} else if (localName.equals("Fransiza")) {
			myOffer.setFransiza(currentValue);
		} else if (localName.equals("TipProdus")) {
			myOffer.setTipProdus(currentValue);
		} else if (localName.equals("SABagaje")) {
			myOffer.setSaBagaje(currentValue);
		} else if (localName.equals("SAEei")) {
			myOffer.setSaEei(currentValue);
		} else if (localName.equals("AcoperireSportAgrement")) {
			myOffer.setAcoperireSport(currentValue);
		} else if (localName.equals("LinkConditii")) {
			myOffer.setLinkConditii(currentValue);
		} else if (localName.equals("Informatii")) {
			myOffer.setInformatii(currentValue);
		} else if (localName.equals("ConditiiHint")) {
			myOffer.setConditiiHint(tempSB);
		} else if (localName.equals("IdReducere")) {
			myOffer.setIdReducere (currentValue);
		}else if (localName.equals("PromoVodafone")){
			if (currentValue.equals("true")) myOffer.setIsRedusByVodafone (true);
			else myOffer.setIsRedusByVodafone (false);
		}
		
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		tempSB = tempSB.append(new String(ch, start, length));
		currentValue = new String(ch, start, length);		
	}
}