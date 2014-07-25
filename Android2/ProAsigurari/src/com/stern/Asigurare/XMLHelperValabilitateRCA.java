package com.stern.Asigurare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperValabilitateRCA extends DefaultHandler {
	String currentValue = null;	
	private String raspuns = null;
	public String getParsedData() {
        return this.raspuns;
	} 
	
	@Override
	public void startDocument() throws SAXException {
		this.raspuns = new String();
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {
			raspuns = new String();
			currentValue = new String();
		}
		else if (localName.equals("Body")) {}
		else if (localName.equals("VerificaRCAResponse")) {}
		else if (localName.equals("VerificaRCAResult")) { currentValue = new String(); }
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {}
		else if (localName.equals("Body")) {}
		else if (localName.equals("VerificaRCAResponse")) {}
		else if (localName.equals("VerificaRCAResult")) { raspuns = currentValue; }
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		currentValue = new String(ch, start, length);		
	}
}

