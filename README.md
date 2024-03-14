Data service is used as a module for interaction with the NoSQL database MongoDB. 
It reads parsing links from the corresponding RabbitMQ queue and places it 
in the corresponding collection in MongoDB. After that, it selects links from this
collection and uses its download method to download images from these links to the 
user-specified directory of the local machine. This service also saves the history 
of parsing user links in a separate MongoDB collection
