package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.OnboardingProgress;
import java.util.Optional;

public interface OnboardingProgressDomainService {

    Optional<OnboardingProgress> fetchOnboardingProgress(String owner);

    void updateOnboardingProgress(OnboardingProgress currentProgress);
}
