package com.empress.springboot.controllers;


import com.empress.springboot.dtos.ProductRecordDto;
import com.empress.springboot.models.ProductModel;
import com.empress.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products") //Inseri um produto / URI - Uniform Resource Identifier - Identificador Uniforme de Recursos
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
      var productModel = new ProductModel(); //var = ProductModel para n ter que escrever a classe dos dois lados
        BeanUtils.copyProperties(productRecordDto, productModel); //copia as propriedades de um objeto para outro objeto nesse caso do productRecordDto para o productModel
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel)); //retorna o status 201 created e o produto salvo visto que o produto foi salvo com sucesso
    }

    @GetMapping("/products")//Get = pega todos os produtos
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll()); //retorna o status 200 ok e todos os produtos, lista de todos os produtos
    }

    @GetMapping("/products/{id}")//Get = pega o produto
    public ResponseEntity<ProductModel> getOneProduct(@PathVariable(value="id") UUID id) { //PathVariable = variavel de caminho, e o value é o valor da variavel
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if(productModelOptional.isEmpty()) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @PutMapping("/products/{id}") //Put = atualiza o produto
    public ResponseEntity<ProductModel> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id); // Verificar se o produto existe
        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//Se não existir retorna o status 404 not found
        }
        ProductModel productModel = productModelOptional.get();//Se existir pegar o produto
        BeanUtils.copyProperties(productRecordDto, productModel); //copia as propriedades de um objeto para outro objeto nesse caso do productRecordDto para o productModel
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}") //Delete = deleta o produto
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id); // Verificar se o produto existe
        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//Se não existir retorna o status 404 not found
        }
        productRepository.delete(productModelOptional.get()); // Utilizando o metodo JPA delete para deletar o produto
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
