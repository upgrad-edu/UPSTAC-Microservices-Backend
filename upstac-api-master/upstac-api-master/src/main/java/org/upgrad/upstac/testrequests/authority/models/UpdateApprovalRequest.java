package org.upgrad.upstac.testrequests.authority.models;

import lombok.Data;
import org.upgrad.upstac.users.models.AccountStatus;

import javax.validation.constraints.NotNull;

@Data
public class UpdateApprovalRequest {

    @NotNull
    Long userId;

    @NotNull
    AccountStatus status;


}
