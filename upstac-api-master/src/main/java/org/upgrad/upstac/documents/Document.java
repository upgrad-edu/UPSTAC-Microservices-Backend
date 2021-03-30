package org.upgrad.upstac.documents;

import lombok.Data;
import org.upgrad.upstac.users.User;

import javax.persistence.*;

@Entity
@Data
public class Document {

    private  long size;
    public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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
