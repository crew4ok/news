package urujdas.social.model;

import urujdas.util.Validation;

public abstract class SocialNetworkUser {

    private final Long id;

    protected SocialNetworkUser(Long id) {
        Validation.isNotNull(id);

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
