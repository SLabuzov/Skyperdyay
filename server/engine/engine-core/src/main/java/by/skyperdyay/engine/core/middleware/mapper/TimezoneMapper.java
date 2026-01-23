package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Timezone;
import by.skyperdyay.engine.core.middleware.model.response.TimezoneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimezoneMapper {

    @Mapping(source = "country.code", target = "countryCode")
    TimezoneResponse convert(Timezone timezone);
}
