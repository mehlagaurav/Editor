CREATE TABLE IF NOT EXISTS article (
  articleUUID varchar(255) NOT NULL,
  header varchar(255) NOT NULL,
  text varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  keywords varchar(255) DEFAULT NULL,
  author varchar(255) DEFAULT NULL,
  publish_date tinyblob,
  etag int(11) NOT NULL,
  PRIMARY KEY (articleUUID)
);