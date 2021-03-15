package org.upgrad.upstac.testrequests.consultation.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateConsultationRequest {

    @NotNull
    private DoctorSuggestion suggestion;

    private String comments;
}
