package com.deloitte.baseapp.modules.food.controllers;

import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.PagingRequest;
import com.deloitte.baseapp.commons.PagingResult;
import com.deloitte.baseapp.modules.food.entities.Food;
import com.deloitte.baseapp.modules.food.payloads.CreateFoodRequest;
import com.deloitte.baseapp.modules.food.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    FoodService foodService;

    /**
     * GET /api/foods
     *
     * @param payload
     * @return
     */
    @GetMapping
    public MessageResponse paging(final PagingRequest payload) {
        final PagingResult<Food> result =
                foodService.pagination(payload.getPage(), payload.getLength());

        return new MessageResponse(result, "Success");
    }

    @GetMapping("/{id}")
    public MessageResponse get(@PathVariable("id") final Long id) {
        try {
            final Food food = foodService.get(id);

            return new MessageResponse(food, "Success");
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

    /**
     * POST /api/foods
     *
     * @param payload
     * @return
     */
    @PostMapping
    public MessageResponse create(@Valid @RequestBody CreateFoodRequest payload) {
        Food food = foodService.create(payload);

        return new MessageResponse(food, "Success");
    }

    /**
     * PATCH /api/foods/:id
     *
     * @param id
     * @param payload
     * @return
     */
    @PatchMapping("/{id}")
    public MessageResponse update(@PathVariable("id") final Long id, @Valid @RequestBody CreateFoodRequest payload) {
        try {
            Food food = foodService.update(id, payload);

            return new MessageResponse(food, "Success");
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

    /**
     * DELETE /api/foods/{id}
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable("id") final Long id) {
        try {
            Boolean delete = foodService.delete(id);

            return new MessageResponse(delete, "Success");
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }

}
