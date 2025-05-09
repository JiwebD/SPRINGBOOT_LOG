package com.example.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="memo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    private int id;
    private String text;

}
