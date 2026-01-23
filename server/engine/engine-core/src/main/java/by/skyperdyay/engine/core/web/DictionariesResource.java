package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.response.CountryResponse;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.model.response.TimezoneResponse;
import by.skyperdyay.engine.core.middleware.service.DictionaryEdgeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dictionaries")
public class DictionariesResource {

    private final DictionaryEdgeService dictionaryEdgeService;

    public DictionariesResource(DictionaryEdgeService dictionaryEdgeService) {
        this.dictionaryEdgeService = dictionaryEdgeService;
    }

    @GetMapping("/currencies")
    List<CurrencyResponse> getAvailableCurrencies() {
        return dictionaryEdgeService.getAvailableCurrencies();
    }

    @GetMapping("/countries")
    List<CountryResponse> getAvailableCountries() {
        return dictionaryEdgeService.getAvailableCountries();
    }

    @GetMapping("/timezones")
    List<TimezoneResponse> getAvailableTimezones() {
        return dictionaryEdgeService.getAvailableTimezones();
    }
}
