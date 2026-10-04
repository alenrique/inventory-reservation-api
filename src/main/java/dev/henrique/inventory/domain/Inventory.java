package dev.henrique.inventory.domain;
import jakarta.persistence.*; import java.util.UUID;
@Entity @Table(name="inventory") public class Inventory {
 @Id @Column(name="product_id") private UUID productId; @OneToOne(fetch=FetchType.LAZY) @MapsId @JoinColumn(name="product_id") private Product product; @Column(name="available_quantity",nullable=false) private int availableQuantity;
 protected Inventory(){} public Inventory(Product product,int quantity){this.product=product;this.productId=product.getId();setAvailableQuantity(quantity);} public void setAvailableQuantity(int q){if(q<0)throw new IllegalArgumentException("Inventory cannot be negative");availableQuantity=q;} public void reserve(int q){if(q<=0)throw new IllegalArgumentException("Quantity must be positive");if(availableQuantity<q)throw new InsufficientInventoryException(productId,q,availableQuantity);availableQuantity-=q;} public void release(int q){availableQuantity=Math.addExact(availableQuantity,q);} public UUID getProductId(){return productId;} public int getAvailableQuantity(){return availableQuantity;}
}
