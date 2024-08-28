package pack.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.entity.PlaceEntity;
import pack.repository.PlaceRepository;

@Service
public class CsvService {
	@Autowired
	private PlaceRepository placeRepository;
	
	//아동서점
	public void childBookstoreCsv(InputStream inputStream) throws IOException {
	    try (Reader reader = new InputStreamReader(inputStream);
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("시설명", "대분류명", "중분류명", "우편번호", "시설도로명주소", "시설위도", "시설경도", "평일개점시간", "평일마감시간", "토요일개점시간", "토요일마감시간", "일요일개점시간", "일요일마감시간", "휴무일개점시간", "휴무일마감시간", "휴무일안내내용", "옵션설명", "추가설명"))) {
	        
	        for (CSVRecord csvRecord : csvParser) {
	            PlaceEntity place = new PlaceEntity();
	            place.setPlaceType("childBookstore"); 
	            place.setName(csvRecord.get("시설명"));
	            place.setAddress(csvRecord.get("시설도로명주소"));
	            place.setLikeIs(false);  // 기본값 설정
	            place.setLikeCnt(0); // 기본값 설정
	            place.setPoint(0.0f);   // 기본값 설정
	            place.setImage(null);   // 기본값 설정
	            place.setDescription(String.join(",", csvRecord.get("옵션설명"), csvRecord.get("추가설명")));
	            place.setTel(null);     // 기본값 설정
	            place.setEntranceFee(null); // 기본값 설정
	            place.setOperationTime(String.join(",", csvRecord.get("평일개점시간"), csvRecord.get("평일마감시간")));
	            
	            placeRepository.save(place);
	        }
	    }
	}
	
	
	//공공형 키즈카페
	public void publicKidsCafeCsv(InputStream inputStream) throws IOException {
	    try (Reader reader = new InputStreamReader(inputStream);
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("구분", "주소", "개소일", "전화번호", "운영일(이용시간)", "이용요금"))) {
	        
	        for (CSVRecord csvRecord : csvParser) {
	            PlaceEntity place = new PlaceEntity();
	            place.setPlaceType("publicKidsCafe"); 
	            place.setName(csvRecord.get("구분"));  // 카페의 구분명
	            place.setAddress(csvRecord.get("주소"));
	            place.setLikeIs(false);  // 기본값 설정
	            place.setLikeCnt(0); // 기본값 설정
	            place.setPoint(0.0f);   // 기본값 설정
	            place.setImage(null);   // 기본값 설정
	            place.setDescription("이용요금: " + csvRecord.get("이용요금"));
	            place.setTel(csvRecord.get("전화번호")); // 전화번호를 저장
	            place.setEntranceFee(csvRecord.get("이용요금")); // 이용 요금을 입장료로 저장
	            place.setOperationTime(csvRecord.get("운영일(이용시간)")); // 운영 시간을 저장
	            
	            placeRepository.save(place);
	        }
	    }
	}

	
	//캠핑장
	public void campingCsv(InputStream inputStream) throws IOException {
	    try (Reader reader = new InputStreamReader(inputStream);
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("시설명", "카테고리1", "카테고리2", "카테고리3", "시도 명칭", "시군구 명칭", "법정읍면동명칭", "리 명칭", "번지", "도로명 이름", "건물 번호", "위도", "경도", "우편번호", "도로명주소", "지번주소", "전화번호", "홈페이지", "사업주체", "평일 운영 여부", "주말 운영 여부", "봄 운영 여부", "여름 운영 여부", "가을 운영 여부", "겨울 운영 여부", "부대시설 전기", "부대시설 온수", "부대시설 무선인터넷", "부대시설 장작판매", "부대시설 산책로", "부대시설 물놀이장", "부대시설 놀이터", "부대시설 마트", "부대시설 화장실 수", "부대시설 샤워실 수", "부대시설 씽크대 수", "부대시설 소화기 수", "주변 시설 낚시", "주변 시설 산책로", "주변 시설 물놀이(해수욕)", "주변 시설 물놀이(수상레저)", "주변 시설 물놀이(계곡)", "주변 시설 물놀이(강)", "주변 시설 물놀이(수영장)", "주변 시설 청소년체험시설", "주변 시설 농어촌체험시설", "주변 시설 어린이놀이시설", "글램핑 침대", "글램핑 티비", "글램핑 냉장고", "글램핑 유무선인터넷", "글램핑 내부화장실", "글램핑 에어컨", "글램핑 난방기구", "글램핑 취사도구", "시설 특징", "시설 소개", "최종작성일"))) {
	        
	        for (CSVRecord csvRecord : csvParser) {
	            PlaceEntity place = new PlaceEntity();
	            place.setPlaceType("camping"); 
	            place.setName(csvRecord.get("시설명"));
	            place.setAddress(csvRecord.get("도로명주소"));
	            place.setLikeIs(false);  // 기본값 설정
	            place.setLikeCnt(0); // 기본값 설정
	            place.setPoint(0.0f);   // 기본값 설정
	            place.setImage(null);   // 기본값 설정
	            place.setDescription(csvRecord.get("시설 소개"));
	            place.setTel(csvRecord.get("전화번호")); // 전화번호를 저장
	            place.setEntranceFee(null);  // 캠핑장 정보에 입장료 데이터가 없다면 null로 설정
	            place.setOperationTime(null); // 운영 시간 정보가 없으므로 null 설정
	            
	            placeRepository.save(place);
	        }
	    }
	}

	
	//자연휴양림
	public void forestCsv(InputStream inputStream) throws IOException {
	    try (Reader reader = new InputStreamReader(inputStream);
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("휴양림명", "시도명", "휴양림구분", "휴양림면적", "수용인원수", "입장료", "숙박가능여부", "주요시설명", "소재지도로명주소", "관리기관명", "휴양림전화번호", "홈페이지주소", "위도", "경도", "데이터기준일자", "제공기관코드", "제공기관명"))) {
	        
	        for (CSVRecord csvRecord : csvParser) {
	            PlaceEntity place = new PlaceEntity();
	            place.setPlaceType("forest"); 
	            place.setName(csvRecord.get("휴양림명"));
	            place.setAddress(csvRecord.get("소재지도로명주소"));
	            place.setLikeIs(false);  // 기본값 설정
	            place.setLikeCnt(0); // 기본값 설정
	            place.setPoint(0.0f);   // 기본값 설정
	            place.setImage(null);   // 기본값 설정
	            place.setDescription(csvRecord.get("주요시설명"));
	            place.setTel(csvRecord.get("휴양림전화번호")); // 전화번호를 저장
	            place.setEntranceFee(csvRecord.get("입장료")); // 입장료를 저장
	            place.setOperationTime(null);  // 운영 시간 데이터가 없으므로 null 설정
	            
	            placeRepository.save(place);
	        }
	    }
	}

	
	
	
	
	
}

