package com.meva.finance.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sub_category")
    private Integer id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;
}
