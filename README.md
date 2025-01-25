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