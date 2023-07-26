package com.food.foods.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AlimentoRecordDto(@NotBlank String nome, @NotNull BigDecimal valor, String imagem, @NotNull boolean fit) {

}
