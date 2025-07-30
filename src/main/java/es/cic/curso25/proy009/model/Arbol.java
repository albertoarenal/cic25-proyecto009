package es.cic.curso25.proy009.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
public class Arbol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private double altura;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE,CascadeType.ALL }, mappedBy = "arbol")
    @JsonIgnore
    private List<Rama> ramas = new ArrayList<>();

    private boolean caducifolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public List<Rama> getRamas() {
        return ramas;
    }

    public void setRamas(List<Rama> ramas) {
        this.ramas = ramas;
    }

    public boolean isCaducifolio() {
        return caducifolio;
    }

    public void setCaducifolio(boolean caducifolio) {
        this.caducifolio = caducifolio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Arbol other = (Arbol) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Arbol [id=" + id + ", version=" + version + ", altura=" + altura + ", ramas=" + ramas + ", caducifolio="
                + caducifolio + "]";
    }

}
