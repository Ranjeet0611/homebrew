package com.homebrew.homebrew.cli.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageMetaData {
    private String name;
    private String version;
    private String s3Url;
}
