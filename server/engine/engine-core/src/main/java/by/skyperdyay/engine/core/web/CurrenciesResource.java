package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import by.skyperdyay.engine.core.middleware.service.CurrencyEdgeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
public class CurrenciesResource {

    private final CurrencyEdgeService currencyEdgeService;

    public CurrenciesResource(CurrencyEdgeService currencyEdgeService) {
        this.currencyEdgeService = currencyEdgeService;
    }

    @GetMapping
    List<CurrencyResponse> getAvailableCurrencies() {
        return currencyEdgeService.getAvailableCurrencies();
    }
}
