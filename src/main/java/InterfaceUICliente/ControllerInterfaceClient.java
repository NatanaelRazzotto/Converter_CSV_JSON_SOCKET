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
	int tamanhodoArquivo;
	private Boolean terminouLeitura;
	private Boolean terminouConversao;
	private Boolean terminouEscrita;
	private String endereco;
	
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
		ProcessoLeitura = arquivo.getProgressLeitura();
		System.out.println("agragação" +  arquivo.getProgressLeitura());
		ProcessoConversao = arquivo.getProgressConversao();
		System.out.println("agragação" +  arquivo.getProgressConversao());
		ProcessoEscrita = arquivo.getProgressEscrita();
		System.out.println("agragação" + arquivo.getProgressEscrita());
		
	//	ProcessoFilaCSV = arquivo.getProgressFilaCSV();
	//	ProcessoFilaJson = arquivo.getProgressFilaJson();
		tamanhodoArquivo = arquivo.getTamanhodoArquivo();
		terminouLeitura = arquivo.getTerminouEscrita();
		terminouConversao = arquivo.getTerminouConversao();
		terminouEscrita = arquivo.getTerminouEscrita();
	}
	 private void atualizaCSV() {
	    	Task taskCSV = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    				//lidos = controller.getQtdRegistros();
	    				updateProgress(ProcessoLeitura, tamanhodoArquivo);
					} while (!terminouLeitura);
	    			updateMessage("Foi Concluida a Leitura de " + tamanhodoArquivo + " Registros do arquivo CSV");
	    			return null;
	    			
	    		}
	    	};
	    	Task taskJon = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			int lidos = 0;
	    			do {
	    			//	lidos = controller.getQtdRegistros();
						updateProgress(ProcessoConversao, tamanhodoArquivo);
						System.out.println("teste testado testou " + ProcessoConversao);
					} while (!terminouEscrita);
	    			updateMessage("Foi Concluida a Conversão de " + tamanhodoArquivo + " Registros de CSV para JSON");
	    			return null;
	    			
	    		}
	    	};
	    	Task taskEscritor = new Task<Void>() {
	    		
	    		@Override
	    		protected Void call() {
	    			do {
	    			//	lidos = controller.getQtdRegistros();
						updateProgress(ProcessoEscrita, tamanhodoArquivo);
					} while (!terminouEscrita);
	    			updateMessage("Foi Concluida a Conversão de " + tamanhodoArquivo + " Registros de CSV para JSON");
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
