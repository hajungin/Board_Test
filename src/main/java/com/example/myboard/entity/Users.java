package com.example.myboard.entity;

import com.example.myboard.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//자동으로 날짜를 생성
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // findById

    private String name;    //findByName

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "Like_color")
    private String likeColor;       //findByLikeColor

    @Column(name = "created_at", updatable = false)
//    updatable = false, 수정이 안되도록
    @CreatedDate
    private LocalDateTime createdAt;        //findByCreatedAt

    @Column(name = "updated_at", insertable = false)
    @LastModifiedDate
//    insertable = false, 입력이 안되도록
    private LocalDateTime updatedAt;        //findByUpdatedAt
}
