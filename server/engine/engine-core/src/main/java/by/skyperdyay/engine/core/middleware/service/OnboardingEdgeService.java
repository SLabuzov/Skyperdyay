package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.response.OnboardingStatusResponse;

public interface OnboardingEdgeService {

    void validateOnboarding();

    OnboardingStatusResponse checkOnboardingStatus();
}
