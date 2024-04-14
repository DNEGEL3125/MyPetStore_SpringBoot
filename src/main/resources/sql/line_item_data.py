import decimal
import mysql.connector
from faker import Faker
import random

# Connect to the MySQL database
conn = mysql.connector.connect(
    host="localhost", user="root", password="", database="my_pet_store"
)
cursor = conn.cursor()

# Create a Faker object
fake = Faker()

cursor.execute("SELECT order_id FROM orders")
order_ids = [row[0] for row in cursor.fetchall()]

cursor.execute("SELECT product_id, list_price FROM product")
products = cursor.fetchall()

# Generate and insert data into the child table with valid foreign key references
for order_id in order_ids:  # Generate 10 records as an example
    # Select a random parent_id from existing parent IDs

    selected_products = random.choices(
        products, k=random.randint(1, len(products) // 5)
    )

    total_price = decimal.Decimal(0)

    for product in selected_products:
        line_item_quantity = random.randint(1, 3)

        # SQL statement to insert data into the child table
        sql = "INSERT INTO line_item (order_id, product_id, quantity, unit_price) VALUES (%s, %s, %s, %s)"
        val = (order_id, product[0], line_item_quantity, product[1])
        cursor.execute(sql, val)
        total_price += line_item_quantity * product[1]

    sql = "UPDATE orders SET total_price = %s WHERE order_id = %s"
    val = (total_price, order_id)
    cursor.execute(sql, val)

# Commit changes to the database
conn.commit()

# Close the cursor and connection
cursor.close()
conn.close()

print("Data has been generated and inserted into the database.")
