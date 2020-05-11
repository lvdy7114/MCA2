package org.magnum.mobilecloud.video.repository;

import javax.persistence.*;

/**
 * User: lin Date: 5.08.2020 Time: 19:45*/

@Embeddable
@Entity
@Table(name = "unlikes")
public class Unlike {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "user")
    private String user;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    public Unlike() {
    }

    public Unlike(String user, Video video) {
        this.user = user;
        this.video = video;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}