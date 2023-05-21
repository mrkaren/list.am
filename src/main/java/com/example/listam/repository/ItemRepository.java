package com.example.listam.repository;

import com.example.listam.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByUser_Id(int userId);

}
