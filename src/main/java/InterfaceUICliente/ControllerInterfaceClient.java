package InterfaceUICliente;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.razzotto.Entidade.Arquivo;
import com.razzotto.Worker.AplicacaoCliente;

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
									ProgressBar progressEscrita, TextArea textarea, File dirOriginario, File dirDestinado) throws ClassNotFoundException, IOException {
		 interfaceMain = main;
		 ProgressLeitura = progressLeitura;
		 ProgressConversao =progressConversão;
		 ProgressEscrita = progressEscrita;
		 TextArea = textarea;
		 PreparaConexao(dirOriginario,dirDestinado);
	}
	public void PreparaConexao(File dirOriginario, File dirDestinado)
	{
		 try {
			endereco = InetAddress.getByName("localhost").getHostAddress();
			Socket Conexao = new Socket(endereco,12345);
			int TotalRegistros;
			Thread t = new AplicacaoCliente(Conexao,dirOriginario,dirDestinado,this);
			t.start();
			do {
				TotalRegistros = tamanhodoArquivo;
				if (tamanhodoArquivo != 0)
					break;
				System.out.println("total" + tamanhodoArquivo);
			} while (TotalRegistros == 0);
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
	//	ProcessoLeitura = arquivo.getProgressLeitura();
	//	System.out.println("agragaçãoL" +  arquivo.getProgressLeitura());
		setProcessoConversao(arquivo.getProgressConversao());
		//ProcessoConversao = arquivo.getProgressConversao();
	//	System.out.println("agragaçãoC" +  arquivo.getProgressConversao());
		setProcessoEscrita(arquivo.getProgressEscrita());
	//	ProcessoEscrita = arquivo.getProgressEscrita();
	//	System.out.println("agragaçãoE" + arquivo.getProgressEscrita());
		
	//	ProcessoFilaCSV = arquivo.getProgressFilaCSV();
	//	ProcessoFilaJson = arquivo.getProgressFilaJson();
		tamanhodoArquivo = arquivo.getTamanhodoArquivo();
		iniciouLeitura = arquivo.getIniciouLeitura();
		terminouLeitura = arquivo.getTerminouEscrita();
		iniciouConversao = arquivo.getIniciouConversao();
		terminouConversao = arquivo.getTerminouConversao(); 
		iniciouEscrito = arquivo.getIniciouEscrita();
		terminouEscrita = arquivo.getTerminouEscrita();
		terminouProcesso = arquivo.getManterConectado();
	}
	 private void atualizaCSV() {
	    	Task taskCSV = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				lidos = getProcessoLeitura();
						updateProgress(getProcessoLeitura(), tamanhodoArquivo);
						System.out.println("teste testado testou " + ProcessoConversao);
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
	    				lidos = getProcessoConversao();
						updateProgress(getProcessoConversao(), tamanhodoArquivo);
						System.out.println("teste testado testou " + ProcessoConversao);
					} while (terminouProcesso);
	    			if (terminouConversao == false)
	    			{
		    			updateMessage("Foi TERMINADA a Conversão de " + lidos + " Registros de CSV para JSON");
	    			}

	    			return null;
	    			
	    		}
	    	};
	    	Task taskEscritor = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				lidos = getProcessoEscrita();
						updateProgress(getProcessoEscrita(), tamanhodoArquivo);
					} while (terminouProcesso);
	    			
	    			if (terminouEscrita == false)
	    			{
	    				updateMessage("Foi TERMINADA a Conversão de " + lidos + " Escrita JSON");
	    			}

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
				ProgressLeitura.progressProperty().bind(taskCSV.progressProperty());
				new Thread(taskCSV).start();

				ProgressConversao.progressProperty().bind(taskJon.progressProperty());
				new Thread(taskJon).start();
				
				ProgressEscrita.progressProperty().bind(taskEscritor.progressProperty());
				new Thread(taskEscritor).start();
			
			
		}
	
}
