package com.food.foods.repositories;

import com.food.foods.models.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Integer> {


}
