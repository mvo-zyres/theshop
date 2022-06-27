package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.minio.FileService;
import com.onlineshop.theshop.service.CategoryService;
import com.onlineshop.theshop.service.ProductService;
import com.onlineshop.theshop.service.ProductTypeService;
import com.onlineshop.theshop.shop.form.producttype.AddProductTypeForm;
import com.onlineshop.theshop.shop.form.producttype.DeleteProductTypeForm;
import com.onlineshop.theshop.shop.form.producttype.EditProductTypeForm;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.shop.model.store.Product;
import com.onlineshop.theshop.shop.model.store.ProductType;
import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.shop.form.product.AddProductForm;
import com.onlineshop.theshop.shop.form.product.DeleteProductForm;
import com.onlineshop.theshop.shop.form.product.EditProductForm;
import com.onlineshop.theshop.typesense.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    SearchService searchService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    FileService fileService;


    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String PRODUCT_TYPES = "productTypes";
    private static final String PRODUCT_TYPE = "productType";

    @GetMapping("/product")
    public String productDetail(@RequestParam(name = "id", required = false)UUID productId, Model model){
        if (productId != null) {
            Product product = productService.getProductById(productId);
            model.addAttribute(PRODUCT, product);
            return "product/detail";
        }
        else {
            return "redirect:/product/all";
        }

    }

    @GetMapping("/product/all")
    public String allProducts( Model model) {
        model.addAttribute(PRODUCTS, productService.getAllProducts());
        model.addAttribute("emptyMsg", "No products currently available");
        return "product/all";
    }

    @GetMapping("/product/search")
    public String search(@RequestParam(name = "value")String search, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("searchvalue", search);
        model.addAttribute(PRODUCTS, searchService.search(search));
        model.addAttribute("emptyMsg", "Sorry, we couldn't found the products you were looking for :(");

        return "product/all";
    }

    @GetMapping("/management/product/add")
    public String addProductManagement(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute(PRODUCT_TYPES, productTypeService.getAllProductTypes());
        return "/product/addProduct";
    }
    @PostMapping("/management/product/add.post")
    public String addProductPostManagement(AddProductForm addProductForm) {
        Product product = new Product(true);
        product.setName(addProductForm.getName());
        product.setDescription(addProductForm.getDescription());
        product.setPrice(addProductForm.getPrice());

        if (!addProductForm.getImg().isEmpty()) {
            product.setImg(fileService.uploadImageAndGetId(addProductForm.getImg()));
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
        if (!addProductForm.getImages().isEmpty()) {
            List<UUID> images = new ArrayList<>();
            addProductForm.getImages().forEach(img -> images.add(fileService.uploadImageAndGetId(img)));
            product.setImages(images);
        }

        product.setTags(addProductForm.getTags());
        product.setTax(addProductForm.getTax());
        product.setCategories(
                addProductForm.getCategoryIds()
                        .stream()
                        .map(uuid -> categoryService.getCategoryById(uuid))
                        .collect(Collectors.toList())
        );
        product.setProductType(productTypeService.getProductTypeById(addProductForm.getProductTypeId()));
        productService.addProduct(product);
        return "redirect:/management/product/detail?id="+product.getId();
    }



    @GetMapping("/management/product/detail")
    public String detailProductManagement(@RequestParam(name = "id") UUID productId, Model model) {
        Product product = productService.getProductById(productId);
        String tags = String.join(",", product.getTags());
        model.addAttribute("tags", tags);
        model.addAttribute(PRODUCT, product);
        model.addAttribute(PRODUCT_TYPES, productTypeService.getAllProductTypes());
        List<Category> allCategories = categoryService.getAllCategories();
        Map<Category, Boolean> categoryBooleanMap = new HashMap<>();
        List<UUID> productCategoriesIds = product.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        allCategories.forEach(category -> categoryBooleanMap.put(category, productCategoriesIds.contains(category.getId())));
        model.addAttribute("categoryBooleanMap", categoryBooleanMap);
        return "/product/detailProduct";
    }
    @PostMapping("/management/product/edit.post")
    public String editProductPostManagement(EditProductForm editProductForm) {
        Product product = productService.getProductById(editProductForm.getProductId());
        product.setName(editProductForm.getName());
        product.setDescription(editProductForm.getDescription());
        product.setPrice(editProductForm.getPrice());
        if (editProductForm.getImg().getSize() > 0) {
            product.setImg(productService.updateMainImage(editProductForm.getImg(), editProductForm.getProductId()));
        }
        editProductForm.getImages().forEach(imageFile -> {
            if (imageFile.getSize() > 0) {
                product.getImages().add(productService.addAdditionalImage(imageFile, editProductForm.getProductId()));
            }
        });
        if (editProductForm.getDeleteImages() != null && !editProductForm.getDeleteImages().isEmpty()) {
            productService.removeAdditionalImages(editProductForm.getDeleteImages());
        }
        product.setTags(editProductForm.getTags());
        product.setTax(editProductForm.getTax());
        product.setProductType(productTypeService.getProductTypeById(editProductForm.getProductTypeId()));
        if (editProductForm.getCategoryIds() != null) {
            product.setCategories(editProductForm.getCategoryIds().stream().map(uuid -> categoryService.getCategoryById(uuid)).collect(Collectors.toList()));
        }
        product.setRecommended(editProductForm.isRecommended());
        productService.updateProduct(product);
        return "redirect:/management/product/detail?id="+editProductForm.getProductId().toString();
    }



    @GetMapping("/management/product/delete")
    public String deleteProductManagement(@RequestParam(name = "id") UUID productId,Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute(PRODUCT, product);
        return "/product/deleteProduct";
    }
    @PostMapping("/management/product/delete.post")
    public String deleteProductPostManagement(DeleteProductForm deleteProductForm) {
        if (deleteProductForm.getProductId() != null) {
            productService.deleteProduct(deleteProductForm.getProductId());
        }
        return "redirect:/management/product/all";
    }


    @GetMapping("/management/product/all")
    public String allProductsManagement(Model model) {
        List<Product> products = productService.getAllProducts();
        Map<Product, String> productTagMap = new HashMap<>();
        final String[] tags = {""};
        products.forEach(product -> {
            tags[0] = "";
            product.getTags().forEach(tag -> tags[0] = tags[0] + tag + ", ");
            productTagMap.put(product, tags[0]);
        });
        model.addAttribute("productTagMap", productTagMap);
        return "/product/allProducts";
    }



    @GetMapping("/management/productType/add")
    public String addProductType(){
        return "/product/addProductType";
    }
    @GetMapping("/management/productType/add.post")
    public String addProductTypePost(AddProductTypeForm addProductTypeForm){
        if (addProductTypeForm.getType() != null) {
            ProductType productType = new ProductType(addProductTypeForm.getType(), addProductTypeForm.isShippingRequired());
            productTypeService.addProductType(productType);
            return "redirect:/management/productType/detail?id="+productType.getId();
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @GetMapping("/management/productType/detail")
    public String detailProductType(@RequestParam(name = "id", required = false) UUID productTypeId, Model model){
        if (productTypeId != null) {
            model.addAttribute(PRODUCT_TYPE, productTypeService.getProductTypeById(productTypeId));
            return "/product/detailProductType";
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @GetMapping("/management/productType/edit.post")
    public String editProductTypePost(EditProductTypeForm editProductTypeForm){
        if (editProductTypeForm.getProductTypeId() != null && editProductTypeForm.getType() != null) {
            ProductType productType = new ProductType(editProductTypeForm.getProductTypeId(), editProductTypeForm.getType(), editProductTypeForm.isShippingRequired());
            productTypeService.updateProductType(productType);
            return "redirect:/management/productType/detail?id="+productType.getId();
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @GetMapping("/management/productType/delete")
    public String deleteProductType(@RequestParam(name = "id", required = false) UUID productTypeId, Model model){
        if (productTypeId != null) {
            ProductType productType = productTypeService.getProductTypeById(productTypeId);
            model.addAttribute(PRODUCT_TYPE, productType);
            return "/product/deleteProductType";
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @GetMapping("/management/productType/delete.post")
    public String deleteProductTypePost(DeleteProductTypeForm deleteProductTypeForm){
        if (deleteProductTypeForm.getProductTypeId() != null) {
            productTypeService.deleteProductTypeById(deleteProductTypeForm.getProductTypeId());
            return "redirect:/management/productType/all";
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }

    @GetMapping("/management/productType/all")
    public String allProductTypes(Model model) {
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        model.addAttribute(PRODUCT_TYPES, productTypes);
        return "/product/allProductTypes";
    }
}
