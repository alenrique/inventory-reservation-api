package dev.henrique.inventory.domain;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="products") public class Product {
 @Id private UUID id; @Column(nullable=false,unique=true,length=80) private String sku; @Column(nullable=false,length=160) private String name; @Column(name="created_at",nullable=false) private Instant createdAt;
 protected Product(){} public Product(String sku,String name){id=UUID.randomUUID();this.sku=sku;this.name=name;createdAt=Instant.now();}
 public UUID getId(){return id;} public String getSku(){return sku;} public String getName(){return name;} public Instant getCreatedAt(){return createdAt;}
}
