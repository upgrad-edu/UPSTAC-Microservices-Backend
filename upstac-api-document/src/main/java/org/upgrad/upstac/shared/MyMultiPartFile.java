package org.upgrad.upstac.shared;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MyMultiPartFile implements MultipartFile {
    private final String name;
    private String originalFilename;
    @Nullable
    private String contentType;
    private final byte[] content;



    public MyMultiPartFile(String name, InputStream contentStream) throws IOException {
        this(name, name, "image/png", (byte[]) FileCopyUtils.copyToByteArray(contentStream));
    }

    public MyMultiPartFile(String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content) {
        this.name = name;
        this.originalFilename = originalFilename != null ? originalFilename : "";
        this.contentType = contentType;
        this.content = content != null ? content : new byte[0];
    }


    public String getName() {
        return this.name;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Nullable
    public String getContentType() {
        return this.contentType;
    }

    public boolean isEmpty() {
        return this.content.length == 0;
    }

    public long getSize() {
        return (long)this.content.length;
    }

    public byte[] getBytes() throws IOException {
        return this.content;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}