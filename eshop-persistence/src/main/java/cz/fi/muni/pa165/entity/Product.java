package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Color;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ESHOP_PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated
    private Color color;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof Product)) {
            return false;
        }
        Product product = (Product) object;
        return getName().equals(product.getName());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(getName());
        return hash;
    }
}
