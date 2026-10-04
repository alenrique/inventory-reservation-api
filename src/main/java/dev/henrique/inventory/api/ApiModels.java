package dev.henrique.inventory.api;
import dev.henrique.inventory.domain.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.time.Instant; import java.util.*;
public final class ApiModels { private ApiModels(){}
 public record CreateProductRequest(@NotBlank @Size(max=80) String sku,@NotBlank @Size(max=160) String name){}
 public record ProductResponse(UUID id,String sku,String name,Instant createdAt){public static ProductResponse from(Product p){return new ProductResponse(p.getId(),p.getSku(),p.getName(),p.getCreatedAt());}}
 public record SetInventoryRequest(@PositiveOrZero int quantity){} public record InventoryResponse(UUID productId,int availableQuantity){public static InventoryResponse from(Inventory i){return new InventoryResponse(i.getProductId(),i.getAvailableQuantity());}}
 public record ReservationLineRequest(@NotNull UUID productId,@Positive int quantity){}
 public record CreateReservationRequest(@NotEmpty List<@Valid ReservationLineRequest> items){}
 public record ReservationLineResponse(UUID productId,int quantity){}
 public record ReservationResponse(UUID id,ReservationStatus status,List<ReservationLineResponse> items,Instant createdAt,Instant updatedAt){public static ReservationResponse from(Reservation r){return new ReservationResponse(r.getId(),r.getStatus(),r.getItems().stream().map(i->new ReservationLineResponse(i.getProduct().getId(),i.getQuantity())).toList(),r.getCreatedAt(),r.getUpdatedAt());}}
 public record ApiError(Instant timestamp,int status,String error,String code,String message,String path,Map<String,String> details){}
}
