package com.deloitte.baseapp.modules.food.repositories;

import com.deloitte.baseapp.modules.food.entities.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepositories extends JpaRepository<Food, Long> {

    Page<Food> findAll(Pageable pageable);

    Optional<Food> findById(Long id);

}
