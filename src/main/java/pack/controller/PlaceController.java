package pack.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.PlaceDto;
import pack.service.CsvService;
import pack.service.PlaceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/places")
public class PlaceController {
	@Autowired
	private CsvService csvService;
	
	@Autowired
	private PlaceService placeService;
	
	@GetMapping("/import-csv/{fileType}")
	public String importCsv(@PathVariable("fileType") String fileType) {
	    String filePath;
	    
	    switch (fileType) {
	        case "childBookstore":
	            filePath = "C:/work2/team/childBookstore.csv";
	            try (InputStream inputStream = new FileInputStream(filePath)) {
	                csvService.childBookstoreCsv(inputStream);
	                return "Child Bookstore CSV import completed successfully!";
	            } catch (IOException e) {
	                e.printStackTrace();
	                return "Error occurred during Child Bookstore CSV import: " + e.getMessage();
	            }
	            
	        case "publicKidsCafe":
	            filePath = "C:/work2/team/공공형키즈카페.csv";
	            try (InputStream inputStream = new FileInputStream(filePath)) {
	                csvService.publicKidsCafeCsv(inputStream);
	                return "Public Kids Cafe CSV import completed successfully!";
	            } catch (IOException e) {
	                e.printStackTrace();
	                return "Error occurred during Public Kids Cafe CSV import: " + e.getMessage();
	            }
	            
	        case "camping":
	            filePath = "C:/work2/team/캠핑장.csv";
	            try (InputStream inputStream = new FileInputStream(filePath)) {
	                csvService.campingCsv(inputStream);
	                return "Camping CSV import completed successfully!";
	            } catch (IOException e) {
	                e.printStackTrace();
	                return "Error occurred during Camping CSV import: " + e.getMessage();
	            }
	            
	        case "forest":
	            filePath = "C:/work2/team/자연휴양림.csv";
	            try (InputStream inputStream = new FileInputStream(filePath)) {
	                csvService.forestCsv(inputStream);
	                return "Forest CSV import completed successfully!";
	            } catch (IOException e) {
	                e.printStackTrace();
	                return "Error occurred during Forest CSV import: " + e.getMessage();
	            }
	            
	        default:
	            return "Invalid file type provided.";
	    }
	}

	/*
	@GetMapping("/upload/{fileType}")
	public ResponseEntity<String> uploadCsv(@RequestParam("filePath") String filePath, @PathVariable String fileType) {
	    try {
	        InputStream inputStream = new FileInputStream(filePath);
	        
	        switch (fileType) {
	            case "childBookstore":
	                csvService.childBookstoreCsv(inputStream);
	                break;
	            case "publicKidsCafe":
	                csvService.publicKidsCafeCsv(inputStream);
	                break;
	            case "camping":
	                csvService.campingCsv(inputStream);
	                break;
	            case "forest":
	                csvService.forestCsv(inputStream);
	                break;
	            default:
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type");
	        }
	        return ResponseEntity.ok("File processed and data saved successfully");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
	    }
	}
*/
	/*
    @PostMapping("/upload/{fileType}")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file, @PathVariable String fileType) {
        try {
            switch (fileType) {
                case "childBookstore":
                	csvService.childBookstoreCsv(file.getInputStream());
                    break;
                case "publicKidsCafe":
                    csvService.publicKidsCafeCsv(file.getInputStream());
                    break;
                case "camping":
                    csvService.campingCsv(file.getInputStream());
                    break;
                case "forest":
                    csvService.forestCsv(file.getInputStream());
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type");
            }
            return ResponseEntity.ok("File uploaded and data saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        }
    }
*/
	/*
	@GetMapping("/import-csv")
    public String importCsv() {
        try {
            csvService.readCsv("C:/work2/team/아동서점.csv");
            return "CSV import completed successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred during CSV import: " + e.getMessage();
        }
    }
 */
	//카테고리별 쿼리메소드와 통합. 같은 방법으로 매핑(get)이기에 통합이 훨씬 restful하다.
	//카테고리별 및 검색어를 통한 장소 목록 조회 //places?placeType=waterpark&keyword=aqua
    @GetMapping
    public List<PlaceDto> getPlacesByTypeAndKeyword(
            @RequestParam(value = "placeType") String placeType, 
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return placeService.findPlacesByTypeAndKeyword(placeType, keyword);
    }

	//특정 장소 상세정보. //places/3
	@GetMapping("/{no}")
	public Optional<PlaceDto> getPlacesByNo(@PathVariable("no") int no){
		
		return placeService.findPlacesByNo(no);
	}
}
