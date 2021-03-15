package org.upgrad.upstac.testrequests.consultation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.users.User;

import java.util.List;
import java.util.Optional;


public interface ConsultationRepository extends JpaRepository<Consultation,Long> {


	Optional<Consultation> findById(Long id);



	void deleteById(Long id);




	
	List<Consultation> findByDoctor(User user);
	Optional<Consultation> findByRequest(TestRequest testRequest);

	Optional<Consultation> findByDoctorAndRequest(User doctor,TestRequest testRequest);


}
