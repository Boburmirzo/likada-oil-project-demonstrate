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
 * Created by Yusupov on 12/22/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="CONTRACTORS_ORGANIZATION_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractorOrganizationType implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="NAME_FULL", nullable=false, unique = true)
    private String nameFull;

    @NotEmpty
    @Column(name="NAME_SHORT", nullable=false, unique = true)
    private String nameShort;

    @Column(name="DESCRIPTION")
    private String description;

    public ContractorOrganizationType() {
    }

    @Override
    public String toString() {
        return "CONTRACTORS_ORGANIZATION_TYPE [id=" + id + ", NAME_FULL=" + nameFull + ", NAME_SHORT" + nameShort + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nameShort == null) ? 0 : nameShort.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ContractorOrganizationType))
            return false;
        ContractorOrganizationType other = (ContractorOrganizationType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nameShort == null) {
            if (other.nameShort != null)
                return false;
        } else if (!nameShort.equals(other.nameShort))
            return false;
        return true;
    }

    @OneToMany(mappedBy = "organizationType")
    private Set<Contractor> contractors;

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

    public Set<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(Set<Contractor> contractors) {
        this.contractors = contractors;
    }
}
