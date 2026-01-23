package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.OnboardingProgress;
import by.skyperdyay.engine.core.domain.repository.OnboardingProgressRepository;
import by.skyperdyay.engine.core.domain.service.OnboardingProgressDomainService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class OnboardingProgressDomainServiceImpl implements OnboardingProgressDomainService {

    private final OnboardingProgressRepository onboardingProgressRepository;

    public OnboardingProgressDomainServiceImpl(OnboardingProgressRepository onboardingProgressRepository) {
        this.onboardingProgressRepository = onboardingProgressRepository;
    }

    public Optional<OnboardingProgress> fetchOnboardingProgress(String owner) {
        return onboardingProgressRepository.findById(owner);
    }

    @Override
    public void updateOnboardingProgress(OnboardingProgress currentProgress) {
        onboardingProgressRepository.save(currentProgress);
    }
}
