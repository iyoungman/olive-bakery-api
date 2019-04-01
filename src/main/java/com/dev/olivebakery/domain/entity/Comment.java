package com.dev.olivebakery.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by YoungMan on 2019-02-11.
 */

@Entity
@Table(name = "comment_tbl")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @CreationTimestamp
    private LocalDateTime insertTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    private String userName;

    @Lob
    private String content;

    @Builder
    public Comment(String userName, String content){
        this.userName = userName;
        this.content = content;
    }

}
