package com.razzotto.Controller;

import org.apache.commons.csv.CSVRecord;

public interface InterfaceJSON {

	public void setContinuaLeituraJson(boolean terminou);
	public boolean emOperacao();
	public CSVRecord obterCSV();
	public void addJson(String ObjGson);
	public void SetTempoInicialJson();
	public void SetTempoFinalJson();
	public int getRegistrosConvertidos();

}