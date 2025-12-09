package com.cellythebackenddeveloper.shopping_cart.service.image;

import com.cellythebackenddeveloper.shopping_cart.model.Image;
import com.cellythebackenddeveloper.shopping_cart.repository.ImageRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor

public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private  final ProductRepository productRepository;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public Image deleteimage(Long id) {
        return null;
    }

    @Override
    public void saveImage(MultipartFile file, Long productId) {

    }

    @Override
    public void updateImage(MultipartFile file, Long productId) {

    }
}
