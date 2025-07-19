package com.homebrew.homebrew.repository.impl;

import com.homebrew.homebrew.model.Package;
import com.homebrew.homebrew.repository.CustomPackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
public class CustomPackageRepositoryImpl implements CustomPackageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Package> getPackageByName(String name) {
        try {
            log.info("Start getPackageByNameAndVersion :{}", name);
            Criteria criteria = Criteria.where("name").is(name);
            Query query = new Query(criteria);
            query.with(Sort.by(Sort.Direction.DESC,"createdAt"));
            return mongoTemplate.find(query, Package.class);
        } catch (Exception e) {
            log.error("Exception occurred while getPackageByNameAndVersion :{} :{}", e, e.getMessage());
            throw e;
        }
    }

    public List<Package> getPackageByNameAndContentHash(String name, String contentHash) {
        try {
            log.info("Start getPackageByNameAndContentHash :{} :{}", name, contentHash);
            Criteria criteria = Criteria.where("contentHash").is(contentHash).orOperator(Criteria.where("name").is(name));
            Query query = new Query(criteria);
            query.with(Sort.by(Sort.Direction.DESC,"createdAt"));
            return mongoTemplate.find(query, Package.class);
        } catch (Exception e) {
            log.error("Exception occurred while getPackageByNameAndContentHash :{} :{}", e, e.getMessage());
            throw e;
        }
    }
}
