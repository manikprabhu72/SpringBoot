CREATE TABLE address (
			id serial PRIMARY KEY,
			line1 VARCHAR ( 100 ) NOT NULL,
			line2 VARCHAR ( 100 ),
			city VARCHAR ( 25 ) NOT NULL,
			state VARCHAR ( 25 ) NOT NULL,
			zip_code VARCHAR ( 25 )
		);
CREATE TABLE delivery_method (
			delivery_id serial PRIMARY KEY,
			delivery_type VARCHAR ( 100 ) NOT NULL,
			charge NUMERIC(6,2) NOT NULL
);

CREATE TABLE ecommerce_order (
			order_id serial PRIMARY KEY,
			status VARCHAR ( 50 ),
			customer_id INT NOT NULL,
			sub_total NUMERIC(8,2),
			tax NUMERIC(8,2),			
			total NUMERIC(8,2),
			created_time TIMESTAMP NOT NULL,
			modified_time TIMESTAMP,
			address_id INT,
			delivery_id INT,
			CONSTRAINT order_address
      			FOREIGN KEY(address_id) 
	  				REFERENCES address(id),
	  		CONSTRAINT order_delivery
      			FOREIGN KEY(delivery_id) 
	  				REFERENCES delivery_method(delivery_id)
		);	
CREATE TABLE item (
			item_id serial PRIMARY KEY,
			name VARCHAR ( 100 ),
			price NUMERIC(6,2) NOT NULL,
			quantity SMALLINT NOT NULL,
			order_id INT,
			CONSTRAINT item_order
      			FOREIGN KEY(order_id) 
	  				REFERENCES ecommerce_order(order_id)
		);	
		
CREATE TABLE payment (
			payment_id serial PRIMARY KEY,
			method VARCHAR ( 100 ),
			amount_paid NUMERIC(8,2) NOT NULL,
			payment_date TIMESTAMP,
			order_id INT,
			CONSTRAINT payment_order
      			FOREIGN KEY(order_id) 
	  				REFERENCES ecommerce_order(order_id)
		);	
		
INSERT INTO address (line1, city, state, zip_code) VALUES ('205 Barton Creek Drive Apt B', 'Charlotte', 'NC','28262');
INSERT INTO delivery_method(delivery_type, charge) VALUES ('In-store pickup' , '0.0');
INSERT INTO delivery_method(delivery_type, charge) VALUES ('Curbside delivery' , '3.0');
INSERT INTO delivery_method(delivery_type, charge) VALUES ('Home delivery' , '5.0');
INSERT INTO delivery_method(delivery_type, charge) VALUES ('Third Part delivery' , '8.0');