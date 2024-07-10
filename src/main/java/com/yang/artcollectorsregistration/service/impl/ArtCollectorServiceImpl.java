package com.yang.artcollectorsregistration.service.impl;

import com.yang.artcollectorsregistration.dto.ArtCollectorDto;
import com.yang.artcollectorsregistration.entity.ArtCollector;
import com.yang.artcollectorsregistration.entity.Role;
import com.yang.artcollectorsregistration.repository.RoleRepository;
import com.yang.artcollectorsregistration.repository.ArtCollectorRepository;
import com.yang.artcollectorsregistration.service.ArtCollectorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtCollectorServiceImpl implements ArtCollectorService {
    private ArtCollectorRepository artCollectorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public ArtCollectorServiceImpl(ArtCollectorRepository ArtCollectorRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.artCollectorRepository = ArtCollectorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveArtCollector(ArtCollectorDto ArtCollectorDto) {
        ArtCollector artCollector = new ArtCollector();
        artCollector.setUsername(ArtCollectorDto.getUsername());
        artCollector.setEmail(ArtCollectorDto.getEmail());

        artCollector.setPassword(passwordEncoder.encode(ArtCollectorDto.getPassword()));


        //Determine the role based on registration criteria
        String roleName;
        if(ArtCollectorDto.isAdminRegistration()){
            roleName = "ROLE_ADMIN";
        }else{
            roleName= "ROLE_ARTCOLLECTOR";
        }

        //Check if role already exists in database, otherwise create it
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName((roleName));
            roleRepository.save(role);
        }

        //Assign the role to the ArtCollector
        artCollector.setRoles((Collections.singletonList(role)));
        artCollectorRepository.save(artCollector);
    }

    @Override
    public ArtCollector findArtCollectorByEmail(String email) {
        return artCollectorRepository.findByEmail(email);
    }

    @Override
    public List<ArtCollectorDto> findAllArtCollectors() {
        List<ArtCollector> artCollectors = artCollectorRepository.findAll();
        return artCollectors.stream().map((artCollector) -> convertEntityToDto(artCollector))
                .collect(Collectors.toList());

    }

    private ArtCollectorDto convertEntityToDto(ArtCollector artCollector){
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        artCollectorDto.setUsername(artCollector.getUsername());
        artCollectorDto.setEmail(artCollector.getEmail());
        return artCollectorDto;
    }
}