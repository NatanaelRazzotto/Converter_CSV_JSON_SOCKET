package com.razzotto.Worker;

import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;

import com.razzotto.Controller.Controller;

public class TratamentoTempo {
	private Controller controller;

	public TratamentoTempo() {
		//this.controller = controladora;
		
	}

	public synchronized String obterDuracao(Controller controladora,Instant inicio, Instant fim, Integer chave) {
		try {
			if (!controladora.ContabilidadeTempo.containsKey(chave))
			{
			Duration decorrido = Duration.between(inicio, fim);
			long decorridoMilissegundos = decorrido.toMillis();
			String Retorno = Long.toString(decorridoMilissegundos);
			controladora.ContabilidadeTempo.put(chave, Retorno);
			return Retorno;
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
	public void GerarLogsdeTempo(Controller controladora) {
		try {
			
				String CaminhoAplicação = System.getProperty("user.dir");
				String CaminoLog = CaminhoAplicação + "\\LogsTempo\\RegistrosTempo.txt";
				System.out.println(CaminhoAplicação);
				FileWriter writerLogs = new FileWriter(CaminoLog, true);
				if (controladora.ContabilidadeTempo.containsKey(1))
				{
				writerLogs.append(controladora.ContabilidadeTempo.get(1)+ ",");
				}
				if (controladora.ContabilidadeTempo.containsKey(2))
				{
					writerLogs.append(controladora.ContabilidadeTempo.get(2)+ ",");
				}
				if (controladora.ContabilidadeTempo.containsKey(3))
				{
					writerLogs.append(controladora.ContabilidadeTempo.get(3)+ ",");
				}
//				for (String tempoContabilizado : controladora.ContabilidadeTempo.values()) {
//					writerLogs.append(tempoContabilizado + ",");
//				}
				writerLogs.append("\n");
				writerLogs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}
