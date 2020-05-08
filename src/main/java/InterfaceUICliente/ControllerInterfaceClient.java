package InterfaceUICliente;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.razzotto.Entidade.Arquivo;
import com.razzotto.Worker.Client.AplicacaoCliente;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class ControllerInterfaceClient  {
	InterfaceMain interfaceMain;
	int ProcessoLeitura;
	int ProcessoConversao;
	int ProcessoEscrita;
	int ProcessoFilaCSV;
	int ProcessoFilaJson;
	ProgressBar ProgressLeitura;
	ProgressBar ProgressConversao;
	ProgressBar ProgressEscrita;
	ProgressBar ProgressFilaCSV;
	ProgressBar ProgressFilaJSON;
	TextArea TextArea;
	int tamanhodoArquivo = 0;
	private boolean iniciouLeitura;
	private boolean terminouLeitura;
	private boolean iniciouConversao;
	private boolean terminouConversao;
	private boolean iniciouEscrito;
	private boolean terminouEscrita;
	private boolean terminouProcesso;
	private String endereco;
	
	
	
	public synchronized int getProcessoLeitura() {
		return ProcessoLeitura;
	}
	public synchronized void setProcessoLeitura(int processoLeitura) {
		ProcessoLeitura = processoLeitura;
	}
	public synchronized int getProcessoConversao() {
		return ProcessoConversao;
	}
	public synchronized void setProcessoConversao(int processoConversao) {
		ProcessoConversao = processoConversao;
	}
	public synchronized int getProcessoEscrita() {
		return ProcessoEscrita;
	}
	public synchronized void setProcessoEscrita(int processoEscrita) {
		ProcessoEscrita = processoEscrita;
	}
	public synchronized int getProcessoFilaCSV() {
		return ProcessoFilaCSV;
	}
	public synchronized void setProcessoFilaCSV(int processoFilaCSV) {
		ProcessoFilaCSV = processoFilaCSV;
	}
	public synchronized int getProcessoFilaJson() {
		return ProcessoFilaJson;
	}
	public synchronized void setProcessoFilaJson(int processoFilaJson) {
		ProcessoFilaJson = processoFilaJson;
	}
	public ControllerInterfaceClient (InterfaceMain main, ProgressBar progressLeitura, ProgressBar progressConversão, 
									ProgressBar progressEscrita, ProgressBar progressFILAC, ProgressBar progressFILCAJ,TextArea textarea, 
									File dirOriginario, File dirDestinado) throws ClassNotFoundException, IOException {
		 interfaceMain = main;
		 ProgressLeitura = progressLeitura;
		 ProgressConversao =progressConversão;
		 ProgressEscrita = progressEscrita;
		 ProgressFilaCSV = progressFILAC;
		 ProgressFilaJSON= progressFILCAJ;
		 TextArea = textarea;
		 PreparaConexao(dirOriginario,dirDestinado);
	}
	public void PreparaConexao(File dirOriginario, File dirDestinado)
	{
		 try {
			endereco = InetAddress.getByName("localhost").getHostAddress();
			Socket Conexao = new Socket(endereco,49152);
			Thread t = new AplicacaoCliente(Conexao,dirOriginario,dirDestinado,this);
			t.start();
			this.atualizaCSV();
		 } catch (UnknownHostException e) {
				System.out.println("Problema na obtenção de endereço IP");
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
	public void agregarProcessamento (Arquivo arquivo)
	{
		setProcessoLeitura(arquivo.getProgressLeitura());
		setProcessoConversao(arquivo.getProgressConversao());
		setProcessoEscrita(arquivo.getProgressEscrita());
		setProcessoFilaCSV(arquivo.getProgressFilaCSV());
		setProcessoFilaJson(arquivo.getProgressFilaJson());
		tamanhodoArquivo = arquivo.getTamanhodoArquivo();
		iniciouLeitura = arquivo.getIniciouLeitura();
		terminouLeitura = arquivo.getTerminouEscrita();
		iniciouConversao = arquivo.getIniciouConversao();
		terminouConversao = arquivo.getTerminouConversao(); 
		iniciouEscrito = arquivo.getIniciouEscrita();
		terminouEscrita = arquivo.getTerminouEscrita();
		terminouProcesso = arquivo.getManterConectado();
	}
	public boolean testeterminou() {
		return terminouProcesso || getProcessoLeitura() < tamanhodoArquivo;
	}

	 private void atualizaCSV() {
		 	
		 
	    	Task taskCSV = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				if (iniciouLeitura = true)
		    				{
		    					updateMessage("A Leitura INICIOU");
		    				}
	    				lidos = getProcessoLeitura();
						updateProgress(getProcessoLeitura(), tamanhodoArquivo);
					} while (terminouProcesso);
	    			updateProgress(getProcessoLeitura(), tamanhodoArquivo);
	    			if (terminouLeitura == false)
	    			{
	    			updateMessage("Foi TERMINADA a Leitura de " + lidos + " Registros do arquivo CSV");
	    			}
	    			return null;
	    			
	    		}
	    	};
	    	Task taskJon = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				if (iniciouConversao = true)
	    				{
	    					updateMessage("A Conversão COMEÇOU");
	    				}
	    				lidos = getProcessoConversao();
						updateProgress(getProcessoConversao(), tamanhodoArquivo);
					} while (lidos < tamanhodoArquivo);
		    			updateMessage("Foi TERMINADA a Conversão de " + lidos + " Registros de CSV para JSON");

	    			return null;
	    			
	    		}
	    	};
	    	Task taskEscritor = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				if (iniciouEscrito = true)
	    				{	    	
	    					updateMessage("A escrita COMEÇOU");
	    				}
	    				lidos = getProcessoEscrita();
						updateProgress(getProcessoEscrita(), tamanhodoArquivo);
					} while (lidos < tamanhodoArquivo);
	    			
	    			if (terminouEscrita == false)
	    			{
	    				updateMessage("Foi TERMINADA a Escrita de " + lidos + " Escrita JSON");
	    			}

	    			return null;
	    			
	    		}
	    	};
	    	Task taskFilaCSV = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				lidos = getProcessoFilaCSV();
						updateProgress(getProcessoFilaCSV(), 100);
						System.out.println("registros na fila CSV" + lidos);
					
					} while (terminouProcesso);
	    			updateProgress(0, 100);
    		
	    			return null;
	    			
	    		}
	    	};
	    	Task taskFilaJson = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				lidos = getProcessoFilaJson();
						updateProgress(getProcessoFilaJson(), 100);
						System.out.println("registros na fila JSON" + lidos);
					} while (terminouProcesso);
	    			updateProgress(0, 100);
	    			return null;
	    			
	    		}
	    	};
	    	
			taskCSV.messageProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					TextArea.appendText("\n"+taskCSV.getMessage());

				}
				
			});
			taskJon.messageProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					TextArea.appendText("\n"+taskJon.getMessage());

				}
				
			});
			taskEscritor.messageProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					TextArea.appendText("\n"+taskEscritor.getMessage());

				}
				
			});
			int TotalRegistros;
			do {
				TotalRegistros = tamanhodoArquivo;
				if (tamanhodoArquivo != 0)
					break;
				System.out.println("total" + tamanhodoArquivo);
			} while (TotalRegistros == 0);
				
				ProgressFilaCSV.progressProperty().bind(taskFilaCSV.progressProperty());
				new Thread(taskFilaCSV).start();
				
				ProgressFilaJSON.progressProperty().bind(taskFilaJson.progressProperty());
				new Thread(taskFilaJson).start();
			
				ProgressLeitura.progressProperty().bind(taskCSV.progressProperty());
				new Thread(taskCSV).start();

				ProgressConversao.progressProperty().bind(taskJon.progressProperty());
				new Thread(taskJon).start();
				
				ProgressEscrita.progressProperty().bind(taskEscritor.progressProperty());
				new Thread(taskEscritor).start();
			
			
		}
	
}
