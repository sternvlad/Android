package com.stern.Asigurare;

import java.io.Serializable;

public class SumareMainPage implements Serializable {
	private static final long serialVersionUID = 1L;
	public int index;
	public int indexId;
	public String title;
	public String detail;
	public SumareMainPage(int index, int indexId, String title, String detail) {
		this.index = index;
		this.indexId = indexId;
		this.title = title;
		this.detail = detail;
	}
}
