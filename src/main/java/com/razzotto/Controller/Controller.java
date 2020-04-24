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

public class Controller implements InterfaceCSV, InterfaceJSON, InterfaceWriter {
	// public static Vector<String>ContabilidadeTempo = new Vector<String>();
	private List<CSVRecord> filaCSV = null;
	public Vector<String> ObjetosJson = null;
	//public Vector<String>ContabilidadeTempo = null;
	public Map<Integer, String> ContabilidadeTempo = null;
	private File dirOriginario;
	private boolean ContinuaLeituraCSV = true;
	private File dirDestinado;
	private int quantidadeRegistros;
	private int RegistrosLidos;
	private int RegistroConvertidos;
	private int RegistrosEscritos;
	private boolean ContinuaEscrita = true;
	private boolean ContinuaConversaoJSON = true;
	private Instant inicioLeituraFile;
	private Instant FimLeituraFile;
	private Instant inicioLeituraFileJSON;
	private Instant inicioLeituraFileWRITER;
	private Instant FimLeituraFileJson;
	private Instant FimLeituraFileWriter;
	private boolean controle;

	// static int ContadorProgresso = 0;
	// static int QTDrowsArquivoAtual;
	// static int MaximoProgresso;
	public File getDirOriginario() {
		return dirOriginario;
	}

	public void setDirOriginario(File direOriginario) {
		dirOriginario = direOriginario;
	}

	public File getDirDestinado() {
		return dirDestinado;
	}

	public static void setDirDestinado(String dirDestinado) {
		dirDestinado = dirDestinado;
	}

	public Controller(File CaminhoOrigem, File CaminhoDestino) {
		dirOriginario = CaminhoOrigem;
		// File Caminho = CaminhoDestino.getAbsoluteFile();
		dirDestinado = CaminhoDestino;
		// dirDestinado = "C:\\Users\\Casa\\Documents\\TESTES PROGRAMAS CSV\\i3";
		filaCSV = new Vector<CSVRecord>();
		ObjetosJson = new Vector<String>();
		//ContabilidadeTempo =  new Vector<String>();
		ContabilidadeTempo = new HashMap<Integer, String>();

		// new EscritorJSON().Escrever(dirDestinado, ObjetosJson);

	}

	public void Inicia() {
		this.TratamentodeCSV();
		this.converterJSON();
		this.EscritordeJSON();

	}

	private void TratamentodeCSV() {
		TratamentoCSV trataCSV = new TratamentoCSV(this, dirOriginario);// pode ser feito as declarações em apensa uma
																		// linha de comando
		quantidadeRegistros = trataCSV.getQtdeRegistros(dirOriginario);
		Thread tCSV = new Thread(trataCSV);
		tCSV.setName("Thread_TRATACSV");
		tCSV.start();

	}

	private void EscritordeJSON() {
		EscritorJSON escritor = new EscritorJSON(dirDestinado, this);// será decomentado
		Thread tEcritor = new Thread(escritor);
		tEcritor.setName("Thread_tEcritor");
		tEcritor.start();
	}
	@Override
	public synchronized void SetTempoInicial() {
		this.inicioLeituraFile = Instant.now();
	}
	@Override
	public synchronized void SetTempoInicialJson() {
		this.inicioLeituraFileJSON = Instant.now();
	}
	@Override
	public synchronized void SetTempoIniciaWriter() {
		this.inicioLeituraFileWRITER = Instant.now();
	}
	public void SetTempoFinal() {
		this.FimLeituraFile = Instant.now();
	}
	public void SetTempoFinalJson() {
		this.FimLeituraFileJson = Instant.now();
	}
	public void SetTempoFinalWriter() {
		this.FimLeituraFileWriter = Instant.now();
	}
	
	public synchronized void ObterTempo(int chave,Instant Inicio, Instant Fim) {
		new TratamentoTempo().obterDuracao(this, Inicio, Fim, chave);
	}
	private void converterJSON() {
		SetTempoInicialJson();
		Thread t1 = new Thread(new ConversorJSON(this));
		Thread t2 = new Thread(new ConversorJSON(this));
		Thread t3 = new Thread(new ConversorJSON(this));
		Thread t4 = new Thread(new ConversorJSON(this));
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		try {
//        	t1.join();
//        	t2.join();
//        	t3.join();
//        	t4.join();
			System.out.println(ObjetosJson.size() + "Listaconvertida");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public synchronized boolean emOperacao()// Usado por todos
	{
		return filaCSV.size() > 0 || ContinuaLeituraCSV;// true
	}

//JSON
	public synchronized boolean emOperacaoJson()// Usado por todos
	{
		return filaCSV.size() > 0 || ObjetosJson.size() > 0;// true
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

//CSV
	public void terminouLeituraCSV() {
		ContinuaLeituraCSV = false;
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
	public synchronized void addRegistroCSV(CSVRecord csvrecord) {
		this.filaCSV.add(csvrecord);
		this.RegistrosLidos++;
		// System.out.println(csvrecord);

	}

	public synchronized int getQtdRegistros() {
		return this.quantidadeRegistros;
	}

	public synchronized int getRegistrosLidos() {
		return this.RegistrosLidos;
	}

	public synchronized int getRegistrosConvertidos() {
		return this.RegistrosLidos;
	}

	public synchronized void setRegistrosWriter() {
		this.RegistrosEscritos++;
		System.out.println("-----" + this.RegistrosEscritos);

	}

	public synchronized int getRegistrosWriter() {
		return this.RegistrosEscritos;

	}



}
