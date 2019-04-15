package com.dev.olivebakery.service.breadService;

import com.dev.olivebakery.domain.dto.BreadDto;
import com.dev.olivebakery.domain.entity.Bread;
import com.dev.olivebakery.domain.entity.BreadImage;
import com.dev.olivebakery.domain.entity.Days;
import com.dev.olivebakery.domain.entity.Ingredients;
import com.dev.olivebakery.domain.enums.DayType;
import com.dev.olivebakery.repository.BreadRepository;
import com.dev.olivebakery.repository.DaysRepository;
import com.dev.olivebakery.repository.IngredientsRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Log
public class BreadSaveService {

    private final BreadRepository breadRepository;
    private final IngredientsRepository ingredientsRepository;
    private final DaysRepository daysRepository;


    public BreadSaveService(BreadRepository breadRepository, IngredientsRepository ingredientsRepository, DaysRepository daysRepository) {
        this.breadRepository = breadRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.daysRepository = daysRepository;
    }

    public void saveBread(BreadDto.BreadSave breadSave, MultipartFile image) throws IOException{

        log.info(breadSave.getName());

        Bread bread = breadSaveDto2Bread(breadSave);

        breadRepository.save(bread);

        saveIngredients(breadSave.getIngredientsList(), bread);
        saveDays(breadSave.getDayTypes(), bread);

        saveImage(image, bread);

    }

    private Bread breadSaveDto2Bread(BreadDto.BreadSave breadSave){
        return Bread.builder()
                .name(breadSave.getName())
                .price(breadSave.getPrice())
                .description(breadSave.getDescription())
                .detailDescription(breadSave.getDetailDescription())
                .build();
    }

    private void saveIngredients(List<BreadDto.BreadIngredient> breadIngredients, Bread bread) {
        breadIngredients.forEach(breadIngredient -> {
            Ingredients ingredients = Ingredients.builder()
                    .bread(bread)
                    .name(breadIngredient.getName())
                    .origin(breadIngredient.getOrigin())
                    .build();

            ingredientsRepository.save(ingredients);
        });
    }

    public void saveDays(List<DayType> daysTypes, Bread bread){

        daysTypes.forEach(dayType -> {
            Days days = Days.builder()
                    .bread(bread)
                    .dayType(dayType).build();

            daysRepository.save(days);
        });
    }

    public BreadImage saveImage(MultipartFile imageFile, Bread bread) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        File destinationFile = new File("C:\\Users\\Kimyunsang\\Desktop\\spring\\imageTest"+ File.separator + fileName);

        imageFile.transferTo(destinationFile);

        BreadImage breadImage = BreadImage.builder()
                .imageName(imageFile.getOriginalFilename())
                .imageSize(imageFile.getSize())
                .imageType(imageFile.getContentType())
                .imageUrl("http://localhost:8080/Users\\Kimyunsang\\Desktop\\spring\\imageTest" + fileName)
                .bread(bread)
                .build();

        return breadImage;
    }
}
