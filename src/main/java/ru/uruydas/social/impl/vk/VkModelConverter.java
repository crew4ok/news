package ru.uruydas.social.impl.vk;

import ru.uruydas.model.social.SocialNetworkType;
import ru.uruydas.model.users.Gender;
import ru.uruydas.model.users.User;
import ru.uruydas.social.impl.vk.model.VkUser;
import ru.uruydas.social.impl.vk.model.VkUserBirthdayVisibility;
import ru.uruydas.social.model.SocialNetworkUser;
import ru.uruydas.social.service.SocialNetworkModelConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VkModelConverter implements SocialNetworkModelConverter {
    @Override
    public User fromSocialNetworkUser(SocialNetworkType socialNetworkType, SocialNetworkUser user) {
        VkUser vkUser = (VkUser) user;

        String birthDateStr = vkUser.getBirthDate();
        LocalDateTime birthDate = null;
        if (vkUser.getBirthdayVisibility() == VkUserBirthdayVisibility.FULL) {
            birthDate = LocalDateTime.parse(birthDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        Gender gender = null;
        if (vkUser.getSex() != null) {
            switch (vkUser.getSex()) {
                case MALE:
                    gender = Gender.MALE;
                    break;
                case FEMALE:
                    gender = Gender.FEMALE;
                    break;
            }
        }

        return User.builder()
                .withFirstname(vkUser.getFirstName())
                .withLastname(vkUser.getLastName())
                .withBirthDate(birthDate)
                .withGender(gender)
                .withVkId(vkUser.getId())
                .build();

    }
}
