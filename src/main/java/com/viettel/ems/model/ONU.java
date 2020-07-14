package com.viettel.ems.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "onu")
public class ONU {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    @Enumerated
    private Status status;

    @Column(name = "pon_onu_id")
    private String ponOnuId;

    @ManyToOne
    @JoinColumn(name = "us_splitter_id", referencedColumnName = "id")
    private Splitter splitter;

    @ManyToOne
    @JoinColumn(name = "us_olt_pon_port_id", referencedColumnName = "id")
    private Port ponPort;

    // @Column(name = "onu_profile_id")
    // private long onuProfileId;

    @SuppressWarnings("unused")
    enum Status {
        UNKNOWN(-1),
        INVALID(0),
        INACTIVE(1),
        ACTIVE_PENDING(2),
        ACTIVE(3),
        DEACTIVATE_PENDING(4),
        DISABLE_PENDING(5),
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

    public static void main(String[] args) {
        System.out.println(Status.fromCode(1));
    }
}
