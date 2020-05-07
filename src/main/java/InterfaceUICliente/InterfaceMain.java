package InterfaceUICliente;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.google.gson.Gson;
import com.razzotto.Controller.Controller;
import com.razzotto.Entidade.Pessoa;
import com.razzotto.Entidade.Temporizacao;
import com.razzotto.Model.Processamento;
import com.razzotto.Worker.AplicacaoCliente;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class InterfaceMain implements javafx.fxml.Initializable{
    @FXML
    private Button btn_AbrirArquivo;
    @FXML
    private Button btn_SalvarArquivo;
    @FXML
    private Button btn_ConverteArquivo;
    @FXML
    private Button btn_TEMPO;
    @FXML
    private Button btn_MedirTEMPO;
    @FXML
    private ProgressBar PrB_ProgressoLeitura;
    @FXML
    private ProgressBar PrB_ProecessoConversao;
    @FXML
    private ProgressBar PrB_ProecessoEscrita;
    @FXML 
    private TextArea txtA_Status;
    @FXML 
    private TextArea txtA_Progress;
    
    
    //
    static Vector<Pessoa> ListaPessoas = new Vector<Pessoa>();
	static Vector<String> ObjetosJson = new Vector<String>();
    //
    public static Vector<String>ContabilidadeTempo = new Vector<String>();
     String MensagemStatus= "opera��o";
     int QTDrowsArquivoAtual = 100;
     static int ContadorProgresso = 0;
     int ContadorProgressoa = 0;
     Boolean ControleStatus = true;
     File dirOriginario ;
     File dirDestinado;
     private int TotalRegistros;
     ///
     Controller controller;
 	@Override
 	public void initialize(URL location, ResourceBundle resources) {
 		//btn_ConverteArquivo.setDisable(true);
 		 		
 	}
     @FXML////////////////////////////////////////////////////////////
     private void AbrirTempo(ActionEvent event) {
     	try {
     		LerTempos();
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

     }
     @FXML////////////////////////////////////////////////////////////
     private void MostrarTempo(ActionEvent event) {
      	try {
      		Tempo();
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}

      }
    @FXML////////////////////////////////////////////////////////////
    private void AbrirArquivo(ActionEvent event) {
    	try {
			Captura_Arquivo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    @FXML//////////////////////////////////////////
    private void SalvarArquivo(ActionEvent event) {
   		try {
   	    	Salva_Arquivo();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

    }


    @FXML
    private void ConverterCSV(ActionEvent event) {
    	try {		
    	    	if ((dirOriginario != null)&&(dirDestinado!=null)) {
				btn_AbrirArquivo.setDisable(true);
				btn_SalvarArquivo.setDisable(true);
	    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
	   			System.out.println(dirOriginario);
				System.out.println(dirDestinado);
				new ControllerInterfaceClient(this, PrB_ProgressoLeitura, PrB_ProecessoConversao, PrB_ProecessoEscrita, txtA_Status, dirOriginario, dirDestinado);
				//controller = new Controller(dirOriginario,dirDestinado);
				//controller.Inicia();
//				btn_ConverteArquivo.setDisable(true);
//				do {
//					TotalRegistros = controller.getQtdRegistros();
//					if (!controller.IsContinuaLeituraCSV())
//						break;
//					
//				} while (TotalRegistros == 0);//
				
				//this.atualizaCSV();
	    		}
				else {
				    Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("INFORME OS DIRET�RIOS!");
					alert.setHeaderText(null);
					alert.setContentText("Para dar prosseguimento, deve-se informar os diretorios !");
	
					alert.showAndWait();
				}
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    private void atualizaCSV() {
    	Task taskCSV = new Task<Void>() {
    		
    		@Override
    		protected Void call() {
    			int lidos = 0;
    			do {
    				lidos = controller.getQtdRegistros();
    				updateProgress(controller.getRegistrosLidos(), TotalRegistros);
				} while (controller.IsContinuaLeituraCSV());
    			updateMessage("Foi Concluida a Leitura de " + lidos + " Registros do arquivo CSV");
    			return null;
    			
    		}
    	};
    	Task taskJon = new Task<Void>() {
    		
    		@Override
    		protected Void call() {
    			int lidos = 0;
    			do {
    				lidos = controller.getQtdRegistros();
					updateProgress(controller.getRegistrosConvertidos(), TotalRegistros);
				} while (controller.IsContinuaLeituraJSON());
    			updateMessage("Foi Concluida a Convers�o de " + lidos + " Registros de CSV para JSON");
    			return null;
    			
    		}
    	};
    	Task taskEscritor = new Task<Void>() {
    		
    		@Override
    		protected Void call() {
    			int lidos = 0;
    			do {
    				lidos = controller.getQtdRegistros();
					updateProgress(controller.getRegistrosWriter(), TotalRegistros);
				} while (controller.IsContinuaEscrita());
    	
    			updateMessage("Foi Concluida a Escrita de " + lidos + " Registros em JSON");
    			return null;
    			
    		}
    	};
		taskCSV.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				txtA_Status.appendText("\n"+taskCSV.getMessage());

			}
			
		});
		taskJon.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				txtA_Status.appendText("\n"+taskJon.getMessage());

			}
			
		});
		taskEscritor.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				txtA_Status.appendText("\n"+taskEscritor.getMessage());

			}
			
		});
			PrB_ProgressoLeitura.progressProperty().bind(taskCSV.progressProperty());
			new Thread(taskCSV).start();

			PrB_ProecessoConversao.progressProperty().bind(taskJon.progressProperty());
			new Thread(taskJon).start();
			
			PrB_ProecessoEscrita.progressProperty().bind(taskEscritor.progressProperty());
			new Thread(taskEscritor).start();
		
		
	}
	public File Captura_Arquivo(){////////
    	try {
	    JFileChooser file_chooser = new JFileChooser();
    	StringBuilder sb = new StringBuilder();
    	
    	if(file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
    	{
    		File file = file_chooser.getSelectedFile();
    		dirOriginario = file;
    		Processamento.setDirOriginario(file);
    		txtA_Status.appendText(dirOriginario + "\n");
    		return dirOriginario;
    	}
    	else
    	{
    		return null;
    	}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    public File Salva_Arquivo() {////////////
    	try {
	   	JFrame parentFrame = new JFrame();
    	JFileChooser file_chooser = new JFileChooser();
    	file_chooser.setDialogTitle("Onde Deseja Salvar o Arquivo CSV");
    	int userSelection = file_chooser.showSaveDialog(parentFrame);
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = file_chooser.getSelectedFile();
    		Processamento.setDirDestinado(fileToSave);
    	    dirDestinado = fileToSave;
    		txtA_Status.appendText(dirDestinado + "\n");
    	    return fileToSave;
    	}
		return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    public static void gerarVeiculos(File dir)//Metodo n�o usado
	{
    	try {
		System.out.println("sdadsad");
		List<String> Modelo = Arrays.asList("Onix","Palio","Fiesta","Argo","HB20");
		List<String> alfabeto = Arrays.asList("A","B","C","D","F","G","H","I","J","K","L",
				"M","N","O","P","Q","R","S","T","U","V","W","Y","X","Z");
	
			System.out.println("gfhghgh");
		Random gerador = new Random();
		FileWriter csvWriter = new FileWriter(dir, false);
			for (int i = 0; i < 50000; i ++)
			{
				csvWriter.append(String.join(",", alfabeto.get(gerador.nextInt(24)) + alfabeto.get(gerador.nextInt(24))
						+alfabeto.get(gerador.nextInt(24))+"-"+gerador.nextInt(10)+gerador.nextInt(10)+gerador.nextInt(10)+gerador.nextInt(10)));
				csvWriter.append(String.join(",","," +Modelo.get(gerador.nextInt(4))));
				csvWriter.append("\n");
			}
			csvWriter.flush();
			csvWriter.close();
			System.out.println("deu");
		} catch (IOException e) {
			e.printStackTrace();
		}
     
	}
	public void LerTempos ()
	{
		try {
			long somTLeitura = 0;
			long medTLeitura = 0;
			long somTParse = 0;
			long medTParse = 0;
			long somEscrita = 0;
			long medEscrita = 0;
			String row;
			String CaminhoAplica��o = System.getProperty("user.dir");
			String CaminoLog = CaminhoAplica��o + "\\LogsTempo\\RegistrosTempo.txt";
			BufferedReader csvReader = new BufferedReader(new FileReader(CaminoLog));
			List<Temporizacao> ListadeTempos = new ArrayList<Temporizacao>();
			try {
				while (( row = csvReader.readLine()) != null) {
				    String[] arquivoMemoria = row.split(",");
				    Temporizacao tempo = new Temporizacao();
				    tempo.setTempodeLeitura(Long.parseLong(arquivoMemoria[0]));
				    tempo.setTempodeConversao(Long.parseLong(arquivoMemoria[1]));
				    tempo.setTempodeEscrita(Long.parseLong(arquivoMemoria[2]));
				    ListadeTempos.add(tempo);
				}
				for (Temporizacao temporizacao : ListadeTempos) {
						somTLeitura = somTLeitura+ temporizacao.getTempodeLeitura();
					somTParse = somTParse+ temporizacao.getTempodeConversao();
					somEscrita = somEscrita+ temporizacao.getTempodeEscrita();
				}
				medTLeitura = (somTLeitura / ListadeTempos.size());
				medTParse = (somTParse / ListadeTempos.size());
				medEscrita = (somEscrita / ListadeTempos.size());
				txtA_Status.appendText("//////Contabiliza��o de Tempos de Processamento\\\\\\  \n");
				txtA_Status.appendText("//////"+ListadeTempos.size()+" J� Processados! \\\\\\  \n");
				txtA_Status.appendText("* TEMPO M�DIO DE LEITURA: " + medTLeitura+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO M�DIO DE PARSE: " + medTParse+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO M�DIO DE ESCRITA: " + medEscrita+ "Milesegundos \n");
				
			} catch (IOException e) {
				txtA_Status.appendText("//////N�o foi possiverler os logs\\\\\\  \n");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			txtA_Status.appendText("//////N�o foi possiverler os logs\\\\\\  \n");
			e.printStackTrace();
		}
	}
	public void Tempo ()
	{
		try {
			if (controller != null)
			{
				if (controller.ContabilidadeTempo.size() > 0)
				{		
			    Map<Integer, String> tempos = controller.ContabilidadeTempo;
				txtA_Status.appendText("//////O ultimo processamento teve como resultado!\\\\\\  \n");
				txtA_Status.appendText("* TEMPO M�DIO DE LEITURA: " + tempos.get(1)+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO M�DIO DE PARSE: " + tempos.get(2)+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO M�DIO DE ESCRITA: " + tempos.get(3)+ "Milesegundos \n");
				}
				else 
				{
					txtA_Status.appendText("//////O programa n�o rodou ainda!\\\\\\  \n");
				}
			}
			else 
			{
				txtA_Status.appendText("//////O programa n�o rodou ainda!\\\\\\  \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


		
	
    
}
