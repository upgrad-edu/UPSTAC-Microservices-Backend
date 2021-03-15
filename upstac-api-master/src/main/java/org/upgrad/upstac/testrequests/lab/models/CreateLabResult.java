package org.upgrad.upstac.testrequests.lab.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateLabResult {

    @NotNull
    private String bloodPressure;

    @NotNull
    private String heartBeat;
    @NotNull
    private String temperature;
    private String oxygenLevel;
    private String comments;
    @NotNull
    private TestStatus result;
}
