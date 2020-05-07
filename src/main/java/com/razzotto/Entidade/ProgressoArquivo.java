package com.razzotto.Entidade;

import java.io.File;
import java.io.Serializable;

public class ProgressoArquivo implements Serializable{
	private static final long serialVersionUID = 5950169519310163575L;
	int ProgressLeitura =1;
	int ProgressConversao= 3;
	int ProgressEscrita;
	int ProgressFilaCSV;
	int ProgressFilaJson;
	int tamanhodoArquivo;
}
