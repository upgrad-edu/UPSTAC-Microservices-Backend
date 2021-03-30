package org.upgrad.upstac.config.loaddata;

import lombok.Data;


@Data
 public class TestData {
    Integer sno;



      public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiagnosisInProcess() {
		return diagnosisInProcess;
	}
	public void setDiagnosisInProcess(String diagnosisInProcess) {
		this.diagnosisInProcess = diagnosisInProcess;
	}
	public String getLabTestInProgress() {
		return labTestInProgress;
	}
	public void setLabTestInProgress(String labTestInProgress) {
		this.labTestInProgress = labTestInProgress;
	}
	public String getLabTestCompleted() {
		return labTestCompleted;
	}
	public void setLabTestCompleted(String labTestCompleted) {
		this.labTestCompleted = labTestCompleted;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getInitiated() {
		return initiated;
	}
	public void setInitiated(String initiated) {
		this.initiated = initiated;
	}
	public String getTestpositive() {
		return testpositive;
	}
	public void setTestpositive(String testpositive) {
		this.testpositive = testpositive;
	}
	public String getAdmit() {
		return admit;
	}
	public void setAdmit(String admit) {
		this.admit = admit;
	}
	public String getQuarantine() {
		return quarantine;
	}
	public void setQuarantine(String quarantine) {
		this.quarantine = quarantine;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	String name;
    String diagnosisInProcess;
    String labTestInProgress;
    String labTestCompleted;
    String completed;
    String initiated;
    String testpositive;
    String admit;
    String quarantine;
     Integer pincode;

    public boolean isDiagnosisInProcess(){
        return isYes(diagnosisInProcess);
    }
    public boolean isLabTestInProgress(){
        return isYes(labTestInProgress);
    }
    public boolean isLabTestCompleted(){
        return isYes(labTestCompleted);
    }
    public boolean isTestPositive(){
        return isYes(testpositive);
    }
    public boolean isCompleted(){
        return isYes(completed);
    }

    public boolean canAdmit(){
        return isYes(admit);
    }
    public boolean canHomeQuarantine(){
        return isYes(quarantine);
    }

    public boolean canAssignForLabTest(){
        return isCompleted() || isDiagnosisInProcess() || isLabTestCompleted() || isLabTestInProgress() ;
    }
    public boolean canUpdateLabTest(){
        return isCompleted() || isDiagnosisInProcess() || isLabTestCompleted()  ;
    }

    public boolean canAssignConsultation(){
        return isCompleted() || isDiagnosisInProcess()  ;
    }

    public boolean canCompleteTest(){
        return isCompleted() ;
    }

    public boolean isYes(String input){
        return input.toLowerCase().equalsIgnoreCase("Y");
    }
}
