package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.File;
import com.canifa.stylenest.entity.Model;
import com.canifa.stylenest.entity.dto.response.ModelResponseDTO;
import com.canifa.stylenest.repository.FileRepository;
import com.canifa.stylenest.repository.ModelRepository;
import com.canifa.stylenest.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final FileRepository fileRepository;
    @Override
    public List<ModelResponseDTO> findAllByProductId(String productId) {
        List<ModelResponseDTO> modelResponseDTOS = new ArrayList<>();
        List<Model> models = modelRepository.findAllByProductIdAndIsDeletedIsFalse(productId);
        for (Model model : models) {
            List<File> files = fileRepository.findAllByModelId(model.getId());

            modelResponseDTOS.add(ModelResponseDTO.builder()
                    .id(model.getId())
                    .color(model.getColor())
                    .size(model.getSize())
                    .stock(model.getStock())
                    .images(files.stream().map(File::getName).toList())
                    .build()
            );
        }
        return modelResponseDTOS;
    }
}
