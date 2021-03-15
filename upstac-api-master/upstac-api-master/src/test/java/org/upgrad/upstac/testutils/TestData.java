package org.upgrad.upstac.testutils;

import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class TestData {



    public static final String DEFAULT_USER="user";
    public static final String DEFAULT_PASSWORD="password";
    public static final String DEFAULT_AUTHORITY="authority";
    public static final String USER_WITH_COMPLETED_TEST="ashgiovannilli";
    public static final String DOCTOR_WITH_COMPLETED_CONSULTATION ="doctor";
    public static final String TESTER_WITH_COMPLETED_LABTEST ="tester";
    public static final String UNAPPROVED_DOCTOR ="doctorunknown";
    public static final Long UNAPPROVED_DOCTOR_ID =209L;
    public static final Long REQUEST_ID_FOR_COMPLETED_TEST =1L;


    public static RequestPostProcessor getMockedUserCredentials(){

        return user(DEFAULT_USER).password("password").roles("USER");
    }
    public static RequestPostProcessor getMockedGovernmentAuthorityCredentials(){

        return user(DEFAULT_AUTHORITY).password("password").roles("GOVERNMENT_AUTHORITY");
    }
    public static RequestPostProcessor getMockedTesterCredentials(){

        return user(TESTER_WITH_COMPLETED_LABTEST).password("password").roles("TESTER");
    }

    public static RequestPostProcessor getMockedCloseAccountCredentials(){

        return user("chrissiebritcher").password("password").roles("USER");
    }
    public static RequestPostProcessor getMockedDoctorCredentials(){

        return user(DOCTOR_WITH_COMPLETED_CONSULTATION).password("password").roles("DOCTOR");
    }
}
