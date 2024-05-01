package com.example.myboard.repository;

import com.example.myboard.constant.Gender;
import com.example.myboard.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.ListUI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
//    이름으로 검색
    List<Users> findByName(String name);
//    Pink 색상 데이터 중 상위 3개 데이터만 가져오기
    List<Users> findTop3ByLikeColor(String color);

//    gender와 color로 테이블 검색
    List<Users> findByGenderAndLikeColor(Gender gender, String color);

//    범위로 검색(After, Befor) -- 날짜/시간 데이터에 한정
    List<Users> findByCreatedAtAfter(LocalDateTime searchDate);

//    Between으로 자료 검색하기
//    List<Users> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Users> findByIdBetween(Long startId, Long endId);

//    Sort 별도 처리하는 법
//    Orange 색상 검색해서 Gender 오름차순, CreatedAt 내림차순
    List<Users> findByLikeColor(String color, Sort sort);

//    페이징 처리
//    주어진 아이디 보다 큰 데이터를 내림차순으로 검색한 후 페이징 처리
//    (id=200,  5번째 페이지, 한페이지당 10개씩)
    Page<Users> findByIdGreaterThanEqualOrderByIdDesc(Long id, Pageable paging);
//    Pageable 담아서 갖고 오는 인터페이스


//    문제 1
    List<Users> findByGenderAndNameLike(Gender gender, String name);
    List<Users> findByNameLikeOrNameLike(String str1, String str2);
    List<Users> findByNameLikeAndGenderOrNameLikeAndGender(String s1,Gender gender1,String s2, Gender gender2);

//  문제 2
    List<Users> findByEmailEndingWith(String email);
    List<Users> findByEmailLike(String Email);

//    문제 3
    List<Users> findByUpdatedAtAfterAndNameLike(LocalDateTime dateTime, String name);
    List<Users> findByUpdatedAtGreaterThanEqualAndNameLike(LocalDateTime date, String name);

//    문제 4
    List<Users> findTop10ByOrderByUpdatedAtDesc();
    List<Users> findTop10ByOrderByCreatedAtDesc();

//    문제 5
    List<Users> findByLikeColorAndGender(String color,Gender gender);

//  문제 6
//    List<Users> findAll();

//    문제 7
    List<Users> findByGenderAndEmailLikeOrderByCreatedAtDesc(Gender gender, String email);
    List<Users> findByEmailLikeAndGenderOrderByCreatedAtDesc(String email, Gender gender);

//    문제 8
    List<Users> findByOrderByLikeColorAscNameDesc();

//    문제 9
    Page<Users> findAllByOrderByCreatedAtDesc(Pageable pageable);


//    문제 10
    Page<Users> findByGenderOrderByIdDesc(Gender gender, Pageable pageable);


//    문제 11
    List<Users> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime lastDate);



}
