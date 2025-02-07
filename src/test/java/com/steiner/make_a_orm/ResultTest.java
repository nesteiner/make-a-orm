package com.steiner.make_a_orm;

import com.steiner.make_a_orm.utils.result.Result;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ResultTest {
    @Test
    public void testResult() {
        Result.from(() -> {
            return "hello";
        }).ifOk(System.out::println);

        Result.from(() -> {
            throw new ClassNotFoundException("fuck");
        }).ifErr(error -> {
            System.out.println(error.getMessage());
        });

        var result = Result.from(() -> {
            Random random = new Random();
            int number = random.nextInt();

            if (number < 0) {
                throw new Exception("fuck");
            }

            if (number > 10) {
                throw new ClassNotFoundException("shit");
            }

            return number;
        });
    }

    @Test
    public void testResultMap() {
        Result.from(() -> {
            return 1;
        })
                .map(String::valueOf)
                .ifOk(System.out::println);
    }
}
