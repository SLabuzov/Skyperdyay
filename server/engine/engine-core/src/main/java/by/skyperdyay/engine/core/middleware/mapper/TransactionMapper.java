package by.skyperdyay.engine.core.middleware.mapper;

import by.skyperdyay.engine.core.domain.model.Transaction;
import by.skyperdyay.engine.core.middleware.model.response.TransactionInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, WalletMapper.class})
public interface TransactionMapper {

    @Mapping(source = "id", target = "transactionId")
    TransactionInfoResponse convert(Transaction transaction);
}
