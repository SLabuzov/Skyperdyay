package by.skyperdyay.engine.core.domain.repository;

import by.skyperdyay.engine.core.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByOwner(String owner);
}
