package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bumur on 13.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="MEASURE_UNITS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeasureUnit implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TURN")
    private Long turn;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "NAME_FULL")
    private String nameFull;

    @Column(name = "NAME_SHORT")
    private String nameShort;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Override
    public String toString() {
        return "MEASURE_UNITS [id=" + id + ", TURN=" + turn +", ACTIVE=" + active + ", NAME_FULL=" + nameFull + ", NAME_SHORT=" + nameShort + ", CODE=" + code+ ", DESCRIPTION=" + description;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
