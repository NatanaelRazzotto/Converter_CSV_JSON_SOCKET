package com.razzotto.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;

import com.google.gson.Gson;
import com.razzotto.Entidade.Pessoa;
import com.razzotto.Worker.TratamentoCSV;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class Processamento {
    public static  Vector<String>ContabilidadeTempo = new Vector<String>();
    public static  Vector<Pessoa> ListaPessoas = new Vector<Pessoa>();
	public static  Vector<String> ObjetosJson = new Vector<String>();
	
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
	private static File dirOriginario;
    private static File dirDestinado;
    static int ContadorProgresso = 0;
    static int QTDrowsArquivoAtual;
    static int MaximoProgresso;
    public static void gestaoProcessamentoTestar (ProgressBar pB_Leitura, ProgressBar pB_Conversao, ProgressBar pB_Escrita, TextArea txtA_Status)
 	{
    //	new TratamentoCSV().ObtencaodDeDados(dirOriginario);
 	}
    public static void gestaoProcessamento (ProgressBar pB_Leitura, ProgressBar pB_Conversao, ProgressBar pB_Escrita, TextArea txtA_Status)
	{
    	
		try {
			    String row;
			    QTDrowsArquivoAtual = 0;
				Instant inicioLeituraFile = Instant.now();
			    BufferedReader contarLinhas = new BufferedReader(new FileReader(dirOriginario));
				while (( row = contarLinhas.readLine()) != null) {
					QTDrowsArquivoAtual++;
				}
				MaximoProgresso = QTDrowsArquivoAtual/100;
				Instant fimLeituraFile = Instant.now();
				obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Para Abertura e Count:");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		Task<Void> Leitura = new Task<Void>() {
			@Override
			protected Void call() {
				try {
					String row;
					int ContaProgresso = 0;
					float acum = 0;
					Instant inicioLeituraFile =Instant.now();
					BufferedReader csvReader = new BufferedReader(new FileReader(dirOriginario));
					while (( row = csvReader.readLine()) != null) {
						ContaProgresso++;
			    	    String[] arquivoMemoria = row.split(",");
			    	    Pessoa informacoesPessoa = new Pessoa(arquivoMemoria[0], arquivoMemoria[1], arquivoMemoria[2],arquivoMemoria[3],arquivoMemoria[4], arquivoMemoria[5],
								arquivoMemoria[6],arquivoMemoria[7],arquivoMemoria[8],arquivoMemoria[9],arquivoMemoria[10],arquivoMemoria[11], arquivoMemoria[12],arquivoMemoria[13],arquivoMemoria[14],arquivoMemoria[15],arquivoMemoria[16],
								arquivoMemoria[17], arquivoMemoria[18],arquivoMemoria[19],arquivoMemoria[20],arquivoMemoria[21], arquivoMemoria[22],
								arquivoMemoria[23],arquivoMemoria[24]);
			    	     ListaPessoas.add(informacoesPessoa);
						if (ContaProgresso == MaximoProgresso) {

							acum += 0.01F;							
							updateProgress(acum, 1);
							ContaProgresso = 0;
						}
		   		 	}
					Instant fimLeituraFile = Instant.now();
					obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo leitura:");
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
								
			}
		};

		Task<Void> Conversao = new Task<Void>() {
			@Override
			protected Void call() {
				try {
					Instant inicioLeituraFile =  Instant.now();
					Instant fimLeituraFile;
					int ContaProgresso = 0;
					float acum = 0;
					do {
						if (ListaPessoas.size()>0)
						{
							Gson gson = new Gson();
							Pessoa pessoaAtual = ListaPessoas.get(0);
							ListaPessoas.remove(0);
							String json = gson.toJson(pessoaAtual);
							ObjetosJson.add(json);
							ContaProgresso++;

							if (ContaProgresso == MaximoProgresso) {

								acum += 0.01F;							
								updateProgress(acum, 1);
								ContaProgresso = 0;
							}
						}
						else 
						{
							System.out.println("A lista está vazia ");
						}
					}while(ListaPessoas.size() > 0); {
						fimLeituraFile = Instant.now();
						Processamento.obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo Conversão:");
				//		System.out.println("A conversão terminou!");
					}
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		Task<Void> Escrever = new Task<Void>() {
			@Override
			protected Void call() {
				try {
					FileWriter writer = new FileWriter(dirDestinado, false);
					int ContadorEscrita = 0;
					Instant inicioLeituraFile =  Instant.now();
					Instant fimLeituraFile;
					int ContaProgresso = 0;
					float acum = 0;
					
					do {
									
		    			if ((ObjetosJson.size() > 0))
						{
						String pessoaAtualGson = ObjetosJson.get(0);
						ObjetosJson.remove(0);
						ContadorEscrita++;
						ContaProgresso++;
						writer.write(pessoaAtualGson +"\n");
						System.out.println("Escrita realizado pela thread " + "\n" + ContadorEscrita);
						if (ContaProgresso == MaximoProgresso) {

							acum += 0.01F;							
							updateProgress(acum, 1);
							ContaProgresso = 0;
						}
						}
						else 
						{
							//System.out.println(ListaPessoas.size());
							//System.out.println(ObjetosJson.size());
							//System.out.println("A lista está vazia ");
							synchronized (this) {
								try {//Thread.currentThread().getName()
									this.wait(500);
									System.out.println("A lista está vazia ");
									//System.out.println(this.nameThread + " foi para Wait.");
								} catch (Exception e) {
									System.out.println("Escrita deu erro thread: " + e.getMessage());
								}
							}
						}
					} 
					while ((ObjetosJson.size() >= 0)&&(ContadorEscrita<QTDrowsArquivoAtual)); {
						writer.close();
				 		fimLeituraFile = Instant.now();
						Processamento.obterDuracao(inicioLeituraFile, fimLeituraFile, "Tempo EScrita:");
						System.out.println(" terminou Escrita");
						
						String CaminhoAplicação = System.getProperty("user.dir");
						String CaminoLog = CaminhoAplicação + "\\LogsTempo\\RegistrosTempo.txt";
						System.out.println(CaminhoAplicação);
						FileWriter writerLogs = new FileWriter(CaminoLog, true);
						for (String tempoContabilizado : ContabilidadeTempo) {
							writerLogs.append(tempoContabilizado + ",");
						}
						writerLogs.append("\n");
						writerLogs.close();
	
						
					}
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	
			
			}
		};
		Task<Void> EscreverLogsTempo = new Task<Void>() {
			
			@Override
			protected Void call() {
				try {
				/*	String CaminhoAplicação = System.getProperty("user.dir");
					String CaminoLog = CaminhoAplicação + "\\LogsTempo\\RegistrosTempo.txt";
					System.out.println(CaminhoAplicação);
					FileWriter writer = new FileWriter(CaminoLog, true);
					for (String tempoContabilizado : ContabilidadeTempo) {
						writer.append(tempoContabilizado + ",");
					}
					writer.append("\n");
					writer.close();
					return null;*/
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return null;
				
			}
		};

		pB_Leitura.progressProperty().bind(Leitura.progressProperty());
		pB_Conversao.progressProperty().bind(Leitura.progressProperty());
		pB_Escrita.progressProperty().bind(Leitura.progressProperty());
		
		Thread t1 = new Thread(Leitura);
		Thread t2 = new Thread(Conversao);
		Thread t3 = new Thread(Conversao);
		Thread t4 = new Thread(Escrever);
		//Thread t5 = new Thread(EscreverLogsTempo);
		
		try {
			t1.start();

			Thread.sleep(15);

			t2.start();

			Thread.sleep(5);

			t3.start();
			
			Thread.sleep(20);
			
			t4.start();
		//	t4.join();
		//	t5.start();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public synchronized static String obterDuracao(Instant inicio, Instant fim, String Mensagem) {
		try {
			Duration decorrido = Duration.between(inicio, fim);
			long decorridoMilissegundos = decorrido.toMillis();
			String Retorno = Long.toString(decorridoMilissegundos) ;
			ContabilidadeTempo.add(Retorno);
			//txtA_Status.appendText("Tempo de Leitura e preparação Concluidos "+ +"\n");
			return Retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}
	
	public int ContabilizarArquivo() {
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
			e.printStackTrace();
			// TODO: handle exception
			return 0;
		}
	}
	public void Leitura() {
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
			e.printStackTrace();
			// TODO: handle exception
		}
	}


}
