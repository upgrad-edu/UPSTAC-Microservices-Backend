package org.upgrad.upstac.testrequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.users.User;

import java.util.List;
import java.util.Optional;


public interface TestRequestRepository extends JpaRepository<TestRequest,Long> {


	Optional<TestRequest> findByRequestId(Long id);

	List<TestRequest> findByCreatedBy(User user);

	Optional<TestRequest> findByRequestIdAndStatus(Long id,RequestStatus status);
	Optional<TestRequest> findByCreatedByAndRequestId(User user,Long id);
	List<TestRequest> findByEmail(String email);
	List<TestRequest> findByPhoneNumber(String phoneNumber);

	void deleteById(Long id);




	
	List<TestRequest> findByName(String name);

	List<TestRequest> findByStatus(RequestStatus status);
	

}
