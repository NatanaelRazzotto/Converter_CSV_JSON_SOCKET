package com.razzotto.Worker.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.razzotto.Controller.Controller;
import com.razzotto.Entidade.Arquivo;

public class AplicacaoServer {
	public AplicacaoServer() throws ClassNotFoundException, IOException
	{
		conectorClienteServer();
	}

	public static void conectorClienteServer () throws IOException, ClassNotFoundException
	{
			ServerSocket server = new ServerSocket(12345);
			
			System.out.println("Antes do Accept");
			Socket cliente = server.accept(); // blocante
	
			ObjectInputStream scanner = new ObjectInputStream(cliente.getInputStream());
		
			while (!cliente.isClosed()) {
				try {
					Arquivo arquivo = (Arquivo) scanner.readObject();
				//	System.out.println("Object received = " + arquivo);
//					Controller controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
//					controller.Inicia();
					//String recebido = scanner.nextLine();
					if (arquivo.getNomeArquivo().equals("sair")) {
						cliente.close();
					}
					else 
					{
						Controller controller = new Controller(arquivo.getDiretorioOriginario(),arquivo.getDiretorioDestinado());
						controller.Inicia();
					}
	
					System.out.println("Object received = " + arquivo);
					
				} catch (NoSuchElementException e) {
					System.out.println("Erro de conexão - Cliente desconectou inesperadamente!!!");
					cliente.close();
				}
	
			}
			System.out.println("Servidor encerrado.");
		}
}
