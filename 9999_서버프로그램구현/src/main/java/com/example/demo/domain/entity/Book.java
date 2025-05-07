package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookcode")
    private Long bookCode;
    @Column(name="bookname")
    private String bookName;
    private String publisher;
    private String isbn;

}
