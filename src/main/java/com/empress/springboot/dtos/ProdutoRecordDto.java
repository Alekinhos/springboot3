package com.empress.springboot.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRecordDto(@NotBlank String nome, @NotNull BigDecimal valor, String imagem) {

}
