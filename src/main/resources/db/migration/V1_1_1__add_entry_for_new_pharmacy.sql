insert into product_entry
(
	catalog_id, pharmacy_product_name, pharmacy_id, pharmacy_product_link
)
select catalog_id, catalog_name as pharmacy_product_name,
(select pharmacy_id from pharmacies where pharmacy_name ='DRUGSTORE'),
'link'|| catalog_id as pharmacy_product_link
from products;
