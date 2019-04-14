package com.dev.olivebakery.repository.implement;

import com.dev.olivebakery.domain.dto.SalesDto;
import com.dev.olivebakery.repository.SalesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SalesRepositoryImplTest {

    @Autowired
    SalesRepository salesRepository;

    @Test
    public void getGraphData(){
        List<SalesDto.GetAverage> averageAnnualSales = salesRepository.getAverageSales("DAY", LocalDate.now());
        averageAnnualSales.forEach(getAverage -> System.out.println(getAverage.getAve() + "    " + getAverage.getDate() + "    " + getAverage.getSaleType()));
    }

}