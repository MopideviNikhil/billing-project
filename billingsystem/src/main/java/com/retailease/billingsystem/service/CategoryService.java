package com.retailease.billingsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.retailease.billingsystem.io.CategoryRequest;
import com.retailease.billingsystem.io.CategoryResponse;

public interface CategoryService {
	

	List<CategoryResponse> read();

	void delete(String categoryId);

	CategoryResponse add(CategoryRequest request, MultipartFile file) throws IOException;
}
