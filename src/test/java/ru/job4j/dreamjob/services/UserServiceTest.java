package ru.job4j.dreamjob.services;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDbStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void whenAddUserAndFindByEmailAnsPwd() {
        var pool = new Main().loadPool();
        var store = new UserDbStore(pool);
        User oneUser = new User(1, "111@gmail.com", "11");

        store.clearTable();

        store.add(oneUser);
        User expected = store.findUserByEmailAndPwd(oneUser.getEmail(), oneUser.getPassword()).get();
        assertThat(oneUser, is(expected));
    }

    @Test
    public void whenFindAllUsers() {
        var pool = new Main().loadPool();
        var store = new UserDbStore(pool);
        User oneUser = new User(1, "111@gmail.com", "11");
        User twoUser = new User(2, "222@gmail.com", "22");
        User treeUser = new User(3, "333@gmail.com", "33");

        store.clearTable();

        store.add(oneUser);
        store.add(twoUser);
        store.add(treeUser);

        List<User> expected = List.of(oneUser, twoUser, treeUser);
        List<User> users = store.findAll();
        assertThat(expected, is(users));
    }

}