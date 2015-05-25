package urujdas.util;

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
}
