package com.bibliotheque.gestion_bibliotheque.adapters.repository;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import com.bibliotheque.gestion_bibliotheque.domain.repository.EmpruntRepository;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.EmpruntEntity;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository.JpaEmpruntRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmpruntRepositoryAdapter implements EmpruntRepository {

    private final JpaEmpruntRepository jpaRepository;

    public EmpruntRepositoryAdapter(JpaEmpruntRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Emprunt save(Emprunt emprunt) {
        EmpruntEntity entity = toEntity(emprunt);
        EmpruntEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Emprunt> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Emprunt> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findByMembreId(Long membreId) {
        return jpaRepository.findByMembreId(membreId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findByLivreId(Long livreId) {
        return jpaRepository.findByLivreId(livreId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findByMembreIdAndStatut(Long membreId, String statut) {
        return jpaRepository.findByMembreIdAndStatut(membreId, statut).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findByStatut(String statut) {
        return jpaRepository.findByStatut(statut).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findByStatutAndDateRetourPrevueBefore(String statut, LocalDate date) {
        return jpaRepository.findByStatutAndDateRetourPrevueBefore(statut, date).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int countByMembreIdAndStatut(Long membreId, String statut) {
        return jpaRepository.countByMembreIdAndStatut(membreId, statut);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    // ========== MAPPERS PRIVÉS ==========

    private EmpruntEntity toEntity(Emprunt emprunt) {
        EmpruntEntity entity = new EmpruntEntity();
        entity.setId(emprunt.getId());
        entity.setLivreId(emprunt.getLivreId());
        entity.setMembreId(emprunt.getMembreId());
        entity.setDateEmprunt(emprunt.getDateEmprunt());
        entity.setDateRetourPrevue(emprunt.getDateRetourPrevue());
        entity.setDateRetourEffective(emprunt.getDateRetourEffective());
        entity.setStatut(emprunt.getStatut());
        entity.setPenalite(emprunt.getPenalite());
        return entity;
    }

    private Emprunt toDomain(EmpruntEntity entity) {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(entity.getId());
        emprunt.setLivreId(entity.getLivreId());
        emprunt.setMembreId(entity.getMembreId());
        emprunt.setDateEmprunt(entity.getDateEmprunt());
        emprunt.setDateRetourPrevue(entity.getDateRetourPrevue());
        emprunt.setDateRetourEffective(entity.getDateRetourEffective());
        emprunt.setStatut(entity.getStatut());
        emprunt.setPenalite(entity.getPenalite());
        return emprunt;
    }
}
