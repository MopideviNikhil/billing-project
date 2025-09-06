package com.retailease.billingsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.retailease.billingsystem.io.ItemRequest;
import com.retailease.billingsystem.io.ItemResponse;

public interface ItemService {


	List<ItemResponse> fetchItems();

	void deleteItem(String itemId);

	ItemResponse add(ItemRequest request, MultipartFile file) throws IOException;
}