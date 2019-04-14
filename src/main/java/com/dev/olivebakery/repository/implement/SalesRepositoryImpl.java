package com.dev.olivebakery.repository.implement;

import com.dev.olivebakery.domain.dto.SalesDto;
import com.dev.olivebakery.domain.entity.QSales;
import com.dev.olivebakery.domain.entity.Sales;
import com.dev.olivebakery.repository.custom.SalesRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Component
public class SalesRepositoryImpl extends QuerydslRepositorySupport implements SalesRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    QSales sales = QSales.sales1;

    public SalesRepositoryImpl() {
        super(Sales.class);
    }

    @Override
    public List<SalesDto.GetAverage> getAverageSales(String dayType, LocalDate date) {
        JPAQuery<SalesDto.GetAverage> query = setGraphQuery(dayType, date);
        return query.fetch();
    }

    @Override
    public List<SalesDto.GetDashBoardData> getDashData() {
        return null;
    }

    private JPAQuery<SalesDto.GetAverage> setGraphQuery(String DayType, LocalDate date){
        JPAQuery<SalesDto.GetAverage> query = new JPAQuery<>(entityManager);
        query.select(Projections.constructor(SalesDto.GetAverage.class, sales.date, sales.sales.avg(), sales.saleType))
                .from(sales);
        if(DayType.equals("YEAR")){
            query.groupBy(sales.date.year(), sales.saleType);
        } else if (DayType.equals("MONTH")) {
            query.where(sales.date.year().eq(date.getYear()))
                    .groupBy(sales.date.month(), sales.saleType);
        } else {
            query.where(sales.date.year().eq(date.getYear()))
                    .where(sales.date.month().eq(date.getMonthValue()))
                    .groupBy(sales.date, sales.saleType);
        }
        return query;
    }
}
