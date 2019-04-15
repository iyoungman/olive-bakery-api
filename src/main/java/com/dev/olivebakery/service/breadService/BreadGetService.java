package com.dev.olivebakery.service.breadService;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.enums.DayType;
import com.dev.olivebakery.repository.BreadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BreadGetService {

    BreadRepository breadRepository;

    public BreadGetService(BreadRepository breadRepository){
        this.breadRepository = breadRepository;
    }

    // 날짜 별로 빵 가져와야 함
    public List<BreadDto.BreadGetAll> getBreadByDay(DayType day){

        List<Bread> breadList = breadRepository.findByDays(day);
        List<BreadDto.BreadGetAll> breadGetAll = new ArrayList<>();
        breadList.forEach(bread -> {
            boolean isSoldOut = false;
            if(bread.getSoldOut() != null)
                isSoldOut = bread.getSoldOut().getDate().isEqual(LocalDate.now());

            breadGetAll.add(
                    BreadDto.BreadGetAll.builder()
                            .name(bread.getName())
                            .price(bread.getPrice())
                            .description(bread.getDescription())
                            .soldOut(isSoldOut)
                            .breadState(bread.getState())
                            .build());
        });

        return breadGetAll;
    }
}
