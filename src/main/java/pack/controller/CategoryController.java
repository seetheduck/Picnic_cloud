package pack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.dto.CategoryDto;
import pack.service.CategoryService;

@RestController
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 목록 조회
    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories();
    }
}
