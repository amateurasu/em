package com.viettel.ems.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "olt_pon_port")
public class Port {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, length = 50)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "olt_slot_id", referencedColumnName = "id")
    private Slot slot;

    @SuppressWarnings("unused")
    enum Status {
        NOT_READY(0),
        INACTIVE(1),
        ACTIVE(2),
        ACTIVATE_PENDING(3),
        DEACTIVATE_PENDING(4),
        SWITCH_OVER_PENDING(5),
        DISABLE(6);

        int code;

        Status(int code) {
            this.code = code;
        }

        static Map<Integer, Status> map = Arrays.stream(values()).collect(Collectors.toMap(e -> e.code, e -> e));

        static Status fromCode(int code) {
            return map.get(code);
        }
    }

    // @Column(name = "layout_x")
    // private double layoutX;

    // @Column(name = "layout_y")
    // private double layoutY;
}
