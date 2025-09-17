package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.CategoryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByOwner(String owner);

    Optional<Category> findByIdAndOwnerAndType(UUID id, String owner, CategoryType categoryType);
}
