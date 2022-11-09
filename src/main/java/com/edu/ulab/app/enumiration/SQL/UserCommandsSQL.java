package com.edu.ulab.app.enumiration.SQL;

public enum UserCommandsSQL {
    INSERT_SQL("INSERT INTO ulab_edu.PERSON(PERSON_ID,FULL_NAME, TITLE, AGE) VALUES (nextval('sequence'),?,?,?)"),
    UPDATE_SQL("UPDATE ulab_edu.PERSON SET full_name = ?, title=?, age=? WHERE person_id=?"),
    SELECT_SQL("SELECT * FROM ulab_edu.person WHERE person_id=?"),
    DELETE_SQL("DELETE FROM ulab_edu.person WHERE person_id = ?");

    private final String command;


    UserCommandsSQL(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}
