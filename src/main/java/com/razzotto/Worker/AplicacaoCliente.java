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

public class AplicacaoCliente {
	private String endereco;
	private Socket cliente;
    private ObjectOutputStream saida;
	//private PrintStream saida;
	String mensagem;
	public AplicacaoCliente(Integer porta) {
		try {
			endereco = InetAddress.getByName("localhost").getHostAddress();
			try {
				cliente = new Socket(endereco,porta);
				saida= new ObjectOutputStream (cliente.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void conectorClienteServer (String dirArquivoOriginario, String dirDestinadoSalvamento) throws IOException
	{
//		Arquivo arquivo = new Arquivo(dirArquivoOriginario, dirDestinadoSalvamento);
//		try {
//			saida.writeObject(arquivo);
//			cliente.close();
//			System.out.println("Conex„o encerrada!!!!");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		mensagem = "caminho";
		while (!cliente.isClosed()) {
			//System.out.print("Digite sua mensagem (ou digite \"sair\"): ");
			Arquivo arquivo = new Arquivo(dirArquivoOriginario, dirDestinadoSalvamento);
			System.out.print("Objeto arquivo ");
			saida.writeObject(arquivo);

			if (dirDestinadoSalvamento.equals("sair") || !cliente.isConnected())
				cliente.close();
			dirDestinadoSalvamento = "sair";
		}
		System.out.println("Conex√£o encerrada!!!!");

	}
}
