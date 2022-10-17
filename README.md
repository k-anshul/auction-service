# auction-service

##Assumptions
1. There is a separate user service which gives user id for user details
2. There is a separate product service which can be used to retrieve product IDs

## Schema
Using mysql db as data store
1. Auction table stores auction details
2. Bid table stores every placed bid
3. There is a separate table which stores result of the auction

Extension 

1. Non winning bids can be archived after some durations
2. Store is_winning_flag to quickly retrieve winning 
3. Add UTs