package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bumur on 11.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="PRODUCTS_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductType implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TURN")
    private Long turn;

    @Column(name = "NAME_FULL")
    private String nameFull;

    @Column(name = "NAME_WORK")
    private String nameWork;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DENSITY")
    private Double density;

    @Override
    public String toString() {
        return "PRODUCTS_TYPE [id=" + id + ", TURN=" + turn + ", NAME_FULL=" + nameFull + ", NAME_WORK=" + nameWork + ", DESCRIPTION=" + description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTurn() {
        return turn;
    }

    public void setTurn(Long turn) {
        this.turn = turn;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }
}
