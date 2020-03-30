package com.razzotto.worker;

import javafx.concurrent.Task;

public class Task1 extends Task<String> {

	@Override
	protected String call() throws Exception {
		  for (int i =1000; i<2000; i++) {
	            this.updateProgress(i,2000); // currently updates mainRun.java progress bar NOT GUI.java
	        }
		return null;
	}

}
