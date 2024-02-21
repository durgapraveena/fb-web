# fb-web

mysql user creation
```
CREATE USER 'mysql'@'%' IDENTIFIED BY 'mysql' ;
```
Grant All Privileges:
```
GRANT ALL PRIVILEGES ON *.* TO 'mysql'@'%' WITH GRANT OPTION;

```
Flush Privileges:
```
FLUSH PRIVILEGES;
```
