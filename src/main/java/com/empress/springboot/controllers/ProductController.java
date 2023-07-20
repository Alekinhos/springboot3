package com.empress.springboot.controllers;


import com.empress.springboot.dtos.ProdutoRecordDto;
import com.empress.springboot.models.Produto;
import com.empress.springboot.repositories.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @CrossOrigin(origins = "http://127.0.0.1:5173") // Insira a origem permitida, substituindo o exemplo pelo seu frontend
    @PostMapping("/products") //Inseri um produto / URI - Uniform Resource Identifier - Identificador Uniforme de Recursos
    public ResponseEntity<Produto> saveProduct(@RequestBody @Valid ProdutoRecordDto produtoRecordDto) {
      var productModel = new Produto(); //var = ProductModel para n ter que escrever a classe dos dois lados
        BeanUtils.copyProperties(produtoRecordDto, productModel); //copia as propriedades de um objeto para outro objeto nesse caso do productRecordDto para o productModel
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(productModel)); //retorna o status 201 created e o produto salvo visto que o produto foi salvo com sucesso
    }


    @CrossOrigin(origins = "http://127.0.0.1:5173") // Insira a origem permitida, substituindo o exemplo pelo seu frontend
    @GetMapping("/products")//Get = pega todos os produtos
    public ResponseEntity<List<Produto>> getAllProducts() {
        List<Produto> productList = produtoRepository.findAll();
        if(!productList.isEmpty()) {
            for(Produto product : productList) {
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());// LinkTo qual endpoint ou metodo que eu vou redirecionar o cliente/methodoOn = metodo que eu quero redirecionar o cliente
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList); //retorna o status 200 ok e todos os produtos, lista de todos os produtos
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173") // Insira a origem permitida, substituindo o exemplo pelo seu frontend
    @GetMapping("/products/{id}")//Get = pega o produto
    public ResponseEntity<Produto> getOneProduct(@PathVariable(value="id") UUID id) { //PathVariable = variavel de caminho, e o value é o valor da variavel
        Optional<Produto> productModelOptional = produtoRepository.findById(id);
        if(productModelOptional.isEmpty()) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        productModelOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Lista de Produtos"));// LinkTo qual endpoint ou metodo que eu vou redirecionar o cliente/methodoOn = metodo que eu quero redirecionar o cliente
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173") // Insira a origem permitida, substituindo o exemplo pelo seu frontend
    @PutMapping("/products/{id}") //Put = atualiza o produto
    public ResponseEntity<Produto> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProdutoRecordDto produtoRecordDto) {
        Optional<Produto> productModelOptional = produtoRepository.findById(id); // Verificar se o produto existe
        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//Se não existir retorna o status 404 not found
        }
        Produto produto = productModelOptional.get();//Se existir pegar o produto
        BeanUtils.copyProperties(produtoRecordDto, produto); //copia as propriedades de um objeto para outro objeto nesse caso do productRecordDto para o productModel
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
    }

    @CrossOrigin(origins = "http://127.0.0.1:5173") // Insira a origem permitida, substituindo o exemplo pelo seu frontend
    @DeleteMapping("/products/{id}") //Delete = deleta o produto
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<Produto> productModelOptional = produtoRepository.findById(id); // Verificar se o produto existe
        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);//Se não existir retorna o status 404 not found
        }
        produtoRepository.delete(productModelOptional.get()); // Utilizando o metodo JPA delete para deletar o produto
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
