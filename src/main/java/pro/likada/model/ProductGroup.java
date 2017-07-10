package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by bumur on 11.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="PRODUCTS_GROUPS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductGroup implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "PARENT_ID", nullable = false)
    private Long parentId;

    @Column(name = "TURN")
    private Long turn;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "productGroup")
    private Set<Order> ordersOfProductGroup;

    @Override
    public String toString() {
        return "PRODUCTS_GROUP [id=" + id + ", PARENT_ID=" + parentId + ", TURN="  + turn + ", TITLE=" + title + ", DESCRIPTION=" + description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTurn() {
        return turn;
    }

    public void setTurn(Long turn) {
        this.turn = turn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Order> getOrdersOfProductGroup() {
        return ordersOfProductGroup;
    }

    public void setOrdersOfProductGroup(Set<Order> ordersOfProductGroup) {
        this.ordersOfProductGroup = ordersOfProductGroup;
    }
}
