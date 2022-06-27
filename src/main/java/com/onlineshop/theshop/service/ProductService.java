package com.onlineshop.theshop.service;

import com.onlineshop.theshop.minio.FileService;
import com.onlineshop.theshop.shop.db.dao.*;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.typesense.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ProductTagDAO productTagDAO;

    @Autowired
    CategoryProductDAO categoryProductDAO;

    @Autowired
    CartProductDAO cartProductDAO;

    @Autowired
    OrderItemDAO orderItemDAO;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    SearchService searchService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    FileService fileService;

    public void loadTags(Product product) {
        product.setTags(productTagDAO.readByProductId(product.getId()));
    }

    public void loadCategories(Product product) {
        List<Category> categories = categoryProductDAO.readByProductId(product.getId())
                .stream()
                .map(uuid -> categoryDAO.readById(uuid))
                .collect(Collectors.toList());
        product.setCategories(categories);
    }
    public void loadProductType(Product product){
        product.setProductType(
                productTypeService.getProductTypeById(
                        productDAO.readTypeIdByProductId(
                                product.getId()
                        )
                )
        );
    }
    public void loadImages(Product product) {
        product.setImages(productImageService.getImagesByProductId(product.getId()));
    }

    public Product getProductById(UUID productId) {
        Product product = productDAO.readById(productId);
        loadTags(product);
        loadCategories(product);
        loadProductType(product);
        loadImages(product);
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = productDAO.read();
        productList.forEach(product -> {
            loadTags(product);
            loadCategories(product);
            loadProductType(product);
            loadImages(product);
        });
        return productList;
    }

    public List<Product> getRecommended() {
        List<Product> productList = productDAO.readByRecommended();
        productList.forEach(product -> {
            loadTags(product);
            loadCategories(product);
        });
        return productList;
    }

    public void addProduct(Product product) {
        searchService.addProduct(product);
        productDAO.create(product);
        if (product.getCategories() != null && !product.getCategories().isEmpty() && product.getCategories().size() > 0) {
            product.getCategories()
                    .forEach(
                            category -> categoryProductDAO.create(
                                    category.getId(),
                                    product.getId()
                            )
                    );
        }
        if (product.getTags() != null && !product.getTags().isEmpty() && product.getTags().size() > 0) {
            product.getTags().forEach(tag -> productTagDAO.create(product.getId(), tag));
        }
        if (product.getImages() != null && !product.getImages().isEmpty() && product.getImages().size() > 0) {
            product.getImages().forEach(uuid -> productImageService.addImages(product.getId(), uuid));
        }

    }

    public void updateProduct(Product product) {
        productDAO.update(product);
        searchService.updateProduct(product);
        categoryProductDAO.deleteByProductId(product.getId());
        if (product.getCategories() != null && !product.getCategories().isEmpty() && product.getCategories().size() > 0) {
            product.getCategories().forEach(
                    category -> categoryProductDAO.create(
                            category.getId(),
                            product.getId()
                    )
            );
        }
        productTagDAO.deleteByProductId(product.getId());
        if (product.getTags() != null && !product.getTags().isEmpty() && product.getTags().size() > 0) {
            product.getTags().forEach(
                    tag -> productTagDAO.create(
                            product.getId(),
                            tag
                    )
            );
        }
    }

    public void deleteProduct(UUID productId) {
        searchService.deleteProduct(productId);
        productTagDAO.deleteByProductId(productId);
        categoryProductDAO.deleteByProductId(productId);
        cartProductDAO.readByProductId(productId).forEach(
                uuid -> cartProductDAO.delete(
                        uuid,
                        productId
                )
        );
        orderItemDAO.deleteProductIdsFromOrderItemsByProductId(productId);
        productImageService.deleteImagesByProductId(productId);
        productDAO.delete(productId);

    }

    public UUID updateMainImage(MultipartFile multipartFile, UUID productId) {
        Product product = getProductById(productId);
        if (multipartFile.getSize() > 0) {
            UUID fileId = fileService.uploadImageAndGetId(multipartFile);
            product.setImg(fileId);
            productDAO.update(product);
            return fileId;
        }
        else {
            return null;
        }
    }
    public UUID addAdditionalImage(MultipartFile multipartFile, UUID productId) {
        if (multipartFile.getSize() > 0) {
            UUID fileId = fileService.uploadImageAndGetId(multipartFile);
            productImageService.addImages(productId, fileId);
            return fileId;
        }
        else {
            return null;
        }
    }

    public List<UUID> addAdditionalImages(List<MultipartFile> multipartFiles, UUID productId) {
        List<UUID> uuids = new ArrayList<>();
        multipartFiles.forEach(multipartFile -> {
            UUID fileId = addAdditionalImage(multipartFile, productId);
            if (fileId != null) {
                uuids.add(fileId);
            }
        });
        return uuids;
    }
    public void removeAdditionalImage(UUID imageId) {
        productImageService.deleteImageById(imageId);

    }
    public void removeAdditionalImages(List<UUID> imageIds) {
        if (!imageIds.isEmpty())
            imageIds.forEach(this::removeAdditionalImage);
    }

    public boolean productExists(UUID productId) {
        List<Product> productList = productDAO.read().stream().filter(product -> product.getId().toString().equals(productId.toString())).collect(Collectors.toList());
        return productList.size() == 1;
    }

}
