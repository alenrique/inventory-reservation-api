package dev.henrique.inventory.repository;
import dev.henrique.inventory.domain.Inventory; import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface InventoryRepository extends JpaRepository<Inventory,UUID>{ @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select i from Inventory i where i.productId in :ids order by i.productId") List<Inventory> lockAllByProductIds(@Param("ids") Collection<UUID> ids); }
