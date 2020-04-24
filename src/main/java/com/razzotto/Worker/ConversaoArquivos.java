package com.razzotto.Worker;

import java.time.Instant;

import com.google.gson.Gson;
import com.razzotto.Entidade.Pessoa;
import com.razzotto.Model.Processamento;

import InterfaceUI.InicialController;

public class ConversaoArquivos implements Runnable{/// classe substituida por task
	
	private String nameThread;
	private Instant inicioLeituraFile;
	private Instant fimLeituraFile;
	private int controle = 0;
	boolean status = true;
	
	 public ConversaoArquivos(String nameThread ) {
		this.nameThread = nameThread;
	}
	
	@Override
	public void run() {
		try {
			do {
				if ((this.nameThread == "Thread1")&&(controle == 0))
				{
					inicioLeituraFile = Instant.now();
					controle++;
				}
				if (Processamento.ListaPessoas.size()>0)
				{
				Gson gson = new Gson();
				Pessoa pessoaAtual = Processamento.ListaPessoas.get(0);
				Processamento.ListaPessoas.remove(0);
				String json = gson.toJson(pessoaAtual);
				Processamento.ObjetosJson.add(json);
				status = false;
				}
				else 
				{
					status = true;
				}
				if (status)
				{
					synchronized (this) {
						try {//Thread.currentThread().getName()
		
							//this.wait(1000);
				
							System.out.println(this.nameThread + " foi para Wait.");
						} catch (Exception e) {
							System.out.println("ParsePessoa: " + e.getMessage());
						}
					}
				}
				else 
				{
					System.out.println("Parse realizado pela thread "+ this.nameThread );
				}
			} 
			while (Processamento.ListaPessoas.size() > 0); {//Processamento.isTerminated()
				
				if (this.nameThread == "Thread1")
				{
					fimLeituraFile = Instant.now();
				   Processamento.obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Conversão:");
				}
				System.out.println(nameThread + " terminou");
		   	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
