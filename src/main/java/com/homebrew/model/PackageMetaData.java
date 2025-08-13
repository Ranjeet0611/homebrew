package com.homebrew.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackageMetaData {
    private String name;
    private String version;
    private String s3Url;
}
