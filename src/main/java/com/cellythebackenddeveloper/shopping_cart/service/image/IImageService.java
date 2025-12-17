package com.cellythebackenddeveloper.shopping_cart.service.image;
import com.cellythebackenddeveloper.shopping_cart.dto.ImageDto;
import com.cellythebackenddeveloper.shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void deleteimageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file , Long imageId);
}
