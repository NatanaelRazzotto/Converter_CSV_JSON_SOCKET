package com.razzotto.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.razzotto.Entidade.Arquivo;

public class AplicacaoServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
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
					Arquivo student = (Arquivo) scanner.readObject();
					System.out.println("Object received = " + student);
					//String recebido = scanner.nextLine();
					if (student.getDiretorioDestinado().equals("sair")) {
						cliente.close();
					}
	
					System.out.println("Object received = " + student);
					
				} catch (NoSuchElementException e) {
					System.out.println("Erro de conexão - Cliente desconectou inesperadamente!!!");
					cliente.close();
				}
	
			}
			System.out.println("Servidor encerrado.");
		}
}
