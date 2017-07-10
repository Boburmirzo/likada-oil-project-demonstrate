package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Yusupov on 12/19/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="CUSTOMERS") // TODO need to put constraints for columns like: not null, unique (firstName, lastName, email ! =null)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED", nullable = false)
    private Date updated;

    @Column(name="CONTACTS")
    private String contacts;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CUSTOMERS_MANAGERS",
            joinColumns = { @JoinColumn(name = "CUSTOMER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    private Set<User> customerManagers = new HashSet<User>();

    @OneToOne
    @JoinColumn(name = "CREATOR_ID")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<Contractor> contractors;

    @OneToMany(mappedBy = "customer")
    private Set<Order> ordersOfCustomer;

    @Override
    public String toString() {
        return "CUSTOMER [id=" + id + ", title=" + title + ", created" + created + ", contacts=" + contacts
                + ", description=" + description + ", updated=" + updated + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Customer))
            return false;
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getCustomerManagers() {
        return customerManagers;
    }

    public void setCustomerManagers(Set<User> customerManagers) {
        this.customerManagers = customerManagers;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getCustomerManagersList() {
        return new ArrayList<User>(getCustomerManagers());
    }

    public List<Contractor> getContractorsList() {
        return new ArrayList<Contractor>(getContractors());
    }

    public Set<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(Set<Contractor> contractors) {
        this.contractors = contractors;
    }

    public Set<Order> getOrdersOfCustomer() {
        return ordersOfCustomer;
    }

    public void setOrdersOfCustomer(Set<Order> ordersOfCustomer) {
        this.ordersOfCustomer = ordersOfCustomer;
    }
}
