package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.File;
import com.canifa.stylenest.entity.Media;
import com.canifa.stylenest.entity.Model;
import com.canifa.stylenest.entity.Product;
import com.canifa.stylenest.entity.dto.request.ModelRequestDTO;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ModelResponseDTO;
import com.canifa.stylenest.entity.dto.response.ProductResponseDTO;
import com.canifa.stylenest.repository.FileRepository;
import com.canifa.stylenest.repository.MediaRepository;
import com.canifa.stylenest.repository.ModelRepository;
import com.canifa.stylenest.repository.ProductRepository;
import com.canifa.stylenest.service.FileService;
import com.canifa.stylenest.service.ModelService;
import com.canifa.stylenest.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelRepository modelRepository;
    private final FileRepository fileRepository;
    private final ModelService modelService;
    private final FileService fileService;
    private final MediaRepository mediaRepository;

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow();
        List<File> files = fileRepository.findAllByProductId(id);
        List<ModelResponseDTO> models = modelService.findAllByProductId(id);
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .instruction(product.getInstruction())
                .materials(product.getMaterials())
                .images(files.stream().map(File::getName).toList())
                .models(models)
                .build();
    }

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Map<String, List<MultipartFile>> multipartFileMap) {
        // lưu tất cả multipartFile
        Map<String, List<File>> fileMap = new HashMap<>();
        for (String key : multipartFileMap.keySet()) {
            List<MultipartFile> multipartFiles = multipartFileMap.get(key);
            fileMap.put(key, multipartFiles.stream().map(fileService::save).toList());
        }

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setInstruction(productRequestDTO.getInstruction());
        product.setMaterials(productRequestDTO.getMaterials());
        product = productRepository.save(product);
        List<File> productFiles = fileMap.get("images");
        // Lưu từng ảnh của sản phẩm
        for(File productFile : productFiles) {
            Media media = new Media(null, product.getId(), null, productFile.getId());
            mediaRepository.save(media);
        }

        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .instruction(product.getInstruction())
                .materials(product.getMaterials())
                .images(productFiles.stream().map(File::getName).toList())
                .models(new ArrayList<>())
                .build();

        for (ModelRequestDTO modelRequestDTO : productRequestDTO.getModels()) {
            Model model = new Model();
            model.setProductId(product.getId());
            model.setStock(modelRequestDTO.getStock());
            model.setSize(modelRequestDTO.getSize());
            model.setColor(modelRequestDTO.getColor());
            model = modelRepository.save(model);
            List<File> modelFiles = fileMap.get(model.getColor());
            for(File modelFile : modelFiles) {
                Media media = new Media(null, product.getId(), model.getId(), modelFile.getId());
                mediaRepository.save(media);
            }
            productResponseDTO.getModels().add(
                    ModelResponseDTO.builder()
                            .id(model.getId())
                            .color(model.getColor())
                            .size(model.getSize())
                            .stock(model.getStock())
                            .images(modelFiles.stream().map(File::getName).toList())
                            .build()
            );

        }
        return productResponseDTO;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(String id) {

    }
}
