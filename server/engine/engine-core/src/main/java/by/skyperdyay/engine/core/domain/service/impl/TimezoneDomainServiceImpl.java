package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.common.exception.api.ResourceNotFoundException;
import by.skyperdyay.engine.core.domain.model.Timezone;
import by.skyperdyay.engine.core.domain.repository.TimezoneRepository;
import by.skyperdyay.engine.core.domain.service.TimezoneDomainService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TimezoneDomainServiceImpl implements TimezoneDomainService {

    private final TimezoneRepository timezoneRepository;

    public TimezoneDomainServiceImpl(TimezoneRepository timezoneRepository) {
        this.timezoneRepository = timezoneRepository;
    }

    @Override
    public List<Timezone> fetchAllTimezones() {
        return timezoneRepository.findAll();
    }

    @Override
    public Timezone fetchTimezone(String timezoneCode) {
        return timezoneRepository
                .findById(timezoneCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Таймзона не найдена",
                        String.format("Запрашиваемая таймзона %s не найдена в системе", timezoneCode)
                ));
    }
}
