package com.estate.estateserver.models.entities;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "RENTALS")
public class Rental implements Serializable {

    public static final long serialVersionUID = 1542478724L;

    @Id
    @NotNull
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    String name;
    @Column(name = "surface")
    Double surface;

    @Column(name = "price")
    Double price;
    @Column(name = "picture")
    String picture;
    @Column(name = "description")
    String description;
    @Column(name = "owner_id")
    Integer ownerId;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return id == rental.id && Objects.equals(name, rental.name) && Objects.equals(surface, rental.surface) && Objects.equals(price, rental.price) && Objects.equals(picture, rental.picture) && Objects.equals(description, rental.description) && Objects.equals(ownerId, rental.ownerId) && Objects.equals(createdAt, rental.createdAt) && Objects.equals(updatedAt, rental.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surface, price, picture, description, ownerId, createdAt, updatedAt);
    }
}
