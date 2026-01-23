package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Country;
import by.skyperdyay.engine.core.middleware.model.response.CountryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class, TimezoneMapper.class})
public interface CountryMapper {
    CountryResponse convert(Country country);
}
