package ru.haval.workRecording.accessingdatajpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hmmr_plant_structure", schema = "hmmr_mu", catalog = "hmmr_mu")
public class HmmrPlantStructure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "userId")
  String userId;
  @Column(name = "fl01_company", length = 5)
  String fl01Company = "HAVAL";
  @Column(name = "fl02_plant")
  String fl02Plant = "HMMR";
  @Column(name = "fl03_shop_s")
  String fl03ShopS;
  @Column(name = "fl04_group_s")
  String fl04GroupS;
  @Column(name = "fl05_line_s")
  String fl05LineS;
  @Column(name = "fl06_station_s")
  String fl06StationS;
  @Column(name = "fl07_equipment_s")
  String fl07EquipmentS;
  @Column(name = "fl03_shop_eng")
  String fl03ShopEng;
  @Column(name = "fl04_group_eng", length = 155)
  String fl04GroupEng = "-";
  @Column(name = "fl05_line_eng")
  String fl05LineEng;
  @Column(name = "fl06_station_eng")
  String fl06StationEng;
  @Column(name = "fl07_equipment_eng")
  String fl06EquipmentEng;
  @Column(name = "fl03_shop_rus")
  String fl03ShopRus = "-";
  @Column(name = "fl04_group_rus")
  String fl04GroupRus = "-";
  @Column(name = "fl05_line_rus")
  String fl05LineRus;
  @Column(name = "fl06_station_rus")
  String fl06StationRus;
  @Column(name = "fl07_equipment_rus")
  String fl07EquipmentRus = "-";
  @Column(name = "description_rus")
  String descriptionRus;
  @Column(name = "equip_label")
  String equipLabel;
  @Column(name = "station_label")
  String stationLabel;
  @Column(name = "equipment_folder_link")
  String equipmentFolderLink;
  @Column(name = "resp_planner_group", length = 55)
  String respPlannerGroup = "-";
  @Column(name = "assets_inventory_number", length = 155)
  String assetsInventoryNumber = "-";
  @Column(name = "assets_os1_number")
  String assetsOs1Number = "-";
  @Column(name = "assets_start_up_date")
  LocalDate assetsStartUpDate = LocalDate.of(2018, 10, 10);
  @Column(name = "cost_center", length = 55)
  String costCenter = "-";
  @Column(name = "site_location", length = 45)
  String siteLocation;
  @Column(name = "site_chamber")
  String siteChamber;
  @Column(name = "site_coordinates")
  String siteCoordinates;
  @Column(name = "site_altitude")
  String siteAltitude;
  // `TR_CU` varchar(255) DEFAULT NULL,
  // `TR_CU_Link` varchar(255) DEFAULT NULL,
  // `Hazardous` varchar(25) DEFAULT NULL,
  // `Key_equipment` varchar(45) DEFAULT NULL,
  // `EQ_Integrator` varchar(255) DEFAULT NULL,
  // `EQ_Manufacture` varchar(255) DEFAULT NULL,
  // `EQ_Type` varchar(255) DEFAULT NULL,
  // `EQ_Serial_Number` varchar(255) DEFAULT NULL,
  // `EQ_Technical_Characteristic` varchar(255) DEFAULT NULL,
  // `Responsobility` varchar(5) DEFAULT NULL,
  // `M_Electric` varchar(255) DEFAULT NULL,
  // `M_Air` varchar(255) DEFAULT NULL,
  // `M_Water` varchar(255) DEFAULT NULL,
  // `M_Cold_Water` varchar(255) DEFAULT NULL,
  // `M_Hot_Water` varchar(255) DEFAULT NULL,
  // `M_RO_Water` varchar(255) DEFAULT NULL,
  // `M_Gas` varchar(255) DEFAULT NULL,
  // `Status` int NOT NULL DEFAULT '0',
}
