package com.deloitte.baseapp.modules.drink.repositories;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.modules.drink.entities.Drink;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends GenericRepository<Drink> {
}
