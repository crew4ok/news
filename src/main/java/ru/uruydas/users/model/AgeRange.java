package ru.uruydas.users.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.uruydas.common.util.Validation;

public class AgeRange {
    private final Integer lower;
    private final Integer higher;

    @JsonCreator
    public AgeRange(@JsonProperty("lower") Integer lower,
                    @JsonProperty("higher") Integer higher) {
        if (lower != null && higher != null) {
            Validation.checkRangeNonStrict(lower, higher);
        }

        this.lower = lower;
        this.higher = higher;
    }

    public Integer getLower() {
        return lower;
    }

    public Integer getHigher() {
        return higher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgeRange ageRange = (AgeRange) o;

        if (lower != null ? !lower.equals(ageRange.lower) : ageRange.lower != null) return false;
        return !(higher != null ? !higher.equals(ageRange.higher) : ageRange.higher != null);

    }

    @Override
    public int hashCode() {
        int result = lower != null ? lower.hashCode() : 0;
        result = 31 * result + (higher != null ? higher.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AgeRange{" +
                "lower=" + lower +
                ", higher=" + higher +
                '}';
    }
}
