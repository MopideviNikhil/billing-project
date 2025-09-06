package com.retailease.billingsystem.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.retailease.billingsystem.entity.CategoryEntity;
import com.retailease.billingsystem.entity.ItemEntity;
import com.retailease.billingsystem.io.ItemRequest;
import com.retailease.billingsystem.io.ItemResponse;
import com.retailease.billingsystem.repository.CategoryRepository;
import com.retailease.billingsystem.repository.ItemRepository;
import com.retailease.billingsystem.service.FileUploadService;
import com.retailease.billingsystem.service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


	private final CategoryRepository categoryRepository;

	private final ItemRepository itemRepository;


	@Override
	public ItemResponse add(ItemRequest request, MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "."
				+ StringUtils.getFilenameExtension(file.getOriginalFilename());
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Files.createDirectories(uploadPath);
		Path targetLocation = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String imgUrl = "http://localhost:8080/api/v1.0/uploads/" + fileName;
		
		ItemEntity newItem = convertToEntity(request);
		CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
		newItem.setCategory(existingCategory);
		newItem.setImgUrl(imgUrl);
		newItem=itemRepository.save(newItem);
		return convertToResponse(newItem);
	}

	@Override
	public List<ItemResponse> fetchItems() {
		return itemRepository.findAll().stream().map(itemEntity -> convertToResponse(itemEntity))
				.collect(Collectors.toList());
	}


	
	@Override
	public void deleteItem(String itemId) {
		ItemEntity existingItem = itemRepository.findByItemId(itemId)
				.orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
		
		String imgUrl = existingItem.getImgUrl();
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Path filePath = uploadPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
			itemRepository.delete(existingItem);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete the image");
		}
	}
	

	private ItemEntity convertToEntity(ItemRequest request) {
		return ItemEntity.builder().itemId(UUID.randomUUID().toString()).name(request.getName())
				.description(request.getDescription()).price(request.getPrice()).build();
	}

	private ItemResponse convertToResponse(ItemEntity newItem) {
		return ItemResponse.builder().itemId(newItem.getItemId()).name(newItem.getName())
				.description(newItem.getDescription()).price(newItem.getPrice()).imgUrl(newItem.getImgUrl())
				.categoryName(newItem.getCategory().getName()).categoryId(newItem.getCategory().getCategoryId())
				.createdAt(newItem.getCreatedAt()).updatedAt(newItem.getUpdatedAt()).build();
	}

}