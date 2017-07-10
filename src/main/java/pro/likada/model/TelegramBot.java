package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Yusupov on 3/23/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="TELEGRAM_BOTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelegramBot implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOT_ID")
    private Integer botId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ABOUT")
    private String about;

    @OneToMany(mappedBy = "telegramBot")
    private Set<TelegramButtonMenu> buttonMenus;

    @OneToMany(mappedBy = "telegramBot")
    private Set<TelegramConversation> conversations;

    @Override
    public String toString() {
        return "TelegramBot [id=" + id + ", username=" + username + ", name="  + name+ ", description="  + description + ", about="  + about;
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
        if (!(obj instanceof TelegramBot))
            return false;
        TelegramBot other = (TelegramBot) obj;
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

    public Integer getBotId() {
        return botId;
    }

    public void setBotId(Integer botId) {
        this.botId = botId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<TelegramButtonMenu> getButtonMenus() {
        return buttonMenus;
    }

    public void setButtonMenus(Set<TelegramButtonMenu> buttonMenus) {
        this.buttonMenus = buttonMenus;
    }

    public Set<TelegramConversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<TelegramConversation> conversations) {
        this.conversations = conversations;
    }
}
