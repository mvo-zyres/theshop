package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.CategoryService;
import com.onlineshop.theshop.service.StoreService;
import com.onlineshop.theshop.shop.model.store.Category;
import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.shop.form.category.AddCategoryForm;
import com.onlineshop.theshop.shop.form.category.DeleteCategoryForm;
import com.onlineshop.theshop.shop.form.category.EditCategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    StoreService storeService;


    @GetMapping(value = "/category/{url}")
    public String category(@PathVariable String url, Model model){
        if (url != null) {
            if (categoryService.urlExists(url)) {
                Category category = categoryService.getCategoryByUrl(url);
                model.addAttribute("products", category.getProducts());
                model.addAttribute("emptyMsg", "The category you selected does not have any products :(");
                return "product/all";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }


    @GetMapping("/management/category/add")
    public String addCategory(Model model) {
        model.addAttribute("allStores", storeService.getAllStores());
        return "/category/addCategory";
    }
    @PostMapping("/management/category/add.post")
    public String addCategoryPost(AddCategoryForm addCategoryForm) {
        if (addCategoryForm.getName() != null && addCategoryForm.getStoreId() != null && addCategoryForm.getUrl() != null) {
            if (!categoryService.nameExists(addCategoryForm.getName())) {
                Category category = new Category(addCategoryForm.getName(), storeService.getStoreById(addCategoryForm.getStoreId()), addCategoryForm.getUrl(), addCategoryForm.getTax());
                categoryService.addCategory(category);
                return "redirect:/management/category/detail?id="+category.getId();
            }
            else {
                return CONSTANT.REDIRECT_403;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }

    @GetMapping("/management/category/detail")
    public String detailCategory(@RequestParam(name = "id", required = false) UUID categoryId, Model model) {
        if (categoryId != null) {
            if (categoryService.categoryExists(categoryId)) {
                Category category = categoryService.getCategoryById(categoryId);
                model.addAttribute("allStores", storeService.getAllStores());
                model.addAttribute("category", category);
                return "/category/detailCategory";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @PostMapping("/management/category/edit.post")
    public String editCategoryPost(EditCategoryForm editCategoryForm) {
        if (editCategoryForm.getName() != null && editCategoryForm.getStoreId() != null && editCategoryForm.getUrl() != null) {
            if (categoryService.categoryExists(editCategoryForm.getCategoryId())) {
                Category category = categoryService.getCategoryById(editCategoryForm.getCategoryId());
                category.setName(editCategoryForm.getName());
                category.setUrl(editCategoryForm.getUrl());
                category.setTax(editCategoryForm.getTax());
                category.setStore(storeService.getStoreById(editCategoryForm.getStoreId()));
                categoryService.updateCategory(category);
                return "redirect:/management/category/detail?id="+category.getId();
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }

    @GetMapping("/management/category/delete")
    public String deleteCategory(@RequestParam(name = "id", required = false) UUID categoryId,Model model) {
        if (categoryId != null) {
            if (categoryService.categoryExists(categoryId)) {
                Category category = categoryService.getCategoryById(categoryId);
                model.addAttribute("category", category);
                return "/category/deleteCategory";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }


        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @PostMapping("/management/category/delete.post")
    public String deleteCategoryPost(DeleteCategoryForm deleteCategoryForm) {
        if (deleteCategoryForm.getCategoryId() != null && categoryService.categoryExists(deleteCategoryForm.getCategoryId())) {
                categoryService.deleteCategory(deleteCategoryForm.getCategoryId());
                return "redirect:/management/category/all";
        }
        return CONSTANT.REDIRECT_404;
    }

    @GetMapping("/management/category/all")
    public String allCategories(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "/category/allCategories";
    }


}
