package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Jenisharta.
 */
@Entity
@Table(name = "jenisharta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jenisharta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jenis", nullable = false)
    private String jenis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public Jenisharta jenis(String jenis) {
        this.jenis = jenis;
        return this;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jenisharta jenisharta = (Jenisharta) o;
        if (jenisharta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jenisharta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jenisharta{" +
            "id=" + getId() +
            ", jenis='" + getJenis() + "'" +
            "}";
    }
}
