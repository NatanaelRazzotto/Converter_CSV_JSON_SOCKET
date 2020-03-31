package com.razzotto;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.google.gson.Gson;
import com.razzotto.model.Processamento;
import com.razzotto.worker.ConversaoArquivos;
import com.razzotto.worker.EscritaArquivos;
import com.razzotto.worker.Temporizacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class InicialController {
    @FXML
    private Button btn_AbrirArquivo;
    @FXML
    private Button btn_SalvarArquivo;
    @FXML
    private Button btn_ConverteArquivo;
    @FXML
    private Button btn_TEMPO;
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
    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
			if ((dirOriginario != null)&&(dirDestinado!=null)) {
				btn_ConverteArquivo.setDisable(true);
				btn_AbrirArquivo.setDisable(true);
				btn_SalvarArquivo.setDisable(true);
	    		txtA_Status.appendText("----------BEM VINDO-----------" + "\n");
	   			System.out.println(dirOriginario);
				System.out.println(dirDestinado);
	    		//Calcula();
				ProcessoConversao();
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
  /*  public void Salva_ArquivoJSON() {
    	JFrame parentFrame = new JFrame();
    	JFileChooser file_chooser = new JFileChooser();
    	file_chooser.setDialogTitle("Onde Deseja Salvar o Arquivo");
    	int userSelection = file_chooser.showSaveDialog(parentFrame);
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    File fileToSave = file_chooser.getSelectedFile();
    	    gerarVeiculos(fileToSave);
      	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    	}
    	
    }*/
    public static void gerarVeiculos(File dir)
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
			// TODO Auto-generated catch block
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
		/*txtA_Status.appendText("Quantidade Arquivo " + Processamento.ContabilizarArquivo() + "\n");
		Processamento.Leitura();/*Constroi a ListaPessoas de retorno do arquivo
		txtA_Status.appendText("Leitura e preparação Concluidos "+ "\n");
		Thread.sleep(1000);
		Instant inicioLeituraFile =Instant.now();
		ContadorProgresso = 0;
		Thread Thread1 = new Thread(new ConversaoArquivos("Thread1"));
		Thread Thread2 = new Thread(new ConversaoArquivos("Thread2"));
		Thread Thread3 = new Thread(new EscritaArquivos("Thread3",dirDestinado,0));
		Thread1.start();
		Thread2.start(); 
		Thread3.start();
		Thread3.join();
		Vector<String>totalTempo = Processamento.ContabilidadeTempo;
		txtA_Status.appendText("Contabilização dos TEMPOS: "+ "\n");
		for (String tempo : totalTempo) {
			txtA_Status.appendText(tempo+ "\n");
		}*/
			
			Processamento.gestaoProcessamento(PrB_ProgressoLeitura, PrB_ProecessoConversao, PrB_ProecessoEscrita,txtA_Status);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void LerTempos ()
	{
		try {
		//	txtA_Status.appendText("Arquivo" + CaminhoAplicação);
			long somTinicio = 0;
			long medTinicio = 0;
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
				    tempo.setTempodeAbertura(Long.parseLong(arquivoMemoria[0]));
				    tempo.setTempodeLeitura(Long.parseLong(arquivoMemoria[1]));
				    tempo.setTempodeConversao(Long.parseLong(arquivoMemoria[2]));
				    tempo.setTempodeEscrita(Long.parseLong(arquivoMemoria[3]));
				    ListadeTempos.add(tempo);
				}
				for (Temporizacao temporizacao : ListadeTempos) {
					somTinicio = somTinicio+ temporizacao.getTempodeAbertura();
					somTLeitura = somTLeitura+ temporizacao.getTempodeLeitura();
					somTParse = somTParse+ temporizacao.getTempodeConversao();
					somEscrita = somEscrita+ temporizacao.getTempodeEscrita();
				}
				medTinicio = (somTinicio / ListadeTempos.size());
				medTLeitura = (somTLeitura / ListadeTempos.size());
				medTParse = (somTParse / ListadeTempos.size());
				medEscrita = (somEscrita / ListadeTempos.size());
				txtA_Status.appendText("//////Contabilização de Tempos de Processamento\\\\\\  \n");
				txtA_Status.appendText("//////"+ListadeTempos.size()+" Já Processados! \\\\\\  \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE ABERTURA: " + medTinicio + "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE LEITURA: " + medTLeitura+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE PARSE: " + medTParse+ "Milesegundos \n");
				txtA_Status.appendText("* TEMPO MÉDIO DE ESCRITA: " + medEscrita+ "Milesegundos \n");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
	
    
}
