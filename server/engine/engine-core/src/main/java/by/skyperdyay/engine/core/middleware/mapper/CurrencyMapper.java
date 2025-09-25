package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.middleware.model.response.CurrencyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyResponse convert(Currency currency);
}
