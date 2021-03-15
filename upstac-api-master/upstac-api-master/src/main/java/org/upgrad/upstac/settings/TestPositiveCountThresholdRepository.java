package org.upgrad.upstac.settings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPositiveCountThresholdRepository extends CrudRepository<TestPositiveCountThreshold, ThresholdType> {

    List<TestPositiveCountThreshold> findAll();

}
