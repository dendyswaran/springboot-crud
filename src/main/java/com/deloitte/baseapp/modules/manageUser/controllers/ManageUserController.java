package com.deloitte.baseapp.modules.manageUser.controllers;

import com.deloitte.baseapp.commons.*;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import com.deloitte.baseapp.modules.account.services.UserService;
import com.deloitte.baseapp.modules.manageUser.payloads.UserResponse;
import com.deloitte.baseapp.modules.manageUser.services.ManageUserService;
import com.deloitte.baseapp.configs.security.services.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/manage-user")
@CrossOrigin(origins = "*")
public class ManageUserController  extends GenericController<User>{

    final ManageUserService manageUserService;
    final UserService userService;


    public ManageUserController(GenericRepository<User> repository, UserService userService, ManageUserService manageUserService) {
        super(repository, "manageUser");
        this.userService = userService;
        this.manageUserService = manageUserService;
    }

    @GetMapping("/user-list")
    public MessageResponse<?> getUserList() {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserResponse> users = manageUserService.getAllUser();
        return new MessageResponse<>(users);
    }

    @PutMapping("/edit/{id}")
    public MessageResponse updateUser(@PathVariable("id") Long id, @RequestBody UserResponse values) {
        try {
            User updated = userService.get(id);
            updated.setUsername(values.getUsername());
            updated.setEmail(values.getEmail());
            return new MessageResponse(userService.update(id, updated));
        } catch (ObjectNotFoundException e) {
            return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
        }
    }

    @DeleteMapping("/delete/{id}")
    public MessageResponse deleteUser(@PathVariable Long id) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(userPrincipal.getId(), id)) {
            try{
                userService.delete(id);
                return new MessageResponse(true);
            } catch (ObjectNotFoundException e) {
                return MessageResponse.ErrorWithCode(e.getMessage(), e.getCode());
            }
        }
        return MessageResponse.ErrorWithCode("User cannot delete self account", 401);
    }

    @PostMapping("/datatable-users")
    public MessageResponse getDatatableManageUser(@RequestBody PagingRequest pagingRequest) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, PagingRequest.FilterObject> dtSearch = pagingRequest.getDtSearch() != null ? pagingRequest.getDtSearch() : new HashMap<>();

        PagingRequest.FilterObject filterObject = new PagingRequest.FilterObject();
        filterObject.setMatchMode(String.valueOf(PagingRequest.FilterObjectMode.NOT_EQUALS));
        filterObject.setType(String.valueOf(PagingRequest.FilterObjectType.NUMERIC));
        String userId = String.valueOf(userPrincipal.getId());
        filterObject.setValue(userId);


        dtSearch.put("id", filterObject);

        dtSearch.forEach((key, value) -> System.out.println(key + ":" + value));

        pagingRequest.setDtSearch(dtSearch);
        Page<User> page = userService.getPage(pagingRequest);
//        page.map(() -> System.out.println(user.toString()));
        page.getContent();
        return new MessageResponse<>();
    }
}
