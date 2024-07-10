package com.yang.artcollectorsregistration.service;

import com.yang.artcollectorsregistration.dto.ArtCollectorDto;
import com.yang.artcollectorsregistration.entity.ArtCollector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtCollectorService {
    void saveArtCollector(ArtCollectorDto artCollectorDto);
    ArtCollector findArtCollectorByEmail(String email);
    List<ArtCollectorDto> findAllArtCollectors();
}
