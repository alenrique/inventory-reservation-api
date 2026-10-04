package dev.henrique.inventory.domain;
import jakarta.persistence.*; import java.util.UUID;
@Entity @Table(name="reservation_items",uniqueConstraints=@UniqueConstraint(columnNames={"reservation_id","product_id"})) public class ReservationItem {
 @Id private UUID id; @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="reservation_id") private Reservation reservation; @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id") private Product product; @Column(nullable=false) private int quantity;
 protected ReservationItem(){} ReservationItem(Reservation r,Product p,int q){if(q<=0)throw new IllegalArgumentException("Quantity must be positive");id=UUID.randomUUID();reservation=r;product=p;quantity=q;} public UUID getId(){return id;} public Product getProduct(){return product;} public int getQuantity(){return quantity;}
}
