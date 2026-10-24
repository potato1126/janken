/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE chamber (
    id IDENTITY,
    userName VARCHAR NOT NULL,
    chamberName VARCHAR NOT NULL
);
CREATE TABLE userinfo (
    userName VARCHAR NOT NULL PRIMARY KEY,
    age INT,
    height DOUBLE NOT NULL
);
CREATE TABLE users(
    id IDENTITY,
    name VARCHAR NOT NULL
);
CREATE TABLE matches (
    id IDENTITY,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand VARCHAR NOT NULL,
    user2Hand VARCHAR NOT NULL
);
