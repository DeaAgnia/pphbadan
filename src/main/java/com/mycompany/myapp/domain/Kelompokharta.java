package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Kelompokharta.
 */
@Entity
@Table(name = "kelompokharta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kelompokharta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @ManyToOne(optional = false)
    @NotNull
    private Jenisharta jenisharta;

    @NotNull
    @Column(name = "masamanfaat", nullable = false)
    private Long masamanfaat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public Long getMasamanfaat()
    {
        return masamanfaat;
    }

    public void setMasamanfaat(Long masamanfaat) {
        this.masamanfaat = masamanfaat;

    }

    public Kelompokharta nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Jenisharta getJenisharta() {
        return jenisharta;
    }

    public Kelompokharta jenisharta(Jenisharta jenisharta) {
        this.jenisharta = jenisharta;
        return this;
    }

    public void setJenisharta(Jenisharta jenisharta) {
        this.jenisharta = jenisharta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kelompokharta kelompokharta = (Kelompokharta) o;
        if (kelompokharta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kelompokharta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kelompokharta{" +
            "id=" + getId() +
            ", nama='" + getNama() + "'" +
            ", masamanfaat='" + getMasamanfaat() + "'" +
            "}";
    }
}
