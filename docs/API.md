# API

The live OpenAPI document is `/v3/api-docs`; Swagger UI is `/swagger-ui.html`.

Create a product with `POST /api/products` and `{"sku":"WIDGET-1","name":"Widget"}` (`201`). Set stock with `PUT /api/inventory/{productId}` and `{"quantity":5}` (`200`). Create a reservation with `POST /api/reservations` and `{"items":[{"productId":"<uuid>","quantity":4}]}` (`201`). Confirm or cancel by posting an empty body to `/api/reservations/{id}/confirm` or `/cancel`.

Validation failures return `400`; missing resources return `404`; insufficient inventory and illegal transitions return `409`. A representative error shape is `{"timestamp":"...","status":409,"error":"Conflict","code":"INSUFFICIENT_INVENTORY","message":"...","path":"/api/reservations","details":{}}`. IDs are UUIDs. Product names/SKUs must be nonblank, inventory cannot be negative, reservation quantities must be positive, and item lists cannot be empty.
