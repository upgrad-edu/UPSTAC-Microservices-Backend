package org.upgrad.upstac.config.loaddata;

import lombok.Data;


@Data
 public class TestData {
    Integer sno;



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
