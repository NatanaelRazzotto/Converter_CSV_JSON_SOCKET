package com.razzotto.Entidade;

import java.io.File;
import java.io.Serializable;

public class Arquivo implements Serializable {
	private static final long serialVersionUID = 5950169519310163575L;
	File diretorioOriginario;
	File diretorioDestinado;
	Boolean ManterConectado;
	Boolean StartNovoProcesso;
	int ProgressLeitura;
	int ProgressConversao;
	int ProgressEscrita;
	int ProgressFilaCSV;
	int ProgressFilaJson;
	int tamanhodoArquivo;
	///
	String StatusProcesso;
	Boolean iniciouLeitura;
	Boolean TerminouLeitura= false;
	Boolean iniciouConversao;
	Boolean TerminouConversao= false;
	Boolean iniciouEscrita;
	Boolean TerminouEscrita = true;
	public Arquivo() {
		super();
	}
	public Arquivo(File diretorioOriginario, File diretorioDestinado, Boolean manterConectado,Boolean startNovoProcesso) {
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
	public Boolean getManterConectado() {
		return ManterConectado;
	}
	public void setManterConectado(Boolean manterConectado) {
		ManterConectado = manterConectado;
	}
	public Boolean getStartNovoProcesso() {
		return StartNovoProcesso;
	}
	public void setStartNovoProcesso(Boolean startNovoProcesso) {
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
	public Boolean getIniciouLeitura() {
		return iniciouLeitura;
	}
	public void setIniciouLeitura(Boolean iniciouLeitura) {
		this.iniciouLeitura = iniciouLeitura;
	}
	public Boolean getTerminouLeitura() {
		return TerminouLeitura;
	}
	public void setTerminouLeitura(Boolean terminouLeitura) {
		TerminouLeitura = terminouLeitura;
	}
	public Boolean getIniciouConversao() {
		return iniciouConversao;
	}
	public void setIniciouConversao(Boolean iniciouConversao) {
		this.iniciouConversao = iniciouConversao;
	}
	public Boolean getTerminouConversao() {
		return TerminouConversao;
	}
	public void setTerminouConversao(Boolean terminouConversao) {
		TerminouConversao = terminouConversao;
	}
	public Boolean getIniciouEscrita() {
		return iniciouEscrita;
	}
	public void setIniciouEscrita(Boolean iniciouEscrita) {
		this.iniciouEscrita = iniciouEscrita;
	}
	public Boolean getTerminouEscrita() {
		return TerminouEscrita;
	}
	public void setTerminouEscrita(Boolean terminouEscrita) {
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
