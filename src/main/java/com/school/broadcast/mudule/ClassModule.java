package com.school.broadcast.mudule;

import lombok.Data;

@Data
public class ClassModule {
    /** 班级名称 */
    private String name;
    /** 放学状态 0：未放学  1：已放学 */
    private int state;

    public ClassModule(String name, int state){
        this.name = name;
        this.state = state;
    }
}
