package com.steiner.make_a_orm;

import com.mysql.cj.jdbc.Driver;
import com.steiner.make_a_orm.database.Database;
import com.steiner.make_a_orm.impl.NumberCollectionTable;
import com.steiner.make_a_orm.transaction.Transaction;
import com.steiner.make_a_orm.utils.SchemaUtils;
import com.steiner.make_a_orm.utils.result.Result;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    Driver driver = Result.from(Driver::new).get();
    Database database = Database.builder()
            .driver(driver)
            .username("steiner")
            .password("779151714")
            .url("jdbc:mysql://localhost/playground")
            .build().get();


    NumberCollectionTable longIdTable = NumberCollectionTable.getInstance();

    @Test
    public void testCreateTable() {
         Transaction.runWith(database, () -> {
                SchemaUtils.drop(longIdTable);
                SchemaUtils.create(longIdTable);
         });
    }

    // 暂时没有测试到 读取 longIdTable 中的值
    @Test
    public void testReadTable() {
        Transaction.runWith(database, () -> {
            longIdTable.selectAll()
                    .stream()
                    .forEach(resultRow -> {
                        System.out.println(resultRow.get(longIdTable.column1));
                        System.out.println(resultRow.get(longIdTable.column2));
                        System.out.println(resultRow.get(longIdTable.column3));
                        System.out.println(resultRow.get(longIdTable.column4));
                        System.out.println(resultRow.get(longIdTable.column5));
                        System.out.println(resultRow.get(longIdTable.column6));
                    });
        });
    }
}
