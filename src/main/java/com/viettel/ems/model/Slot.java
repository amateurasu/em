package com.viettel.ems.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "olt_slot")
public class Slot {
    @Id
    @GeneratedValue
    private long id;

    // @NotNull
    @Column(nullable = false, length = 50)
    private String name;

    // @NotNull
    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "olt_id", referencedColumnName = "id")
    private OLT olt;


    @SuppressWarnings("unused")
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
