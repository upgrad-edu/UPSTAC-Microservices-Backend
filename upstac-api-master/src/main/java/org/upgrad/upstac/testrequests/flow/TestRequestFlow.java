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

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestRequest getRequest() {
		return request;
	}

	public void setRequest(TestRequest request) {
		this.request = request;
	}

	public RequestStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(RequestStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public RequestStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(RequestStatus toStatus) {
		this.toStatus = toStatus;
	}

	public User getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}

	public LocalDate getHappenedOn() {
		return happenedOn;
	}

	public void setHappenedOn(LocalDate happenedOn) {
		this.happenedOn = happenedOn;
	}

	private RequestStatus fromStatus ;
    private RequestStatus toStatus ;

    @ManyToOne
    private User changedBy;

    private LocalDate happenedOn=LocalDate.now();







}
