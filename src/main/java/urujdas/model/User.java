package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalLong;

public class User {
    private final Optional<Long> id;
    private final String username;
    private final String password;

    private final Optional<String> firstname;
    private final Optional<String> lastname;

    private final Optional<LocalDateTime> birthDate;

    private final Optional<String> email;

    private final Optional<Gender> gender;

    private final Optional<String> phone;

    @GeneratePojoBuilder
    User(Long id, String username, String password, String firstname, String lastname, LocalDateTime birthDate,
         String email, Gender gender, String phone) {

        if (username == null
                || password == null) {
            throw new RuntimeException("Required fields are empty");
        }

        this.id = Optional.ofNullable(id);
        this.username = username;
        this.password = password;
        this.firstname = Optional.ofNullable(firstname);
        this.lastname = Optional.ofNullable(lastname);
        this.birthDate = Optional.ofNullable(birthDate);
        this.email = Optional.ofNullable(email);
        this.gender = Optional.ofNullable(gender);
        this.phone = Optional.ofNullable(phone);
    }

    public Optional<Long> getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Optional<String> getFirstname() {
        return firstname;
    }

    public Optional<String> getLastname() {
        return lastname;
    }

    public Optional<LocalDateTime> getBirthDate() {
        return birthDate;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<Gender> getGender() {
        return gender;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
