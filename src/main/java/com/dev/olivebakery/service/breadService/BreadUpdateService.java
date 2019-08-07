package com.dev.olivebakery.service.breadService;

import com.dev.olivebakery.domain.dtos.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.BreadImage;
import com.dev.olivebakery.exception.UserDefineException;
import com.dev.olivebakery.repository.BreadImageRepository;
import com.dev.olivebakery.repository.BreadRepository;
import com.dev.olivebakery.repository.DaysRepository;
import com.dev.olivebakery.repository.IngredientsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BreadUpdateService {

    private final BreadRepository breadRepository;
    private final IngredientsRepository ingredientsRepository;
    private final BreadSaveService breadSaveService;
    private final DaysRepository daysRepository;
    private final BreadImageRepository breadImageRepository;

        /* 빵 이름 수정*/
    public Bread updateBreadName(BreadDto.BreadUpdateName breadNames){
        Bread bread = breadRepository.findByName(breadNames.getOldName()).get();

        bread.updateName(breadNames.getNewName());

        return breadRepository.save(bread);
    }

    /* 빵 내용 수정 */
    @Transactional
    public Bread updateBread(MultipartFile image, String breadUpdateDtoJson) throws IOException {

        BreadDto.BreadUpdate breadUpdate = breadUpdateMapper(breadUpdateDtoJson);

        Bread bread = breadRepository.findByName(breadUpdate.getOldName())
                .orElseThrow(() -> new UserDefineException(breadUpdate.getOldName() + "이란 빵은 존재하지 않습니다."));

        if(image != null){
            updateBreadImage(image, breadUpdate.getOldName());
        }

        daysRepository.deleteByBread(bread);

        breadSaveService.saveDays(breadUpdate.getDayTypes(), bread);

        bread = updateBreadInfo(bread, breadUpdate);

        return breadRepository.save(bread);
    }

    private Bread updateBreadInfo(Bread bread, BreadDto.BreadUpdate breadUpdate){

        if(breadUpdate.getNewName() != null){
            bread.updateName(breadUpdate.getNewName());
        }

        bread.updatePrice(breadUpdate.getPrice());
        bread.updateDescription(breadUpdate.getDescription());
        bread.updateDetailDescription(breadUpdate.getDetailDescription());
        bread = updateIngredients(breadUpdate.getIngredientsList(), bread);

        return bread;
    }

    private BreadDto.BreadUpdate breadUpdateMapper(String breadUpdateDto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(breadUpdateDto, BreadDto.BreadUpdate.class);
    }

    private Bread updateIngredients(List<BreadDto.BreadIngredient> breadIngredients, Bread bread) {
        bread.updateBreadIngredients(breadSaveService.getIngredientsListFromIngredientsDtoList(breadIngredients));
        return bread;
    }

    /* 빵 이미지 수정*/
    @Transactional
    public BreadImage updateBreadImage(MultipartFile image, String breadName) throws IOException {
        Optional<Bread> breadOptional = breadRepository.findByName(breadName);

        Optional<BreadImage> breadImageOptional = breadImageRepository.findByBread(breadOptional.get());

        breadImageOptional.get().changeCurrentStatus(false);

        breadImageRepository.save(breadImageOptional.get());

        return breadImageRepository.save(breadSaveService.saveImage(image, breadOptional.get()));
    }

    /* 빵 삭제 */
    public Bread deleteBread(String name){
        Optional<Bread> breadOptional = breadRepository.findByName(name);
        breadOptional.get().deleteBread(true);
        return breadRepository.save(breadOptional.get());
    }

    /* 빵 상태 수정 */
    public Bread updateBreadState(BreadDto.BreadUpdateState breadUpdateState){
        Optional<Bread> breadOptional = breadRepository.findByName(breadUpdateState.getName());
        breadOptional.get().updateBreadState(breadUpdateState.getBreadState());
        return breadRepository.save(breadOptional.get());
    }

    /* 빵 매진 상태 변경 */
    public Bread updateBreadSoldOut(BreadDto.BreadUpdateSoldOut breadUpdateSoldOut){
        Bread bread = breadRepository.findByName(breadUpdateSoldOut.getName()).get();
        log.info("매진상태 변경 ---- ");
        bread.updateBreadSoldOut(breadUpdateSoldOut.getIsSoldOut());
        return breadRepository.save(bread);
    }
}
