package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.common.dm.ExchangeRateData;
import by.skyperdyay.engine.core.domain.model.ExchangeRateId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateIdMapper {

    ExchangeRateId convert(ExchangeRateData data);
}
