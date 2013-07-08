package net.nucleus.rss.controller;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: starasov
 * Date: 7/8/13
 * Time: 10:09 AM
 */
public class OmplImportForm {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
