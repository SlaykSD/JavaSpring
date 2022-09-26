insert into PERSON (PERSON_ID, FULL_NAME, TITLE, AGE)
values (1001, 'default uer', 'reader', 55);

insert into BOOK (BOOK_ID, TITLE, AUTHOR, PAGE_COUNT, USER_ID)
values (2002, 'default book', 'author', 5500, 1001);

insert into BOOK (BOOK_ID, TITLE, AUTHOR, PAGE_COUNT, USER_ID)
values (3003, 'more default book', 'on more author', 6655, 1001);