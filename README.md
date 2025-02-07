# Make a ORM

## 介绍

最近对 ORM 比较有兴趣，想自己做一个玩玩
由于自己实在不喜欢 mybatis 那种自己写 SQL 的框架，这个 SQL 在编写时检查不到错误，只有在
运行时才会有错误检测，还是数据库抛出来的

近几年我在学习 Kotlin ，虽然写的很舒服，渐渐的我意识到编程语言只是一个工具，不应该把它视作一个宗教信仰，
不应该把它归为 甜豆腐脑 还是 咸豆腐脑，Emacs 还是 Vim 这样的问题
因此，我决定先把业界最常用的 Java 学好，这个项目就先用 Java 来写吧

还有一点，我在看一些 ORM 框架的时候，有些框架选定字段的时候用的是字符串，比如说我要选定一个名叫 `name` 的字段，
在这个 ORM 中他这样指定 "name"  
我们要做的是类型安全的 ORM ，我们假定有一个 Table 表，有 `name` 这个成员变量表示一个叫做 `name` 的数据库 column ，这样他就能在编写代码时由
LSP 识别出来，具体参考 Kotlin 的 ORM 框架 

## 用法

这个项目的 SQL 语法暂时不考虑方言，都基于 MySQL 语法
这个 ORM 的写法参考自 Kotlin 的 Exposed 框架

```java
public class Users extends IntIdTable {
    public Column<String> name = characterVarying("name", 256);
    public Column<String> address = characterVarying("address", 256);
    public Column<Integer> age = integer("age").autoIncrement();
    
    public Users() {
        super("users");
    }
}
```

```java
public class DatabaseTest {
    @Test
    void testCreateTable() {
        Users users = new Users();

        System.out.println(users.build());
    }
}
```

运行结果
```sql
create table users (
	`id` int not null,
	`name` varchar(256) not null,
	`address` varchar(256) not null,
	`age` int auto_increment not null,
	primary key(`id`)
);
```

## 进度

### 数据表的字段类型翻译

[MySQL 数据类型](https://www.runoob.com/mysql/mysql-data-types.html)

#### 数值类型

- [x] int
- [ ] tinyint
- [ ] smallint
- [ ] mediumint
- [ ] bigint
- [ ] float
- [ ] double
- [ ] decimal

#### 日期和时间类型

- [ ] date
- [ ] time
- [ ] year
- [ ] datetime
- [ ] timestamp

#### 字符串类型

- [x] char
- [x] varchar
- [x] text
- [ ] tinyblob
- [ ] tinytext
- [ ] blob
- [ ] mediumblob
- [ ] mediumtext
- [ ] longblog
- [ ] longtext

#### 约束

- [x] primary key
- [x] unique
- [ ] foreign key | reference
 
### 查询

- [ ] select 的行为
- [ ] where 子句
- [ ] having 子句 (这玩意跟 where 有区别吗？我都忘了)
- [ ] group by
- [ ] order
- [ ] limit
- [ ] distinct


### 插入/更新数据

- [ ] insert
- [ ] update
- [ ] default value

### 删除
- [ ] delete

### 事务
- [x] thread local 事务

好的，我们可以将 MySQL 字段类型按照其数据类别进行分类，并列出每种类别对应的 Java 数据类型。这样可以更清晰地理解不同类型字段之间的映射关系。

### MySQL 字段类型分类及与 Java 类型的对应

#### 1. 数值类型
这些类型用于存储数值数据，包括整数和浮点数。

| MySQL 类型            | Java 类型                | 备注                                                                 |
|-----------------------|--------------------------|----------------------------------------------------------------------|
| `TINYINT(1)`          | `boolean`                | 布尔值（虽然实际上是整数类型）                                       |
| `TINYINT`             | `byte`                   | 小整数值                                                             |
| `SMALLINT`            | `short`                  | 较小的整数值                                                         |
| `MEDIUMINT`, `INT`    | `int`                    | 标准整数值                                                           |
| `BIGINT`              | `long`                   | 大整数值                                                             |
| `FLOAT`               | `float`                  | 单精度浮点数                                                         |
| `DOUBLE`              | `double`                 | 双精度浮点数                                                         |
| `DECIMAL`             | `java.math.BigDecimal`   | 高精度定点数，适合货币等需要高精度计算的场景                         |

#### 2. 字符串类型
这些类型用于存储字符数据，包括固定长度和可变长度的字符串。

| MySQL 类型            | Java 类型                | 备注                                                                 |
|-----------------------|--------------------------|----------------------------------------------------------------------|
| `CHAR`, `VARCHAR`, `TEXT`, `MEDIUMTEXT`, `LONGTEXT` | `String` | 字符串类型，长度可变                                                 |
| `BLOB`, `LONGBLOB`    | `byte[]`                 | 二进制大对象，适合存储图像、文件等                                    |

#### 3. 日期和时间类型
这些类型用于存储日期和时间信息。

| MySQL 类型            | Java 类型                | 备注                                                                 |
|-----------------------|--------------------------|----------------------------------------------------------------------|
| `DATE`                | `java.sql.Date`          | 日期类型                                                             |
| `TIME`                | `java.sql.Time`          | 时间类型                                                             |
| `DATETIME`, `TIMESTAMP`| `java.sql.Timestamp`     | 包含日期和时间的类型                                                 |
| `YEAR`                | `short` 或 `int`         | 年份类型，根据需求选择合适的 Java 类型                               |

#### 4. 位类型
这些类型用于存储位字段。

| MySQL 类型            | Java 类型                | 备注                                                                 |
|-----------------------|--------------------------|----------------------------------------------------------------------|
| `BIT`                 | `boolean[]` 或 `byte`    | 位字段，取决于具体使用情况                                           |

### 示例代码

以下是一个示例代码，演示如何从 MySQL 数据库中读取不同类型的数据并将其转换为相应的 Java 类型：

```java
import java.sql.*;
import java.math.BigDecimal;

public class DatabaseExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/yourDatabase";
        String user = "username";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM yourTable");

            while (rs.next()) {
                // 假设表中有以下列：id(INT), name(VARCHAR), price(DECIMAL), created_at(TIMESTAMP)
                
                // 数值类型
                int id = rs.getInt("id");
                
                // 字符串类型
                String name = rs.getString("name");
                
                // 浮点数类型
                BigDecimal price = rs.getBigDecimal("price");
                
                // 日期时间类型
                Timestamp createdAt = rs.getTimestamp("created_at");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Created At: " + createdAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### 总结

通过上述分类，你可以更清楚地了解 MySQL 字段类型与 Java 数据类型的对应关系。在实际开发过程中，确保正确处理不同类型的数据转换非常重要。以下是几个关键点：

- **布尔值**：虽然 `TINYINT(1)` 在 MySQL 中是整数类型，但在 JDBC 中通常作为布尔值处理。
- **高精度计算**：对于涉及金融计算或其他需要高精度的场合，推荐使用 `BigDecimal`。
- **日期和时间**：从 Java 8 开始，你也可以考虑使用 `LocalDate`, `LocalTime`, `LocalDateTime` 等新 API 来处理日期和时间，但要注意它们与 JDBC 的兼容性问题。

希望这个分类和示例能够帮助你更好地理解和处理 MySQL 和 Java 之间的数据类型映射。如果有任何进一步的问题或需要更多示例，请随时告诉我！