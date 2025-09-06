package com.retailease.billingsystem.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.retailease.billingsystem.entity.CategoryEntity;
import com.retailease.billingsystem.io.CategoryRequest;
import com.retailease.billingsystem.io.CategoryResponse;
import com.retailease.billingsystem.repository.CategoryRepository;
import com.retailease.billingsystem.repository.ItemRepository;
import com.retailease.billingsystem.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;

	@Override
	public CategoryResponse add(CategoryRequest request, MultipartFile file) throws IOException {

		String fileName = UUID.randomUUID().toString() + "."
				+ StringUtils.getFilenameExtension(file.getOriginalFilename());
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Files.createDirectories(uploadPath);
		Path targetLocation = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String imgUrl = "http://localhost:8080/api/v1.0/uploads/" + fileName;
		CategoryEntity newCategory = convertToEntity(request);
		newCategory.setImageUrl(imgUrl);
		newCategory = categoryRepository.save(newCategory);
		return convertToResponse(newCategory);
	}


	private CategoryEntity convertToEntity(CategoryRequest request) {
		CategoryEntity category = CategoryEntity.builder().categoryId(UUID.randomUUID().toString())
				.name(request.getName()).description(request.getDescription()).bgColor(request.getBgColor()).build();
		return category;
	}

	@Override
	public List<CategoryResponse> read() {
		return categoryRepository.findAll().stream().map(category -> convertToResponse(category))
				.collect(Collectors.toList());
	}

	@Override
	public void delete(String categoryId) {
		CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

		String imgUrl = existingCategory.getImageUrl();
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Path filePath = uploadPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		categoryRepository.delete(existingCategory);
	}
	


	private CategoryResponse convertToResponse(CategoryEntity entity) {
		Integer itemsCount = itemRepository.countByCategoryId(entity.getId());
		return CategoryResponse.builder().categoryId(entity.getCategoryId()).name(entity.getName())
				.description(entity.getDescription()).bgColor(entity.getBgColor()).imageUrl(entity.getImageUrl())
				.createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt()).items(itemsCount).build();
	}
}
