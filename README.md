# ProjectBid Application
Project Bid - demo

# POC Assumptions
 Creation of User profiles (buyer/seller) are out of scope
		
 Authentication and Role Entitlements are out of scope
		
 Only Create Project API has been implemented. Edit/Delete projects are out of scope
		
 One buyer (buyerId) is allowed to place multiple bids for a given Project (projectId). However the latest bid will replace the existing one previously created by the buyer.
		
Only creation of bids is in scope. Deletion of existing bids are out of scope.
		
Projects created are maintained in Redis InMemory Cache for POC. For real implementation, it would be stored in external RDB.
		
Bids created are maintained in Redis InMemory Cache for POC. For real implementation, we would leverage the same approach. In addtion   to this bid event logs would be maintained in NoSQL databases like Cassandra.
		
All validation errors and exceptions handled are wrapped as error code & messages in the response. Response code will be 200 Success      for all these scenarios as well.
		
Sequence ID generation (for projects and bids) is implemented using an Enum class wrapping Atomic Long variables for this POC. For real  implementation, we would leverage a deicated service to generate sequence IDs.
		
Bids management service would need to have a daemon process which would monitor all active bids in the cache. If any of the projects     exceeds the targeted deadline, then this would be cleaned from the cache and order processing service would be notified of the winning   bid. This functionality can also be achieved by confuring spring data event listener on Redis cache (event when a record expires based   on TTL). However this functionality is not in scope for this POC.


# POC Technology Stack
Sprint BOOT based application

Jersey for REST services

Embedded Tomcat Server

Embedded REDIS InMemory DB for Caching (Projects and Bids)

HATEOAS plugin for REST resources


# REST APIs exposed in this POC


1. List of all projects
GET - http://localhost:8008/intuit/demo/projects


2. List of all open projects
GET - http://localhost:8008/intuit/demo/projects?status=O


3. List of all closed projects
GET - http://localhost:8008/intuit/demo/projects?status=C


4. Get Project Details by Project ID
GET - http://localhost:8008/intuit/demo/projects/1001


5. Create a new Project
POST - http://localhost:8008/intuit/demo/projects

{
	"title":"Create new project",
	"description":"Create new desc",
	"sellerId":"105",
	"maxBudget":9999,
	"targetDate": "2018-01-12 08:00:20 AM GMT"
}


6. Create a new Bid for a project
POST - http://localhost:8008/intuit/demo/projects/1004/bids

{
	"buyerId":"1805",
	"bidAmount":1000
}	


7. Get all Bids for a given project
GET - http://localhost:8008/intuit/demo/projects/1004/bids


# Key Points

1. Projects - Redis Hash has been used to store projects. (Key - projectID, Value - Project)

2. Bids - Redis Hash and Sorted Set (ZSet) has been used to store bids. ZSet stores list of bid Ids sorted by lowest bid amount first.

3. HATEOAS plugin is used to compose action links for a given resource. Links are dynamically rendered based on state of a given REST resource. For eg). "Create Bid" link will be available only for Projects with Open status
	
