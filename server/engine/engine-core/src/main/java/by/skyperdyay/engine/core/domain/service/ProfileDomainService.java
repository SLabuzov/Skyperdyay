package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Profile;

public interface ProfileDomainService {
    void createProfile(Profile profile);

    boolean existsProfile(String owner);
}
