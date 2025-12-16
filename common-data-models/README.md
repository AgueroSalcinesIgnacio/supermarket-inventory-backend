# Common Data Models

This module contains the shared domain entities and data models used across the Supermarket Inventory system. It serves as a central library to ensure consistency in data structures between different microservices.

## Entities

### Product
Represents a product in the supermarket catalog.
- **productId** (String): Unique identifier for the product.
- **name** (String): Name of the product.
- **category** (String): Category the product belongs to.
- **price** (BigDecimal): Unit price of the product.

### Inventory
Represents the stock information for a product.
- **id** (UUID): Unique identifier for the inventory record.
- **productId** (String): Reference to the associated product.
- **stockLocal** (Integer): Current stock level available locally.
- **version** (Long): Version number for optimistic locking to prevent concurrent update issues.
- **updatedAt** (Instant): Timestamp of the last update.

### InventoryProjection
A read-optimized projection of the inventory data, useful for queries and views.
- **productId** (String): Unique identifier of the product.
- **productName** (String): Name of the product (denormalized for easier access).
- **currentStock** (Integer): Current stock level.
- **lastUpdatedByEventId** (String): ID of the last event that updated this projection, used for idempotency and tracking.

### Outbox
Implements the Outbox pattern to ensure reliable event publishing.
- **id** (UUID): Unique identifier for the outbox message.
- **occurredOn** (Instant): Timestamp when the event occurred.
- **aggregateType** (String): Type of the aggregate root (e.g., "Inventory").
- **aggregateId** (String): ID of the aggregate root.
- **type** (String): Type of the event.
- **payload** (String): JSON payload of the event.
- **topic** (String): Kafka topic where the event should be published.

## Usage

To use this library in other modules, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.supermarket</groupId>
    <artifactId>common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
