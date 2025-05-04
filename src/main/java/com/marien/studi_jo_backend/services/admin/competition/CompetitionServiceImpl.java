package com.marien.studi_jo_backend.services.admin.competition;

import com.marien.studi_jo_backend.dto.CompetitionDto;
import com.marien.studi_jo_backend.dto.TicketDto;
import com.marien.studi_jo_backend.entity.Competition;
import com.marien.studi_jo_backend.entity.Ticket;
import com.marien.studi_jo_backend.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    @Override
    public CompetitionDto creatCompetition(CompetitionDto competitionDto) throws IOException {

        Competition competition = new Competition();

        competition.setName(competitionDto.getName());
        competition.setCompDate(competitionDto.getCompDate());
        competition.setCompTime(competitionDto.getCompTime());
        competition.setSite(competitionDto.getSite());
        competition.setCity(competitionDto.getCity());
        competition.setImg(competitionDto.getImg().getBytes());

        return competitionRepository.save(competition).getDto();
    }

    @Override
    public List<CompetitionDto> getAllCompetitions() {

        List<Competition> competitions = competitionRepository.findAll();
        return  competitions.stream().map(Competition::getDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCompetition(Long id) {
        Optional<Competition> competitionOptional = competitionRepository.findById(id);

        if(competitionOptional.isPresent()){
            competitionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CompetitionDto getCompetitionById(Long competitionId) {
        Optional<Competition> competitionOptional = competitionRepository.findById(competitionId);

        if(competitionOptional.isPresent()){

            return competitionOptional.get().getDto();

        }else {
            return null;
        }

    }

    @Override
    public CompetitionDto updateCompetition(Long competitionId, CompetitionDto competitionDto) throws IOException {

        Optional<Competition> competitionOptional = competitionRepository.findById(competitionId);

        if(competitionOptional.isPresent()){
           Competition competition = competitionOptional.get();
           competition.setName(competitionDto.getName());
           competition.setCompDate(competitionDto.getCompDate());
           competition.setCompTime(competitionDto.getCompTime());
           competition.setSite(competitionDto.getSite());
           competition.setCity(competitionDto.getCity());

           if(competitionDto.getImg() !=null){
               competition.setImg(competitionDto.getImg().getBytes());
           }

           return competitionRepository.save(competition).getDto();
        }
        return null;
    }


}
