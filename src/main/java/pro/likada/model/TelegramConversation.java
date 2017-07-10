package pro.likada.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Yusupov on 3/23/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="TELEGRAM_CONVERSATIONS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TelegramConversation implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHAT_ID")
    private Long chatId;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TELEGRAM_USER_ID")
    private TelegramUser telegramUser;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TELEGRAM_BOT_ID")
    private TelegramBot telegramBot;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "CURRENT_MENU_ID")
    private TelegramButtonMenu currentMenu;

    @Override
    public String toString() {
        return "TelegramConversation [ID=" + id + ", CHAT_ID=" + chatId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((chatId == null) ? 0 : chatId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TelegramConversation))
            return false;
        TelegramConversation other = (TelegramConversation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (chatId == null) {
            if (other.chatId != null)
                return false;
        } else if (!chatId.equals(other.chatId))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public TelegramButtonMenu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(TelegramButtonMenu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
