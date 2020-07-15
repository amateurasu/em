package com.viettel.ems.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "splitter")
public class Splitter {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String status;

    @Column
    private double ratio;

    @ManyToOne
    @JoinColumn(name = "us_splitter_id", referencedColumnName = "id")
    private Splitter splitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_olt_pon_port_id", referencedColumnName = "id")
    private Port port;

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "us_splitter_id")
    private List<ONU> onuList;


    enum Ratio {
        _1_TO_8(8), _1_TO_16(16), _1_TO_32(32), _1_TO_64(64), _1_TO_128(128);

        int size;

        Ratio(int size) {
            this.size = size;
        }

        static Map<Integer, Ratio> map = Arrays.stream(values()).collect(Collectors.toMap(e -> e.size, e -> e));

        static Ratio fromCode(int size) {
            return map.get(size);
        }

        public String toString() {
            return "1:" + size;
        }
    }

    // @Column(name = "layout_x")
    // private double layoutX;

    // @Column(name = "layout_y")
    // private double layoutY;
}
