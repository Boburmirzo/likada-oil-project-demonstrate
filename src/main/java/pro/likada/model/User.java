package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Yusupov on 12/14/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="USERS")  // TODO need to put constraints for columns like: not null, unique (firstName, lastName, email != null)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="USERNAME", unique=true, nullable=false)
    private String username;

    @NotEmpty
    @Column(name="PASSWORD", nullable=false)
    private String password;

    @NotEmpty
    @Column(name="PASSWORD_ORIGINAL", nullable=false)
    private String passwordOriginal;

    @Column(name="ACTIVE", nullable=false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @NotEmpty
    @Column(name="FIRST_NAME")
    private String firstName;

    @NotEmpty
    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="PATRONYMIC")
    private String patronymic;

    @Column(name="TITLE")
    private String title;

    @NotEmpty
    @Column(name="EMAIL", unique = true)
    private String email;

    @Column(name="PHONE")
    private String phone;

    @Column(name="MOBILE")
    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_SEEN", nullable = false)
    private Date lastSeen;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> userRoles = new HashSet<Role>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_PERMISSIONS",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") })
    private Set<Permission> userPermissions = new HashSet<Permission>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_SUBORDINATES",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "CHILD_ID") })
    private Set<User> subordinates = new HashSet<User>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_SUBORDINATES",
            joinColumns = { @JoinColumn(name = "CHILD_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    private Set<User> parents = new HashSet<User>();

    @OneToMany(mappedBy = "creator")
    private Set<Order> ordersOfCreator;

    @OneToMany(mappedBy = "owner")
    private Set<Order> ordersOfOwner;

    @OneToMany(mappedBy = "transportManager")
    private Set<Order> ordersOfTransportManager;

    @OneToMany(mappedBy = "ownerUser")
    private Set<TelegramUser> telegrams;

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public List<Role> getUserRoles() {
        return new ArrayList<Role>(userRoles);
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<User> subordinates) {
        this.subordinates = subordinates;
    }

    public Set<User> getParents() {
        return parents;
    }

    public void setParents(Set<User> parents) {
        this.parents = parents;
    }

    public Set<Permission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Set<Permission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public String getPasswordOriginal() {
        return passwordOriginal;
    }

    public void setPasswordOriginal(String passwordOriginal) {
        this.passwordOriginal = passwordOriginal;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Order> getOrdersOfCreator() {
        return ordersOfCreator;
    }

    public void setOrdersOfCreator(Set<Order> ordersOfCreator) {
        this.ordersOfCreator = ordersOfCreator;
    }

    public Set<Order> getOrdersOfOwner() {
        return ordersOfOwner;
    }

    public void setOrdersOfOwner(Set<Order> ordersOfOwner) {
        this.ordersOfOwner = ordersOfOwner;
    }

    public Set<Order> getOrdersOfTransportManager() {
        return ordersOfTransportManager;
    }

    public void setOrdersOfTransportManager(Set<Order> ordersOfTransportManager) {
        this.ordersOfTransportManager = ordersOfTransportManager;
    }

    public Set<TelegramUser> getTelegrams() {
        return telegrams;
    }

    public void setTelegrams(Set<TelegramUser> telegrams) {
        this.telegrams = telegrams;
    }
}
