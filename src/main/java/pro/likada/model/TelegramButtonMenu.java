package pro.likada.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Yusupov on 3/30/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="TELEGRAM_BUTTON_MENUS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelegramButtonMenu implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TELEGRAM_BOT_ID")
    private TelegramBot telegramBot;

    @OneToMany(mappedBy = "currentMenu")
    private Set<TelegramConversation> conversations;

    @Override
    public String toString() {
        return "TelegramButtonMenu [ID=" + id + ", TYPE=" + type + ", NAME="  + name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TelegramButtonMenu))
            return false;
        TelegramButtonMenu other = (TelegramButtonMenu) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
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

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public Set<TelegramConversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<TelegramConversation> conversations) {
        this.conversations = conversations;
    }
}
