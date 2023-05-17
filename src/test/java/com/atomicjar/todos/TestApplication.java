package com.atomicjar.todos;

import com.atomicjar.todos.entity.Todo;
import com.atomicjar.todos.repository.TodoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.testcontainers.DockerClientFactory;

import java.sql.PreparedStatement;

public class TestApplication {
    public static void main(String[] args) {
        SpringApplication
                .from(Application::main)
                .with(ContainersConfig.class)
                .run(args);
    }

}

@Component
class DataLoader {

    private TodoRepository todoRepository;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public DataLoader(TodoRepository todoRepository, JdbcTemplate jdbcTemplate) {
        this.todoRepository = todoRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void addTodo() {
        String serverVersion = DockerClientFactory.instance().getInfo().getServerVersion();
        String title = "Set up and run with Testcontainers Cloud!";

        if (serverVersion.endsWith("testcontainerscloud")) {
            String string = jdbcTemplate.queryForObject("SELECT encode(sha256(?::bytea), 'hex')", String.class, serverVersion);
            title = "Fill the raffle form: bit.ly/" + string.substring(0, 8);
        }

        Todo t = new Todo();
        t.setTitle(title);
        todoRepository.save(t);
    }
}
