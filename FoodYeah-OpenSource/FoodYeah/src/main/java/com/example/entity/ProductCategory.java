package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Entity
@Data
@Table(name = "product_categories")
public class ProductCategory {



    @ApiModelProperty(value="ID de la categoría de productos", dataType="Long", position=1)
    @Id
    @Column(name="category_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value="Nombre de la categoría", dataType="String", position=2)
    @NotEmpty(message = "El nombre no puede ser vacío")
    @Column(name = "product_category_name",nullable = false)
    private String productCategoryName;
    
    @ApiModelProperty(value="Descripción de la categoría", dataType="String", position=3)
    @NotEmpty(message = "La descripción no puede ser vacía")
    @Column(name = "product_category_description",nullable = false)
    private String productCategoryDescription;

    @ApiModelProperty(value="Ultima acción realizada por el usuario", dataType="String",  example="CREATED", position=5)
    @Column(name = "product_category_state",nullable = true)
    public String state;
}
