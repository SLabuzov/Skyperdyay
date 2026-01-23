package by.skyperdyay.engine.core.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(schema = "moneybox", name = "onboarding_progress")
@EntityListeners(AuditingEntityListener.class)
public class OnboardingProgress {
    @Id
    private String owner;

    @Enumerated(EnumType.STRING)
    private OnboardingState state;

    @OneToMany(mappedBy = "onboardingProgress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OnboardingAction> onboardingActions = new HashSet<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public void addOnboardingAction(OnboardingAction action) {
        onboardingActions.add(action);
        action.setOnboardingProgress(this);
    }

    public void removeOnboardingAction(OnboardingAction action) {
        onboardingActions.remove(action);
        action.setOnboardingProgress(null);
    }
}
