package com.quantumdataengines.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Extraction {
	private Date processStartDate;
	private Date processEndDate;
	private Date startDate;
	private Date endDate;
	private String strProcessStartDate;
	private String strProcessEndDate;
	private String strStartDate;
	private String strEndDateMessage;
	private String strEndDateValue;
	private int status;
	private String lastMessage;
	private List<ExtractionDBMessage> extractionDBMessageList;
	private List<ExtractionDBMessage> extractionErrorMessageList = new ArrayList<ExtractionDBMessage>();
	private List<ExtractionProcedure> totalProceduresList;
	private List<ExtractionProcedure> completedProceduresList;
	private int percentage;
	private String timeMessage;
	private String timeValue;
	private Map<Integer, Map<String,RunningProcedure>> runningProc = new HashMap<Integer, Map<String,RunningProcedure>>();
	private Thread mainThread;
	private Thread processThread;
	private Thread parallelMainThread;
	private List<Thread> parallelThread;
	private Thread sequentialhread;
	
	public Date getProcessStartDate() {
		return processStartDate;
	}
	public void setProcessStartDate(Date processStartDate) {
		this.processStartDate = processStartDate;
	}
	public Date getProcessEndDate() {
		return processEndDate;
	}
	public void setProcessEndDate(Date processEndDate) {
		this.processEndDate = processEndDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStrProcessStartDate() {
		return strProcessStartDate;
	}
	public void setStrProcessStartDate(String strProcessStartDate) {
		this.strProcessStartDate = strProcessStartDate;
	}
	public String getStrProcessEndDate() {
		return strProcessEndDate;
	}
	public void setStrProcessEndDate(String strProcessEndDate) {
		this.strProcessEndDate = strProcessEndDate;
	}
	public String getStrStartDate() {
		return strStartDate;
	}
	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}
	public String getStrEndDateMessage() {
		return strEndDateMessage;
	}
	public void setStrEndDateMessage(String strEndDateMessage) {
		this.strEndDateMessage = strEndDateMessage;
	}
	public String getStrEndDateValue() {
		return strEndDateValue;
	}
	public void setStrEndDateValue(String strEndDateValue) {
		this.strEndDateValue = strEndDateValue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public List<ExtractionDBMessage> getExtractionDBMessageList() {
		List<ExtractionDBMessage> combinedDBMessage = new ArrayList<ExtractionDBMessage>();
		if(getExtractionErrorMessageList() != null)
			combinedDBMessage.addAll(getExtractionErrorMessageList());
		if(extractionDBMessageList != null)
			combinedDBMessage.addAll(extractionDBMessageList);
		return combinedDBMessage;
	}
	public void setExtractionDBMessageList(List<ExtractionDBMessage> extractionDBMessageList) {
		this.extractionDBMessageList = extractionDBMessageList;
	}	
	public List<ExtractionProcedure> getTotalProceduresList() {
		return totalProceduresList;
	}
	public void setTotalProceduresList(List<ExtractionProcedure> totalProceduresList) {
		this.totalProceduresList = totalProceduresList;
	}
	public int getTotalProceduresListSize(){
		return getTotalProceduresList() != null ? getTotalProceduresList().size() : 0;
	}
	
	public List<ExtractionProcedure> getCompletedProceduresList() {
		return completedProceduresList;
	}	
	public int getCompletedProceduresListSize(){
		List<ExtractionProcedure> completedProcesuresList = getCompletedProceduresList();
		return completedProcesuresList != null ? completedProcesuresList.size() : 0;
	}
	
	public void setCompletedProceduresList(List<ExtractionProcedure> completedProceduresList) {
		this.completedProceduresList = new  CopyOnWriteArrayList<ExtractionProcedure>();
		Collections.synchronizedList(this.completedProceduresList);
		this.completedProceduresList.addAll(completedProceduresList);
	}
	
	public int getPercentage() {
		int completeCount = getCompletedProceduresListSize();
		int totalCount = getTotalProceduresListSize();
		this.percentage = totalCount > 0 ? (completeCount*100)/totalCount : 0;
		return this.percentage;
	}
	
	public String getTimeMessage() {
		return timeMessage;
	}
	public void setTimeMessage(String timeMessage) {
		this.timeMessage = timeMessage;
	}
	public String getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}
	public Map<Integer, Map<String, RunningProcedure>> getRunningProc() {
		return runningProc;
	}
	public void setRunningProc(Map<Integer, Map<String, RunningProcedure>> runningProc) {
		this.runningProc = runningProc;
	}
	
	public int getProceduresCompletedInGroup(int a_intGroupId){
		List<ExtractionProcedure> completedProcesuresList = getCompletedProceduresList();
		int l_intProcedureCompletedCount = 0;
		try{
			for(ExtractionProcedure a_objProcedure : completedProcesuresList){
				if(a_objProcedure.getGroupId() == a_intGroupId){
					l_intProcedureCompletedCount++;
				}
			}
		}catch(Exception e){}
		return l_intProcedureCompletedCount;
	}
	
	public Thread getMainThread() {
		return mainThread;
	}
	public void setMainThread(Thread mainThread) {
		this.mainThread = mainThread;
	}
	public Thread getProcessThread() {
		return processThread;
	}
	public void setProcessThread(Thread processThread) {
		this.processThread = processThread;
	}
	public List<Thread> getParallelThread() {
		return parallelThread;
	}
	public void setParallelThread(List<Thread> parallelThread) {
		this.parallelThread = parallelThread;
	}
	public Thread getParallelMainThread() {
		return parallelMainThread;
	}
	public void setParallelMainThread(Thread parallelMainThread) {
		this.parallelMainThread = parallelMainThread;
	}
	public Thread getSequentialhread() {
		return sequentialhread;
	}
	public void setSequentialhread(Thread sequentialhread) {
		this.sequentialhread = sequentialhread;
	}
	public List<ExtractionDBMessage> getExtractionErrorMessageList() {
		return extractionErrorMessageList;
	}
	public void addExtractionErrorMessageList(ExtractionDBMessage extractionErrorMessageList) {
		this.extractionErrorMessageList.add(extractionErrorMessageList);
	}
	
}
