package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bumur on 13.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="PRODUCTS_PRICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductPrice implements Serializable, Comparable<ProductPrice> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="TIME_MODIFIED", nullable = false)
    private Date timeModified;

    @OneToOne
    @JoinColumn(name = "CREATOR_USER_ID")
    private User creator_user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Override
    public String toString() {
        return "PRODUCTS_PRICE [id=" + id + ", PRICE=" + price + ", DESCRIPTION="  + description + ", TIME_MODIFIED=" + timeModified + ", CREATOR_USER_ID=" + creator_user_id+ ", PRODUCT_ID=" + product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public User getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(User creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int compareTo(ProductPrice arg1) {
        if (arg1 != null) {
            if (this.getTimeModified() != null && arg1.getTimeModified() != null) {
                int comp = this.getTimeModified().compareTo(arg1.getTimeModified());
                switch (comp) {
                    case -1:
                        return 1;
                    case 1:
                        return -1;
                    case 0:
                        return 0;
                }
            } else if (this.getTimeModified() != null && arg1.getTimeModified() == null) {
                return -1;
            } else if (this.getTimeModified() == null && arg1.getTimeModified() != null) {
                return 1;
            }
            return 0;
        }
        return 0;
    }
}
