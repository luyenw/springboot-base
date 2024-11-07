package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    private String id;
    private String description;
    @Column(name = "parent_id")
    private String parentId;

    public String getName(){
        return this.id;
    }

    public String setName(String name){
        return this.id = name;
    }
}
