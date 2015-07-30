package urujdas.social.impl.vk;

import urujdas.model.social.SocialNetworkType;
import urujdas.model.users.Gender;
import urujdas.model.users.User;
import urujdas.social.impl.vk.model.VkUser;
import urujdas.social.impl.vk.model.VkUserBirthdayVisibility;
import urujdas.social.model.SocialNetworkUser;
import urujdas.social.service.SocialNetworkModelConverter;

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
