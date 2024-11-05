package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    String name;
}
