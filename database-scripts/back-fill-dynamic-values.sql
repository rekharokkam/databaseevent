do $$
declare
    eachRow record;
	generated_short_order_id varchar (255);
	each_id varchar (255);
begin
   for eachRow in (select * from orderdb.orders where order_id like 'COCO-XBAA%') loop
   	each_id := eachRow.id;
    generated_short_order_id := (SELECT floor(random() * (1000000000-1+1) + 1)::int);
	update orderdb.orders set short_order_id = generated_short_order_id where id = each_id;
   end loop;
   raise notice 'short_order_id column is populated with the data';
end; $$`