package com.deloitte.baseapp.modules.account.services;

import com.deloitte.baseapp.commons.GenericRepository;
import com.deloitte.baseapp.commons.GenericService;
import com.deloitte.baseapp.modules.account.entities.User;
import com.deloitte.baseapp.modules.account.exceptions.UserNotFoundException;
import com.deloitte.baseapp.modules.account.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService extends GenericService<User> {

    @Autowired
    UserRepository userRepository;

    public UserService(GenericRepository<User> repository) {
        super(repository);
    }

    /**
     *
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public User getProfileByEmail(final String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        return optionalUser.get();
    }

}
