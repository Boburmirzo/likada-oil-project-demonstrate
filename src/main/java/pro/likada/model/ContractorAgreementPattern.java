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
@Table(name="CONTRACTORS_AGREEMENT_PATTERNS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContractorAgreementPattern implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="PATTERN", nullable=false, unique = true)
    private String pattern;

    @NotEmpty
    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "agreementPattern")
    private Set<Contractor> contractors;

    @Override
    public String toString() {
        return "AGREEMENT_STATUS [id=" + id + ", PATTERN=" + pattern + ", DESCRIPTION=" + description;
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
        if (!(obj instanceof ContractorAgreementPattern))
            return false;
        ContractorAgreementPattern other = (ContractorAgreementPattern) obj;
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

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
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
