package com.schoolSearch.model;

public interface IndexField {

    // 우선적으로 색인할 필드 8개
    String SIDO_OFFICE_EDUCATION_NAME = "sidoOfficeOfEducationName";
    String SCHOOL_KOREAN_NAME = "schoolKoreanName";
    String SCHOOL_ENGLISH_NAME = "schoolEnglishName";
    String SCHOOL_TYPE = "schoolType";
    String SIDO_NAME = "sidoName";
    String ROAD_NAME_ZIP_CODE = "roadNameZipCode";
    String ROAD_NAME_ADDRESS = "roadNameAddress";
    String PHONE_NUMBER = "phoneNumber";
    String ESTABLISHED_DATE = "establishmentDate";
    String SORT_ESTABLISHED_DATE = "sort_establishmentDate";
}
