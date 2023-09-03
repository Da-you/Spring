package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    private final static int BAR_LENGTH = 10;

    public List<Integer> getPaginationBarNumbers(int currentPage, int totalPage) {
        return null;
    }


    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
