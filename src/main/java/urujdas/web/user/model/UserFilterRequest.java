package urujdas.web.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import urujdas.model.users.AgeRange;
import urujdas.model.users.Gender;
import urujdas.model.users.GenderPreferences;
import urujdas.model.users.RelationsPreferences;
import urujdas.model.users.UserFilter;

public class UserFilterRequest {
    private final Gender gender;
    private final GenderPreferences genderPreferences;
    private final RelationsPreferences relationsPreferences;
    private final AgeRange ageRange;

    @JsonCreator
    public UserFilterRequest(@JsonProperty("gender") Gender gender,
                             @JsonProperty("genderPreferences") GenderPreferences genderPreferences,
                             @JsonProperty("relationsPreferences") RelationsPreferences relationsPreferences,
                             @JsonProperty("ageRange") AgeRange ageRange) {
        this.gender = gender;
        this.genderPreferences = genderPreferences;
        this.relationsPreferences = relationsPreferences;
        this.ageRange = ageRange;
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

    public UserFilter toUserFilter() {
        return UserFilter.builder()
                .withGender(gender)
                .withGenderPreferences(genderPreferences)
                .withRelationsPreferences(relationsPreferences)
                .withAgeRange(ageRange)
                .build();
    }
}
