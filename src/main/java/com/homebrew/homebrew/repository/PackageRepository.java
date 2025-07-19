package com.homebrew.homebrew.repository;

import com.homebrew.homebrew.model.Package;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageRepository extends MongoRepository<Package, String> ,CustomPackageRepository{

}
