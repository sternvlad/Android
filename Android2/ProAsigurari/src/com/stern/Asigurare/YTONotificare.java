package com.stern.Asigurare;


public class YTONotificare {
		int id;
		String subiect;
		String dataAlerta;
		int tipAlerta , status;//citita,necitita
		
		public YTONotificare ()
		{
			this.id = 0;
			this.subiect = "";
			this.dataAlerta = "";
			this.tipAlerta = 1;
			this.status = 0;
		}
}
