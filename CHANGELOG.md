# Changelog

今天(2025-2-11)才知道可以写 CHANGELOG 来管理版本更新的信息，0.2.0 版本前都是对 ORM 的制作做一个探索
这个版本以后开始进行重构，大规模更新

## [v0.2.0] - 2025-2-11

### 新增 字段

#### 新增字段限制

- [x] [check](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/CheckConstraint.java)
- [x] [auto increment](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/DefaultAutoIncrementConstraint.java)
- [x] [auto increment start by](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/CustomAutoIncrementConstraint.java)
- [x] [default value](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/DefaultValueConstraint.java)
- [x] [foreign key](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/ForeignKeyConstraint.java)
- [x] [not null](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/NotNullConstraint.java)
- [x] [primary key](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/PrimaryKeyConstraint.java)
- [x] [unique](./src/main/java/com/steiner/make_a_orm/column/constraint/impl/UniqueConstraint.java)

#### 新增字段的功能 trait

- [x] [auto increment](./src/main/java/com/steiner/make_a_orm/column/trait/IAutoIncrementColumn.java)
- [x] [between](./src/main/java/com/steiner/make_a_orm/column/trait/IBetweenColumn.java)
- [x] [compare](./src/main/java/com/steiner/make_a_orm/column/trait/ICompareColumn.java)
- [x] [compare date](./src/main/java/com/steiner/make_a_orm/column/trait/ICompareDateColumn.java)
- [x] [default value](./src/main/java/com/steiner/make_a_orm/column/trait/IDefaultValueColumn.java)
- [x] [equal] (./src/main/java/com/steiner/make_a_orm/column/trait/IEqualColumn.java)
- [x] [in list](./src/main/java/com/steiner/make_a_orm/column/trait/IInListColumn.java)
- [x] [like](./src/main/java/com/steiner/make_a_orm/column/trait/ILikeColumn.java)
- [x] [null or not](./src/main/java/com/steiner/make_a_orm/column/trait/INullOrNotColumn.java)
- [x] [primary key](./src/main/java/com/steiner/make_a_orm/column/trait/IPrimaryKeyColumn.java)

#### 新增数字字段

- [x] [bigint](./src/main/java/com/steiner/make_a_orm/column/numeric/BigIntColumn.java)
- [x] [int](./src/main/java/com/steiner/make_a_orm/column/numeric/IntColumn.java)
- [x] [smallint](./src/main/java/com/steiner/make_a_orm/column/numeric/SmallIntColumn.java)
- [x] [tinyint](./src/main/java/com/steiner/make_a_orm/column/numeric/TinyIntColumn.java)
- [x] [boolean](./src/main/java/com/steiner/make_a_orm/column/bool/BooleanColumn.java)
- [x] [float](./src/main/java/com/steiner/make_a_orm/column/numeric/FloatColumn.java)
- [x] [double](./src/main/java/com/steiner/make_a_orm/column/numeric/DoubleColumn.java)
- [x] [decimal](./src/main/java/com/steiner/make_a_orm/column/numeric/DecimalColumn.java)

#### 新增日期字段

- [x] [date](./src/main/java/com/steiner/make_a_orm/column/date/DateColumn.java)
- [x] [time](./src/main/java/com/steiner/make_a_orm/column/date/TimeColumn.java)
- [x] [timestamp/datetime](./src/main/java/com/steiner/make_a_orm/column/date/TimestampColumn.java)

#### 新增 Blob 字段

- [x] [blob](./src/main/java/com/steiner/make_a_orm/column/blob/BlobColumn.java)
- [x] [medium blob](./src/main/java/com/steiner/make_a_orm/column/blob/MediumBlobColumn.java)
- [x] [long blob](./src/main/java/com/steiner/make_a_orm/column/blob/LongBlobColumn.java)

#### 新增字符串字段

- [x] [char](./src/main/java/com/steiner/make_a_orm/column/string/CharacterColumn.java)
- [x] [varchar](./src/main/java/com/steiner/make_a_orm/column/string/CharacterVaryingColumn.java)
- [x] [text](./src/main/java/com/steiner/make_a_orm/column/string/TextColumn.java)
- [x] [medium text](./src/main/java/com/steiner/make_a_orm/column/clob/MediumTextColumn.java)
- [x] [long text](./src/main/java/com/steiner/make_a_orm/column/clob/LongTextColumn.java)

### 新增工具类

1. [Result 参考 Rust lang](./src/main/java/com/steiner/make_a_orm/utils/result/Result.java)
2. [SchemaUtil 用来操作 Table](./src/main/java/com/steiner/make_a_orm/utils/SchemaUtils.java)

### 新增操作

#### 查询操作

- [x] where 子句
- [x] order
- [x] offset
- [x] reverse
- [x] limit

#### 插入操作

- [x] InsertStatement

#### 更新操作

- [x] UpdateStatement

### 下一步

- [ ] 测试 clob
- [ ] 测试 blob
- [ ] 测试 check
- [ ] 在 Table 中添加外键支持
- [ ] 删除操作
