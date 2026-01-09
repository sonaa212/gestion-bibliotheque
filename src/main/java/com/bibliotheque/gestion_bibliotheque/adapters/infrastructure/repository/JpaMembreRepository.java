package com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository;

import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.MembreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMembreRepository extends JpaRepository<MembreEntity, Long> {

    Optional<MembreEntity> findByEmail(String email);

    List<MembreEntity> findByTypeMembre(String typeMembre);

    List<MembreEntity> findByNomContaining(String nom);

    boolean existsByEmail(String email);
}