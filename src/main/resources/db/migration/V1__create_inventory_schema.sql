CREATE TABLE products (id UUID PRIMARY KEY, sku VARCHAR(80) NOT NULL UNIQUE, name VARCHAR(160) NOT NULL CHECK (length(trim(name)) > 0), created_at TIMESTAMPTZ NOT NULL);
CREATE TABLE inventory (product_id UUID PRIMARY KEY REFERENCES products(id) ON DELETE CASCADE, available_quantity INTEGER NOT NULL CHECK (available_quantity >= 0));
CREATE TABLE reservations (id UUID PRIMARY KEY, status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING','CONFIRMED','CANCELLED','EXPIRED')), created_at TIMESTAMPTZ NOT NULL, updated_at TIMESTAMPTZ NOT NULL);
CREATE TABLE reservation_items (id UUID PRIMARY KEY, reservation_id UUID NOT NULL REFERENCES reservations(id) ON DELETE CASCADE, product_id UUID NOT NULL REFERENCES products(id) ON DELETE RESTRICT, quantity INTEGER NOT NULL CHECK (quantity > 0), UNIQUE(reservation_id,product_id));
CREATE INDEX reservation_items_product_idx ON reservation_items(product_id);
CREATE INDEX reservations_status_idx ON reservations(status);
