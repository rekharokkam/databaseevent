--insert into product (product_id, prId, name, tcin, dpci, upc, model, created_date_time) values
  --('b60e4383-70e6-4757-a2f9-69c446c3d6a0',542323 , 'Apple iPhone 8 Plus 64GB - Space Gray', '52880309', '190198453655', '6166B', '2022-10-25 09:43:48.799472')

--Exception thrown for this script as Table gets created after these scripts are run

--Caused by: org.springframework.jdbc.datasource.init.ScriptStatementFailedException: Failed to execute SQL script statement #1 of URL [file:/Users/z00465g/Documents/Rekha/personal_workspace/database-event/build/resources/main/data.sql]: insert into product (product_id, prId, name, tcin, dpci, upc, model, created_date_time) values; nested exception is org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "PRODUCT" not found (this database is empty); SQL statement: