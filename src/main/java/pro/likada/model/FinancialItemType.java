package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by bumur on 27.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="FINANCIAL_ITEMS_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FinancialItemType implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "financialItemType")
    private Set<FinancialItem> financialItems;

    @Override
    public String toString() {
        return "FINANCIAL_ITEMS_TYPE [id=" + id + ", NAME=" + name + ", DESCRIPTION=" + description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
