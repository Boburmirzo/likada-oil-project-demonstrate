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
 * Created by bumur on 29.01.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DOCUMENTS_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentType implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="TYPE", nullable=false, unique = true)
    private String type;

    @NotEmpty
    @Column(name="NAME", nullable=false, unique = true)
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "document_type")
    private Set<Document> documents;

    @Override
    public String toString() {
        return "DOCUMENT_TYPE [id=" + id + ", TYPE=" + type + ", NAME=" + name + ", DESCRIPTION=" + description;
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

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
}
