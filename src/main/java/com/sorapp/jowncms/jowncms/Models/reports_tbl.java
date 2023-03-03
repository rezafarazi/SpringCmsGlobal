package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "reports_tbls")
public class reports_tbl
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String content;
    String date_time;
    boolean condition;
    @ManyToOne
    @JoinColumn(name = "report_id")
    report_tbl report_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    posts_tbl post_id;

    public reports_tbl() {
    }

    public reports_tbl(String content, String date_time, boolean condition, report_tbl report_id, users_tbl user_id, posts_tbl post_id) {
        this.content = content;
        this.date_time = date_time;
        this.condition = condition;
        this.report_id = report_id;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public boolean getCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public report_tbl getReport_id() {
        return report_id;
    }

    public void setReport_id(report_tbl report_id) {
        this.report_id = report_id;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }

    public posts_tbl getPost_id() {
        return post_id;
    }

    public void setPost_id(posts_tbl post_id) {
        this.post_id = post_id;
    }
}
