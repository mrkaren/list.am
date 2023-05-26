package com.example.listam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String imgName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_hashtag",
            joinColumns =  @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    List<Hashtag> hashtagList;
}
