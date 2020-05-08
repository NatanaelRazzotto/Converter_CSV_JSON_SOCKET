package com.razzotto.Worker.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.razzotto.Controller.Controller;
import com.razzotto.Entidade.Arquivo;
import com.razzotto.Entidade.ProgressoArquivo;

import javafx.concurrent.Task;

public class AplicacaoServer extends Thread{
	Controller controller;
	private Socket conexao;
	public AplicacaoServer(Socket s){
		conexao = s;
	}
	public AplicacaoServer() throws ClassNotFoundException, IOException
	{
		ServerSocket server = null;
		try {
			server = new ServerSocket(49152);
			while(true){	
			System.out.println("Antes do Accept");
			Socket Conexao = server.accept();
			System.out.println("depois do Accept");
			Thread t = new AplicacaoServer(Conexao);
			t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run(){
		ObjectInputStream scanner;
		ObjectOutputStream outToClient;
		boolean controleConectado;
		boolean controleNovo = true;
		Arquivo arquivo = new Arquivo();
		arquivo.setManterConectado(true);
		try {
			scanner = new ObjectInputStream(conexao.getInputStream());
			outToClient = new ObjectOutputStream(conexao.getOutputStream());
			System.out.println("COMEÇOU");
			int i = 0;
		while (!conexao.isClosed()) {
			try {
			    outToClient.flush();
			  
			    if (arquivo.getManterConectado()==true)
			    {
					arquivo  = (Arquivo) scanner.readObject();
					System.out.println(arquivo);
					System.out.println(arquivo.getDiretorioOriginario());
					System.out.println("status   " + arquivo.getStartNovoProcesso());

			 
			    	 if (arquivo.getStartNovoProcesso()==true)
					    {
			    		 if (i == 0)
			    		 {
			    	    	System.out.println("---Começando novo Processamento------");
			       			controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
			    			Thread tp = new Thread(controller);
			    			tp.start();
			    			i++;
			    		 }
						    controleNovo = false;
						    arquivo = obteratualizacoes(arquivo,controleNovo,true);
							outToClient.writeObject(arquivo);
					    }
			    	 else
			    	 {
			    		 System.out.println("Processando----------");
						 arquivo = obteratualizacoes(arquivo,controleNovo,true);
					     outToClient.writeObject(arquivo);
			    	 }

			    }
			    else
			    {
			    	System.out.println("Encerrando Conexao");
			    	conexao.close();
			    	break;
			    }
			    System.out.println("Realizou Processo conectado");
			} catch (NoSuchElementException | ClassNotFoundException e) {
				System.out.println("Erro de conexÃ£o - Cliente desconectou inesperadamente!!!");
				conexao.close();
			}

		}
	    outToClient.flush();
	    outToClient.close();
		conexao.close();
		System.out.println("Servidor encerrado.");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    	
	}
	public Arquivo obteratualizacoes(Arquivo arquivonovo, boolean controleNovo, boolean controleConexao)
	{
		int totalRegistros = controller.getQtdRegistros();
		boolean definicao = true;
		if (controller.IsContinuaProcessamento()==false)
		{
			if ((controller.getRegistrosLidos()<=totalRegistros-1)||(controller.getRegistrosConvertidos()<=totalRegistros-1)||(controller.getRegistrosWriter()<=totalRegistros-1))
			{
				definicao = true;
			}
			else
			{
				definicao = false;
			}
		}

		arquivonovo.setProgressLeitura(controller.getRegistrosLidos());
		arquivonovo.setProgressConversao(controller.getRegistrosConvertidos());
		arquivonovo.setProgressEscrita(controller.getRegistrosWriter());
		arquivonovo.setProgressFilaCSV(controller.IsSizeListaCSV());
		arquivonovo.setProgressFilaJson(controller.IsSizeListaJson());
		arquivonovo.setTamanhodoArquivo(totalRegistros);
		arquivonovo.setManterConectado(definicao);
		arquivonovo.setIniciouLeitura(controller.IsinicioLeitura());
		arquivonovo.setIniciouConversao(controller.IsinicioEscrita());
		arquivonovo.setIniciouEscrita(controller.IsinicioEscrita());
		arquivonovo.setTerminouLeitura(controller.IsContinuaLeituraCSV());
		arquivonovo.setTerminouLeitura(controller.IsContinuaLeituraJSON());
		arquivonovo.setTerminouEscrita(controller.IsContinuaEscrita());
		
		arquivonovo.setStartNovoProcesso(controleNovo);
		return arquivonovo;
		
	}


}
