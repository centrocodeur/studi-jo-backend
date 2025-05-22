package com.marien.studi_jo_backend.controller.admin;


import com.marien.studi_jo_backend.dto.CompetitionDto;

import com.marien.studi_jo_backend.services.admin.competition.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCompetitionController {


    private final CompetitionService competitionService;

    @PostMapping("competition")
    @Operation(summary = "Créer une competition")
    public ResponseEntity<CompetitionDto> createCompetition(@ModelAttribute CompetitionDto competitionDto) throws IOException {

        CompetitionDto competitionDto1 = competitionService.creatCompetition(competitionDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(competitionDto1);
    }


    @GetMapping("competitions")
    @Operation(summary = "voir la liste des compétitions")
    public ResponseEntity<List<CompetitionDto>>getAllCompetitions(){
        List<CompetitionDto> competitionDtos = competitionService.getAllCompetitions();
        return ResponseEntity.ok(competitionDtos);
    }


    @DeleteMapping("/competition/{competitionId}")
    @Operation(summary = "Supprimer une compétition")
    public  ResponseEntity<Void> deleteCompetition(@PathVariable Long competitionId){
        boolean deleted = competitionService.deleteCompetition(competitionId);

        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/competition/{competitionId}")
    @Operation(summary = "Obtenir une compétition à l'aide de son identification")
    public ResponseEntity<CompetitionDto> getCompetitionById(@PathVariable Long competitionId){

        CompetitionDto competitionDto = competitionService.getCompetitionById(competitionId);
        if(competitionDto!=null){
            return ResponseEntity.ok(competitionDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/competition/{competitionId}")
    @Operation(summary = "Modifier une compétition")
    public ResponseEntity<CompetitionDto> updateCompetition(@PathVariable Long competitionId, @ModelAttribute CompetitionDto competitionDto) throws IOException {

        CompetitionDto updatedCompetition = competitionService.updateCompetition(competitionId,competitionDto);
        if(updatedCompetition!=null){
            return ResponseEntity.ok(updatedCompetition);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
