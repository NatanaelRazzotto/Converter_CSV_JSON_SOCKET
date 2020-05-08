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
			ObjectOutputStream saida= new ObjectOutputStream (conexao.getOutputStream());
			ObjectInputStream scannerServer = new ObjectInputStream(conexao.getInputStream());
			saida.flush();
			Boolean defMantemConected = true;
			Boolean defNovoProcesso = true;
			System.out.print("---------Inicio Comunicação------ ");

			try {
				while (true)
				{
					saida.flush();
					Arquivo arquivo = new Arquivo(dirOriginario,dirDestinado,defMantemConected,defNovoProcesso);
					// Envia Dados//
					saida.writeObject(arquivo);
					//Recebe dados//
					Arquivo arquivorecebido = (Arquivo) scannerServer.readObject();
					    if (arquivorecebido.getManterConectado()==true)
					    {
					    	if (arquivorecebido.getStartNovoProcesso()==false)
					    		defNovoProcesso = false;
					    controllerClient.agregarProcessamento(arquivorecebido);
					    }
					    else
					    {
					    	controllerClient.agregarProcessamento(arquivorecebido);
							conexao.close();
					    	break;
					    }
				}
	
			} catch (ClassNotFoundException e) {
				System.out.println("Erro de conexão - Cliente desconectou inesperadamente!!!");
				conexao.close();
				e.printStackTrace();
			}
			
			saida.flush();
			saida.close();
			conexao.close();
			System.out.println("ConexÃ£o encerrada!!!!");
		
		

		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
