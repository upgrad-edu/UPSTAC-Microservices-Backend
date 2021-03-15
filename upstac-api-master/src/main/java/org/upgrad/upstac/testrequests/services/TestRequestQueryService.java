package org.upgrad.upstac.testrequests.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestRepository;
import org.upgrad.upstac.testrequests.consultation.Consultation;
import org.upgrad.upstac.testrequests.consultation.ConsultationRepository;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.LabResultRepository;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.users.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class TestRequestQueryService {

    @Autowired
    private TestRequestRepository testRequestRepository;


    @Autowired
    private LabResultRepository labResultRepository;


    @Autowired
    private ConsultationRepository consultationRepository;

    private static Logger logger = LoggerFactory.getLogger(TestRequestQueryService.class);


    public List<TestRequest> findAll() {

        return testRequestRepository.findAll();
    }


    public Optional<TestRequest> getTestRequestById(Long id) {

        return testRequestRepository.findById(id);
    }




    public List<TestRequest> findBy(RequestStatus requestStatus) {
        return testRequestRepository.findByStatus(requestStatus);

    }



    public List<TestRequest> findByTester(User user) {

        return  labResultRepository.findByTester(user)
                .stream()
                .map(LabResult::getRequest)
                .collect(Collectors.toList());

    }

    public List<TestRequest> findByDoctor(User user) {
        return  consultationRepository.findByDoctor(user)
                .stream()
                .map(Consultation::getRequest)
                .collect(Collectors.toList());
    }


    public Optional<TestRequest> findTestRequestForUserByID(User user,Long id) {


        logger.info("findTestRequestForUserByID" + user.getRoles().toString());

        if(user.doesRoleIsUser())
            return  findByUserAndID(user,id);
        else if(user.doesRoleIsTester())
            return findByTesterAndID(user,id);
        else if(user.doesRoleIsDoctor())
            return findByDoctorAndID(user,id);
        else if(user.doesRoleIsAuthority())
            return testRequestRepository.findByRequestId(id);
        else
            throw new AppException("Invalid Role");

    }



    public Optional<TestRequest> findByDoctorAndID(User doctor,Long id) {


        return  testRequestRepository.findByRequestId(id)
                .filter(testRequest -> consultationRepository.findByDoctorAndRequest(doctor,testRequest).isPresent());

    }
    public Optional<TestRequest> findByTesterAndID(User tester,Long id) {




        return  testRequestRepository.findByRequestId(id)
                .filter(testRequest -> labResultRepository.findByTesterAndRequest(tester,testRequest).isPresent());







    }

    public Optional<TestRequest> findByUserAndID(User user,Long id) {

        return  testRequestRepository.findByCreatedByAndRequestId(user,id);

    }

    public List<TestRequest> findByUser(User user) {
        return  testRequestRepository.findByCreatedBy(user);


    }

}
