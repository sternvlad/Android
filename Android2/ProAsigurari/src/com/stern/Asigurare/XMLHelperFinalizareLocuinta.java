package com.stern.Asigurare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperFinalizareLocuinta extends DefaultHandler {
	String currentValue = null;	
	private String[] raspuns = null;
	public String[] getParsedData() {
        return this.raspuns;
	} 
	
	@Override
	public void startDocument() throws SAXException {
		this.raspuns = new String[2];
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {
			raspuns = new String[2];
			currentValue = new String();
		}
		else if (localName.equals("Body")) {}
		else if (localName.equals("CallInregistrareComandaSmartphoneResponse")) {}
		else if (localName.equals("CallInregistrareComandaSmartphoneResult")) {}
		else if (localName.equals("InregistrareComandaResponse")) {}
		else if (localName.equals("return")) {}
		else if (localName.equals("NumarComanda")) { currentValue = new String();  }
		else if (localName.equals("MesajComanda")) { currentValue = new String(); }
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {}
		else if (localName.equals("Body")) {}
		else if (localName.equals("CallInregistrareComandaSmartphoneResponse")) {}
		else if (localName.equals("CallInregistrareComandaSmartphoneResult")) {}
		else if (localName.equals("InregistrareComandaResponse")) {}
		else if (localName.equals("return")) {}
		else if (localName.equals("NumarComanda")) { raspuns[0] = currentValue;  }
		else if (localName.equals("MesajComanda")) { raspuns[1] = currentValue; }
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		currentValue = new String(ch, start, length);		
	}
}
