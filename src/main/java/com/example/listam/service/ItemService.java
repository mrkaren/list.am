package com.example.listam.service;


import com.example.listam.entity.Item;
import com.example.listam.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findItemsByUser(User user);
    Optional<Item> findById(int id);

    void addItem(User currentUser, MultipartFile multipartFile, Item item) throws IOException;

    void deleteById(int id);
}
