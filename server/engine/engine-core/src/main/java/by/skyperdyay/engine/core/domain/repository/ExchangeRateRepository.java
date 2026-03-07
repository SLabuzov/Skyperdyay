package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.ExchangeRate;
import by.skyperdyay.engine.core.domain.model.ExchangeRateId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRateId> {

    @Query("SELECT er FROM ExchangeRate er where er.id.baseCurrencyCode = :code and er.id.rateDate = :date")
    List<ExchangeRate> findForUpdate(@Param("code") String baseCurrencyCode, @Param("date") LocalDate rateDate);

}
