package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.engine.core.domain.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExchangeRateIdMapper.class})
public interface ExchangeRateMapper {

    @Mapping(target = "id", source = ".")
    ExchangeRate convert(ExchangeRateData data);

}
