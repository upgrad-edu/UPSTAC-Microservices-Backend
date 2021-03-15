package org.upgrad.upstac.documents;

import lombok.Data;
import org.upgrad.upstac.users.User;

import javax.persistence.*;

@Entity
@Data
public class Document {

    private  long size;
    private  String contentType;
    private  String fileName;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;

    private String url;

    @OneToOne
    private User  user;

    public Document(User  user) {
        this.user=user;
    }

    public Document() {
    }
//    public Document(User  user, String url, String contentType, long size) {
//        this.user=user;
//        this.url=url;
//        this.contentType=contentType;
//        this.size=size;
//    }
}
