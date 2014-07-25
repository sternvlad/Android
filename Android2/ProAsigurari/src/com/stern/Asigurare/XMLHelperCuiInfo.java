package com.stern.Asigurare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperCuiInfo extends DefaultHandler {
	String currentValue = null;	
	StringBuilder tempSB = new StringBuilder();
	private StringBuilder raspuns = null;
	public StringBuilder getParsedData() {
        return this.raspuns;
	} 
	
	@Override
	public void startDocument() throws SAXException {
		this.raspuns = new StringBuilder();
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {
			raspuns = new StringBuilder();
			currentValue = new String();
		}
		else if (localName.equals("Body")) {}
		else if (localName.equals("GetCUIInfoResponse")) {}
		else if (localName.equals("GetCUIInfoResult")) { tempSB.delete(0, tempSB.length()); }
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {}
		else if (localName.equals("Body")) {}
		else if (localName.equals("GetCUIInfoResponse")) {}
		else if (localName.equals("GetCUIInfoResult")) { raspuns = tempSB; }
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		currentValue = new String(ch, start, length);	
		tempSB = tempSB.append(new String(ch, start, length));
	}
}

