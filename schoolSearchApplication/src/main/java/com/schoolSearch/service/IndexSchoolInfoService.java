package com.schoolSearch.service;

import com.schoolSearch.model.IndexField;
import com.schoolSearch.model.SchoolInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IndexSchoolInfoService {

    private final Analyzer analyzer;

    public IndexSchoolInfoService() {
        analyzer = new KoreanAnalyzer();
    }

    @Value("${mypath}")
    String myPath;

    // 색인 작업을 실행합니다.
    public void createIndex() throws IOException, ParseException {
        System.out.println("----------- 색인 작업이 시작되었습니다 -----------");
        String dataPath = myPath + "data/학교기본정보_20240131.csv";
        String indexPath = myPath + "indices/schoolSearchApplication";

        Directory index = FSDirectory.open(Path.of(indexPath));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(index, config);

        List<SchoolInfo> schoolInfos = csv2SchoolInfos(dataPath);

        int count = 0;
        for (SchoolInfo schoolInfo : schoolInfos) {
            Document document = new Document();
            addStringField(document, IndexField.SIDO_OFFICE_EDUCATION_NAME, schoolInfo.getSidoOfficeOfEducationName());
            addTextField(document, IndexField.SCHOOL_KOREAN_NAME, schoolInfo.getSchoolKoreanName());
            addTextField(document, IndexField.SCHOOL_ENGLISH_NAME, schoolInfo.getSchoolEnglishName());
            addTextField(document, IndexField.SCHOOL_TYPE, schoolInfo.getSchoolType());
            addStringField(document, IndexField.SIDO_NAME, schoolInfo.getSidoName());
            addStringField(document, IndexField.ROAD_NAME_ZIP_CODE, schoolInfo.getRoadNameZipCode());
            addTextField(document, IndexField.ROAD_NAME_ADDRESS, schoolInfo.getRoadNameAddress());
            addStringField(document, IndexField.PHONE_NUMBER, schoolInfo.getPhoneNumber());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(schoolInfo.getEstablishmentDate());
            long epochMillis = date.getTime();

            System.out.println("establishedDate = " + date + " epochSeconds = " + epochMillis);
            document.add(new NumericDocValuesField(IndexField.SORT_ESTABLISHED_DATE, epochMillis));
            document.add(new StoredField(IndexField.SORT_ESTABLISHED_DATE, epochMillis));
            document.add(new StringField(IndexField.ESTABLISHED_DATE, schoolInfo.getEstablishmentDate(), Field.Store.YES));


            writer.addDocument(document);
            count ++;
        }
        System.out.println(count + " 라인");
        System.out.println("----------- 색인 작업이 완료되었습니다 -----------");

        writer.close();
    }

    private void addStringField(Document document, String indexField, String value) {
        document.add(new Field(indexField, value, StringField.TYPE_STORED));
    }

    private void addTextField(Document document, String indexField, String value) {
        document.add(new Field(indexField, value, TextField.TYPE_STORED));
    }

    public List<SchoolInfo> csv2SchoolInfos(String dataPath) throws IOException {

        List<SchoolInfo> schoolInfos = new ArrayList<>();
        try (BufferedReader schoolInfoReader = new BufferedReader(new FileReader(dataPath))) {
            // 첫 줄을 읽고 헤드라인을 확인한다.
            String headerLine = schoolInfoReader.readLine();
            System.out.println(headerLine);

            // CSVFormat을 이용하여 포맷을 설정한다.
            // CSVParser를 생성한다.
            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(headerLine).setRecordSeparator(',').build();
            CSVParser schoolInfoParser = new CSVParser(schoolInfoReader, csvFormat, 0, 1);
            // 헤더 확인
//            List<String> headerNames = schoolInfoParser.getHeaderNames();
            for (CSVRecord csvRecord : schoolInfoParser.getRecords()) {
                schoolInfos.add(SchoolInfo.from(csvRecord));
                // Model을 설정했나보다 이거 확인어디서 하는지 모려무나
//                System.out.println("csvRecord.values = " + csvRecord.values()[1]);
//                String infoLine = csvRecord.values()[0];
//                schoolInfos.add(SchoolInfo.to(infoLine));
            }
        }
        return schoolInfos;
    }

}
