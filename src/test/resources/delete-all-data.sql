SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE review_entity RESTART IDENTITY;
TRUNCATE TABLE product_entity RESTART IDENTITY;
TRUNCATE TABLE image_file_entity RESTART IDENTITY;
SET REFERENTIAL_INTEGRITY TRUE;