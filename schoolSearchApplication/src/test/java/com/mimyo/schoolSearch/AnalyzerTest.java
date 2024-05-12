package com.mimyo.schoolSearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.email.UAX29URLEmailAnalyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AnalyzerTest {

    @Test
    @DisplayName("분석된 토큰 결과가 '신상', '초등', '학교' 인지 테스트")
    void koreanAnalyzerTest () throws IOException {
        String text1 = "신상계초등학교";
        String text2 = "신상도초등학교";

        Analyzer analyzer = new KoreanAnalyzer();

        TokenStream tokenStream1 = analyzer.tokenStream("field", new StringReader(text1));
        tokenStream1.reset();;

        List<String> analyzedText1Tokens = new ArrayList<>();
        while(tokenStream1.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream1.addAttribute(CharTermAttribute.class);
            analyzedText1Tokens.add(charTermAttribute.toString());
        }
        tokenStream1.close();

        TokenStream tokenStream2 = analyzer.tokenStream("field", new StringReader(text2));
        tokenStream2.reset();;
        List<String> analyzedText2Tokens = new ArrayList<>();
        while(tokenStream1.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream2.addAttribute(CharTermAttribute.class);
            analyzedText2Tokens.add(charTermAttribute.toString());
        }
        tokenStream2.close();

        Assert.isTrue(analyzedText1Tokens.containsAll(List.of("신상", "초등", "학교")), "분석 결과가 다릅니다.");
        Assert.isTrue(analyzedText2Tokens.containsAll(List.of("신상", "초등", "학교")), "분석 결과가 다릅니다.");
    }

    @Test
    @DisplayName("Analyzer 분석 결과 출력")
    public void analyzerTest () throws IOException {
        // 분석할 내용
        String text = "아이유가 부릅니다. 블루밍. IU is singing strawberry.";
//        String text = "젠지는 빠르게 드래곤 버스트에 성공한 뒤 바론으로 향했다. 탈리야의 궁극기를 통해 바론을 계속 치다가 DK가 전투를 시작했다. 젠지는 굉장히 위험한 상황에서 탑 타워까지 전장을 유인해 득점을 따냈다.";
        // 공백 기반 분석기를 이용
        Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
        analyzerTest(text, whitespaceAnalyzer);
//        Analyzer standardAnalyzer = new StandardAnalyzer();
//        analyzerTest(text, standardAnalyzer);
//        Analyzer keywordAnalyzer = new KeywordAnalyzer();
//        analyzerTest(text, keywordAnalyzer);
//        Analyzer koreanAnalyzer = new KoreanAnalyzer();
//        analyzerTest(text, koreanAnalyzer);
    }

    public static void analyzerTest(String text, Analyzer analyzer) throws IOException {
        String analyzerClassName = analyzer.getClass().getName();

        // 텍스트를 분석한다. -> TokenStream 을 반환
        TokenStream tokenStream = analyzer.tokenStream("field", new StringReader(text));

//         분석된 토큰의 속성 값을 설정한다.
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();

        System.out.println(analyzerClassName + " 를 이용한 분석");
        while(tokenStream.incrementToken()) {
            System.out.print(charTermAttribute.toString() + " 🔻 ");
        }

        // TokenStream의 각 속성에 접근하기 위한 Attribute 선언
//        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
//        PositionIncrementAttribute positionIncrementAttribute = tokenStream.addAttribute(PositionIncrementAttribute.class);
//        PositionLengthAttribute positionLengthAttribute = tokenStream.addAttribute(PositionLengthAttribute.class);
//        TermToBytesRefAttribute termToBytesRefAttribute = tokenStream.addAttribute(TermToBytesRefAttribute.class);
//        TypeAttribute typeAttribute = tokenStream.addAttribute(TypeAttribute.class);
//
//        // TokenStream 반복하여 출력
//        tokenStream.reset();
//        while (tokenStream.incrementToken()) {
//            System.out.println("Token: " + charTermAttribute.toString());
//            System.out.println("  Offset: " + offsetAttribute.startOffset() + "-" + offsetAttribute.endOffset());
//            System.out.println("  Position increment: " + positionIncrementAttribute.getPositionIncrement());
//            System.out.println("  Position length: " + positionLengthAttribute.getPositionLength());
//            System.out.println("  Term bytes: " + termToBytesRefAttribute.getBytesRef().utf8ToString());
//            System.out.println("  Type: " + typeAttribute.type());
//            System.out.println();
//        }

        tokenStream.close();
    }

}