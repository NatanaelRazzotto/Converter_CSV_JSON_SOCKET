package com.razzotto.Worker;

import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;
import com.razzotto.Controller.Controller;
import com.razzotto.Controller.InterfaceJSON;
import com.razzotto.Entidade.Pessoa;

public class ConversorJSON implements Runnable {
	private InterfaceJSON controller;
	public ConversorJSON(InterfaceJSON interfaceJson) {
		this.controller = interfaceJson;

	}

	@Override
	public void run() {
		do {
			CSVRecord csvRecord = controller.obterCSV();
			if(csvRecord==null)
			{
				synchronized (this) {
					try {
						this.wait(1000);
						System.out.println(Thread.currentThread().getName() + " foi para Wait.");
					} catch (Exception e) {
						System.out.println("ParsePessoa: " + e.getMessage());
					}
				}
			}
			else 
			{
				String Json = converterParaJson(csvRecord);
				controller.addJson(Json);
				System.out.println(Thread.currentThread().getName() + "Colocou na fila");
			}
		} while (controller.emOperacao());
		{
			controller.setContinuaLeituraJson(false);
			
		}

	}
	private String converterParaJson(CSVRecord csvRecord )
	{
		String Json = null; 
	    Pessoa informacoesPessoa = new Pessoa(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2),csvRecord.get(3),csvRecord.get(4),
	    		csvRecord.get(5),csvRecord.get(6),csvRecord.get(7),csvRecord.get(8),csvRecord.get(9),csvRecord.get(10),csvRecord.get(11), 
	    		csvRecord.get(12),csvRecord.get(13),csvRecord.get(14),csvRecord.get(15),csvRecord.get(16),csvRecord.get(17),
	    		csvRecord.get(18),csvRecord.get(19),csvRecord.get(20),csvRecord.get(21), csvRecord.get(22),csvRecord.get(23),csvRecord.get(24));
		Gson json = new Gson();
		return json.toJson(informacoesPessoa,Pessoa.class);
	}

}
