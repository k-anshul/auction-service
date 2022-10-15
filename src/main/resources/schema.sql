USE marketplace;

CREATE TABLE auction (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  product_id  INT UNSIGNED NOT NULL,
  seller_id  INT UNSIGNED NOT NULL,
  start_time DATETIME NOT NULL default NOW(),
  end_time DATETIME,
  start_bid_value DECIMAL not null,
  extension TINYINT NOT NULL default 1,
  state TINYINT NOT NULL
);

CREATE TABLE bids (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  auction_id  INT UNSIGNED NOT NULL,
  bidder_id  INT UNSIGNED NOT NULL,
  bid_time DATETIME NOT NULL default NOW(),
  bid_value DECIMAL not null,
  FOREIGN KEY(auction_id) references auction(id)
);

CREATE TABLE auction_result (
  auction_id  INT UNSIGNED NOT NULL,
  bid_id INT UNSIGNED NOT NULL,
  FOREIGN KEY(auction_id) references auction(id),
  FOREIGN KEY(bid_id) references bids(id)
);

