package org.upgrad.upstac.settings;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class TestPositiveCountThreshold {


      @Id
      ThresholdType thresholdType;

      int maxLimit;

}
