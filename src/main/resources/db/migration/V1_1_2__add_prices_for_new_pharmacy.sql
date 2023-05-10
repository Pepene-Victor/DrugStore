insert into product_entry_price
(
	product_entry_id, day, price, in_stock
)
select pe.product_entry_id, CURRENT_DATE as day, s.price as price ,true as in_stock  from
(
	 select pe.catalog_id, round(avg(pee.price)::numeric,2) as price,
    	(select pharmacy_id from pharmacies where pharmacy_name ='DRUGSTORE')
		from
	product_entry pe inner join product_entry_price pee ON pee.product_entry_id = pe.product_entry_id
	where pharmacy_id <> 14
	group by pe.catalog_id
) s inner join product_entry pe on pe.catalog_id = s.catalog_id and pe.pharmacy_id = s.pharmacy_id;


