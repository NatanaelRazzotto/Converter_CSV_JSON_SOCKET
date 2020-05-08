package com.razzotto.Controller;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.csv.CSVRecord;

import com.razzotto.Entidade.Pessoa;
import com.razzotto.Model.Processamento;
import com.razzotto.Worker.ConversorJSON;
import com.razzotto.Worker.EscritorJSON;
import com.razzotto.Worker.TratamentoCSV;
import com.razzotto.Worker.TratamentoTempo;
import com.sun.javafx.webkit.ThemeClientImpl;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class Controller implements InterfaceCSV, InterfaceJSON, InterfaceWriter, Runnable{
	private List<CSVRecord> filaCSV = null;
	public Vector<String> ObjetosJson = null;
	public Map<Integer, String> ContabilidadeTempo = null;
	private File dirOriginario;
	private File dirDestinado;
	private boolean ContinuaLeituraCSV = true;	
	private int quantidadeRegistros;
	private int RegistrosLidos;
	private int RegistroConvertidos;
	private int RegistrosEscritos;
	private boolean ContinuaEscrita = true;
	private boolean ContinuaConversaoJSON = true;
	private boolean ContinuaProcessamento = true;
	private boolean IniciouLeitura = false;
	private boolean IniciouConversao = false;
	private boolean IniciouEscrita = false;	
	private Instant inicioLeituraFile;
	private Instant FimLeituraFile;
	private Instant inicioLeituraFileJSON;
	private Instant inicioLeituraFileWRITER;
	private Instant FimLeituraFileJson;
	private Instant FimLeituraFileWriter;
	private boolean controle;
	
	EscritorJSON escritor;
	TratamentoCSV trataCSV;
	
	/////////////////////////////////////////// CONTROLLER/////////////////////////////////////////
	public File getDirOriginario() {
		return dirOriginario;
	}
	public void setDirOriginario(File direOriginario) {
		dirOriginario = direOriginario;
	}
	public File getDirDestinado() {
		return dirDestinado;
	}
	public void setDirDestinado(File direDestinado) {
		dirDestinado = direDestinado;
	}
	public Controller(File CaminhoOrigem, File CaminhoDestino) {
		dirOriginario = CaminhoOrigem;
		dirDestinado = CaminhoDestino;
		filaCSV = new Vector<CSVRecord>();
		ObjetosJson = new Vector<String>();
		ContabilidadeTempo = new HashMap<Integer, String>();
	}
	@Override
	public void run() {
		try {
			
		
		this.TratamentodeCSV();
		this.converterJSON();
		this.EscritordeJSON();
		while (this.IsContinuaEscrita()==true) {
			if (this.filaCSV.size() > 50)
			{
				new Thread(new ConversorJSON(this)).start();
				System.out.println("----Startou Thread--------------");
			}
			if (this.ObjetosJson.size() > 50)
			{
				new Thread(escritor).start();
			}
		}
		escritor.EncerrarArquivo();
		setContinuaProcessamento();
		
		System.out.println("Terminou com sucesso");
		}
		catch (Exception e) {
			System.out.println("Erro No gestor");
		}
		
	}
	public synchronized void ObterTempo(int chave,Instant Inicio, Instant Fim) {
		new TratamentoTempo().obterDuracao(this, Inicio, Fim, chave);
	}
	
	public void setContinuaProcessamento() {
       		this.ContinuaProcessamento = false;
	}
	public void setinicioLeitura() {
   		this.IniciouLeitura = true;
	}	
	public synchronized boolean IsinicioLeitura() {
		return IniciouLeitura;
	}
	public void setinicioConversao() {
   		this.IniciouConversao = true;
	}	
	public synchronized boolean IsinicioConversao() {
		return IniciouConversao;
	}
	public void setinicioEscrita() {
   		this.IniciouEscrita = true;
	}	
	public synchronized boolean IsinicioEscrita() {
		return IniciouEscrita;
	}
	public synchronized boolean IsContinuaProcessamento() {
		return this.ContinuaProcessamento;
	}
	public synchronized int IsSizeListaCSV() {
		return filaCSV.size();
	}
	public synchronized int IsSizeListaJson() {
		return ObjetosJson.size();
	}
	/////////////////////////////////////////// CSV/////////////////////////////////////////
	private void TratamentodeCSV() {
		trataCSV = new TratamentoCSV(this, dirOriginario);// 
																		
		quantidadeRegistros = trataCSV.getQtdeRegistros(dirOriginario);
		Thread tCSV = new Thread(trataCSV);
		tCSV.setName("Thread_TRATACSV");
		tCSV.start();
		setinicioLeitura();

	}
	@Override
	public synchronized void SetTempoInicial() {
		this.inicioLeituraFile = Instant.now();
	}
	@Override
	public void SetTempoFinal() {
		this.FimLeituraFile = Instant.now();
	}
	@Override
	public synchronized void addRegistroCSV(CSVRecord csvrecord) {
		this.filaCSV.add(csvrecord);
		this.RegistrosLidos++;
	}
	@Override
	public synchronized void setContinuaLeituraCSV(boolean terminou) {
		if ((this.ContinuaLeituraCSV == true)&&(terminou == false))
		{
			SetTempoFinal();
			ObterTempo(1,this.inicioLeituraFile,this.FimLeituraFile);			
		}
		ContinuaLeituraCSV = terminou;

	}
	public synchronized boolean IsContinuaLeituraCSV() {
		return this.ContinuaLeituraCSV;
	}
	public synchronized int getQtdRegistros() {
		return this.quantidadeRegistros;
	}
	public synchronized int getRegistrosLidos() {
		return this.RegistrosLidos;
	}
	///////////////////////////////////////////JSON/////////////////////////////////////////
	private void converterJSON() {
		SetTempoInicialJson();
		Thread t1 = new Thread(new ConversorJSON(this));
	//	Thread t2 = new Thread(new ConversorJSON(this));
		t1.start();
	//	t2.start();
		try {
			setinicioConversao();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	@Override
	public synchronized void SetTempoInicialJson() {
		this.inicioLeituraFileJSON = Instant.now();
	}
	@Override
	public void SetTempoFinalJson() {
		this.FimLeituraFileJson = Instant.now();
	}
	@Override
	public synchronized boolean emOperacao()// Usado por JSON
	{
		return filaCSV.size() > 0 || ContinuaLeituraCSV;// true || true
	}
	@Override
	public synchronized CSVRecord obterCSV() {
		if (filaCSV.size() > 0)
			return filaCSV.remove(0);
		return null;
	}
	@Override
	public synchronized void addJson(String ObjGson) {
		ObjetosJson.add(ObjGson);
		this.RegistroConvertidos++;
	}
	@Override
	public void setContinuaLeituraJson(boolean terminou) {

		if ((this.ContinuaConversaoJSON == true)&&(terminou == false)&&(controle = true))
		{
			controle = false;
			SetTempoFinalJson();
			ObterTempo(2,this.inicioLeituraFileJSON,this.FimLeituraFileJson);			
		}
		this.ContinuaConversaoJSON = terminou;
		
	}
	public synchronized boolean IsContinuaLeituraJSON() {
		return this.ContinuaConversaoJSON;
	}
	@Override
	public synchronized int getRegistrosConvertidos() {
		return this.RegistroConvertidos;
	}
	//////////////////////////////////////////WRITER/////////////////////////////////////////
	private void EscritordeJSON() {
		escritor = new EscritorJSON(dirDestinado, this);// ser� decomentado
		Thread tEcritor = new Thread(escritor);
		tEcritor.setName("Thread_tEcritor");
		tEcritor.start();
		setinicioEscrita();
		SetTempoIniciaWriter();

	}
	@Override
	public synchronized void SetTempoIniciaWriter() {
		this.inicioLeituraFileWRITER = Instant.now();
	}
	@Override
	public void SetTempoFinalWriter() {
		this.FimLeituraFileWriter = Instant.now();
	}
	public synchronized boolean emOperacaoJson()// Usado por ESCRITOR
	{
		return filaCSV.size() > 0 || ObjetosJson.size() > 0;// true
	}
	
	public synchronized String obterJson() {
		if (ObjetosJson.size() > 0)
			return ObjetosJson.remove(0);
		return null;
	}
	@Override
	public synchronized void setContinuaEscrita(boolean terminou) {
		if ((this.ContinuaEscrita == true)&&(terminou == false))
		{
			SetTempoFinalWriter();
			ObterTempo(3,this.inicioLeituraFileWRITER,this.FimLeituraFileWriter);
			new TratamentoTempo().GerarLogsdeTempo(this);
		}

		ContinuaEscrita = terminou;

	}
	public synchronized boolean IsContinuaEscrita() {
		return this.ContinuaEscrita;
	}
	@Override
	public synchronized int getRegistrosWriter() {
		return this.RegistrosEscritos;

	}
	public synchronized void setRegistrosWriter() {
		this.RegistrosEscritos++;

	}

}
