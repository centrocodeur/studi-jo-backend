package com.marien.studi_jo_backend.services.admin.competition;

import com.marien.studi_jo_backend.dto.CompetitionDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.entity.Competition;

import java.io.IOException;
import java.util.List;

public interface CompetitionService {

    CompetitionDto creatCompetition(CompetitionDto competitionDto) throws IOException;

    List<CompetitionDto> getAllCompetitions();



    boolean deleteCompetition(Long id);


    CompetitionDto getCompetitionById(Long competitionId);

    CompetitionDto updateCompetition(Long competitionId, CompetitionDto competitionDto) throws IOException;
}
