package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Yusupov on 1/5/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="ADDRESS_HOUSE_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AddressHouseType implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="NAME_FULL", nullable=false, unique = true)
    private String nameFull;

    @NotEmpty
    @Column(name="NAME_SHORT")
    private String nameShort;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "addressFactHouseType")
    private Set<Requisite> factRequisites;

    @OneToMany(mappedBy = "addressLegalHouseType")
    private Set<Requisite> legalRequisites;

    @Override
    public String toString() {
        return "ADDRESS_HOUSE_TYPE [id=" + id + ", NAME_FULL=" + nameFull + ", NAME_SHORT=" + nameShort + ", DESCRIPTION=" + description;
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
        if (!(obj instanceof AddressHouseType))
            return false;
        AddressHouseType other = (AddressHouseType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Requisite> getFactRequisites() {
        return factRequisites;
    }

    public void setFactRequisites(Set<Requisite> factRequisites) {
        this.factRequisites = factRequisites;
    }

    public Set<Requisite> getLegalRequisites() {
        return legalRequisites;
    }

    public void setLegalRequisites(Set<Requisite> legalRequisites) {
        this.legalRequisites = legalRequisites;
    }
}
