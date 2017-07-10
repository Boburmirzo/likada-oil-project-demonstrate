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
 * Created by Yusupov on 1/24/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="AGREEMENT_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AgreementType implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="TYPE", nullable=false, unique = true)
    private String type;

    @NotEmpty
    @Column(name="NAME", nullable=false, unique = true)
    private String name;

    @NotEmpty
    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "type")
    private Set<Agreement> agreements;

    @Override
    public String toString() {
        return "AGREEMENT_TYPE [id=" + id + ", TYPE=" + type + ", NAME=" + name + ", DESCRIPTION=" + description;
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
        if (!(obj instanceof AgreementType))
            return false;
        AgreementType other = (AgreementType) obj;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<Agreement> agreements) {
        this.agreements = agreements;
    }
}
