package com.yang.artcollectorsregistration.repository;

import com.yang.artcollectorsregistration.entity.ArtCollector;
import com.yang.artcollectorsregistration.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtCollectorRepository extends JpaRepository<ArtCollector, Long> {
    ArtCollector findByEmail(String name);
}
