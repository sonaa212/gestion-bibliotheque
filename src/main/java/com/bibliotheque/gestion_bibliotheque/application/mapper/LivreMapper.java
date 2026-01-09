package com.bibliotheque.gestion_bibliotheque.application.mapper;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;
import com.bibliotheque.gestion_bibliotheque.application.dto.LivreDto;

import java.util.List;
import java.util.stream.Collectors;

public class LivreMapper {

    public static LivreDto toDto(Livre livre) {
        if (livre == null) return null;

        return new LivreDto(
                livre.getId(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getIsbn(),
                livre.getEditeur(),
                livre.getAnneePublication(),
                livre.getCategorie(),
                livre.getNombreExemplaires(),
                livre.getNombreDisponibles(),
                livre.getEtatPhysique()
        );
    }

    public static Livre toEntity(LivreDto dto) {
        if (dto == null) return null;

        return new Livre(
                dto.getId(),
                dto.getTitre(),
                dto.getAuteur(),
                dto.getIsbn(),
                dto.getEditeur(),
                dto.getAnneePublication(),
                dto.getCategorie(),
                dto.getNombreExemplaires(),
                dto.getEtatPhysique()
        );
    }

    public static List<LivreDto> toDtoList(List<Livre> livres) {
        return livres.stream()
                .map(LivreMapper::toDto)
                .collect(Collectors.toList());
    }
}