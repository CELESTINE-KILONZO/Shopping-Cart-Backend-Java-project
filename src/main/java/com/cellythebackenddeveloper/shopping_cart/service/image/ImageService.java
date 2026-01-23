package com.cellythebackenddeveloper.shopping_cart.service.image;

import com.cellythebackenddeveloper.shopping_cart.dto.ImageDto;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Image;
import com.cellythebackenddeveloper.shopping_cart.model.Product;
import com.cellythebackenddeveloper.shopping_cart.repository.ImageRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.ProductRepository;
import com.cellythebackenddeveloper.shopping_cart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotException("No image found"));
    }

    @Override
    public void deleteimageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
                    throw new ResourceNotException("No image found");
                });
    }

//        return imageRepository.findById(id)
//                .map(image -> {
//                    imageRepository.delete(image);
//                    return image;
//                })
//                .orElseThrow(() -> new ResourceNotException("No image found"));

//    @Override
//    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
//      Product product= productService.getProductById(productId);
//      List <ImageDto> savedImageDtos = new ArrayList<>();
//        for (MultipartFile file : files) {
//            try {
//                Image image = new Image();
//                image.setFileName(file.getOriginalFilename());
//                image.setFileType(file.getContentType());
//                image.setImage(new SerialBlob(file.getBytes()));
//                image.setProduct(product);
//
//                String buildDownloadUrl = "/api/v1/images/image/download";
//                String DownloadUrl = buildDownloadUrl + image.getId();
//                image.setDownloadUrl(DownloadUrl);
//                Image savedImage = imageRepository.save (image);
//                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
//                imageRepository.save(image);
//
//                ImageDto imageDto = new ImageDto();
//                imageDto.setId(savedImage.getId());
//                imageDto.setImageName(savedImage.getFileName());
//                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
//                savedImageDtos.add(imageDto);
//
//            } catch (IOException | SQLException e) {
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//        return savedImageDtos;
//    }
@Override
public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
    Product product = productService.getProductById(productId);
    List<ImageDto> savedImageDtos = new ArrayList<>();

    for (MultipartFile file : files) {
        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setProduct(product);

            // Save FIRST to get the ID
            Image savedImage = imageRepository.save(image);

            // THEN build the download URL with the actual ID (notice the / at the end)
            String buildDownloadUrl = "/shoppingcart/v1/api/images/image/download/";
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

            // Save AGAIN with the updated download URL (use savedImage, not image!)
            imageRepository.save(savedImage);

            // Create DTO
            ImageDto imageDto = new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setImageName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            savedImageDtos.add(imageDto);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    return savedImageDtos;
}

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image =getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
//            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
