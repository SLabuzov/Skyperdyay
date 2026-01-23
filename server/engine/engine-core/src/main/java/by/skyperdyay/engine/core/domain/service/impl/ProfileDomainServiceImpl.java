package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Profile;
import by.skyperdyay.engine.core.domain.repository.ProfileRepository;
import by.skyperdyay.engine.core.domain.service.ProfileDomainService;
import org.springframework.stereotype.Service;

@Service
public class ProfileDomainServiceImpl implements ProfileDomainService {

    private final ProfileRepository profileRepository;

    public ProfileDomainServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public boolean existsProfile(String owner) {
        return profileRepository.existsById(owner);
    }

}
