package com.quantumdataengines.app.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.quantumdataengines.app.model.Extraction;

@Component
public class ExtractionUtil {
	private Map<String, Extraction> extractionDetails = new HashMap<String, Extraction>();
	
	public void newExtractionInstance(String instanceName){
		Extraction extraction = new Extraction();
		extractionDetails.put(instanceName, extraction);
	}
	
	public Extraction getInstance(String instance){
		return extractionDetails.get(instance);
	}
}
