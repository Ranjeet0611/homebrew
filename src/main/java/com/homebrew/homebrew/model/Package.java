package com.homebrew.homebrew.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
@Getter
@Setter
@Document(collection = "package")
public class Package {
    private String id;
    private String name;
    private String version;
    private String description;
    private String downloadUrl;
    private String contentHash;
    private LocalDateTime createdAt;
}
