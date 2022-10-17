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
  is_winning_bid TINYINT not null default 0;
  FOREIGN KEY(auction_id) references auction(id)
);

CREATE INDEX idx_auction_id_bid ON bids(auction_id, bid_value);
CREATE INDEX idx_end_state ON auction(state, end_time);
