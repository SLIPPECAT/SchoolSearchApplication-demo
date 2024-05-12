package com.schoolSearch.service;

import com.schoolSearch.dto.Request;
import com.schoolSearch.dto.Response;
import com.schoolSearch.model.IndexField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchSchoolService {

    private final Analyzer analyzer;
    private final int MAX_HIT_NUM = 100;

    public SearchSchoolService() {
        analyzer = new KoreanAnalyzer();
    }

    @Value("${mypath}")
    String myPath;

    public List<Response> search(Request request) throws IOException, ParseException {

        String indexPath = myPath + "indices/schoolSearchApplication";
        String schoolName = request.schoolName();

        Directory index = FSDirectory.open(Path.of(indexPath));
        DirectoryReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        StoredFields storedFields = reader.storedFields();

        QueryParser queryParser = new QueryParser(IndexField.SCHOOL_KOREAN_NAME, analyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = queryParser.parse(schoolName);
//
        List<Response> responseList = new ArrayList<>();

        ScoreDoc[] hits = searcher.search(query, MAX_HIT_NUM).scoreDocs;

        for (ScoreDoc hit : hits) {
            Document document = reader.storedFields().document(hit.doc);
            String schoolKorName = document.get(IndexField.SCHOOL_KOREAN_NAME);
            String schoolEngName = document.get(IndexField.SCHOOL_ENGLISH_NAME);
            String schoolType = document.get(IndexField.SCHOOL_TYPE);
            String establishedDate = document.get(IndexField.ESTABLISHED_DATE);;

            // 유사도 검색 결과 점수 정보를 갖고 있는 Explanation 객체 (필요한 경우 주석 해제하여 결과 확인)
//            Explanation explanation = searcher.explain(query, hit.doc);
//            System.out.println("explanation = " + explanation);

            responseList.add(new Response(schoolKorName, schoolEngName, schoolType, establishedDate));

        }
        return responseList;
    }

}