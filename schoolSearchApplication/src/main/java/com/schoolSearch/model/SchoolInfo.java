package com.schoolSearch.model;


import lombok.Builder;
import lombok.Data;
import org.apache.commons.csv.CSVRecord;

@Data
@Builder
public class SchoolInfo {

    private String sidoOfficeOfEducationName;  // column[1]
    private String schoolKoreanName;  // column[3]
    private String schoolEnglishName;  // column[4]
    private String schoolType;  // column[5]
    private String sidoName;  // column[6]
    private String roadNameZipCode;  // column[9]
    private String roadNameAddress;  // column[10]
    private String phoneNumber;  // column[11]
    private String establishmentDate;  // column[16]
    private String schoolAnniversary;  // column[17]

    public static SchoolInfo from(CSVRecord csvRecord) {
        return SchoolInfo.builder()
                .sidoOfficeOfEducationName(csvRecord.values()[1])
                .schoolKoreanName(csvRecord.values()[3])
                .schoolEnglishName(csvRecord.values()[4])
                .schoolType(csvRecord.values()[5])
                .sidoName(csvRecord.values()[6])
                .roadNameZipCode(csvRecord.values()[9])
                .roadNameAddress(csvRecord.values()[10])
                .phoneNumber(csvRecord.values()[11])
                .establishmentDate(csvRecord.values()[16])
                .schoolAnniversary(csvRecord.values()[17])
                .build();
    }

    public static SchoolInfo to(String infoLine) {
        String[] schoolInfoLine = infoLine.split("\t");
        return SchoolInfo.builder()
                .sidoOfficeOfEducationName(schoolInfoLine[1])
                .schoolKoreanName(schoolInfoLine[3])
                .schoolEnglishName(schoolInfoLine[4])
                .schoolType(schoolInfoLine[5])
                .sidoName(schoolInfoLine[6])
                .roadNameZipCode(schoolInfoLine[9])
                .roadNameAddress(schoolInfoLine[10])
                .phoneNumber(schoolInfoLine[11])
                .establishmentDate(schoolInfoLine[16])
                .schoolAnniversary(schoolInfoLine[17])
                .build();
    }
}