package org.upgrad.upstac.testrequests.flow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class TestRequestFlow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @ManyToOne
    @JsonIgnore
    private TestRequest request;

    private RequestStatus fromStatus ;
    private RequestStatus toStatus ;

    @ManyToOne
    private User changedBy;

    private LocalDate happenedOn=LocalDate.now();







}
