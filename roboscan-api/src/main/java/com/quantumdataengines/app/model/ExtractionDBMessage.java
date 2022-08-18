package com.quantumdataengines.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExtractionDBMessage {
	
	private int rowNum;
	private String dateTime;
	private String processName;
	private String statusMessage;

}
