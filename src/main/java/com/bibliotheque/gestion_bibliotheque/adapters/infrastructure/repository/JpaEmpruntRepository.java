package com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository;

import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.EmpruntEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaEmpruntRepository extends JpaRepository<EmpruntEntity, Long> {

    List<EmpruntEntity> findByMembreId(Long membreId);

    List<EmpruntEntity> findByLivreId(Long livreId);

    List<EmpruntEntity> findByMembreIdAndStatut(Long membreId, String statut);

    List<EmpruntEntity> findByStatut(String statut);

    List<EmpruntEntity> findByStatutAndDateRetourPrevueBefore(String statut, LocalDate date);

    int countByMembreIdAndStatut(Long membreId, String statut);
}
