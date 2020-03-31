package com.razzotto.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

import com.razzotto.InicialController;
import com.razzotto.model.Processamento;

public class EscritaArquivos implements Runnable{ /// classe substituida por task
	
	private String nameThread;
	private Instant inicioLeituraFile;
	private Instant fimLeituraFile;
	
	private File dirDestinado;
	FileWriter writer ;
	int ContadorEscrita = 0;
//	private int controle = 0;
	boolean status = true;
	
	
	 public EscritaArquivos(String nameThread, File diretorioDestinado,int Contador) throws IOException {
		this.nameThread = nameThread;
		dirDestinado = diretorioDestinado;
		writer = new FileWriter(dirDestinado, false);
		ContadorEscrita = Contador;

	}
	
	@Override
	public void run() {
		try {
			do {
				if (ContadorEscrita == 0)
				{
					inicioLeituraFile = Instant.now();
				}
				ContadorEscrita++;
				//boolean converteu = Processamento.Escrita(writer,ContadorEscrita);
				if ((Processamento.ObjetosJson.size() > 0) &&(ContadorEscrita < Processamento.ObjetosJson.size()) )
				{
				String pessoaAtualGson = Processamento.ObjetosJson.get(ContadorEscrita);
				//	ObjetosJson.remove(0);
				writer.write(pessoaAtualGson +"\n");
				status = false;
				}
				else
				{status = true;}
				if (status)
				{
					synchronized (this) {
						try {//Thread.currentThread().getName()
							this.wait(500);
							//System.out.println(this.nameThread + " foi para Wait.");
						} catch (Exception e) {
							System.out.println("Escrita deu erro thread: " + e.getMessage());
						}
					}
				}
				else 
				{
					System.out.println("Parse realizado pela thread "+ this.nameThread );
				}
			} 
			while (ContadorEscrita <= 10/*Processamento.TerminouEscrita()*/); {
				writer.close();
		 		fimLeituraFile = Instant.now();
				Processamento.obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo EScrita:");
				System.out.println(nameThread + " terminou");
			}
		} catch (IOException e) {
			//System.out.println("Metodo Escrita" + e);
			e.printStackTrace();
		}
		
		
	}
	

}
