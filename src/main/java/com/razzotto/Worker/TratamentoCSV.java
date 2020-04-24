package com.razzotto.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.razzotto.Controller.Controller;
import com.razzotto.Controller.InterfaceCSV;

public class TratamentoCSV implements Runnable {
	private InterfaceCSV Controller;
	private File fileCSV;

	public TratamentoCSV (InterfaceCSV controladoraconversao,File file) {
		this.Controller = controladoraconversao;
		this.fileCSV = file;
		
	}
	public int getQtdeRegistros(File dirOriginario) {
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(dirOriginario))){
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber() -1;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public void run() {
		try (Reader reader = new FileReader(fileCSV);){
			Controller.SetTempoInicial();
			CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT);
			
			for (CSVRecord registro : parser)
			{
				if (parser.getCurrentLineNumber()==1)
					continue;
				Controller.addRegistroCSV(registro); 
			}
			Controller.setContinuaLeituraCSV(false);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
