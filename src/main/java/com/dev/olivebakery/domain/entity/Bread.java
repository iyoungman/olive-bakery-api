package com.dev.olivebakery.domain.entity;

import com.dev.olivebakery.domain.enums.BreadState;
import com.dev.olivebakery.domain.enums.DayType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO 빵이 지금 판매되고 있는지


@Entity
@Table(name = "bread_tbl")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bread_id")
    private Long breadId;

    @Column(unique = true)
    private String name;

    private Integer price;

    private String picturePath;

    //상세정보가 아닌 간단한 소개(리스트에서 보내줄 것)
    private String description;

    //빵을 클릭했을 때 선택한 빵의 상세 소개
    private String detailDescription;

    private boolean isSelling;

    // 관리자가 선정한 빵 상태(추천, 등등)
    @Enumerated(value = EnumType.STRING)
    private BreadState state;

    // 무슨 요일에 파는 빵인지
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<DayType> days = new HashSet<>();

    // 빵이 매진인지. (true면 매진)
    @OneToOne(fetch = FetchType.EAGER,mappedBy = "bread")
    private SoldOut soldOut;

    // 어떤 재료가 들어가며 재료의 원산지 표기
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bread", fetch = FetchType.LAZY)
    private List<Ingredients> ingredients = new ArrayList<>();

    @Builder
    public Bread(String name, Integer price, String picturePath, String detailDescription, String description, boolean isSelling, BreadState state, Set<DayType> days, List<Ingredients> ingredients) {
        this.name = name;
        this.price = price;
        this.picturePath = picturePath;
        this.description = description;
        this.detailDescription = detailDescription;
        this.isSelling = isSelling;
        this.state = state;
        this.days = days;
        this.ingredients = ingredients;
    }
}
