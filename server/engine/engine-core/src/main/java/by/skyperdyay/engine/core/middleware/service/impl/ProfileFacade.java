package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.common.exception.api.ResourceConflictException;
import by.skyperdyay.engine.core.domain.model.Country;
import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Profile;
import by.skyperdyay.engine.core.domain.model.Timezone;
import by.skyperdyay.engine.core.domain.service.CountryDomainService;
import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.domain.service.ProfileDomainService;
import by.skyperdyay.engine.core.domain.service.TimezoneDomainService;
import by.skyperdyay.engine.core.middleware.model.request.ProfileRequest;
import by.skyperdyay.engine.core.middleware.service.ProfileEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileFacade implements ProfileEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final CurrencyDomainService currencyDomainService;
    private final CountryDomainService countryDomainService;
    private final TimezoneDomainService timezoneDomainService;
    private final ProfileDomainService profileDomainService;

    public ProfileFacade(CurrentUserApiService currentUserApiService,
                         CurrencyDomainService currencyDomainService,
                         CountryDomainService countryDomainService,
                         TimezoneDomainService timezoneDomainService,
                         ProfileDomainService profileDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.currencyDomainService = currencyDomainService;
        this.countryDomainService = countryDomainService;
        this.timezoneDomainService = timezoneDomainService;
        this.profileDomainService = profileDomainService;
    }

    @Override
    public void initializeProfile(ProfileRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();

        if (profileDomainService.existsProfile(owner)) {
            throw new ResourceConflictException(
                    "Профиль пользователя был заполнен ранее",
                    "Попытка повторной инициализации профиля пользователя"
            );
        }

        Country country = countryDomainService.fetchCountry(request.country());
        Currency currency = currencyDomainService.fetchCurrency(request.mainCurrencyCode());
        Timezone timezone = timezoneDomainService.fetchTimezone(request.timezone());

        Profile profile = new Profile();
        profile.setOwner(owner);
        profile.setMainCurrency(currency);
        profile.setCountry(country);
        profile.setTimezone(timezone);
        profile.setAcceptedTerms(request.acceptedTerms());
        profileDomainService.createProfile(profile);
    }
}
