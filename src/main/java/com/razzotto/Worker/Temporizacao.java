package com.razzotto.Worker;

public class Temporizacao {
	private long TempodeAbertura;
	private long TempodeLeitura;
	private long tempodeConversao;
	private long tempodeEscrita;
	public long getTempodeAbertura() {
		return TempodeAbertura;
	}
	public void setTempodeAbertura(long tempodeAbertura) {
		TempodeAbertura = tempodeAbertura;
	}
	public long getTempodeLeitura() {
		return TempodeLeitura;
	}
	public void setTempodeLeitura(long tempodeLeitura) {
		TempodeLeitura = tempodeLeitura;
	}
	public long getTempodeConversao() {
		return tempodeConversao;
	}
	public void setTempodeConversao(long tempodeConversao) {
		this.tempodeConversao = tempodeConversao;
	}
	public long getTempodeEscrita() {
		return tempodeEscrita;
	}
	public void setTempodeEscrita(long tempodeEscrita) {
		this.tempodeEscrita = tempodeEscrita;
	}
	public Temporizacao(long tempodeAbertura, long tempodeLeitura, long tempodeConversao, long tempodeEscrita) {
		super();
		TempodeAbertura = tempodeAbertura;
		TempodeLeitura = tempodeLeitura;
		this.tempodeConversao = tempodeConversao;
		this.tempodeEscrita = tempodeEscrita;
	}
	
	
	public Temporizacao() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Temporizacao [TempodeAbertura=" + TempodeAbertura + ", TempodeLeitura=" + TempodeLeitura
				+ ", tempodeConversao=" + tempodeConversao + ", tempodeEscrita=" + tempodeEscrita + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (TempodeAbertura ^ (TempodeAbertura >>> 32));
		result = prime * result + (int) (TempodeLeitura ^ (TempodeLeitura >>> 32));
		result = prime * result + (int) (tempodeConversao ^ (tempodeConversao >>> 32));
		result = prime * result + (int) (tempodeEscrita ^ (tempodeEscrita >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Temporizacao other = (Temporizacao) obj;
		if (TempodeAbertura != other.TempodeAbertura)
			return false;
		if (TempodeLeitura != other.TempodeLeitura)
			return false;
		if (tempodeConversao != other.tempodeConversao)
			return false;
		if (tempodeEscrita != other.tempodeEscrita)
			return false;
		return true;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	

}
