package InterfaceUI;

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
import com.razzotto.Model.Processamento;
import com.razzotto.Worker.ConversaoArquivos;
import com.razzotto.Worker.EscritaArquivos;
import com.razzotto.Worker.Temporizacao;

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

public class InicialController implements javafx.fxml.Initializable{
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
     String MensagemStatus= "operação";
     int QTDrowsArquivoAtual = 100;
     static int ContadorProgresso = 0;
     int ContadorProgressoa = 0;
     Boolean ControleStatus = true;
     File dirOriginario ;
     static File dirDestinado;
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

	private int TotalRegistros;
    @FXML
    private void ConverterCSV(ActionEvent event) {
    	try {		
    	    	if ((dirOriginario != null)&&(dirDestinado!=null)) {
				//btn_ConverteArquivo.setDisable(true);
				btn_AbrirArquivo.setDisable(true);
				btn_SalvarArquivo.setDisable(true);
	    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
	   			System.out.println(dirOriginario);
				System.out.println(dirDestinado);
	    		//Calcula();
				//ProcessoConversao();
				controller = new Controller(dirOriginario,dirDestinado);
				controller.Inicia();
				btn_ConverteArquivo.setDisable(true);
				do {
					TotalRegistros = controller.getQtdRegistros();
					if (!controller.IsContinuaLeituraCSV())
						break;
					
				} while (TotalRegistros ==0);//Necessita de um controle onde se terminou leitura não é para chamar o atualizaCSV
				this.atualizaCSV();
			//	PrB_ProgressoLeitura.progressProperty().bind(taskCSV.progressProperty());
			//	new Thread(taskCSV).start();
	    		}
				else {
				    Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("INFORME OS DIRETÓRIOS!");
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
				//	updateMessage("\n"+ controller.getRegistrosLidos());
				} while (controller.IsContinuaLeituraCSV());
    			updateMessage("Total Linhas: " + "Total Linha" + "LinhasLidas" + lidos);
    	
//    			do {
//    				updateProgress(controller.getQtdRegistros(), controller.getQtdRegistros());
//    			} while (controller.IsContinuaLeituraCSV());
//    		
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
				//	updateMessage("\n"+ controller.getRegistrosConvertidos());
				} while (controller.IsContinuaLeituraJSON());
    		//	updateMessage("Total Linhas Json: " + "Total Linha" + "LinhasLidas" + lidos);
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
					updateMessage("\n"+ controller.getRegistrosWriter());
				} while (controller.IsContinuaEscrita());
    	
    	//		updateMessage("Total Linhas Json: " + "Total Linha" + "LinhasLidas" + lidos);
    			return null;
    			
    		}
    	};
		txtA_Status.appendText("\n"+taskCSV.getMessage());

		taskJon.messageProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				txtA_Status.appendText("\n"+taskJon.getMessage());

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
    public static void gerarVeiculos(File dir)//Metodo não usado
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
    
	public void UpdateProgress(int progresão, int maximo) {
		try {
			PrB_ProgressoLeitura.setProgress((double)progresão/maximo);
			PrB_ProgressoLeitura.progressProperty().unbind();
			txtA_Status.appendText(progresão + "\n");
			} catch (Exception e) {
				e.printStackTrace();
		
			} 
	}

	public void ProcessoConversao() {
		try {
			 new Controller(dirOriginario, dirDestinado);
			//Processamento.gestaoProcessamentoTestar(PrB_ProgressoLeitura, PrB_ProecessoConversao, PrB_ProecessoEscrita,txtA_Status);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Task task = new Task<Void>() {
		
		@Override
		protected Void call() {
			try {
				final int max = 100000000;
				for (int i=1;i<=max;i++)
				{
					if (isCancelled()){
						break;
					}
					updateProgress(i, max);
					if (i % 100 == 0)
					{
						updateMessage("Processados:"+ i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
			
		}
	};
	public void LerTempos ()
	{
		try {
//			long somTinicio = 0;
//			long medTinicio = 0;
			long somTLeitura = 0;
			long medTLeitura = 0;
			long somTParse = 0;
			long medTParse = 0;
			long somEscrita = 0;
			long medEscrita = 0;
			String row;
			String CaminhoAplicação = System.getProperty("user.dir");
			String CaminoLog = CaminhoAplicação + "\\LogsTempo\\RegistrosTempo.txt";
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
					//somTinicio = somTinicio+ temporizacao.getTempodeAbertura();
					somTLeitura = somTLeitura+ temporizacao.getTempodeLeitura();
					somTParse = somTParse+ temporizacao.getTempodeConversao();
					somEscrita = somEscrita+ temporizacao.getTempodeEscrita();
				}
			//	medTinicio = (somTinicio / ListadeTempos.size());
				medTLeitura = (somTLeitura / ListadeTempos.size());
				medTParse = (somTParse / ListadeTempos.size());
				medEscrita = (somEscrita / ListadeTempos.size());
				txtA_Status.appendText("//////Contabilização de Tempos de Processamento\\\\\\  \n");
				txtA_Status.appendText("//////"+ListadeTempos.size()+" Já Processados! \\\\\\  \n");
				//txtA_Status.appendText("* TEMPO MÉDIO DE ABERTURA: " + medTinicio + "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE LEITURA: " + medTLeitura+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE PARSE: " + medTParse+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE ESCRITA: " + medEscrita+ "Milesegundos \n");
				
			} catch (IOException e) {
				txtA_Status.appendText("//////Não foi possiverler os logs\\\\\\  \n");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			txtA_Status.appendText("//////Não foi possiverler os logs\\\\\\  \n");
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
				//txtA_Status.appendText("* TEMPO MÉDIO DE ABERTURA: " + tempos.get(0) + "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE LEITURA: " + tempos.get(1)+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE PARSE: " + tempos.get(2)+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE ESCRITA: " + tempos.get(3)+ "Milesegundos \n");
				}
				else 
				{
					txtA_Status.appendText("//////O programa não rodou ainda!\\\\\\  \n");
				}
			}
			else 
			{
				txtA_Status.appendText("//////O programa não rodou ainda!\\\\\\  \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


		
	
    
}
