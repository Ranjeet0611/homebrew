package com.homebrew.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageRequest {
    private String name;
    private String description;
    private String version;
    private String contentHash;
    public PackageRequest(String name,String description,String version){
        this.name = name;
        this.description = description;
        this.version = version;
    }
}
