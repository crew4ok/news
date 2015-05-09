package urujdas.model;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.time.LocalDateTime;

public class User {
    private final Long id;
    private final String username;
    private final String password;

    private final String firstname;
    private final String lastname;

    private final LocalDateTime birthDate;

    private final String email;

    private final Gender gender;

    private final String phone;

    @GeneratePojoBuilder
    User(Long id, String username, String password, String firstname, String lastname, LocalDateTime birthDate,
                String email, Gender gender, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    public Long getId() {
        return id;
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
