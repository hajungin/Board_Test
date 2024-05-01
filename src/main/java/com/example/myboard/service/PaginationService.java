package com.example.myboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages){
        int startNumber = Math.max(currentPageNumber-(BAR_LENGTH/2),0);
//        0과 비교해서 큰 값을 가져온다
        int endNumber = Math.min(startNumber + BAR_LENGTH,totalPages);
//        총 페이지 개수와 비교해서 마지막 페이지 번호는 작은 값을 보여주는 걸로 한다.
//        스트림을 사용하면 마지막 번호가 잡히지 않는다
        return IntStream.range(startNumber,endNumber).boxed().toList();
//        정수치 리스트를 만든다 시작과 끝을 기점으로 boxed 는 맨 뒷자리를 빼는 역할을 해주어서 5개만 페이지 번호가 출력되게 한다
    }
}
