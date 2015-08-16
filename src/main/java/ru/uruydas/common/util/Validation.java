package ru.uruydas.common.util;

import ru.uruydas.common.util.exception.InvalidParamException;

public final class Validation {

    private Validation() { }

    public static void isGreaterThanZero(Number value) {
        isNotNull(value);
        if (value.doubleValue() <= 0) {
            throw new InvalidParamException("Value is lesser than of equal to zero");
        }
    }

    public static void isGreaterThanOrEqualToZero(Number value) {
        isNotNull(value);

        if (value.doubleValue() < 0) {
            throw new InvalidParamException("Value is lesser than to zero");
        }
    }

    public static void isNotNull(Object value) {
        if (value == null) {
            throw new InvalidParamException("Value is null");
        }
    }

    public static void checkRangeNonStrict(Number lower, Number higher) {
        isNotNull(lower);
        isNotNull(higher);

        if (lower.doubleValue() > higher.doubleValue()) {
            throw new InvalidParamException("Range is invalid: " + lower + " is bigger than " + higher);
        }
    }

    public static void isNotEmpty(String title) {
        isNotNull(title);

        if (title.isEmpty()) {
            throw new InvalidParamException("Value is empty");
        }
    }
}
