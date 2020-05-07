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
	public AplicacaoServer(Socket s){//recebe o valor do socket enviado na thread
		conexao = s;
	}
	public AplicacaoServer() throws ClassNotFoundException, IOException
	{
		ServerSocket server = null;
		try {
			server = new ServerSocket(12345);
			while(true){	
			System.out.println("Antes do Accept");
			Socket Conexao = server.accept();
			System.out.println("depois do Accept");
			Thread t = new AplicacaoServer(Conexao);
			t.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	//	conectorClienteServer();
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
			//scanner = new Scanner(conexao.getInputStream());
			System.out.println("COME�OU");

		while (!conexao.isClosed()) {
			try {
				//Arquivo arquivo = new Arquivo();
			    outToClient.flush();
			  
			    if (arquivo.getManterConectado()==true)
			    {
				arquivo  = (Arquivo) scanner.readObject();
				System.out.println(arquivo);
				System.out.println(arquivo.getDiretorioOriginario());
				System.out.println("status   " + arquivo.getStartNovoProcesso());

			 
			    	 if (arquivo.getStartNovoProcesso()==true)
					    {
			    	    	System.out.println("true----------");
			       			controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
			       			controller.Inicia();
			       			System.out.println(arquivo.getDiretorioOriginario());
			       			System.out.println("destino " +arquivo.getDiretorioDestinado());
						
						    arquivo.setStartNovoProcesso(false);
						    controleNovo = false;
						    System.out.println( arquivo.getStartNovoProcesso());
						    
							int TotalRegistros =0;
							do {
								TotalRegistros = controller.getQtdRegistros();
								if (!controller.IsContinuaLeituraCSV())
									break;
								
							} while (TotalRegistros == 0);
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
			    

//			    boolean cont = true;
//			    while (cont) {
//			    	arquivo = obteratualizacoes(arquivo);
//			    	outToClient.writeObject(arquivo);
//			    }
				
					
				
	
			//   Arquivo arquivoEnvio = new Arquivo();
			  //  while (arquivoEnvio.getTerminouEscrita()) {
			//	arquivo.setProgressLeitura(10000);
			    	
			    	//ProgressoArquivo pa = new ProgressoArquivo();
				
					//System.out.println(pa);
			//    }
			    System.out.println("Terminou server");


				//String recebido = scanner.nextLine();
				//System.out.println(arquivo);
			 //   outToClient.flush();
			//    outToClient.close();
			//	conexao.close();
//				if (arquivo.getManterConectado()==false) {
//					conexao.close();
//				}

			//	System.out.println(arquivo.getDiretorioOriginario());
				
			} catch (NoSuchElementException | ClassNotFoundException e) {
				System.out.println("Erro de conexão - Cliente desconectou inesperadamente!!!");
				conexao.close();
			}

		}
	    outToClient.flush();
	    outToClient.close();
		conexao.close();
		System.out.println("Servidor encerrado.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	
	}
	public Arquivo obteratualizacoes(Arquivo arquivonovo, boolean controleNovo, boolean controleConexao)
	{
		//Arquivo arquivonovo = new Arquivo();

		arquivonovo.setProgressLeitura(controller.getRegistrosLidos());
		arquivonovo.setProgressConversao(controller.getRegistrosConvertidos());
		arquivonovo.setProgressEscrita(controller.getRegistrosWriter());
		arquivonovo.setTamanhodoArquivo(controller.getQtdRegistros());
		arquivonovo.setTerminouEscrita(controller.IsContinuaEscrita());
		arquivonovo.setManterConectado(controller.IsContinuaEscrita());
		arquivonovo.setStartNovoProcesso(controleNovo);
		return arquivonovo;
		
	}

	

//	public void conectorClienteServer () throws IOException, ClassNotFoundException
//	{
//			//ServerSocket server = new ServerSocket(12345);
//			
//		
//		//	Socket cliente = server.accept(); // blocante
//	
//			ObjectInputStream scanner = new ObjectInputStream(cliente.getInputStream());
//			ObjectOutputStream outToClient = new ObjectOutputStream(cliente.getOutputStream());
//		
//			while (!cliente.isClosed()) {
//				try {
//					Arquivo arquivo = new Arquivo();
//					arquivo = (Arquivo) scanner.readObject();
//				//	System.out.println("Object received = " + arquivo);
////					Controller controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
////					controller.Inicia();
//					//String recebido = scanner.nextLine();
//					if (arquivo.getManterConectado() == false) {
//						cliente.close();
//					}
//					else 
//					{
//						if (arquivo.getStartNovoProcesso()== true) {
//							controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
//    						controller.Inicia();
//							arquivo.setIniciouLeitura(true);
//							System.out.println(arquivo);
//			                outToClient.writeObject(obteratualizacoes(arquivo));
//						}
//						else
//						{
//							System.out.println(arquivo);
//			                outToClient.writeObject(obteratualizacoes(arquivo));
//						}
//
//					}
//	
//					System.out.println("Object received = " + arquivo);
//					
//				} catch (NoSuchElementException e) {
//					System.out.println("Erro de conexão - Cliente desconectou inesperadamente!!!");
//					cliente.close();
//				}
//	
//			}
//			System.out.println("Servidor encerrado.");
//		}

}
