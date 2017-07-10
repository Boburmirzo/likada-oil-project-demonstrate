package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bumur on 29.01.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DOCUMENTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "DOCUMENT_NAME", nullable = false)
    private String document_name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_CREATED", nullable = false)
    private Date date_created;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DOCUMENT_PATH")
    private String document_path;

    @NotNull
    @Column(name = "DOCUMENT_SIZE", nullable = false)
    private Long document_size;

    @NotNull
    @Column(name = "DOCUMENT_EXTENSION", nullable = false)
    private String document_extension;

    @NotNull
    @Column(name = "PARENT_ID", nullable = false)
    private Long parent_id;

    @Column(name = "PARENT_TYPE")
    private String parent_type;

    @OneToOne
    @JoinColumn(name = "CREATOR_USER_ID")
    private User creator_user_id;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_TYPE_ID")
    private DocumentType document_type;

    @Override
    public String toString() {
        return "DOCUMENT [id=" + id + ", DOCUMENT_NAME=" + document_name + ", DATE_CREATED=" + date_created + ", DESCRIPTION=" + description+ ", DOCUMENT_PATH=" + document_path+
                ", DOCUMENT_SIZE=" + document_size+", DOCUMENT_EXTENSION=" + document_extension+", PARENT_ID="+parent_id+", PARENT_TYPE="+parent_type+", DOCUMENT_TYPE_ID="+document_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocument_path() {
        return document_path;
    }

    public void setDocument_path(String document_path) {
        this.document_path = document_path;
    }

    public Long getDocument_size() {
        return document_size;
    }

    public void setDocument_size(Long document_size) {
        this.document_size = document_size;
    }

    public String getDocument_extension() {
        return document_extension;
    }

    public void setDocument_extension(String document_extension) {
        this.document_extension = document_extension;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_type() {
        return parent_type;
    }

    public void setParent_type(String parent_type) {
        this.parent_type = parent_type;
    }

    public User getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(User creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public DocumentType getDocument_type() {
        return document_type;
    }

    public void setDocument_type(DocumentType document_type) {
        this.document_type = document_type;
    }
}
