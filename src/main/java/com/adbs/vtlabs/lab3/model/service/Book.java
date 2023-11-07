package com.adbs.vtlabs.lab3.model.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Book {
    private Long bookId;
    private String name;
    private String author;
    private BigDecimal price;
    private String description;
}
