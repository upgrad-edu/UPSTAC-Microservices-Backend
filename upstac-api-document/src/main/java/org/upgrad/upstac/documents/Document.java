package org.upgrad.upstac.documents;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private Long userId;

    public Document(Long  userId) {
        this.userId=userId;
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
