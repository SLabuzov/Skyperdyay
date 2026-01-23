package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.ProfileRequest;
import by.skyperdyay.engine.core.middleware.service.ProfileEdgeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileResource {

    private final ProfileEdgeService profileEdgeService;

    public ProfileResource(ProfileEdgeService profileEdgeService) {
        this.profileEdgeService = profileEdgeService;
    }

    @PostMapping("/initialize")
    void initializeProfiling(@Valid @RequestBody ProfileRequest request) {
        profileEdgeService.initializeProfile(request);
    }
}
