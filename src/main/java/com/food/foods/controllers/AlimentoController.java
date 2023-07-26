package com.food.foods.controllers;


import com.food.foods.dtos.AlimentoRecordDto;

import com.food.foods.models.Alimento;
import com.food.foods.repositories.AlimentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlimentoController {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @PostMapping("/alimento")
    public ResponseEntity<Alimento> saveProduct(@RequestBody @Valid AlimentoRecordDto alimentoRecordDto) {
        var alimento = new Alimento();
        alimento.setDataCadastro(new Date());
        BeanUtils.copyProperties(alimentoRecordDto, alimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentoRepository.save(alimento));
    }


    @GetMapping("/alimento")//Get = pega todos os produtos
    public ResponseEntity<List<Alimento>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-pages", String.valueOf(alimentoRepository.findAll(pageable).getTotalPages()));
        Page<Alimento> productPage = alimentoRepository.findAll(pageable);
        List<Alimento> productList = productPage.getContent();
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(productList);
    }


    @GetMapping("/alimento/{id}")//Get = pega o produto
    public ResponseEntity<Alimento> getOneProduct(@PathVariable(value = "id") Integer id) {
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(id);
        if (alimentoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(alimentoOptional.get());
    }


    @PutMapping("/alimento/{id}")
    public ResponseEntity<Alimento> updateProduct(@PathVariable(value = "id") Integer id, @RequestBody @Valid AlimentoRecordDto alimentoRecordDto) {
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(id);
        if (alimentoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Alimento alimento = alimentoOptional.get();
        BeanUtils.copyProperties(alimentoRepository, alimento);
        return ResponseEntity.status(HttpStatus.OK).body(alimentoRepository.save(alimento));
    }


    @DeleteMapping("/alimento/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Integer id) {
        Optional<Alimento> alimentoOptional = alimentoRepository.findById(id);
        if (alimentoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        alimentoRepository.delete(alimentoOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
