package com.viettel.ems.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "olt")
public class OLT {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String ip;

    @Column
    private Status status;

    enum Status {
        UNKNOWN(0), ON(1), OFF(2);

        int code;

        Status(int code) {
            this.code = code;
        }

        static Map<Integer, Status> map = Arrays.stream(values()).collect(Collectors.toMap(e -> e.code, e -> e));

        static Status fromCode(int code) {
            return map.get(code);
        }
    }
}
