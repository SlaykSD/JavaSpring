package com.edu.ulab.app.enumiration.SQL;

public enum BookCommandsSQL {
    INSERT_SQL ("INSERT INTO ulab_edu.BOOK(BOOK_ID,TITLE, AUTHOR, PAGE_COUNT, PERSON_ID) VALUES (nextval('sequence'),?,?,?,?)"),
    UPDATE_SQL ("UPDATE ulab_edu.BOOK SET title = ?, author=?, page_count=?, person_id=? WHERE book_id=?"),
    SELECT_SQL  ("SELECT * FROM ulab_edu.book WHERE book_id=?"),
    SELECT_BY_USER_ID_SQL  ("SELECT * FROM ulab_edu.book WHERE person_id=?"),
    DELETE_SQL ("DELETE FROM ulab_edu.BOOK WHERE book_id = ?");

    private final String command;


    BookCommandsSQL(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}
