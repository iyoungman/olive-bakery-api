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
        List<SalesDto.GetGraphTmp> averageAnnualSales = salesRepository.getAverageSales("DAY", LocalDate.now());
        averageAnnualSales.forEach(getGraphTmp -> System.out.println(getGraphTmp.getAve() + "    " + getGraphTmp.getDate() + "    " + getGraphTmp.getSaleType()));
    }

}