package com.razzotto.Entidade;

import java.io.File;
import java.io.Serializable;

public class Arquivo implements Serializable {
	private static final long serialVersionUID = 5950169519310163575L;
	File diretorioOriginario;
	File diretorioDestinado;
	boolean ManterConectado;
	boolean StartNovoProcesso;
	int ProgressLeitura;
	int ProgressConversao;
	int ProgressEscrita;
	int ProgressFilaCSV;
	int ProgressFilaJson;
	int tamanhodoArquivo;
	///
	String StatusProcesso;
	boolean iniciouLeitura;
	boolean TerminouLeitura= true;
	boolean iniciouConversao;
	boolean TerminouConversao= true;
	boolean iniciouEscrita;
	boolean TerminouEscrita = true;
	public Arquivo() {
		super();
	}
	public Arquivo(File diretorioOriginario, File diretorioDestinado, boolean manterConectado,boolean startNovoProcesso) {
		super();
		this.diretorioOriginario = diretorioOriginario;
		this.diretorioDestinado = diretorioDestinado;
		ManterConectado = manterConectado;
		StartNovoProcesso = startNovoProcesso;
	}
	public File getDiretorioOriginario() {
		return diretorioOriginario;
	}
	public void setDiretorioOriginario(File diretorioOriginario) {
		this.diretorioOriginario = diretorioOriginario;
	}
	public File getDiretorioDestinado() {
		return diretorioDestinado;
	}
	public void setDiretorioDestinado(File diretorioDestinado) {
		this.diretorioDestinado = diretorioDestinado;
	}
	public boolean getManterConectado() {
		return ManterConectado;
	}
	public void setManterConectado(boolean manterConectado) {
		ManterConectado = manterConectado;
	}
	public boolean getStartNovoProcesso() {
		return StartNovoProcesso;
	}
	public void setStartNovoProcesso(boolean startNovoProcesso) {
		StartNovoProcesso = startNovoProcesso;
	}
	public int getProgressLeitura() {
		return ProgressLeitura;
	}
	public void setProgressLeitura(int progressLeitura) {
		ProgressLeitura = progressLeitura;
	}
	public int getProgressConversao() {
		return ProgressConversao;
	}
	public void setProgressConversao(int progressConversao) {
		ProgressConversao = progressConversao;
	}
	public int getProgressEscrita() {
		return ProgressEscrita;
	}
	public void setProgressEscrita(int progressEscrita) {
		ProgressEscrita = progressEscrita;
	}
	public int getProgressFilaCSV() {
		return ProgressFilaCSV;
	}
	public void setProgressFilaCSV(int progressFilaCSV) {
		ProgressFilaCSV = progressFilaCSV;
	}
	public int getProgressFilaJson() {
		return ProgressFilaJson;
	}
	public void setProgressFilaJson(int progressFilaJson) {
		ProgressFilaJson = progressFilaJson;
	}
	public String getStatusProcesso() {
		return StatusProcesso;
	}
	public void setStatusProcesso(String statusProcesso) {
		StatusProcesso = statusProcesso;
	}
	public boolean getIniciouLeitura() {
		return iniciouLeitura;
	}
	public void setIniciouLeitura(boolean iniciouLeitura) {
		this.iniciouLeitura = iniciouLeitura;
	}
	public boolean getTerminouLeitura() {
		return TerminouLeitura;
	}
	public void setTerminouLeitura(boolean terminouLeitura) {
		TerminouLeitura = terminouLeitura;
	}
	public boolean getIniciouConversao() {
		return iniciouConversao;
	}
	public void setIniciouConversao(boolean iniciouConversao) {
		this.iniciouConversao = iniciouConversao;
	}
	public boolean getTerminouConversao() {
		return TerminouConversao;
	}
	public void setTerminouConversao(boolean terminouConversao) {
		TerminouConversao = terminouConversao;
	}
	public boolean getIniciouEscrita() {
		return iniciouEscrita;
	}
	public void setIniciouEscrita(boolean iniciouEscrita) {
		this.iniciouEscrita = iniciouEscrita;
	}
	public boolean getTerminouEscrita() {
		return TerminouEscrita;
	}
	public void setTerminouEscrita(boolean terminouEscrita) {
		TerminouEscrita = terminouEscrita;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTamanhodoArquivo() {
		return tamanhodoArquivo;
	}
	public void setTamanhodoArquivo(int tamanhodoArquivo) {
		this.tamanhodoArquivo = tamanhodoArquivo;
	}
	
	
	

	
	
	
	


}
