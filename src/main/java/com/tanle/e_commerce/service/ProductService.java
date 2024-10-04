package com.tanle.e_commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanle.e_commerce.dto.ProductDTO;
import com.tanle.e_commerce.entities.OptionValue;
import com.tanle.e_commerce.entities.Product;
import com.tanle.e_commerce.payload.MessageResponse;
import com.tanle.e_commerce.payload.PageResponse;
import com.tanle.e_commerce.request.ProductCreationRequest;
import org.springframework.data.domain.Pageable;

import javax.json.JsonPatch;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ProductService {

    PageResponse<ProductDTO> findAll(int page, int size, String direction, String... field);

    PageResponse<ProductDTO> findByName(int page, int size, String nameProduct);

    ProductDTO findById(Integer id, String optionValue);

    PageResponse<ProductDTO> findByCategory(String categoryId, Pageable pageable);

    ProductDTO update(Product product);

    void delete(Integer id);

    ProductDTO save(ProductCreationRequest productCreationRequest);

    ProductDTO save(Product product);

    ProductDTO update(Integer id, JsonPatch jsonPatch, String skuNo) throws JsonProcessingException;

    PageResponse<ProductDTO> findByTenant(int page, int size, Integer tenantId);

    ProductDTO addOption(Integer productId, ProductCreationRequest request);

    MessageResponse deleteOption(List<Integer> skusId, List<Integer> optionId
            , List<LinkedHashMap<Integer, List<Integer>>> optionValuesId, int productId);

    ProductDTO updatePrice(Integer productId, Map<String, Integer> data);

    ProductDTO updateStock(Integer productId, Map<String, Integer> data);
}