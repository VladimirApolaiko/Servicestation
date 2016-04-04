/* Oauth Access Token table definition */
CREATE TABLE oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256) NOT NULL,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
);
/* Oauth Refresh Token table definition */
CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
);