package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.ProfileRequest;

public interface ProfileEdgeService {
    void initializeProfile(ProfileRequest request);
}
