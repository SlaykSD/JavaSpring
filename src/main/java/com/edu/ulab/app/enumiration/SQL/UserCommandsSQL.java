package com.edu.ulab.app.enumiration.SQL;

public enum UserCommandsSQL {
    INSERT_SQL("INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)"),
    UPDATE_SQL("UPDATE PERSON SET full_name = ?, title=?, age=? WHERE person_id=?"),
    SELECT_SQL("SELECT * FROM person WHERE person_id=?"),
    DELETE_SQL("DELETE FROM person WHERE person_id = ?");

    private final String command;


    UserCommandsSQL(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}
