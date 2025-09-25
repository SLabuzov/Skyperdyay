package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.middleware.model.response.WalletInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface WalletMapper {

    @Mapping(source = "id", target = "walletId")
    @Mapping(source = "name", target = "walletName")
    @Mapping(source = "balance", target = "walletBalance")
    @Mapping(source = "currency", target = "walletCurrency")
    WalletInfoResponse convert(Wallet wallet);
}
