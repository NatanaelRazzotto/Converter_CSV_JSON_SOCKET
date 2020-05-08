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
import com.razzotto.Worker.Client.AplicacaoCliente;

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
    private ProgressBar PrB_ProecessoFilaConv;
    @FXML
    private ProgressBar PrB_ProecessoFilaEscrt;
    
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
     File dirDestinado;
     private int TotalRegistros;
     ///
     Controller controller;
 	@Override
 	public void initialize(URL location, ResourceBundle resources) {
 		//btn_ConverteArquivo.setDisable(true);
 		 		
 	}
     @FXML////////////////////////////////////////////////////////////!!!!!!!!
     private void AbrirTempo(ActionEvent event) {
     	try {
     		LerTempos();
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
				btn_ConverteArquivo.setDisable(true);
				btn_TEMPO.setDisable(false);
	    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
	   			System.out.println(dirOriginario);
				System.out.println(dirDestinado);
				new ControllerInterfaceClient(this, PrB_ProgressoLeitura, PrB_ProecessoConversao, PrB_ProecessoEscrita, PrB_ProecessoFilaConv, PrB_ProecessoFilaEscrt, txtA_Status, dirOriginario, dirDestinado);
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
						somTLeitura = somTLeitura+ temporizacao.getTempodeLeitura();
					somTParse = somTParse+ temporizacao.getTempodeConversao();
					somEscrita = somEscrita+ temporizacao.getTempodeEscrita();
				}
				Temporizacao ultimatomada = ListadeTempos.get(ListadeTempos.size()-1);
				txtA_Status.appendText(" \n //////O ultimo processamento teve como resultado!\\\\\\  \n");
				txtA_Status.appendText("* TEMPO DE LEITURA: " +ultimatomada.getTempodeLeitura() + "Milesegundos \n");
				txtA_Status.appendText("* TEMPO DE PARSE: " + ultimatomada.getTempodeConversao() + "Milesegundos \n");
				txtA_Status.appendText("* TEMPO DE ESCRITA: " + ultimatomada.getTempodeEscrita() + "Milesegundos \n");
				medTLeitura = (somTLeitura / ListadeTempos.size());
				medTParse = (somTParse / ListadeTempos.size());
				medEscrita = (somEscrita / ListadeTempos.size());
				txtA_Status.appendText("//////Contabilização de Tempos de Processamento\\\\\\  \n");
				txtA_Status.appendText("//////"+ListadeTempos.size()+" Já Processados! \\\\\\  \n");
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
