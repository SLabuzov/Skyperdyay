package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.OnboardingAction;
import by.skyperdyay.engine.core.domain.model.OnboardingActionType;
import by.skyperdyay.engine.core.domain.model.OnboardingProgress;
import by.skyperdyay.engine.core.domain.model.OnboardingState;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import by.skyperdyay.engine.core.domain.service.OnboardingProgressDomainService;
import by.skyperdyay.engine.core.domain.service.ProfileDomainService;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import by.skyperdyay.engine.core.middleware.model.response.OnboardingStatus;
import by.skyperdyay.engine.core.middleware.model.response.OnboardingStatusResponse;
import by.skyperdyay.engine.core.middleware.service.OnboardingEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OnboardingFacade implements OnboardingEdgeService {

    private static final OnboardingStatusResponse UNCOMPLETED_STATUS = new OnboardingStatusResponse(OnboardingStatus.UNCOMPLETED);
    private static final OnboardingStatusResponse COMPLETED_STATUS = new OnboardingStatusResponse(OnboardingStatus.COMPLETED);

    private final CurrentUserApiService currentUserApiService;
    private final ProfileDomainService profileDomainService;
    private final WalletDomainService walletDomainService;
    private final CategoryDomainService categoryDomainService;
    private final OnboardingProgressDomainService onboardingProgressDomainService;

    public OnboardingFacade(CurrentUserApiService currentUserApiService,
                            ProfileDomainService profileDomainService,
                            WalletDomainService walletDomainService,
                            CategoryDomainService categoryDomainService,
                            OnboardingProgressDomainService onboardingProgressDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.profileDomainService = profileDomainService;
        this.walletDomainService = walletDomainService;
        this.categoryDomainService = categoryDomainService;
        this.onboardingProgressDomainService = onboardingProgressDomainService;
    }

    @Override
    public void validateOnboarding() {
        String owner = currentUserApiService.currentUserAccount().userId();
        // Выбираем текущий статус по процессу онбординга или инициализируем.
        OnboardingProgress onboardingProgress = fetchOrCreate(owner);

        boolean initProfileCondition = profileDomainService.existsProfile(owner);
        processOnboarding(onboardingProgress, OnboardingActionType.INIT_PROFILE, initProfileCondition);

        boolean defineWalletsCondition = walletDomainService.existsUserWallets(owner);
        processOnboarding(onboardingProgress, OnboardingActionType.DEFINE_WALLET, defineWalletsCondition);

        boolean defineIncomeCategoriesCondition = categoryDomainService.existsIncomeUserCategory(owner);
        processOnboarding(onboardingProgress, OnboardingActionType.DEFINE_INCOME_CATEGORY, defineIncomeCategoriesCondition);

        boolean defineExpenseCategoriesCondition = categoryDomainService.existsExpenseUserCategory(owner);
        processOnboarding(onboardingProgress, OnboardingActionType.DEFINE_EXPENSE_CATEGORY, defineExpenseCategoriesCondition);

        checkOnboardingCompleted(onboardingProgress);
        onboardingProgressDomainService.updateOnboardingProgress(onboardingProgress);
    }

    private void checkOnboardingCompleted(OnboardingProgress onboardingProgress) {
        if (onboardingProgress.getOnboardingActions().size() == OnboardingActionType.values().length) {
            onboardingProgress.setState(OnboardingState.COMPLETED);
        } else {
            onboardingProgress.setState(OnboardingState.PENDING);
        }
    }

    private void processOnboarding(OnboardingProgress onboardingProgress, OnboardingActionType action, boolean condition) {
        if (condition) {
            processOnboardingIfTrue(onboardingProgress, action);
        } else {
            processOnboardingIfFalse(onboardingProgress, action);
        }
    }

    private void processOnboardingIfFalse(OnboardingProgress onboardingProgress, OnboardingActionType action) {
        onboardingProgress.getOnboardingActions()
                .stream()
                .filter(it -> it.getActionType().equals(action))
                .findFirst()
                .ifPresent(onboardingProgress::removeOnboardingAction);
    }

    private void processOnboardingIfTrue(OnboardingProgress onboardingProgress, OnboardingActionType action) {
        boolean existsAction = onboardingProgress.getOnboardingActions()
                .stream()
                .anyMatch(it -> it.getActionType().equals(action));

        if (!existsAction) {
            OnboardingAction onboardingAction = new OnboardingAction();
            onboardingAction.setActionType(action);
            onboardingProgress.addOnboardingAction(onboardingAction);
        }
    }

    private OnboardingProgress fetchOrCreate(String owner) {
        return onboardingProgressDomainService
                .fetchOnboardingProgress(owner)
                .orElseGet(() -> {
                    OnboardingProgress initializedOnboardingProgress = new OnboardingProgress();
                    initializedOnboardingProgress.setOwner(owner);
                    initializedOnboardingProgress.setState(OnboardingState.PENDING);

                    return initializedOnboardingProgress;
                });
    }

    @Override
    public OnboardingStatusResponse checkOnboardingStatus() {
        String owner = currentUserApiService.currentUserAccount().userId();

        OnboardingState onboardingState = onboardingProgressDomainService
                .fetchOnboardingProgress(owner)
                .map(OnboardingProgress::getState)
                .orElse(OnboardingState.PENDING);

        if (onboardingState == OnboardingState.COMPLETED) {
            return COMPLETED_STATUS;
        }
        return UNCOMPLETED_STATUS;
    }
}
