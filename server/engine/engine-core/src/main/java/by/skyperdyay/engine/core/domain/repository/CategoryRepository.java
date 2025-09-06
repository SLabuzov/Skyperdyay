package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.CategoryType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByOwner(String owner);

    Optional<Category> findByIdAndOwnerAndType(UUID id, String owner, CategoryType categoryType);
}
