package com.adbs.vtlabs.lab3.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "`book`")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "author", length = 256)
    private String author;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "description")
    private String description;
}
