package com.razzotto.Entidade;

import java.io.File;
import java.io.Serializable;

public class Arquivo implements Serializable {
	private static final long serialVersionUID = 5950169519310163575L;
	File diretorioOriginario;
	File diretorioDestinado;
	String nomeArquivo;
	public Arquivo(File diretorioOriginario, File diretorioDestinado, String nomeArquivo) {
		super();
		this.diretorioOriginario = diretorioOriginario;
		this.diretorioDestinado = diretorioDestinado;
		this.nomeArquivo = nomeArquivo;
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
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Arquivo [diretorioOriginario=" + diretorioOriginario + ", diretorioDestinado=" + diretorioDestinado
				+ ", nomeArquivo=" + nomeArquivo + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diretorioDestinado == null) ? 0 : diretorioDestinado.hashCode());
		result = prime * result + ((diretorioOriginario == null) ? 0 : diretorioOriginario.hashCode());
		result = prime * result + ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
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
		Arquivo other = (Arquivo) obj;
		if (diretorioDestinado == null) {
			if (other.diretorioDestinado != null)
				return false;
		} else if (!diretorioDestinado.equals(other.diretorioDestinado))
			return false;
		if (diretorioOriginario == null) {
			if (other.diretorioOriginario != null)
				return false;
		} else if (!diretorioOriginario.equals(other.diretorioOriginario))
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		return true;
	}
	
	
	



}
