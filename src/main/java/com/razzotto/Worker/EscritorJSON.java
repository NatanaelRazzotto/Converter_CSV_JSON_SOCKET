package com.razzotto.Worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.razzotto.Controller.Controller;

public class EscritorJSON implements Runnable{
	private File file;
	FileWriter writer;
	private Controller controller;
	public void salvar(String DiretorioDestino, List<String>FilaJson) {
		Path path = Paths.get("C:\\Users\\Casa\\Documents\\TESTES PROGRAMAS CSV\\i3");
		System.out.println(DiretorioDestino);
		try {
			for (String lista : FilaJson)
			{
				System.out.println(lista);
			}
			//Files.write(path,FilaJson,StandardCharsets.ISO_8859_1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public EscritorJSON(File filedestino, Controller controladora) {
		try {
			this.file = filedestino;
			this.controller = controladora;
			writer = new FileWriter(this.file, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void EncerrarArquivo() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("N�o encerrou arquivo");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
//			System.out.println(controller.ObjetosJson.get(0) +"restante");
			

	//		System.out.println(controller.ObjetosJson.get(1) +"restante");
			do {		
				if(controller.ObjetosJson.size()==0)
				{
					synchronized (this) {
						try {
							this.wait(1000);
							System.out.println(Thread.currentThread().getName() + " foi para Wait.");
						} catch (Exception e) {
							System.out.println("ParsePessoa: " + e.getMessage());
						}
					}
				}
				else 
				{
					//	System.out.println(controller.ObjetosJson.get(0) +"restante");
					String pessoaAtualGson = controller.obterJson();
				//controller.ObjetosJson.remove(0);
					if (pessoaAtualGson!=null)
					{
					controller.setRegistrosWriter();
					writer.write(pessoaAtualGson +"\n");
					}
				}
			}
			while(controller.emOperacaoJson()); {
			//	writer.write("Terminou");
				controller.setContinuaEscrita(false);
				System.out.println(controller.ObjetosJson.size() +"restante");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
