package com.example.myboard.repository;

import com.example.myboard.constant.Gender;
import com.example.myboard.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Test
    void findByName테스트(){
        String findName = "Nydia";
        usersRepository.findByName(findName).forEach(users -> System.out.println(users));
    }

    @Test
    void findTop3LikeColor테스트(){
        String color = "Pink";
        usersRepository.findTop3ByLikeColor(color)
                .forEach(users -> System.out.println(users));
    }
    @Test
    void findByGenderAndLikeColor테스트(){
        String color = "Pink";
        usersRepository.findByGenderAndLikeColor(Gender.Male,color)
                .forEach(users -> System.out.println(users));
    }

    @Test
    void findByCreatedAtAfter테스트(){
        usersRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(30L))
                .forEach(users -> System.out.println(users));
//        롱타입이라서 L을 붙여준다
    }

    @Test
    void findByCreatedAtBetween테스트(){
        usersRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(31L),LocalDateTime.now())
                .forEach(users -> System.out.println(users));
    }

    @Test
    void findByIdBetween테스트(){
        usersRepository.findByIdBetween(1L, 5L)
                .forEach(users -> System.out.println(users));
    }

    @Test
    void findByColorAndSort(){
        usersRepository.findByLikeColor("Orange",
                Sort.by(Sort.Order.asc("gender"),
                        Sort.Order.desc("createdAt")))
                .forEach(users -> System.out.println(users));
    }

// 전체 페이징
    @Test
    void pagingTest(){
        System.out.println("페이지 = 0, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                PageRequest.of(0,5,Sort.by(Sort.Order.desc("id")))).
                getContent()
                .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 1, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(1,5,Sort.by(Sort.Order.desc("id")))).
                getContent()
                .forEach(users -> System.out.println(users));

        System.out.println("페이지 = 2, 페이지당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(2,5,Sort.by(Sort.Order.desc("id")))).
                getContent()
                .forEach(users -> System.out.println(users));
    }

    @Test
    void pagingTest2(){
        Pageable pageable = PageRequest.of(30, 10);
        Page<Users> result= usersRepository.findByIdGreaterThanEqualOrderByIdDesc(
                200L, pageable);
        result.getContent().forEach(users -> System.out.println(users));
//        총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());
//        전체 데이터 개수
        System.out.println("Total Contents Count : " + result.getContent());
//        현재 페이지 번호, 0부터 시작하기 때문에 현재 페이지보다 1개가 높다 배열과 비슷
        System.out.println("Current Page Number : " + result.getNumber());
//        다음 페이지 존재 여부, true/false 전달
        System.out.println("Next Page?" + result.hasNext());
//        시작 페이지인지 여부, 이전 버튼이 있는 지 여부와 같다
        System.out.println("Is First Page?" + result.isFirst());

    }

    @Test
    void 문제1(){
        String name1 = "%w%";
        String name2 = "%m%";

        usersRepository.findByGenderAndNameLike(Gender.Female, name1)
                .forEach(users -> System.out.println("w포함 : " + users));
        usersRepository.findByGenderAndNameLike(Gender.Female, name2)
                .forEach(users -> System.out.println("m포함 : " + users));
    }

    @Test
    void 문제1_1(){
        List<Users> usersList = usersRepository.findByNameLikeOrNameLike("%w%", "%m%");
        for (Users users : usersList){
            if (users.getGender().equals(Gender.Female)){
                System.out.println(users);
            }
        }
        System.out.println("=============================");
        usersRepository.findByNameLikeAndGenderOrNameLikeAndGender(
                "%w%",Gender.Female,"%m%",Gender.Female
        ).forEach(users -> System.out.println(users));
    }


    @Test
    void 문제2(){
        String email = "net";
        List<Users> users = usersRepository.findByEmailEndingWith(email);
        usersRepository.findByEmailEndingWith(email)
                .forEach(users1 -> System.out.println(users1));
        Long count = (long) users.size();
        System.out.println("카운트: " + count);
    }
    @Test
    void 문제2_2(){
        System.out.println("Count : " + usersRepository.findByEmailLike("%net%").stream().count());
    }


    @Test
    void 문제3(){
        String name = "J%";
        usersRepository.findByUpdatedAtAfterAndNameLike(LocalDateTime.now(),name)
                .forEach(users -> System.out.println(users));
    }
    @Test
    void 문제3_3(){
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(1L);
        usersRepository.findByUpdatedAtGreaterThanEqualAndNameLike(dateTime,"Q%")
                .forEach(users -> System.out.println(users));
    }

    @Test
    void 문제4(){
        usersRepository.findTop10ByOrderByUpdatedAtDesc()
                .forEach(users ->{
                            System.out.println("ID : " + users.getId()
                                    + " 이름 : " + users.getName()
                            + " 성별 : " + users.getGender()
                            + " 생성일 : " + users.getCreatedAt());
//                            System.out.println("성별 : " + users.getGender());
//                            System.out.println("생성일 : " + users.getCreatedAt());
//                            System.out.println("=========================");
                        });
    }
    @Test
    void 문제4_4(){
        List<Users> users = usersRepository.findTop10ByOrderByCreatedAtDesc();
        for (Users users1 : users){
            System.out.println("ID : " + users1.getName() +
                    "Gender : " + users1.getGender() +
                    "CreatedAt : " + users1.getCreatedAt() );
//            System.out.println("Gender : " + users1.getGender());
//            System.out.println("CreatedAt : " + users1.getCreatedAt());
        }
    }

    @Test
    void 문제5(){
        String color = "Red";
        usersRepository.findByLikeColorAndGender(color,Gender.Male)
                .forEach(users ->
                        System.out.println("Red를 좋아하는 남성 이메일 : " + users.getEmail().substring(0,8)));
        System.out.println("=======================");
        List<Users> users = usersRepository.findByLikeColorAndGender("Red", Gender.Male);
                for (Users users1 : users){
                    String email = users1.getEmail();
                    String e = email.substring(0,email.indexOf("@"));
                    System.out.println("Email : " + email + ", @빼고 : " + e);
                }
    }

    @Test
    void 문제6(){
        List<Users> users = usersRepository.findAll();
                for(Users user : users){
                    LocalDateTime createdAt = user.getCreatedAt();
                    LocalDateTime updatedAt = user.getUpdatedAt();
                    if (createdAt.compareTo(updatedAt) > 0){
//                        "createdAt이 updatedAt보다 이후입니다."
                        System.out.println(user);
                    }
                }
        System.out.println("===============");
                for (Users users1 : users){
                    if (users1.getUpdatedAt().isBefore(users1.getCreatedAt())){
                        System.out.println(users1);
                    }
                }
    }

    @Test
    void 문제7(){
        String email = "%edu%";
        usersRepository.findByGenderAndEmailLikeOrderByCreatedAtDesc(Gender.Female,email)
                .forEach(users -> System.out.println("edu를 갖는 여성 데이터 최근 순서 : " + users));
        System.out.println("==============================");
        usersRepository.findByEmailLikeAndGenderOrderByCreatedAtDesc("%edu%",Gender.Female)
                .forEach(users -> System.out.println(users));
    }

    @Test
    void 문제8(){
        usersRepository.findByOrderByLikeColorAscNameDesc()
                .forEach(users -> System.out.println("색 오름차순 및 이름 내림차순 : " + users));
        System.out.println("=================");
        usersRepository.findAll(
                Sort.by(Sort.Order.asc("LikeColor"),
                        Sort.Order.desc("name"))
        ).forEach(users -> System.out.println(users));
    }

//    @Test
//    void 문제9(){
////        Pageable pageable = PageRequest.of(1, 10);
////        Page<Users> result = usersRepository.findOrderByCreatedAtSort()
//        usersRepository.findAll(
//                        PageRequest.of(1,10,Sort.by(Sort.Order.desc("updateAt")))).
//                getContent()
//                .forEach(users -> System.out.println(users));
//    }
    @Test
    void 문제9(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Order.desc("createdAt")));
        Page<Users> result = usersRepository.findAllByOrderByCreatedAtDesc(pageable);
        result.getContent().forEach(users -> System.out.println("최근 입력한 자료 순으로 1번째 페이지 : " + users));
        System.out.println("=======================");
        usersRepository.findAll(
                PageRequest.of(1,10,Sort.by(Sort.Order.desc("createdAt")))
        ).getContent()
                .forEach(users -> System.out.println(users));
    }

    @Test
    void 문제10(){
        Pageable pageable = PageRequest.of(2, 3, Sort.by(Sort.Order.desc("id")));
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male, pageable);
        result.getContent().forEach(users -> System.out.println("남성 ID 내림차순 2번째 페이지 : " + users));
    }

    @Test
    void 문제11(){
        // 현재 시간을 가져옵니다.
        LocalDateTime now = LocalDateTime.now();
        // 현재 시간에서 한 달을 빼서 이전 달로 이동합니다.
        LocalDateTime firstDayOfThisMonth = now.minusMonths(1);
        // 이전 달의 첫 번째 날을 나타내는 변수를 만듭니다.
        LocalDateTime firstDayOfLastMonth = firstDayOfThisMonth
                // 이전 달의 첫 번째 날로 설정합니다.
                .withDayOfMonth(1);

        // 'createdAt' 필드가 이전 달의 첫 번째 날부터 마지막 날까지의 범위에 속하는 데이터를 데이터베이스에서 가져옵니다.
        usersRepository.findByCreatedAtBetween(firstDayOfLastMonth, firstDayOfThisMonth)
                .forEach(users -> System.out.println(users));

        System.out.println("==========================");
//      기준일
        LocalDate baseDate = LocalDate.now().minusMonths(1L);
//        시작일
        LocalDateTime startDate = baseDate.withDayOfMonth(1).atTime(0,0,0);
//        종료일
        LocalDateTime lastDate = baseDate.withDayOfMonth(baseDate.lengthOfMonth()).atTime(23,59,59);
//        검색
        System.out.println("start date : " + startDate + ", last date : " + lastDate);
        usersRepository.findByCreatedAtBetween(startDate,lastDate)
                .forEach(users -> System.out.println(users));
    }














//    @Autowired
//    UsersRepository usersRepository;
//    @Test
//    @DisplayName("Users 테이블 입력_테스트")
//    void inputUsers(){
//        Users users = Users.builder()
//                .name("홍길동")
//                .email("test@test.com")
//                .gender(Gender.Male)
//                .likeColor("Red")
//                .build();
//        usersRepository.save(users);
//    }
//    @Test
//    @DisplayName("Users 테이블 수정_테스트")
//    public void usersUpdate(){
//        Users users = Users.builder()
//                .id(1L)
//                .name("홍길순")
//                .email("test@test.com")
//                .gender(Gender.Female)
//                .likeColor("Yellow")
//                .build();
//        usersRepository.save(users);
//
//
//    }

}