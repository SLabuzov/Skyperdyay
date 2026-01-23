package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.DefineCategoryRequest;
import by.skyperdyay.engine.core.middleware.model.request.OnboardingRequest;
import by.skyperdyay.engine.core.middleware.model.request.ProfileRequest;
import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.model.response.OnboardingStatusResponse;
import by.skyperdyay.engine.core.middleware.service.CategoryEdgeService;
import by.skyperdyay.engine.core.middleware.service.OnboardingEdgeService;
import by.skyperdyay.engine.core.middleware.service.ProfileEdgeService;
import by.skyperdyay.engine.core.middleware.service.WalletEdgeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingResource {

    private final OnboardingEdgeService onboardingEdgeService;
    private final ProfileEdgeService profileEdgeService;
    private final CategoryEdgeService categoryEdgeService;
    private final WalletEdgeService walletEdgeService;

    public OnboardingResource(OnboardingEdgeService onboardingEdgeService,
                              ProfileEdgeService profileEdgeService,
                              CategoryEdgeService categoryEdgeService,
                              WalletEdgeService walletEdgeService) {
        this.onboardingEdgeService = onboardingEdgeService;
        this.profileEdgeService = profileEdgeService;
        this.categoryEdgeService = categoryEdgeService;
        this.walletEdgeService = walletEdgeService;
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    void finalizeOnboarding(@Valid @RequestBody OnboardingRequest request) {
        ProfileRequest profileRequest = request.profile();
        profileEdgeService.initializeProfile(profileRequest);

        RegisterWalletRequest walletRequest = request.wallet();
        walletEdgeService.registerWallet(walletRequest);

        List<DefineCategoryRequest> incomeCategoryRequest = request.incomeCategories();
        categoryEdgeService.defineCategories(incomeCategoryRequest);

        List<DefineCategoryRequest> expenseCategoryRequest = request.expenseCategories();
        categoryEdgeService.defineCategories(expenseCategoryRequest);

        onboardingEdgeService.validateOnboarding();
    }

    @GetMapping("/status")
    OnboardingStatusResponse onboardingStatus() {
        return onboardingEdgeService.checkOnboardingStatus();
    }
}
