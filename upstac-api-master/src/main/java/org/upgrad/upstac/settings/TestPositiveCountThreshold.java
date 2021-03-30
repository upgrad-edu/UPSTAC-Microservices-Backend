package org.upgrad.upstac.settings;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class TestPositiveCountThreshold {


      @Id
      ThresholdType thresholdType;

      public ThresholdType getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(ThresholdType thresholdType) {
		this.thresholdType = thresholdType;
	}

	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}

	int maxLimit;

}
