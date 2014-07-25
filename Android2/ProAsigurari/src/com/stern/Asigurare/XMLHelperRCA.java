package com.stern.Asigurare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperRCA extends DefaultHandler{
	String currentValue = null;
	static StringBuilder tempSB = new StringBuilder();	
	private RaspunsRCA myOffer = null;
	public RaspunsRCA getParsedData() {
        return this.myOffer;
	}
	
	@Override
	public void startDocument() throws SAXException {
		this.myOffer = new RaspunsRCA();
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {
			myOffer = new RaspunsRCA();		
			currentValue = new String();
		} else if (localName.equals("Body")) {			
			currentValue = new String();
		} else if (localName.equals("calcul_prima_rcaResponse")) {	
			currentValue = new String();
		} else if (localName.equals("return")) {	
			currentValue = new String();
		} else if (localName.equals("eroare_ws")) {	
			currentValue = new String();
		} else if (localName.equals("precizari")) {	
			currentValue = new String();
		} else if (localName.equals("sec_to_wait")) {	
			currentValue = new String();
		} else if (localName.equals("response_time_sec")) {	
			currentValue = new String();
		} else if (localName.equals("Allianz_prima")) {
			currentValue = new String();			
		} else if (localName.equals("Allianz_cod_oferta")) {
			currentValue = new String();			
		} else if (localName.equals("Allianz_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Allianz_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Ardaf_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Ardaf_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Ardaf_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Ardaf_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Asirom_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Asirom_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Asirom_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Asirom_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Astra_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Astra_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Astra_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Astra_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("BCR_prima")) {
			currentValue = new String();	
		} else if (localName.equals("BCR_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("BCR_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("BCR_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Carpatica_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Carpatica_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Carpatica_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Carpatica_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("City_prima")) {
			currentValue = new String();	
		} else if (localName.equals("City_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("City_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("City_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Euroins_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Euroins_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Euroins_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Euroins_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Generali_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Generali_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Generali_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Generali_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Groupama_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Groupama_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Groupama_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Groupama_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Omniasig_prima")) {
			 tempSB.delete(0, tempSB.length());	
		} else if (localName.equals("Omniasig_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Omniasig_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Omniasig_status_response")) {
			currentValue = new String();	
		} else if (localName.equals("Uniqa_prima")) {
			currentValue = new String();	
		} else if (localName.equals("Uniqa_cod_oferta")) {
			currentValue = new String();	
		} else if (localName.equals("Uniqa_clasa_bm")) {
			currentValue = new String();	
		} else if (localName.equals("Uniqa_status_response")) {
			currentValue = new String();	
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {			
		} else if (localName.equals("Body")) {			
		} else if (localName.equals("calcul_prima_rcaResponse")) {			
		} else if (localName.equals("return")) {
		} else if (localName.equals("eroare_ws")) {	
			myOffer.setEroare_ws(currentValue);
		} else if (localName.equals("precizari")) {	
			myOffer.setPrecizari(currentValue);
		} else if (localName.equals("sec_to_wait")) {			
		} else if (localName.equals("response_time_sec")) {			
		} else if (localName.equals("Allianz_prima")) {
			myOffer.setAllianz_prima(currentValue);			
		} else if (localName.equals("Allianz_cod_oferta")) {
			myOffer.setAllianz_cod_oferta(currentValue);
		} else if (localName.equals("Allianz_clasa_bm")) {
			myOffer.setAllianz_clasa_bm(currentValue);
		} else if (localName.equals("Allianz_status_response")) {
			myOffer.setAllianz_status_response(currentValue);
		} else if (localName.equals("Ardaf_prima")) {
			myOffer.setArdaf_prima(currentValue);			
		} else if (localName.equals("Ardaf_cod_oferta")) {
			myOffer.setArdaf_cod_oferta(currentValue);
		} else if (localName.equals("Ardaf_clasa_bm")) {
			myOffer.setArdaf_clasa_bm(currentValue);
		} else if (localName.equals("Ardaf_status_response")) {
			myOffer.setArdaf_status_response(currentValue);
		} else if (localName.equals("Asirom_prima")) {
			myOffer.setAsirom_prima(currentValue);			
		} else if (localName.equals("Asirom_cod_oferta")) {
			myOffer.setAsirom_cod_oferta(currentValue);
		} else if (localName.equals("Asirom_clasa_bm")) {
			myOffer.setAsirom_clasa_bm(currentValue);
		} else if (localName.equals("Asirom_status_response")) {
			myOffer.setAsirom_status_response(currentValue);
		} else if (localName.equals("Astra_prima")) {
			myOffer.setAstra_prima(currentValue);			
		} else if (localName.equals("Astra_cod_oferta")) {
			myOffer.setAstra_cod_oferta(currentValue);
		} else if (localName.equals("Astra_clasa_bm")) {
			myOffer.setAstra_clasa_bm(currentValue);
		} else if (localName.equals("Astra_status_response")) {
			myOffer.setAstra_status_response(currentValue);
		} else if (localName.equals("BCR_prima")) {
			myOffer.setBCR_prima(currentValue);			
		} else if (localName.equals("BCR_cod_oferta")) {
			myOffer.setBCR_cod_oferta(currentValue);
		} else if (localName.equals("BCR_clasa_bm")) {
			myOffer.setBCR_clasa_bm(currentValue);
		} else if (localName.equals("BCR_status_response")) {
			myOffer.setBCR_status_response(currentValue);
		} else if (localName.equals("Carpatica_prima")) {
			myOffer.setCarpatica_prima(currentValue);			
		} else if (localName.equals("Carpatica_cod_oferta")) {
			myOffer.setCarpatica_cod_oferta(currentValue);
		} else if (localName.equals("Carpatica_clasa_bm")) {
			myOffer.setCarpatica_clasa_bm(currentValue);
		} else if (localName.equals("Carpatica_status_response")) {
			myOffer.setCarpatica_status_response(currentValue);
		} else if (localName.equals("City_prima")) {
			myOffer.setCity_prima(currentValue);			
		} else if (localName.equals("City_cod_oferta")) {
			myOffer.setCity_cod_oferta(currentValue);
		} else if (localName.equals("City_clasa_bm")) {
			myOffer.setCity_clasa_bm(currentValue);
		} else if (localName.equals("City_status_response")) {
			myOffer.setCity_status_response(currentValue);
		} else if (localName.equals("Euroins_prima")) {
			myOffer.setEuroins_prima(currentValue);			
		} else if (localName.equals("Euroins_cod_oferta")) {
			myOffer.setEuroins_cod_oferta(currentValue);
		} else if (localName.equals("Euroins_clasa_bm")) {
			myOffer.setEuroins_clasa_bm(currentValue);
		} else if (localName.equals("Euroins_status_response")) {
			myOffer.setEuroins_status_response(currentValue);
		} else if (localName.equals("Generali_prima")) {
			myOffer.setGenerali_prima(currentValue);			
		} else if (localName.equals("Generali_cod_oferta")) {
			myOffer.setGenerali_cod_oferta(currentValue);
		} else if (localName.equals("Generali_clasa_bm")) {
			myOffer.setGenerali_clasa_bm(currentValue);
		} else if (localName.equals("Generali_status_response")) {
			myOffer.setGenerali_status_response(currentValue);
		} else if (localName.equals("Groupama_prima")) {
			myOffer.setGroupama_prima(currentValue);			
		} else if (localName.equals("Groupama_cod_oferta")) {
			myOffer.setGroupama_cod_oferta(currentValue);
		} else if (localName.equals("Groupama_clasa_bm")) {
			myOffer.setGroupama_clasa_bm(currentValue);
		} else if (localName.equals("Groupama_status_response")) {
			myOffer.setGroupama_status_response(currentValue);
		} else if (localName.equals("Omniasig_prima")) {
			myOffer.setOmniasig_prima(tempSB.toString());			
		} else if (localName.equals("Omniasig_cod_oferta")) {
			myOffer.setOmniasig_cod_oferta(currentValue);
		} else if (localName.equals("Omniasig_clasa_bm")) {
			myOffer.setOmniasig_clasa_bm(currentValue);
		} else if (localName.equals("Omniasig_status_response")) {
			myOffer.setOmniasig_status_response(currentValue);
		} else if (localName.equals("Uniqa_prima")) {
			myOffer.setUniqa_prima(currentValue);			
		} else if (localName.equals("Uniqa_cod_oferta")) {
			myOffer.setUniqa_cod_oferta(currentValue);
		} else if (localName.equals("Uniqa_clasa_bm")) {
			myOffer.setUniqa_clasa_bm(currentValue);
		} else if (localName.equals("Uniqa_status_response")) {
			myOffer.setUniqa_status_response(currentValue);
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		tempSB = tempSB.append(new String(ch, start, length));
		currentValue = new String(ch, start, length);		
	}
}