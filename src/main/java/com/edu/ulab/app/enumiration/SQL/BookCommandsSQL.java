package com.edu.ulab.app.enumiration.SQL;

public enum BookCommandsSQL {
    INSERT_SQL ("INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)"),
    UPDATE_SQL ("UPDATE BOOK SET title = ?, author=?, page_count=?, user_id=? WHERE book_id=?"),
    SELECT_SQL  ("SELECT * FROM book WHERE book_id=?"),
    SELECT_BY_USER_ID_SQL  ("SELECT * FROM book WHERE user_id=?"),
    DELETE_SQL ("DELETE FROM BOOK WHERE book_id = ?");

    private final String command;


    BookCommandsSQL(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}
