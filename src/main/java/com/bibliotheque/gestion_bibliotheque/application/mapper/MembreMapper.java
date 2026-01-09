package com.bibliotheque.gestion_bibliotheque.application.mapper;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Membre;
import com.bibliotheque.gestion_bibliotheque.application.dto.MembreDto;

import java.util.List;
import java.util.stream.Collectors;

public class MembreMapper {

    public static MembreDto toDto(Membre membre) {
        if (membre == null) return null;

        return new MembreDto(
                membre.getId(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getEmail(),
                membre.getTypeMembre(),
                membre.getQuotaEmprunt(),
                membre.getScoreFiabilite(),
                membre.getDateInscription()
        );
    }

    public static Membre toEntity(MembreDto dto) {
        if (dto == null) return null;

        Membre membre = new Membre(
                dto.getId(),
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                dto.getTypeMembre()
        );
        membre.setQuotaEmprunt(dto.getQuotaEmprunt());
        membre.setScoreFiabilite(dto.getScoreFiabilite());
        membre.setDateInscription(dto.getDateInscription());

        return membre;
    }

    public static List<MembreDto> toDtoList(List<Membre> membres) {
        return membres.stream()
                .map(MembreMapper::toDto)
                .collect(Collectors.toList());
    }
}