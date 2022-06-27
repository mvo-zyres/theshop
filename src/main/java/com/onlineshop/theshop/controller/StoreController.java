package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.StoreService;
import com.onlineshop.theshop.shop.model.store.Store;
import com.onlineshop.theshop.shop.form.store.AddStoreForm;
import com.onlineshop.theshop.shop.form.store.DeleteStoreForm;
import com.onlineshop.theshop.shop.form.store.EditStoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping("/management/store")
@Controller
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping("/add")
    public String addStore() {
        return "/store/addStore";
    }
    @PostMapping("/add.post")
    public String addStorePost(AddStoreForm addStoreForm) {
        Store store = new Store(addStoreForm.getName(),addStoreForm.getTax());
        storeService.addStore(store);
        return "redirect:/management/store/detail?id="+store.getId();
    }



    @GetMapping("/detail")
    public String detailStore(@RequestParam(name = "id")UUID storeId, Model model) {
        Store store = storeService.getStoreById(storeId);
        model.addAttribute("store", store);
        return "/store/detailStore";
    }
    @PostMapping("/edit.post")
    public String editStorePost(EditStoreForm editStoreForm) {
        Store store = storeService.getStoreById(editStoreForm.getStoreId());
        store.setName(editStoreForm.getName());
        store.setTax(editStoreForm.getTax());
        storeService.updateStore(store);
        return "redirect:/management/store/detail?id="+store.getId();
    }



    @GetMapping("/delete")
    public String deleteStore(@RequestParam(name = "id") UUID storeId,Model model) {
        Store store = storeService.getStoreById(storeId);
        model.addAttribute("store", store);
        return "/store/deleteStore";
    }
    @PostMapping("/delete.post")
    public String deleteStorePost(DeleteStoreForm deleteStoreForm) {
        if (deleteStoreForm.getStoreId() != null) {
            storeService.deleteStore(deleteStoreForm.getStoreId());
        }
        return "redirect:/management/store/all";
    }


    @GetMapping("/all")
    public String allStores(Model model) {
        model.addAttribute("allStores", storeService.getAllStores());
        return "/store/allStores";
    }


}
