package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.common.PageRequest;
import com.canifa.stylenest.entity.*;
import com.canifa.stylenest.entity.dto.request.ModelRequestDTO;
import com.canifa.stylenest.entity.dto.request.ProductRequestDTO;
import com.canifa.stylenest.entity.dto.response.ModelResponseDTO;
import com.canifa.stylenest.entity.dto.response.ProductResponseDTO;
import com.canifa.stylenest.exception.NotFoundException;
import com.canifa.stylenest.repository.*;
import com.canifa.stylenest.service.FileService;
import com.canifa.stylenest.service.ModelService;
import com.canifa.stylenest.service.ProductService;
import com.canifa.stylenest.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelRepository modelRepository;
    private final FileRepository fileRepository;
    private final ModelService modelService;
    private final FileService fileService;
    private final MediaRepository mediaRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO getProductById(String id) throws NotFoundException{
        Product product = productRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(()->new NotFoundException("nn"));
        List<File> files = fileRepository.findAllByProductId(id);
        List<ModelResponseDTO> models = modelService.findAllByProductId(id);
        System.out.println(models.toString());
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

        Product product = Product.builder()
            .name(productRequestDTO.getName())
            .price(productRequestDTO.getPrice())
            .description(productRequestDTO.getDescription())
            .instruction(productRequestDTO.getInstruction())
            .materials(productRequestDTO.getMaterials()).build();
        product = productRepository.save(product);

        // save model images
        List<File> productFiles = fileMap.get("images");
        String productId = product.getId();
        List<Media> mediaList = productFiles.stream().map(productFile->new Media(null, productId, null, productFile.getId())).toList();
        mediaRepository.saveAll(mediaList);

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
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO, Map<String, List<MultipartFile>> multipartFileMap) throws NotFoundException {
        Map<String, List<File>> fileMap = new HashMap<>();
        for (String key : multipartFileMap.keySet()) {
            List<MultipartFile> multipartFiles = multipartFileMap.get(key);
            fileMap.put(key, multipartFiles.stream().map(fileService::save).toList());
        }

        Optional<Product> result = productRepository.findByIdAndIsDeletedIsFalse(id);
        if (!result.isPresent()) throw new NotFoundException("Không tìm thấy sản phẩm ..");
        Product product = result.get();
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
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private List<Product> getProductByParentCategory(String id){
        List<Category> categoryList = categoryRepository.findAllByParentId(id);
        var subProducts = categoryList.stream().map(category -> getProductByParentCategory(category.getId())).toList();
        var catProducts = productRepository.findByCategoryIdAndIsDeletedIsFalse(id);
        subProducts.stream().forEach(subProduct->catProducts.addAll(subProduct));
        return catProducts;
    }

    @Override
    public List<ProductResponseDTO> getProductByCategory(String id) {
        var products = this.getProductByParentCategory(id);
        return products.stream().map(
                product -> {
                    List<File> files = fileRepository.findAllByProductId(product.getId());
                    List<ModelResponseDTO> models = modelService.findAllByProductId(product.getId());
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
        ).toList();
    }

    @Override
    public PaginationUtils.PageResponse<ProductResponseDTO> getProductByCategory(String id, PageRequest pageRequest) {
        return PaginationUtils.paginate(getProductByCategory(id), pageRequest);
    }

    @Override
    public PaginationUtils.PageResponse getAll(PageRequest pageRequest) {
        return PaginationUtils.paginate(getAll(), pageRequest);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        var products = productRepository.findAllByIsDeletedIsFalse();
        return products.stream().map(
                product -> {
                    List<File> files = fileRepository.findAllByProductId(product.getId());
                    List<ModelResponseDTO> models = modelService.findAllByProductId(product.getId());
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
        ).toList();
    }

    @Override
    public void softDeleteProduct(String id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setIsDeleted(Boolean.TRUE);
            modelRepository.findAllByProductId(product.getId()).stream()
                    .peek(model -> model.setIsDeleted(Boolean.TRUE))
                    .map(modelRepository::save).toList();
            productRepository.save(product);
        });
    }
}
