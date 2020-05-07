package com.razzotto.Worker;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.razzotto.Entidade.Arquivo;
import com.razzotto.Entidade.ProgressoArquivo;

import InterfaceUICliente.ControllerInterfaceClient;

public class AplicacaoCliente extends Thread{
	private ControllerInterfaceClient controllerClient;
	private String endereco;
//	private Socket cliente;
	private Socket conexao;
	//Arquivo arquivo;
  //  private ObjectOutputStream saida;
  //  private ObjectInputStream scannerServer;
    File dirOriginario;
    File dirDestinado;
    Integer porta;
    
	//private PrintStream saida;
	Boolean mensagem;
	Boolean NovoProcesso;
	public AplicacaoCliente(ControllerInterfaceClient controller,Integer pota, File dirArquivoOriginario, File dirDestinadoSalvamento) {
		try {
			endereco = InetAddress.getByName("localhost").getHostAddress();
//			try {
				System.out.println("tesndao");
	   			System.out.println(dirOriginario);
				System.out.println(dirDestinado);
				controllerClient = controller;
		
				porta =pota;

			//	saida= new ObjectOutputStream (cliente.getOutputStream());
			//	scannerServer = new ObjectInputStream(cliente.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
	}
	public AplicacaoCliente(Socket s, File dirArquivoOriginario, File dirDestinadoSalvamento,ControllerInterfaceClient controller){//recebe o valor do socket enviado na thread
		conexao = s;
		dirOriginario = dirArquivoOriginario;
		dirDestinado = dirDestinadoSalvamento;
		controllerClient = controller;
	}



	public void run() {
		try {
		//	PrintStream saida = new PrintStream(conexao.getOutputStream());
			ObjectOutputStream saida= new ObjectOutputStream (conexao.getOutputStream());
			ObjectInputStream scannerServer = new ObjectInputStream(conexao.getInputStream());
			saida.flush();
			Boolean mensagem = true;
			Boolean mensagema = true;
			System.out.print("teste ");
			System.out.println("COME�OU");
	//		saida.writeObject("teste");
			try {
				while (true)
				{
					saida.flush();
					Arquivo arquivo = new Arquivo(dirOriginario,dirDestinado,mensagem,mensagema);
					System.out.println("dfdsfdsfsdfsdf" + dirOriginario);
					saida.writeObject(arquivo);
					System.out.println( arquivo.getStartNovoProcesso());
			
		
			
						Arquivo arquivorecebido = (Arquivo) scannerServer.readObject();
					    if (arquivorecebido.getManterConectado()==true)
					    {
					    	if (arquivorecebido.getStartNovoProcesso()==false)
						    {
					    		mensagema = false;
					    		System.out.println("deu false" + arquivorecebido.getStartNovoProcesso());
						    }
					    	else
					    	{
					    		System.out.println("deu true" + arquivorecebido.getStartNovoProcesso());
					    	}
					    controllerClient.agregarProcessamento(arquivorecebido);
						System.out.println(arquivorecebido);
						System.out.println(arquivorecebido.getProgressLeitura());
					    }
					    else
					    {
					    	break;
					    }
						

				}
	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			saida.flush();
			saida.close();
			conexao.close();
			
//			while (!conexao.isClosed()) {
//				System.out.print("teste ");
//				Arquivo arquivo = new Arquivo(dirOriginario,dirDestinado,mensagem,true);
//				System.out.print("Objeto arquivo ");
//				saida.writeObject("teste");
//			//	saida.println(mensagem);
//
//				if (mensagem.equals("sair") || !conexao.isConnected())
//					conexao.close();
//				 mensagem = false;
//			}
			System.out.println("Conexão encerrada!!!!");
		
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
//		try {
//	//		endereco = InetAddress.getByName("localhost").getHostAddress();
//	//		Socket cliente = new Socket(endereco,porta);
//			ObjectOutputStream saida= new ObjectOutputStream (conexao.getOutputStream());
//			ObjectInputStream scannerServer = new ObjectInputStream(conexao.getInputStream());
//			do {
//
//				
//				Arquivo arquivo = new Arquivo(dirOriginario,dirDestinado,mensagem,NovoProcesso);
//				System.out.print("Objeto arquivo ");
//				saida.writeObject(arquivo);
//				if (mensagem == false || !conexao.isConnected())
//					conexao.close();
//				else 
//				{
//					//if (scannerServer.readObject() != null)
//					//{
//				
//					Arquivo arquivorecebido;
//					try {
//						arquivorecebido = (Arquivo) scannerServer.readObject();
//						mensagem = arquivorecebido.getTerminouEscrita();
//						controllerClient.agregarProcessamento(arquivorecebido);
//						System.out.println("teste");
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	
//					//}
//				}
//				NovoProcesso = false;
//			
//				
//			} while (!conexao.isClosed());{
//				System.out.println("Conexão encerrada!!!!");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
}
