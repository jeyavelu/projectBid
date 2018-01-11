# projectBid
Project Bid - demo

# POC Assumptions
  	Creation of User profiles (buyer/seller) are out of scope
		
  	Authentication and Role Entitlements are out of scope
		
  	Only Create Project API has been implemented. Edit/Delete projects are out of scope
		
  	One buyer (buyerId) is allowed to place multiple bids for a given Project (projectId). However the latest bid will replace the existing 		one previously created by the buyer.
		
		Only creation of bids is in scope. Deletion of existing bids are out of scope.
		
		Projects created are maintained in Redis InMemory Cache for POC. For real implementation, it would be stored in external RDB.
		
		Bids created are maintained in Redis InMemory Cache for POC. For real implementation, we would leverage the same approach. In addtion       to this bid event logs would be maintained in NoSQL databases like Cassandra.
		
		All validation errors and exceptions handled are wrapped as error code & messages in the response. Response code will be 200 Success       for all these scenarios as well.
		
		Sequence ID generation (for projects and bids) is implemented using an Enum class wrapping Atomic Long variables for this POC. For real     implementation, we would leverage a deicated service to generate sequence IDs.
		
		Bids management service would need to have a daemon process which would monitor all active bids in the cache. If any of the projects       exceeds the targeted deadline, then this would be cleaned from the cache and order processing service would be notified of the winning     bid. This functionality can also be achieved by confuring spring data event listener on Redis cache (event when a record expires based     on TTL). However this functionality is not in scope for this POC.
