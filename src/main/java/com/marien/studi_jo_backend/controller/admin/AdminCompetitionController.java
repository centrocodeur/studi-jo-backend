package com.marien.studi_jo_backend.controller.admin;


import com.marien.studi_jo_backend.dto.CompetitionDto;

import com.marien.studi_jo_backend.services.admin.competition.CompetitionService;
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
    public ResponseEntity<CompetitionDto> createCompetition(@ModelAttribute CompetitionDto competitionDto) throws IOException {

        CompetitionDto competitionDto1 = competitionService.creatCompetition(competitionDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(competitionDto1);
    }


    @GetMapping("competitions")
    public ResponseEntity<List<CompetitionDto>>getAllCompetitions(){
        List<CompetitionDto> competitionDtos = competitionService.getAllCompetitions();
        return ResponseEntity.ok(competitionDtos);
    }


    @DeleteMapping("/competition/{competitionId}")
    public  ResponseEntity<Void> deleteCompetition(@PathVariable Long competitionId){
        boolean deleted = competitionService.deleteCompetition(competitionId);

        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/competition/{competitionId}")
    public ResponseEntity<CompetitionDto> getCompetitionById(@PathVariable Long competitionId){

        CompetitionDto competitionDto = competitionService.getCompetitionById(competitionId);
        if(competitionDto!=null){
            return ResponseEntity.ok(competitionDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/competition/{competitionId}")
    public ResponseEntity<CompetitionDto> updateCompetition(@PathVariable Long competitionId, @ModelAttribute CompetitionDto competitionDto) throws IOException {

        CompetitionDto updatedCompetition = competitionService.updateCompetition(competitionId,competitionDto);
        if(updatedCompetition!=null){
            return ResponseEntity.ok(updatedCompetition);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
