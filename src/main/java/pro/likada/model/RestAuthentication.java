package pro.likada.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by bumur on 10.04.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="REST_AUTHENTICATION")
public class RestAuthentication {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "GCM_TOKEN")
    private String gcmToken;

    @OneToOne
    @JoinColumn(name = "REST_USER")
    private User restUser;

    @Override
    public String toString() {
        return "REST_AUTHENTICATION[" +
                "id=" + id +
                ", TOKEN='" + token +
                ']';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getRestUser() {
        return restUser;
    }

    public void setRestUser(User restUser) {
        this.restUser = restUser;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }
}
