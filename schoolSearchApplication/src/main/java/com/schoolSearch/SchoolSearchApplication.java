package com.schoolSearch;

import com.schoolSearch.dto.Request;
import com.schoolSearch.dto.Response;
import com.schoolSearch.service.IndexSchoolInfoService;
import com.schoolSearch.service.SearchSchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class SchoolSearchApplication implements CommandLineRunner {

    private final IndexSchoolInfoService indexSchoolInfoService;
    private final SearchSchoolService searchSchoolService;
    public static void main(String[] args) {
        SpringApplication.run(SchoolSearchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        indexSchoolInfoService.createIndex();
//
        String searchWord = "신상도초등학교";
        String date = "20240430";
        Request request = new Request(searchWord, date);

        List<Response> searchResults = searchSchoolService.search(request);

        System.out.println("검색어 : " + request);
        System.out.println("검색 결과 : " + searchResults.size());
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.printf("검색 결과 [" + i + "] :");
            System.out.println(searchResults.get(i));
        }
    }
}