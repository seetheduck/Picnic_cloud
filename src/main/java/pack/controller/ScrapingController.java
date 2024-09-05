package pack.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.bonigarcia.wdm.WebDriverManager;
import pack.entity.EduEventEntitiy;
import pack.repository.EduEventRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScrapingController {

    @Autowired
    private EduEventRepository eduEventRepository;

    @GetMapping("/getEduEvents") // 이 메소드가 호출되면 스크래핑 및 DB 저장 작업만 수행
    public void getEducationEvents() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");  // 헤드리스 모드 추가

        WebDriver driver = new ChromeDriver(options);

        List<Map<String, Object>> events = new ArrayList<>();

        try {// 세종문화회관 페이지 접속
            driver.get("https://www.sejongpac.or.kr/portal/academy/academyProgram/list.do?menuNo=200560");
            // 페이지 로딩 대기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content")));

            // 이벤트 리스트 추출
            List<WebElement> items = content.findElements(By.cssSelector("#listType1 > li"));
            if (items.isEmpty()) {
                throw new Exception("No items found in the 'listType1' section.");
            }
            // 각 항목에 대해 데이터 추출 및 DB 저장
            for (WebElement item : items) {
                WebElement linkElement = item.findElement(By.tagName("a"));
                String link = linkElement.getAttribute("href");
                String imageUrl = item.findElement(By.tagName("img")).getAttribute("src");
                List<WebElement> titles = item.findElements(By.cssSelector(".tit"));
                String title = titles.get(0).getText(); // 첫 번째 .tit 클래스 요소에서 제목 가져오기
                String period = titles.get(1).getText(); // 두 번째 .tit 클래스 요소에서 기간 가져오기

                String location = item.findElement(By.cssSelector(".flex > span:nth-child(1)")).getText(); // 장소 정보 추출
                String day = item.findElement(By.cssSelector(".flex > span:nth-child(2)")).getText(); // 요일 정보 추출

                // 기간에서 시작일과 종료일 추출
                String[] periodParts = period.split(" ~ ");
                String startDate = periodParts[0].replace(".", "-").trim();
                String endDate = periodParts.length > 1 ? periodParts[1].replace(".", "-").trim() : startDate;

                LocalDate endDateObject = LocalDate.parse(endDate);
                endDate = endDateObject.plusDays(1).toString(); // 종료일에 하루 추가

                // EduEvent 객체를 생성하여 DB에 저장
                EduEventEntitiy eduEvent = EduEventEntitiy.builder()
                        .title(title)
                        .startDate(startDate)
                        .endDate(endDate)
                        .link(link)
                        .imageUrl(imageUrl)
                        .location(location)
                        .day(day)
                        .build();

                eduEventRepository.save(eduEvent);  // 데이터베이스에 저장
            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 로그 출력
        } finally {
            driver.quit(); // 브라우저 드라이버 종료
        }
    }
}


