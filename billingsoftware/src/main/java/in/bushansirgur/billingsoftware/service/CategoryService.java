package in.bushansirgur.billingsoftware.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.bushansirgur.billingsoftware.io.CategoryRequest;
import in.bushansirgur.billingsoftware.io.CategoryResponse;

public interface CategoryService {
	

	List<CategoryResponse> read();

	void delete(String categoryId);

	CategoryResponse add(CategoryRequest request, MultipartFile file) throws IOException;
}
