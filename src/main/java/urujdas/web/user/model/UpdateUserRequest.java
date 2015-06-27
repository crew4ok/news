package urujdas.web.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.users.Gender;
import urujdas.model.users.GenderPreferences;
import urujdas.model.users.RelationsPreferences;
import urujdas.model.users.User;

import java.time.LocalDateTime;

public class UpdateUserRequest {
    private final String password;

    private final String firstname;
    private final String lastname;

    private final LocalDateTime birthDate;

    private final String email;

    private final Gender gender;

    private final String phone;

    private final GenderPreferences genderPreferences;
    private final RelationsPreferences relationsPreferences;

    private final Long imageId;

    private final Long quickBloxId;

    @GeneratePojoBuilder
    @JsonCreator
    public UpdateUserRequest(@JsonProperty("password") String password,
                             @JsonProperty("firstname") String firstname,
                             @JsonProperty("lastname") String lastname,
                             @JsonProperty("birthDate") LocalDateTime birthDate,
                             @JsonProperty("email") String email,
                             @JsonProperty("gender") Gender gender,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("genderPreferences") GenderPreferences genderPreferences,
                             @JsonProperty("relationsPreferences") RelationsPreferences relationsPreferences,
                             @JsonProperty("imageId") Long imageId,
                             @JsonProperty("quickBloxId") Long quickBloxId) {
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.genderPreferences = genderPreferences;
        this.relationsPreferences = relationsPreferences;
        this.imageId = imageId;
        this.quickBloxId = quickBloxId;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public GenderPreferences getGenderPreferences() {
        return genderPreferences;
    }

    public RelationsPreferences getRelationsPreferences() {
        return relationsPreferences;
    }

    public Long getImageId() {
        return imageId;
    }

    public Long getQuickBloxId() {
        return quickBloxId;
    }

    public User toUser() {
        return User.builder()
                .withPassword(password)
                .withFirstname(firstname)
                .withLastname(lastname)
                .withBirthDate(birthDate)
                .withEmail(email)
                .withGender(gender)
                .withPhone(phone)
                .withGenderPreferences(genderPreferences)
                .withRelationsPreferences(relationsPreferences)
                .withImageId(imageId)
                .withQuickBloxId(quickBloxId)
                .build();
    }

    public static UpdateUserRequestBuilder builder() {
        return new UpdateUserRequestBuilder();
    }
}
