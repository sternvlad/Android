package com.stern.Asigurare;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelperExistingClient extends DefaultHandler {
	String currentValue = null;	
	StringBuilder tempSB1 = new StringBuilder();
	StringBuilder tempSB2 = new StringBuilder();
	StringBuilder tempSB3 = new StringBuilder();
	StringBuilder tempSB4 = new StringBuilder();
	private StringBuilder[] raspuns = null;
	public StringBuilder[] getParsedData() {
        return this.raspuns;
	} 
	
	@Override
	public void startDocument() throws SAXException {
		this.raspuns = new StringBuilder[4];
	}
	
	@Override
	public void endDocument() throws SAXException {
		//nothing
	}
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals("Envelope")) {
			raspuns[0] = new StringBuilder();
			raspuns[1] = new StringBuilder();
			raspuns[2] = new StringBuilder();
			raspuns[3] = new StringBuilder();
			currentValue = new String();
		}
		else if (localName.equals("Body")) {}
		else if (localName.equals("ExistingClientResponse")) {}
		else if (localName.equals("ExistingClientResult")) {}
		else if (localName.equals("ExistingClient")) {}
		else if (localName.equals("return")) {}
		else if (localName.equals("proprietar")) {tempSB1.delete(0, tempSB1.length());  }
		else if (localName.equals("masini")) { tempSB2.delete(0, tempSB2.length()); }
		else if (localName.equals("locuinte")) { tempSB3.delete(0, tempSB3.length()); }
		else if (localName.equals("persoane")) { tempSB4.delete(0, tempSB4.length()); }
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (localName.equals("Envelope")) {}
		else if (localName.equals("Body")) {}
		else if (localName.equals("ExistingClientResponse")) {}
		else if (localName.equals("ExistingClientResult")) {}
		else if (localName.equals("ExistingClient")) {}
		else if (localName.equals("return")) {}
		else if (localName.equals("proprietar")) { raspuns[0] = tempSB1;  }
		else if (localName.equals("masini")) { raspuns[1] = tempSB2; }
		else if (localName.equals("locuinte")) { raspuns[2] = tempSB3; }
		else if (localName.equals("persoane")) { raspuns[3] = tempSB4; }
	}
	
	@Override
	public void characters(char ch[], int start, int length) {		
		currentValue = new String(ch, start, length);	
		tempSB1 = tempSB1.append(new String(ch, start, length));
		tempSB2 = tempSB2.append(new String(ch, start, length));
		tempSB3 = tempSB3.append(new String(ch, start, length));
		tempSB4 = tempSB4.append(new String(ch, start, length));
	}
}
