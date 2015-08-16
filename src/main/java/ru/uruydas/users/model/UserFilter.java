package ru.uruydas.users.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;

public class UserFilter {
    private final Long userId;
    private final Gender gender;
    private final GenderPreferences genderPreferences;
    private final RelationsPreferences relationsPreferences;
    private final AgeRange ageRange;

    @GeneratePojoBuilder
    public UserFilter(Long userId, Gender gender, GenderPreferences genderPreferences,
                      RelationsPreferences relationsPreferences, AgeRange ageRange) {
        this.userId = userId;
        this.gender = gender;
        this.genderPreferences = genderPreferences;
        this.relationsPreferences = relationsPreferences;
        this.ageRange = ageRange;
    }

    public Long getUserId() {
        return userId;
    }

    public Gender getGender() {
        return gender;
    }

    public GenderPreferences getGenderPreferences() {
        return genderPreferences;
    }

    public RelationsPreferences getRelationsPreferences() {
        return relationsPreferences;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFilter filter = (UserFilter) o;

        if (userId != null ? !userId.equals(filter.userId) : filter.userId != null) return false;
        if (gender != filter.gender) return false;
        if (genderPreferences != filter.genderPreferences) return false;
        if (relationsPreferences != filter.relationsPreferences) return false;
        return !(ageRange != null ? !ageRange.equals(filter.ageRange) : filter.ageRange != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (genderPreferences != null ? genderPreferences.hashCode() : 0);
        result = 31 * result + (relationsPreferences != null ? relationsPreferences.hashCode() : 0);
        result = 31 * result + (ageRange != null ? ageRange.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserFilter{" +
                "userId=" + userId +
                ", gender=" + gender +
                ", genderPreferences=" + genderPreferences +
                ", relationsPreferences=" + relationsPreferences +
                ", ageRange=" + ageRange +
                '}';
    }

    public static UserFilterBuilder fromUserFilter(UserFilter filter) {
        return builder()
                .withUserId(filter.getUserId())
                .withGender(filter.getGender())
                .withGenderPreferences(filter.getGenderPreferences())
                .withRelationsPreferences(filter.getRelationsPreferences())
                .withAgeRange(filter.getAgeRange());
    }

    public static UserFilterBuilder builder() {
        return new UserFilterBuilder();
    }
}
