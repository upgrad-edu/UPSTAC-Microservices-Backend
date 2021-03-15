package org.upgrad.upstac.testrequests.lab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.lab.models.TestStatus;
import org.upgrad.upstac.users.User;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class LabResult {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long resultId;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private TestRequest request;

    private String bloodPressure;
    private String heartBeat;
    private String temperature;
    private String oxygenLevel;
    private String comments;
    private TestStatus result;
    private LocalDate updatedOn;

    @ManyToOne
    private User tester;


}
