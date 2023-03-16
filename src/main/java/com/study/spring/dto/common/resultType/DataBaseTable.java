package com.study.spring.dto.common.resultType;

public enum DataBaseTable {
    USER("user"),
    SOCIAL("social"),
    WAIT_ROOM_CARD("waitRoomCard"),
    WAIT_ROOM_COSTUME("waitRoomCostume");

    private String table;

    DataBaseTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public DataBaseTable setTable(String table) {
        this.table = table;
        return this;
    }
}
