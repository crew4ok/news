package urujdas.web.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import urujdas.model.users.Gender;
import urujdas.model.users.User;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RegisterUserRequest {

    @NotNull
    private final String username;

    @NotNull
    private final String password;

    private final String firstname;
    private final String lastname;

    private final LocalDateTime birthDate;

    private final String email;

    private final Gender gender;

    private final String phone;

    private final Long imageId;

    private final GeoLocation geoLocation;

    @GeneratePojoBuilder
    @JsonCreator
    public RegisterUserRequest(@JsonProperty("username") String username,
                               @JsonProperty("password") String password,
                               @JsonProperty("firstname") String firstname,
                               @JsonProperty("lastname") String lastname,
                               @JsonProperty("birthDate") LocalDateTime birthDate,
                               @JsonProperty("email") String email,
                               @JsonProperty("gender") Gender gender,
                               @JsonProperty("phone") String phone,
                               @JsonProperty("imageId") Long imageId,
                               @JsonProperty("latitude") BigDecimal latitude,
                               @JsonProperty("longitude") BigDecimal longitude,
                               @JsonProperty("geoLocation") GeoLocation geoLocation) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.imageId = imageId;
        this.geoLocation = geoLocation;
    }

    public String getUsername() {
        return username;
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

    public Long getImageId() {
        return imageId;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public User toUser() {


        return User.builder()
                .withUsername(username)
                .withPassword(password)
                .withFirstname(firstname)
                .withLastname(lastname)
                .withBirthDate(birthDate)
                .withEmail(email)
                .withGender(gender)
                .withPhone(phone)
                .withImageId(imageId)
                .withGeoLocation(geoLocation.toGeoLocation())
                .build();
    }

    public static RegisterUserRequestBuilder builder() {
        return new RegisterUserRequestBuilder();
    }
}
