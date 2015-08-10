package ru.uruydas.social.impl.vk.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.uruydas.social.model.SocialNetworkUser;

public class VkUser extends SocialNetworkUser {
    private final String firstName;
    private final String lastName;
    private final String screenName;

    private final VkUserSex sex;

    private final VkUserRelation relation;

    private final String birthDate;
    private final VkUserBirthdayVisibility birthdayVisibility;

    private final VkUserCountry country;

    private final VkUserCity city;

    @JsonCreator
    public VkUser(@JsonProperty("id") Long id,
                  @JsonProperty("first_name") String firstName,
                  @JsonProperty("last_name") String lastName,
                  @JsonProperty("screen_name") String screenName,
                  @JsonProperty("sex") VkUserSex sex,
                  @JsonProperty("relation") VkUserRelation relation,
                  @JsonProperty("bdate") String birthDate,
                  @JsonProperty("bdate_visibility") VkUserBirthdayVisibility birthdayVisibility,
                  @JsonProperty("country") VkUserCountry country,
                  @JsonProperty("city") VkUserCity city) {
        super(id);

        this.firstName = firstName;
        this.lastName = lastName;
        this.screenName = screenName;
        this.sex = sex;
        this.relation = relation;
        this.birthDate = birthDate;
        this.birthdayVisibility = birthdayVisibility;
        this.country = country;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getScreenName() {
        return screenName;
    }

    public VkUserSex getSex() {
        return sex;
    }

    public VkUserRelation getRelation() {
        return relation;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public VkUserBirthdayVisibility getBirthdayVisibility() {
        return birthdayVisibility;
    }

    public VkUserCountry getCountry() {
        return country;
    }

    public VkUserCity getCity() {
        return city;
    }
}
