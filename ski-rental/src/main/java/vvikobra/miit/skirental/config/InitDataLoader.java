package vvikobra.miit.skirental.config;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.models.entities.*;
import vvikobra.miit.skirental.models.enums.*;
import vvikobra.miit.skirental.repositories.Impl.*;

@Component
public class InitDataLoader implements CommandLineRunner {

    private final EquipmentRepositoryImpl equipmentRepository;
    private final EquipmentTypeRepositoryImpl equipmentTypeRepository;
    private final SkillLevelRepositoryImpl skillLevelRepository;
    private final BookingStatusRepositoryImpl bookingStatusRepository;
    private final PaymentStatusRepositoryImpl paymentStatusRepository;
    private final SkipassTypeRepositoryImpl skipassTypeRepository;

    public InitDataLoader(EquipmentRepositoryImpl equipmentRepository,
                          EquipmentTypeRepositoryImpl equipmentTypeRepository,
                          SkillLevelRepositoryImpl skillLevelRepository,
                          BookingStatusRepositoryImpl bookingStatusRepository,
                          PaymentStatusRepositoryImpl paymentStatusRepository,
                          SkipassTypeRepositoryImpl skipassTypeRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentTypeRepository = equipmentTypeRepository;
        this.skillLevelRepository = skillLevelRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.skipassTypeRepository = skipassTypeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initEnums();
        initEquipment();
    }

    private void initEnums() {
        if (bookingStatusRepository.findAll().isEmpty()) {
            bookingStatusRepository.create(new BookingStatus("CREATED"));
            bookingStatusRepository.create(new BookingStatus("PENDING_PAYMENT"));
            bookingStatusRepository.create(new BookingStatus("PAID"));
            bookingStatusRepository.create(new BookingStatus("CANCELLED"));
        }

        // Payment statuses
        if (paymentStatusRepository.findAll().isEmpty()) {
            paymentStatusRepository.create(new PaymentStatus("PENDING"));
            paymentStatusRepository.create(new PaymentStatus("PAID"));
            paymentStatusRepository.create(new PaymentStatus("FAILED"));
        }

        // Skill levels
        if (skillLevelRepository.findAll().isEmpty()) {
            skillLevelRepository.create(new SkillLevel("BEGINNER"));
            skillLevelRepository.create(new SkillLevel("INTERMEDIATE"));
            skillLevelRepository.create(new SkillLevel("ADVANCED"));
        }

        if (equipmentTypeRepository.findAll().isEmpty()) {
            equipmentTypeRepository.create(new EquipmentType("SKI"));
            equipmentTypeRepository.create(new EquipmentType("SNOWBOARD"));
            equipmentTypeRepository.create(new EquipmentType("BOOTS"));
            equipmentTypeRepository.create(new EquipmentType("HELMET"));
        }

        if (skipassTypeRepository.findAll().isEmpty()) {
            skipassTypeRepository.create(new SkipassType("HOURS"));
            skipassTypeRepository.create(new SkipassType("LIFTS"));
        }
    }

    private void initEquipment() {
        if (equipmentRepository.findAll().isEmpty()) {
            EquipmentType ski = equipmentTypeRepository.findByName("SKI").orElseThrow();
            EquipmentType snowboard = equipmentTypeRepository.findByName("SNOWBOARD").orElseThrow();
            EquipmentType boots = equipmentTypeRepository.findByName("BOOTS").orElseThrow();
            EquipmentType helmet = equipmentTypeRepository.findByName("HELMET").orElseThrow();

            SkillLevel beginner = skillLevelRepository.findByName("BEGINNER").orElseThrow();
            SkillLevel intermediate = skillLevelRepository.findByName("INTERMEDIATE").orElseThrow();
            SkillLevel advanced = skillLevelRepository.findByName("ADVANCED").orElseThrow();

            equipmentRepository.create(new Equipment(ski, "Atomic Redster", 150.0, 190.0, 50.0, 100.0, null, beginner, 15.0));
            equipmentRepository.create(new Equipment(ski, "Fischer Pro MTN", 160.0, 200.0, 60.0, 110.0, null, beginner, 12.0));
            equipmentRepository.create(new Equipment(ski, "Rossignol Hero", 170.0, 200.0, 65.0, 120.0, null, intermediate, 18.0));
            equipmentRepository.create(new Equipment(ski, "Head Supershape", 165.0, 195.0, 70.0, 130.0, null, advanced, 20.0));
            equipmentRepository.create(new Equipment(ski, "Salomon XDR", 150.0, 185.0, 55.0, 105.0, null, beginner, 14.0));
            equipmentRepository.create(new Equipment(ski, "Nordica Enforcer", 175.0, 205.0, 75.0, 130.0, null, advanced, 22.0));

            equipmentRepository.create(new Equipment(snowboard, "Burton Custom", 140.0, 180.0, 50.0, 120.0, null, advanced, 20.0));
            equipmentRepository.create(new Equipment(snowboard, "Ride Warpig", 150.0, 190.0, 55.0, 105.0, null, beginner, 18.0));
            equipmentRepository.create(new Equipment(snowboard, "Capita Defenders", 145.0, 185.0, 60.0, 110.0, null, intermediate, 17.0));
            equipmentRepository.create(new Equipment(snowboard, "Jones Mountain Twin", 155.0, 195.0, 65.0, 120.0, null, advanced, 21.0));
            equipmentRepository.create(new Equipment(snowboard, "GNU Riders Choice", 150.0, 190.0, 60.0, 115.0, null, intermediate, 19.0));

            equipmentRepository.create(new Equipment(boots, "Burton Ruler", null, null, null, null, 38, beginner, 10.0));
            equipmentRepository.create(new Equipment(boots, "Burton Ruler", null, null, null, null, 39, beginner, 10.0));
            equipmentRepository.create(new Equipment(boots, "Salomon Dialogue", null, null, null, null, 40, intermediate, 12.0));
            equipmentRepository.create(new Equipment(boots, "Salomon Dialogue", null, null, null, null, 41, intermediate, 12.0));
            equipmentRepository.create(new Equipment(boots, "Head Scout Pro", null, null, null, null, 42, advanced, 13.0));
            equipmentRepository.create(new Equipment(boots, "Head Scout Pro", null, null, null, null, 43, advanced, 13.0));

            equipmentRepository.create(new Equipment(helmet, "POC Auric Cut", null, null, null, null, null, beginner, 8.0));
            equipmentRepository.create(new Equipment(helmet, "Giro Ledge", null, null, null, null, null, intermediate, 9.0));
            equipmentRepository.create(new Equipment(helmet, "Smith Vantage", null, null, null, null, null, advanced, 11.0));
        }
    }
}