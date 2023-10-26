package com.example.midtermtrying2.service;

import com.example.midtermtrying2.model.UsersModel;
import com.example.midtermtrying2.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String login, String password, String email){
        if(login == null || password == null){
            return null;
        }
        else {
            if (usersRepository.findFirstByLogin(login).isPresent()){
                return null;
            }
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setEmail(email);
            usersModel.setPassword(password);
            return usersRepository.save(usersModel);
        }
    }
    public UsersModel authenticate(String login, String password){
        return usersRepository.findByLoginAndPassword(login, password).orElse(null);
    }
    public void deleteById(Integer id){
        usersRepository.deleteById(id);
    }
    public void changeName(UsersModel usersModel){
        UsersModel usersModel1 = usersRepository.getReferenceById(usersModel.getId());
        usersModel1.setLogin(usersModel.getLogin());
        usersRepository.save(usersModel1);
    }
}
