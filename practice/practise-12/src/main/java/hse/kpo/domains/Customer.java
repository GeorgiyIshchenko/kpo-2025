package hse.kpo.domains;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.catamarans.Catamaran;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Класс, описывающий покупателя.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int legPower;

    @Column(nullable = false)
    private int handPower;

    @Column(nullable = false)
    private int iq;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catamaran_id")
    private Catamaran catamaran;

    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }

    public static Customer builder() {
        return new Customer();
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public Customer legPower(int legPower) {
        this.legPower = legPower;
        return this;
    }

    public Customer handPower(int handPower) {
        this.handPower = handPower;
        return this;
    }

    public Customer iq(int iq) {
        this.iq = iq;
        return this;
    }

    public Customer build() {
        return new Customer(name, legPower, handPower, iq);
    }

}
