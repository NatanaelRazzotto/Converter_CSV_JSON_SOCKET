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
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(dirOriginario))){//colocar o caminho originario
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber() -1;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
//	public void ObtencaodDeDados(InterfaceCSV controladoraconversao,File file) {
//
//		
////		String[] Cabecalho = {"Number","Gender","NameSet","Title","GivenName","Surname","StreetAddress","City","State",
////				"ZipCode","CountryFull","EmailAddress","Username","Password","TelephoneNumber","Birthday","CCType",
////				"CCNumber","CVV2","CCExpires","NationalID","Color","Kilograms","Centimeters","GUID"};
////		Iterable<CSVRecord> record = null;
////		Reader ln = null;
////		try {
////			ln = new FileReader(file);
////			record = CSVFormat.DEFAULT.withHeader(Cabecalho).withFirstRecordAsHeader().parse(ln);//O formato do CSV é defalt o comando a seguir dis que a linha é um cabeçalho
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		
////		for (CSVRecord registro : record)
////		{
////			controladoraconversao.addRegistro(registro); 
////		}
////		controladoraconversao.terminouLeituraCSV();
//	}

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
			Controller.terminouLeituraCSV();///

			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
