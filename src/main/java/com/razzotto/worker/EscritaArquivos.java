package com.razzotto.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

import com.razzotto.InicialController;
import com.razzotto.model.Processamento;

public class EscritaArquivos implements Runnable{
	
	private String nameThread;
	private Instant inicioLeituraFile;
	private Instant fimLeituraFile;
	
	private File dirDestinado;
	FileWriter writer ;
	int ContadorEscrita = 0;
	
	 public EscritaArquivos(String nameThread, File diretorioDestinado,int Contador) throws IOException {
		this.nameThread = nameThread;
		dirDestinado = diretorioDestinado;
		writer = new FileWriter(dirDestinado, false);
		ContadorEscrita = Contador;

	}
	
	@Override
	public void run() {
		do {
		ContadorEscrita++;
		boolean converteu = Processamento.Escrita(writer,ContadorEscrita);
		if (converteu)
		{
			synchronized (this) {
				try {//Thread.currentThread().getName()
					this.wait(500);
					System.out.println(this.nameThread + " foi para Wait.");
				} catch (Exception e) {
					System.out.println("Escrita deu erro thread: " + e.getMessage());
				}
			}
		}
		else 
		{
			System.out.println("Parse realizado pela thread "+ this.nameThread );
		}
		} while (ContadorEscrita <= Processamento.TerminouEscrita()); {
			try {
			System.out.println(nameThread + " terminou");
		  	writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	}
		
		
	}
	

}
