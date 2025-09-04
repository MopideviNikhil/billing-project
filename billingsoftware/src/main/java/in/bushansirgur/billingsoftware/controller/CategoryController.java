package in.bushansirgur.billingsoftware.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.bushansirgur.billingsoftware.io.CategoryRequest;
import in.bushansirgur.billingsoftware.io.CategoryResponse;
import in.bushansirgur.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(
    	    @RequestPart
    	    ("category") String categoryString, 
    	    @RequestPart("file") MultipartFile file) throws IOException {
    	    
    	    ObjectMapper objectMapper = new ObjectMapper();
    	    CategoryRequest request = null;
    	    
    	    try {
    	        request = objectMapper.readValue(categoryString, CategoryRequest.class);
    	        return categoryService.add(request, file);
    	    } catch (JsonProcessingException ex) {
    	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occurred while parsing the json "+ex.getMessage());
    	    }
    	}

    
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.read();
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("admin/categories/{categoryId}")
    public void remove(@PathVariable String categoryId) {
		try {
			categoryService.delete(categoryId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
		}
	}

}
