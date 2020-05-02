package com.razzotto.Entidade;

import java.io.Serializable;

public class Arquivo implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5950169519310163575L;
	String diretorioOriginario;
	String diretorioDestinado;
	public Arquivo(String diretorioOriginario, String diretorioDestinado) {
		super();
		this.diretorioOriginario = diretorioOriginario;
		this.diretorioDestinado = diretorioDestinado;
	}
	public String getDiretorioOriginario() {
		return diretorioOriginario;
	}
	public void setDiretorioOriginario(String diretorioOriginario) {
		this.diretorioOriginario = diretorioOriginario;
	}
	public String getDiretorioDestinado() {
		return diretorioDestinado;
	}
	public void setDiretorioDestinado(String diretorioDestinado) {
		this.diretorioDestinado = diretorioDestinado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Arquivo [diretorioOriginario=" + diretorioOriginario + ", diretorioDestinado=" + diretorioDestinado
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diretorioDestinado == null) ? 0 : diretorioDestinado.hashCode());
		result = prime * result + ((diretorioOriginario == null) ? 0 : diretorioOriginario.hashCode());
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
		return true;
	}
	


}
