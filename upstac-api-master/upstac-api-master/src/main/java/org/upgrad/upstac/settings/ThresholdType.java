package org.upgrad.upstac.settings;

public enum ThresholdType {
    RED(0),YELLOW(1),GREEN(2);

    int value;
    ThresholdType(int value) {

        this.value=value;
    }
}
