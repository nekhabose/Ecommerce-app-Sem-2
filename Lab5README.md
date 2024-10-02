Nekha Bose Lab 5 README file

**Customer**
1. Customer - Order Relationship (One-to-Many, Bidirectional):
This relationship is bidirectional, meaning both the Customer and Order entities are aware of each other. The Customer can have many orders, while each Order references one Customer.

The Customer entity has a list of Order objects, representing all the orders made by that customer.
The Order entity has a reference back to the Customer to show which customer placed the order.

**Order**
2. Order - OrderLine Relationship (One-to-Many, Bidirectional):
This relationship is also bidirectional. The Order can have multiple OrderLine entries, while each OrderLine belongs to a specific Order.

The Order entity has a list of OrderLine objects, representing the individual items in the order.
The OrderLine entity references back to the Order it belongs to.

**OrderLine**
3. OrderLine - Product Relationship (Many-to-One, Unidirectional):
This relationship is unidirectional, meaning only the OrderLine entity knows about the Product. Each OrderLine references a specific Product, but the Product entity does not keep track of which OrderLines include it.

The OrderLine entity has a reference to the Product, showing which product was ordered.
The Product entity does not reference the OrderLine, so it doesn't "know" which orders include it.