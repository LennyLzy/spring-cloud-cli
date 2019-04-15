767来源：
MySQL5.5之前，各种字符编码中utf8（3字节）是占用空间最大的字节，
所以限制了单列索引限制256*3-1 = 767。

5.5之后引入了utf8mb4编码占用4字节，增加innodb_large_prefix参数，当置为ON时允许列索引最大达到3072。

3072来源：
innodb的page默认大小为16k
由于采用b+tree结构，为了不退化为链表，要求子节点一个page最好包含两条记录，所以一个记录不能超过8k。
又由于聚簇索引，在二级索引中包含主键索引，所以单个索引不能超过4k，去除预留和辅助空间，取值3072。
--------------------- 
作者：yjfsoft 
来源：CSDN 
原文：https://blog.csdn.net/yjfsoft/article/details/83003252 
版权声明：本文为博主原创文章，转载请附上博文链接！
-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(64) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY,
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256)
);