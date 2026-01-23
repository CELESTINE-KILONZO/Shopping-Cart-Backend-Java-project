package com.cellythebackenddeveloper.shopping_cart.controller;
import com.cellythebackenddeveloper.shopping_cart.dto.ImageDto;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Image;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService iimageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse>saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> ImageDtos=iimageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Image saved successfully",ImageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to save images: "+ e.getMessage() ,null));
        }
    }

//    @GetMapping("image/download/{imageId}")
//    public ResponseEntity<ByteArrayResource>downloadImage(@PathVariable Long imageId) throws SQLException {
//        Image image=iimageService.getImageById(imageId);
//        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName() + "\"")
//                .body(resource);
//    }
@GetMapping("/image/download/{imageId}")
public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) {
    try {
        System.out.println("=== DOWNLOAD DEBUG ===");
        System.out.println("Attempting to download image with ID: " + imageId);

        Image image = iimageService.getImageById(imageId);
        System.out.println("Image found: " + image.getFileName());
        System.out.println("File type: " + image.getFileType());
        System.out.println("Image blob is null? " + (image.getImage() == null));

        if (image.getImage() == null) {
            System.out.println("ERROR: Image blob is null!");
            return ResponseEntity.status(NOT_FOUND).body(null);
        }

        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        System.out.println("Image size in bytes: " + imageBytes.length);

        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        System.out.println("Resource created successfully");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);

    } catch (Exception e) {
        System.out.println("ERROR during download: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
    }
}

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity <ApiResponse> updateImage(@RequestParam MultipartFile file , @PathVariable Long imageId){
        try {
            Image image=iimageService.getImageById(imageId);
            if(image!=null){
                iimageService.updateImage( file , imageId);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully",null));
            }
        } catch (ResourceNotException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage() ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to update image: ", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete" )
    public ResponseEntity <ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            Image image=iimageService.getImageById(imageId);
            if(image!=null){
                iimageService.deleteimageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image deleted successfully",null));
            }
        } catch (ResourceNotException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage() ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to delete image: ", INTERNAL_SERVER_ERROR));
    }
}
