package com.razzotto.worker;

import java.time.Instant;

import com.razzotto.InicialController;
import com.razzotto.model.Processamento;

public class ConversaoArquivos implements Runnable{
	
	private String nameThread;
	private Instant inicioLeituraFile;
	private Instant fimLeituraFile;
	
	 public ConversaoArquivos(String nameThread,Instant inicioLeitura ) {
		this.nameThread = nameThread;
		if (this.nameThread == "Thread1")
		{
			inicioLeituraFile = inicioLeitura;
		}

	}
	
	@Override
	public void run() {
		do {
			
			boolean converteu = Processamento.Conversao();
			if (converteu)
			{
				synchronized (this) {
					try {//Thread.currentThread().getName()
						this.wait(500);
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
		} while (Processamento.isTerminated()); {
			System.out.println(nameThread + " terminou");
			//	if (this.nameThread == "Thread1")
			//	{
				//    fimLeituraFile = Instant.now();
				  //  InicialController.obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Conversão:");
			//	}
	    	}
		
		
	}
	

}
