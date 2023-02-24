package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "notifications_tbls")
public class notifications_tbl
{

    @Id
    @GeneratedValue
    int id;
    String title;
    String content;
    String link;
    String from;
    boolean seen;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;

    public notifications_tbl() {
    }

    public notifications_tbl(String title, String content, String link, String from, boolean seen, users_tbl user_id) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.from = from;
        this.seen = seen;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }
}
