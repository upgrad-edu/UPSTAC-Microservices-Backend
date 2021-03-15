package org.upgrad.upstac.testrequests.lab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

import java.util.List;
import java.util.Optional;


public interface LabResultRepository extends JpaRepository<LabResult,Long> {


	Optional<LabResult> findById(Long id);



	void deleteById(Long id);




	
	List<LabResult> findByTester(User user);
	Optional<LabResult> findByTesterAndRequest(User user,TestRequest testRequest);
	Optional<LabResult> findByRequest(TestRequest request);


}
