package com.marien.studi_jo_backend.controller.customer;

import com.marien.studi_jo_backend.dto.CompetitionDto;
import com.marien.studi_jo_backend.services.admin.competition.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerCompetitionController {
    private final CompetitionService competitionService;




    @GetMapping("competitions")
    public ResponseEntity<List<CompetitionDto>> getAllCompetitions(){
        List<CompetitionDto> competitionDtos = competitionService.getAllCompetitions();
        return ResponseEntity.ok(competitionDtos);
    }

}
