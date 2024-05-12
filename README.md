# SchoolSearchApplication-demo(학교검색서비스 데모)
순서 1. 소스 다운로드 이후, Reload All Gradle Projects 실행
순서 2. SchoolSearchApplication의 run 메소드의 메서드 실행
순서 3. indexSchoolInfoService.createIndex() 를 통해 index 생성
순서 4. searchSchoolService.search(request) 를 통해 검색

## 주요 기능
### 색인 기능
- IndexSchoolInfoService의createIndex() 메서드
### 검색 기능
- SearchSchoolService의search() 메서드

## 데이터
- 수집 : [나이스 교육정보 개방포털](https://open.neis.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=OPEN17020190531110010104913&infSeq=3)
- 가공
  - 사용된 칼럼 : ['시도교육청코드','시도교육청명','행정표준코드','학교명','영문학교명','학교종류명','시도명','관할조직명','설립명','도로명우편번호','도로명주소','전화번호', '홈페이지주소','남녀공학구분명','팩스번호', '주야구분명', '설립일자', '개교기념일', '수정일자']

## 프로젝트 디렉터리 구조
```
├── build.gradle
├── data
│   └── 학교기본정보_20240131.csv
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── indices
│   └── schoolSearchApplication
│       ├── 색인 파일
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── schoolSearch
    │   │           ├── SchoolSearchApplication.java
    │   │           ├── dto
    │   │           │   ├── Request.java
    │   │           │   └── Response.java
    │   │           ├── model
    │   │           │   ├── IndexField.java
    │   │           │   └── SchoolInfo.java
    │   │           └── service
    │   │               ├── IndexSchoolInfoService.java
    │   │               └── SearchSchoolService.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── com
                └── mimyo
                    └── schoolSearch
                        └── AnalyzerTest.java
```
