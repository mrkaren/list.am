package com.example.listam.controller;

import com.example.listam.entity.Category;
import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import com.example.listam.repository.CategoryRepository;
import com.example.listam.repository.CommentRepository;
import com.example.listam.security.CurrentUser;
import com.example.listam.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;



    @GetMapping
    public String itemsPage(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("items", itemService.findItemsByUser(currentUser.getUser()));
        return "items";
    }

    @GetMapping("/{id}")
    public String singleItemPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemService.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            List<Comment> comments = commentRepository.findAllByItem_Id(item.getId());
            modelMap.addAttribute("item", item);
            modelMap.addAttribute("comments", comments);
            return "singleItem";
        } else {
            return "redirect:/items";
        }

    }

    @GetMapping("/add")
    public String itemsAddPage(ModelMap modelMap) {
        List<Category> all = categoryRepository.findAll();
        modelMap.addAttribute("categories", all);
        return "addItem";
    }

    @PostMapping("/add")
    public String itemsAdd(@ModelAttribute Item item,
                           @RequestParam("image") MultipartFile multipartFile,
                           @AuthenticationPrincipal CurrentUser currentUser) throws IOException {
        itemService.addItem(currentUser.getUser(), multipartFile, item);
        return "redirect:/items";
    }

    @GetMapping("/remove")
    public String removeCategory(@RequestParam("id") int id) {
        itemService.deleteById(id);
        return "redirect:/items";
    }

    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        comment.setCommentDate(new Date());
        comment.setUser(currentUser.getUser());
        commentRepository.save(comment);
        return "redirect:/items/" + comment.getItem().getId();
    }
}
