package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Timezone;
import java.util.List;

public interface TimezoneDomainService {
    List<Timezone> fetchAllTimezones();

    Timezone fetchTimezone(String timezoneCode);
}
