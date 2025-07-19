package com.homebrew.homebrew.repository;

import com.homebrew.homebrew.model.Package;

import java.util.List;

public interface CustomPackageRepository {
  List<Package> getPackageByName(String name);
  List<Package> getPackageByNameAndContentHash(String name,String contentHash);
}
