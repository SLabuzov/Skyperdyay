package by.skyperdyay.security.core;

import by.skyperdyay.security.api.CurrentUserApiModel;
import by.skyperdyay.security.api.CurrentUserApiService;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserApiServiceImpl implements CurrentUserApiService {

    @Override
    public CurrentUserApiModel currentUserAccount() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional
                .ofNullable(securityContext.getAuthentication())
                .map(Authentication::getName)
                .map(CurrentUserApiModel::new)
                .orElseThrow();
    }
}
