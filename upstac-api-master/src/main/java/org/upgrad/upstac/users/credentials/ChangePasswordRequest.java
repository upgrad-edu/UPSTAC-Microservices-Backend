package org.upgrad.upstac.users.credentials;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordRequest {


    @NotNull
    @Size(min = 8,max = 255)
    String password;

    @NotNull
    @Size(min = 8,max = 255)
    String oldPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


}
