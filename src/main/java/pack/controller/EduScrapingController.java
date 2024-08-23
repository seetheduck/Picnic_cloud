package pack.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EduScrapingController {

    @GetMapping("/getEduEvents")
    public List<Map<String, Object>> getEducationEvents() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");  // 헤드리스 모드 추가

        WebDriver driver = new ChromeDriver(options);

        List<Map<String, Object>> events = new ArrayList<>();

        try {
            driver.get("https://www.sejongpac.or.kr/portal/academy/academyProgram/list.do?menuNo=200560");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content")));

            // `#listType1` 내부의 모든 `li` 요소를 찾습니다.
            List<WebElement> items = content.findElements(By.cssSelector("#listType1 > li"));
            if (items.isEmpty()) {
                throw new Exception("No items found in the 'listType1' section.");
            }

            for (WebElement item : items) {
                WebElement linkElement = item.findElement(By.tagName("a"));
                String link = linkElement.getAttribute("href");
                String imageUrl = item.findElement(By.tagName("img")).getAttribute("src"); // 이미지 URL 스크래핑
                List<WebElement> titles = item.findElements(By.cssSelector(".tit"));
                String title = titles.get(0).getText(); // 첫 번째 .tit 클래스 요소에서 제목 가져오기
                String period = titles.get(1).getText(); // 두 번째 .tit 클래스 요소에서 기간 가져오기

                String location = item.findElement(By.cssSelector(".flex > span:nth-child(1)")).getText();
                String day = item.findElement(By.cssSelector(".flex > span:nth-child(2)")).getText();

                // 기간을 'YYYY.MM.DD ~ YYYY.MM.DD' 형식에서 분리하여 start와 end 날짜 추출
                String[] periodParts = period.split(" ~ ");
                String startDate = periodParts[0].replace(".", "-").trim();
                String endDate = periodParts.length > 1 ? periodParts[1].replace(".", "-").trim() : startDate;

                // 종료일을 다음 날로 설정 (FullCalendar가 종료일을 포함하지 않기 때문)
                LocalDate endDateObject = LocalDate.parse(endDate);
                endDate = endDateObject.plusDays(1).toString();

                // 이벤트 객체 생성
                Map<String, Object> event = new HashMap<>();
                event.put("title", title);
                event.put("start", startDate);
                event.put("end", endDate);
                event.put("url", link);
                event.put("imageUrl", imageUrl); // 이미지 URL 추가
                event.put("location", location); // 장소 정보 추가
                event.put("day", day); // 요일 정보 추가

                events.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return events;
    }
}
