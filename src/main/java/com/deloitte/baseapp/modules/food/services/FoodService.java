package com.deloitte.baseapp.modules.food.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.PagingResult;
import com.deloitte.baseapp.modules.food.entities.Food;
import com.deloitte.baseapp.modules.food.payloads.CreateFoodRequest;
import com.deloitte.baseapp.modules.food.repositories.FoodRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FoodService {

    @Autowired
    FoodRepositories foodRepositories;

    /**
     *
     * @param page
     * @param length
     * @return
     */
    public PagingResult<Food> pagination(int page, int length) {
        final Pageable pageable = PageRequest.of(page - 1, length);
        final Page<Food> paging = foodRepositories.findAll(pageable);
        final List<Food> result = paging.getContent();

        PagingResult<Food> resp = new PagingResult<>();
        resp.setContent(result);
        resp.setLength(length);
        resp.setTotal(paging.getTotalPages());
        resp.setPage(page);
        return resp;
    }

    public Food get(final Long id) throws ObjectNotFoundException {
        Optional<Food> optionalFood = foodRepositories.findById(id);
        if (optionalFood.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        return optionalFood.get();
    }

    /**
     *
     * @param payload
     * @return
     */
    public Food create(final CreateFoodRequest payload) {
        Food food = new Food();
        food.setName(payload.getName());

        return foodRepositories.save(food);
    }

    public Food update(final Long id, final CreateFoodRequest payload) throws ObjectNotFoundException {
        Optional<Food> optionalFood = foodRepositories.findById(id);
        if (optionalFood.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        Food food = optionalFood.get();
        food.setName(payload.getName());

        return foodRepositories.save(food);
    }

    public boolean delete(final Long id) throws ObjectNotFoundException {
        Optional<Food> optionalFood = foodRepositories.findById(id);
        if (optionalFood.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        foodRepositories.deleteById(id);
        return true;
    }
}
