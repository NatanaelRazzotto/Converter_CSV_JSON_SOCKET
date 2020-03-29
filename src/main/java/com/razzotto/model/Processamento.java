package com.razzotto.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

import com.google.gson.Gson;
import com.razzotto.Pessoa;
import com.razzotto.worker.ConversaoArquivos;
import com.razzotto.worker.EscritaArquivos;

public class Processamento {
    public static Vector<String>ContabilidadeTempo = new Vector<String>();
    public static Vector<Pessoa> ListaPessoas = new Vector<Pessoa>();
	static Vector<String> ObjetosJson = new Vector<String>();
	
    private static File dirOriginario ;
    private static File dirDestinado;
    static int ContadorProgresso = 0;
    
	public static File getDirOriginario() {
		return dirOriginario;
	}
	public static void setDirOriginario(File dirOriginario) {
		Processamento.dirOriginario = dirOriginario;
	}
	public static File getDirDestinado() {
		return dirDestinado;
	}
	public static void setDirDestinado(File dirDestinado) {
		Processamento.dirDestinado = dirDestinado;
	}
	public static boolean isTerminated() {
	//	Boolean lendoDados = true;
		return ListaPessoas.size() > 0;
	
	}
	public static int TerminouEscrita() {
		
		return ObjetosJson.size();
		
	}
	public static String obterDuracao(Instant inicio, Instant fim, String Mensagem) {
		Duration decorrido = Duration.between(inicio, fim);
		long decorridoMilissegundos = decorrido.toMillis();
		String Retorno = Mensagem + decorridoMilissegundos + " Milesgundos";
		ContabilidadeTempo.add(Retorno);
		//txtA_Status.appendText("Tempo de Leitura e preparação Concluidos "+ +"\n");
		return Retorno;
		
	}
	
	public static int ContabilizarArquivo() {
		try {
		    int QTDrowsArquivoAtual = 0;
			String row;
			Instant inicioLeituraFile = Instant.now();
		    BufferedReader contarLinhas = new BufferedReader(new FileReader(dirOriginario));
	//		QTDrowsArquivoAtual = 0;
			while (( row = contarLinhas.readLine()) != null) {
				QTDrowsArquivoAtual++;
			}
			Instant fimLeituraFile = Instant.now();
			obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Para Abertura e Count:");
			return QTDrowsArquivoAtual;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	public static void Leitura() {
		try {
			String row;
			ContadorProgresso = 0;
			Instant inicioLeituraFile =Instant.now();
			BufferedReader csvReader = new BufferedReader(new FileReader(dirOriginario));
			while (( row = csvReader.readLine()) != null) {
				ContadorProgresso++;
	    	    String[] arquivoMemoria = row.split(",");
	    	    Pessoa informacoesPessoa = new Pessoa(arquivoMemoria[0], arquivoMemoria[1], arquivoMemoria[2],arquivoMemoria[3],arquivoMemoria[4], arquivoMemoria[5],
						arquivoMemoria[6],arquivoMemoria[7],arquivoMemoria[8],arquivoMemoria[9],arquivoMemoria[10],arquivoMemoria[11], arquivoMemoria[12],arquivoMemoria[13],arquivoMemoria[14],arquivoMemoria[15],arquivoMemoria[16],
						arquivoMemoria[17], arquivoMemoria[18],arquivoMemoria[19],arquivoMemoria[20],arquivoMemoria[21], arquivoMemoria[22],
						arquivoMemoria[23],arquivoMemoria[24]);
	    	     ListaPessoas.add(informacoesPessoa);
   		 	}
			Instant fimLeituraFile = Instant.now();
			obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo leitura:");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static synchronized boolean Conversao() {
		Gson gson = new Gson();
		if (ListaPessoas.size()>0)
		{
			ContadorProgresso++;
			System.out.println(ContadorProgresso + "/n");
			Pessoa pessoaAtual = ListaPessoas.get(0);
			ListaPessoas.remove(0);
			String json = gson.toJson(pessoaAtual);
			ObjetosJson.add(json);
			return false;
		}
		return true;
	}
	public static synchronized boolean Escrita(FileWriter writer,int contadorEscrita) {
		try {
			//Instant inicioLeituraFile = Instant.now();
			
			//writer = new FileWriter(dirDestinado, false);
			System.out.println(ObjetosJson.size()+"///////////////////////////////");
			if ((ObjetosJson.size() > 0) &&(contadorEscrita < ObjetosJson.size()) )
			{
			String pessoaAtualGson = ObjetosJson.get(contadorEscrita);
		//	ObjetosJson.remove(0);
			writer.write(pessoaAtualGson +"\n");
			return false;
			}
			 
			/*for (ContadorProgresso = 0; ContadorProgresso < ObjetosJson.size(); ContadorProgresso++)
			{
				String pessoaAtualGson = ObjetosJson.get(ContadorProgresso);
				 writer.write(pessoaAtualGson +"\n");
			}*/
		   //	writer.close();
			//Instant fimLeituraFile = Instant.now();
			//obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Escrita:");
			return true;
		
		} catch (Exception e) {
			System.out.println("Ocoreu erro na escrita: " + e.getMessage());
			return false;
		}
	}
}
