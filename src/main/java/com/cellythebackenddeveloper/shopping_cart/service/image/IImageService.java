package com.cellythebackenddeveloper.shopping_cart.service.image;

import com.cellythebackenddeveloper.shopping_cart.model.Image;
import com.cellythebackenddeveloper.shopping_cart.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    Image getImageById(Long id);
    Image deleteimage(Long id);
    void saveImage(MultipartFile file , Long productId);
    void updateImage(MultipartFile file , Long productId);
}
