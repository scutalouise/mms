/*
SQLyog Ultimate v11.27 (64 bit)
MySQL - 5.6.25-log : Database - itam
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`itam` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `itam`;

/*Table structure for table `alarm_condition` */

DROP TABLE IF EXISTS `alarm_condition`;

CREATE TABLE `alarm_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `alarm_device_type` int(11) DEFAULT NULL,
  `alarm_option_type` varchar(255) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `interval_time` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notice_after` int(11) DEFAULT NULL,
  `repeat_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `stay_time` int(11) DEFAULT NULL,
  `alarm_level_id` int(11) DEFAULT NULL,
  `alarm_template_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9069ekgmien2brvmqsf0ryb6t` (`alarm_level_id`),
  KEY `FK_lk9l2v35tok64rflkrxj8j979` (`alarm_template_id`),
  CONSTRAINT `FK_9069ekgmien2brvmqsf0ryb6t` FOREIGN KEY (`alarm_level_id`) REFERENCES `alarm_level` (`id`),
  CONSTRAINT `FK_lk9l2v35tok64rflkrxj8j979` FOREIGN KEY (`alarm_template_id`) REFERENCES `alarm_template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alarm_condition` */

/*Table structure for table `alarm_level` */

DROP TABLE IF EXISTS `alarm_level`;

CREATE TABLE `alarm_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `alarm_sort` int(11) NOT NULL,
  `enabled` int(11) DEFAULT NULL,
  `is_email` int(11) DEFAULT NULL,
  `is_sms` int(11) DEFAULT NULL,
  `is_sound` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alarm_level` */

/*Table structure for table `alarm_log` */

DROP TABLE IF EXISTS `alarm_log`;

CREATE TABLE `alarm_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `collect_time` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `current_state` int(11) DEFAULT NULL,
  `device_interface_type` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `alarm_option_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alarm_log` */

/*Table structure for table `alarm_rule` */

DROP TABLE IF EXISTS `alarm_rule`;

CREATE TABLE `alarm_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `alarm_rule_type` int(11) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `max_value` int(11) DEFAULT NULL,
  `min_value` int(11) DEFAULT NULL,
  `operation_type` int(11) DEFAULT NULL,
  `remark` longtext,
  `status` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `alarm_condition_id` int(11) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6c4axwh2rwhv5rtcrkchif2d9` (`alarm_condition_id`),
  CONSTRAINT `FK_6c4axwh2rwhv5rtcrkchif2d9` FOREIGN KEY (`alarm_condition_id`) REFERENCES `alarm_condition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alarm_rule` */

/*Table structure for table `alarm_template` */

DROP TABLE IF EXISTS `alarm_template`;

CREATE TABLE `alarm_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` longtext,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `alarm_template` */

/*Table structure for table `area_info` */

DROP TABLE IF EXISTS `area_info`;

CREATE TABLE `area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_code` varchar(12) NOT NULL,
  `area_name` varchar(50) NOT NULL,
  `pid` int(11) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `area_info` */

insert  into `area_info`(`id`,`area_code`,`area_name`,`pid`,`sort`) values (1,'cd0001','成都市',0,1);

/*Table structure for table `arrange` */

DROP TABLE IF EXISTS `arrange`;

CREATE TABLE `arrange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `enable` bit(1) NOT NULL,
  `remark` longtext,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `work_day` longtext,
  `work_time` longtext,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `arrange` */

insert  into `arrange`(`id`,`version`,`enable`,`remark`,`status`,`update_time`,`user_id`,`work_day`,`work_time`,`user_name`) values (1,1,'','',1,'2016-02-02 17:01:43',6,'2,4,6','02:00-03:00',NULL),(2,1,'\0','',1,'2016-02-02 17:01:46',1,'2,4,6,1','04:00-09:00',NULL),(3,0,'\0','',0,'2016-02-02 17:02:06',6,'3,5,7','02:00-05:00','超级管理员'),(4,3,'\0','',1,'2016-02-03 10:53:01',14,'1,3,5,2','01:00-02:00','administrator'),(5,0,'','',0,'2016-02-03 10:54:12',1,'2,3,4,6,5','09:00-17:00','admin');

/*Table structure for table `assets` */

DROP TABLE IF EXISTS `assets`;

CREATE TABLE `assets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assets_purchase_id` int(11) NOT NULL,
  `authorization_code` varchar(200) DEFAULT NULL,
  `device_type` int(11) NOT NULL,
  `factory_date` date DEFAULT NULL,
  `function` varchar(100) DEFAULT NULL,
  `identifier` varchar(32) NOT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `is_in_use` int(11) DEFAULT NULL,
  `is_pda` bit(1) NOT NULL,
  `maintenance_org_id` int(11) DEFAULT NULL,
  `mange_user_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `org_id` int(11) NOT NULL,
  `other_note` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `real_use_date` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `warranty_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assets` */

/*Table structure for table `assets_change` */

DROP TABLE IF EXISTS `assets_change`;

CREATE TABLE `assets_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_content` varchar(200) DEFAULT NULL,
  `change_time` datetime NOT NULL,
  `change_user_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `is_new` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assets_change` */

/*Table structure for table `assets_purchase` */

DROP TABLE IF EXISTS `assets_purchase`;

CREATE TABLE `assets_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_type` int(11) DEFAULT NULL,
  `factory` varchar(100) DEFAULT NULL,
  `factory_address` varchar(200) DEFAULT NULL,
  `factory_date` date DEFAULT NULL,
  `is_purchase` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `other_note` varchar(200) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `warranty_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assets_purchase` */

/*Table structure for table `assets_type` */

DROP TABLE IF EXISTS `assets_type`;

CREATE TABLE `assets_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desciption` varchar(255) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assets_type` */

/*Table structure for table `authority_transmit` */

DROP TABLE IF EXISTS `authority_transmit`;

CREATE TABLE `authority_transmit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_user_id` int(11) NOT NULL,
  `cancel_transmit_time` datetime DEFAULT NULL,
  `is_transmit` int(11) NOT NULL,
  `transmit_roles` varchar(255) DEFAULT NULL,
  `transmit_time` datetime NOT NULL,
  `transmit_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `authority_transmit` */

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `first_device_type` int(11) DEFAULT NULL,
  `is_initial` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `other_note` varchar(255) DEFAULT NULL,
  `second_device_type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `brand` */

insert  into `brand`(`id`,`version`,`enable`,`first_device_type`,`is_initial`,`name`,`other_note`,`second_device_type`,`status`,`update_time`) values (1,0,0,1,1,'联想','',0,0,'2016-01-15 11:08:16'),(2,0,0,1,1,'戴尔','',2,0,'2016-01-15 11:08:27');

/*Table structure for table `collection_device` */

DROP TABLE IF EXISTS `collection_device`;

CREATE TABLE `collection_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `manufacture_date` datetime DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `server_state` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_device_type_id` int(11) NOT NULL,
  `warranty_date` datetime DEFAULT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `collection_device` */

/*Table structure for table `device_change` */

DROP TABLE IF EXISTS `device_change`;

CREATE TABLE `device_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `change_content` varchar(255) DEFAULT NULL,
  `change_time` datetime DEFAULT NULL,
  `change_user_id` int(11) NOT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `is_new` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `device_change` */

/*Table structure for table `device_inspect_status` */

DROP TABLE IF EXISTS `device_inspect_status`;

CREATE TABLE `device_inspect_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inspect_device_status` varchar(20) NOT NULL,
  `inspect_record_id` int(11) NOT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `device_id` int(11) NOT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `device_inspect_status` */

insert  into `device_inspect_status`(`id`,`inspect_device_status`,`inspect_record_id`,`identifier`,`device_id`,`device_type`) values (5,'已巡检',4,'051512211205007',0,NULL),(6,'未巡检',4,'051512211205021',0,NULL),(7,'未巡检',4,'051512211205138',0,NULL),(8,'非网点',4,'011512211205004',0,NULL),(9,'已巡检',6,'051512211205007',0,NULL),(10,'未巡检',6,'051512211205021',0,NULL),(11,'未巡检',6,'051512211205138',0,NULL),(12,'非网点',6,'011512211205004',0,NULL),(13,'已巡检',7,'051512211205007',0,NULL),(14,'未巡检',7,'051512211205021',0,NULL),(15,'未巡检',7,'051512211205138',0,NULL),(16,'非网点',7,'011512211205004',0,NULL),(17,'已巡检',8,'051512211205007',0,NULL),(18,'未巡检',8,'051512211205021',0,NULL),(19,'未巡检',8,'051512211205138',0,NULL),(20,'非网点',8,'011512211205004',0,NULL),(21,'已巡检',9,'051512211205007',0,NULL),(22,'未巡检',9,'051512211205021',0,NULL),(23,'未巡检',9,'051512211205138',0,NULL),(24,'非网点',9,'011512211205004',0,NULL),(25,'已巡检',10,'051512211205007',0,NULL),(26,'未巡检',10,'051512211205021',0,NULL),(27,'未巡检',10,'051512211205138',0,NULL),(28,'非网点',10,'011512211205004',0,NULL),(29,'已巡检',11,'051512211205007',0,NULL),(30,'未巡检',11,'051512211205021',0,NULL),(31,'未巡检',11,'051512211205138',0,NULL),(32,'非网点',11,'011512211205004',0,NULL);

/*Table structure for table `device_inventory` */

DROP TABLE IF EXISTS `device_inventory`;

CREATE TABLE `device_inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `brand_id` int(11) NOT NULL,
  `first_device_type` int(11) DEFAULT NULL,
  `free_quantity` int(11) NOT NULL,
  `other_note` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `scrap_quantity` int(11) NOT NULL,
  `second_device_type` int(11) DEFAULT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `device_inventory` */

insert  into `device_inventory`(`id`,`version`,`brand_id`,`first_device_type`,`free_quantity`,`other_note`,`quantity`,`scrap_quantity`,`second_device_type`,`brand_name`) values (1,0,1,1,4,'',4,0,0,'联想');

/*Table structure for table `device_purchase` */

DROP TABLE IF EXISTS `device_purchase`;

CREATE TABLE `device_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `device_inventory_id` int(11) NOT NULL,
  `is_purchase` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `org_id` int(11) NOT NULL,
  `other_note` varchar(255) DEFAULT NULL,
  `purchase_date` datetime DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `warranty_date` datetime DEFAULT NULL,
  `org_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `device_purchase` */

insert  into `device_purchase`(`id`,`version`,`device_inventory_id`,`is_purchase`,`name`,`org_id`,`other_note`,`purchase_date`,`quantity`,`update_time`,`warranty_date`,`org_name`) values (1,0,1,1,'abcdefg',1,'','2016-01-15 00:00:00',4,'2016-01-15 11:14:17','2017-01-25 00:00:00','申控测试点');

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `del_flag` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dict` */

/*Table structure for table `dimention_code` */

DROP TABLE IF EXISTS `dimention_code`;

CREATE TABLE `dimention_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `device_type` int(11) NOT NULL,
  `is_print` int(11) DEFAULT NULL,
  `last_print_time` date NOT NULL,
  `print_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dimention_code` */

/*Table structure for table `discharge_statistic` */

DROP TABLE IF EXISTS `discharge_statistic`;

CREATE TABLE `discharge_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `batch` bigint(20) DEFAULT NULL,
  `capacity` double DEFAULT NULL,
  `collect_time` datetime DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  `device_load` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `discharge_statistic` */

/*Table structure for table `host_device` */

DROP TABLE IF EXISTS `host_device`;

CREATE TABLE `host_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `alarm_template_id` int(11) DEFAULT NULL,
  `alarm_template_name` varchar(255) DEFAULT NULL,
  `authorization_code` varchar(255) DEFAULT NULL,
  `cpu` varchar(255) DEFAULT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `harddisk` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `manager_name` varchar(255) DEFAULT NULL,
  `manufacture_date` date DEFAULT NULL,
  `memory` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `motherboard` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `osbits` int(11) DEFAULT NULL,
  `ostype` int(11) DEFAULT NULL,
  `organization_id` int(11) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `purchase_id` int(11) DEFAULT NULL,
  `purchase_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_device_type_id` int(11) DEFAULT NULL,
  `user_device_type_name` varchar(255) DEFAULT NULL,
  `warranty_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `host_device` */

insert  into `host_device`(`id`,`version`,`alarm_template_id`,`alarm_template_name`,`authorization_code`,`cpu`,`current_state`,`enable`,`harddisk`,`identifier`,`ip`,`manager_id`,`manager_name`,`manufacture_date`,`memory`,`model`,`motherboard`,`name`,`osbits`,`ostype`,`organization_id`,`organization_name`,`purchase_id`,`purchase_name`,`remark`,`role_id`,`role_name`,`status`,`update_time`,`user_device_type_id`,`user_device_type_name`,`warranty_date`) values (1,5,NULL,NULL,'','',NULL,0,'','021601131752687','',NULL,NULL,'2016-01-20','','pc','','申控测试机',32,1,1,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-18 14:04:12',NULL,NULL,'2016-01-22'),(2,4,NULL,NULL,'','',NULL,0,'','021601131753480','',NULL,NULL,'2016-01-27','','android','','外包测试机',32,0,4,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-29 09:15:26',NULL,NULL,'2016-01-20'),(3,5,NULL,NULL,'','',NULL,0,'','021601151046102','',NULL,NULL,'2016-01-12','','1100','','长江一号',32,0,1,NULL,1,'abcdefg','',1,NULL,0,'2016-01-18 14:46:03',NULL,NULL,'2016-01-22'),(4,3,NULL,NULL,'','',NULL,0,'','021601151117691','',NULL,NULL,'2016-01-19','','2200','','长江二号',32,1,1,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-18 14:46:14',NULL,NULL,'2016-01-20'),(5,2,NULL,NULL,'','',NULL,0,'','021601151118194','',NULL,NULL,'2016-01-24','','3300','','长江三号',32,1,1,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-18 14:46:24',NULL,NULL,'2016-01-14'),(6,7,NULL,NULL,'','',NULL,0,'','021601151120315','',NULL,NULL,'2016-01-21','','4400','','长江四号',32,1,4,NULL,1,'abcdefg','',1,NULL,0,'2016-01-29 09:15:35',NULL,NULL,'2016-01-13'),(7,3,NULL,NULL,'','',NULL,0,'','021601151248970','',NULL,NULL,'2016-01-20','','5500','','长江五号',32,1,4,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-29 09:15:40',NULL,NULL,'2016-01-21'),(8,2,NULL,NULL,'','',NULL,0,'','021601191035749','',NULL,NULL,NULL,'','','','01',32,1,4,NULL,1,'abcdefg','',1,NULL,0,'2016-01-29 09:13:37',NULL,NULL,NULL),(9,1,NULL,NULL,'','',NULL,0,'','021601191035827','',NULL,NULL,NULL,'','','','02',32,1,4,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-29 09:13:47',NULL,NULL,NULL),(10,1,NULL,NULL,'','',NULL,0,'','021601191035059','',NULL,NULL,NULL,'','','','03',32,1,4,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-29 09:14:01',NULL,NULL,NULL),(11,1,NULL,NULL,'','',NULL,0,'','021601191036857','',NULL,NULL,NULL,'','','','04',32,1,4,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-29 09:14:12',NULL,NULL,NULL),(12,0,NULL,NULL,'','',NULL,0,'','021601191036599','',NULL,NULL,NULL,'','','','05',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:36:13',NULL,NULL,NULL),(13,0,NULL,NULL,'','',NULL,0,'','021601191036914','',NULL,NULL,NULL,'','','','06',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:36:23',NULL,NULL,NULL),(14,0,NULL,NULL,'','',NULL,0,'','021601191036757','',NULL,NULL,NULL,'','','','07',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:36:40',NULL,NULL,NULL),(15,0,NULL,NULL,'','',NULL,0,'','021601191036608','',NULL,NULL,NULL,'','','','08',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:36:50',NULL,NULL,NULL),(16,0,NULL,NULL,'','',NULL,0,'','021601191037322','',NULL,NULL,NULL,'','','','09',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:37:01',NULL,NULL,NULL),(17,0,NULL,NULL,'','',NULL,0,'','021601191037110','',NULL,NULL,NULL,'','','','10',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:37:12',NULL,NULL,NULL),(18,0,NULL,NULL,'','',NULL,0,'','021601191037344','',NULL,NULL,NULL,'','','','11',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:37:25',NULL,NULL,NULL),(19,0,NULL,NULL,'','',NULL,0,'','021601191037324','',NULL,NULL,NULL,'','','','12',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:37:41',NULL,NULL,NULL),(20,0,NULL,NULL,'','',NULL,0,'','021601191037317','',NULL,NULL,NULL,'','','','13',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:37:54',NULL,NULL,NULL),(21,0,NULL,NULL,'','',NULL,0,'','021601191038739','',NULL,NULL,NULL,'','','','14',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:38:23',NULL,NULL,NULL),(22,0,NULL,NULL,'','',NULL,0,'','021601191038552','',NULL,NULL,NULL,'','','','15',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:38:41',NULL,NULL,NULL),(23,0,NULL,NULL,'','',NULL,0,'','021601191038333','',NULL,NULL,NULL,'','','','16',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:38:51',NULL,NULL,NULL),(24,0,NULL,NULL,'','',NULL,0,'','021601191039280','',NULL,NULL,NULL,'','','','17',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:39:06',NULL,NULL,NULL),(25,0,NULL,NULL,'','',NULL,0,'','021601191039363','',NULL,NULL,NULL,'','','','18',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:39:17',NULL,NULL,NULL),(26,0,NULL,NULL,'','',NULL,0,'','021601191039801','',NULL,NULL,NULL,'','','','19',32,1,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:39:29',NULL,NULL,NULL),(27,0,NULL,NULL,'','',NULL,0,'','021601191039683','',NULL,NULL,NULL,'','','','20',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:39:40',NULL,NULL,NULL),(28,0,NULL,NULL,'','',NULL,0,'','021601191039019','',NULL,NULL,NULL,'','','','21',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:39:51',NULL,NULL,NULL),(29,0,NULL,NULL,'','',NULL,0,'','021601191040612','',NULL,NULL,NULL,'','','','22',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:40:03',NULL,NULL,NULL),(30,0,NULL,NULL,'','',NULL,0,'','021601191040668','',NULL,NULL,NULL,'','','','23',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:40:15',NULL,NULL,NULL),(31,0,NULL,NULL,'','',NULL,0,'','021601191040216','',NULL,NULL,NULL,'','','','24',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:40:25',NULL,NULL,NULL),(32,0,NULL,NULL,'','',NULL,0,'','021601191040291','',NULL,NULL,NULL,'','','','25',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:40:38',NULL,NULL,NULL),(33,0,NULL,NULL,'','',NULL,0,'','021601191040902','',NULL,NULL,NULL,'','','','26',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:40:53',NULL,NULL,NULL),(34,0,NULL,NULL,'','',NULL,0,'','021601191041545','',NULL,NULL,NULL,'','','','27',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:41:04',NULL,NULL,NULL),(35,0,NULL,NULL,'','',NULL,0,'','021601191041378','',NULL,NULL,NULL,'','','','28',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:41:14',NULL,NULL,NULL),(36,0,NULL,NULL,'','',NULL,0,'','021601191041952','',NULL,NULL,NULL,'','','','29',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:41:25',NULL,NULL,NULL),(37,0,NULL,NULL,'','',NULL,0,'','021601191041873','',NULL,NULL,NULL,'','','','30',32,0,2,NULL,1,'abcdefg','',NULL,NULL,0,'2016-01-19 10:41:38',NULL,NULL,NULL),(38,0,NULL,NULL,'','',NULL,0,'','021601221345843','',NULL,NULL,'2016-01-19','','','','演示一号',32,0,3,NULL,1,NULL,'',NULL,NULL,0,'2016-01-22 13:45:33',NULL,NULL,'2016-01-20'),(39,0,NULL,NULL,'','',NULL,0,'','021601221347423','',NULL,NULL,'2016-01-18','','','','演示二号',32,1,3,NULL,1,NULL,'',NULL,NULL,0,'2016-01-22 13:47:59',NULL,NULL,'2016-01-29'),(40,0,NULL,NULL,'','',NULL,0,'','021601221348012','',NULL,NULL,'2016-01-06','','','','演示三号',32,2,3,NULL,1,NULL,'',NULL,NULL,0,'2016-01-22 13:48:39',NULL,NULL,'2016-01-28');

/*Table structure for table `inspect_record` */

DROP TABLE IF EXISTS `inspect_record`;

CREATE TABLE `inspect_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_total` int(11) NOT NULL,
  `inspect_time` date NOT NULL,
  `inspect_status` varchar(20) NOT NULL,
  `inspected_total` int(11) NOT NULL,
  `org_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `inexistend_total` int(11) NOT NULL,
  `unchecked_total` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `inspect_record` */

insert  into `inspect_record`(`id`,`device_total`,`inspect_time`,`inspect_status`,`inspected_total`,`org_id`,`user_id`,`inexistend_total`,`unchecked_total`,`status`) values (4,3,'2016-01-11','不合格',1,1,1,1,2,NULL),(6,3,'2016-01-14','不合格',1,1,1,1,2,NULL),(7,3,'2016-01-14','不合格',1,1,1,1,2,NULL),(8,3,'2016-01-14','不合格',1,1,1,1,2,NULL),(9,3,'2016-01-14','不合格',1,1,1,1,2,NULL),(10,3,'2016-01-14','不合格',1,1,1,1,2,NULL),(11,3,'2016-01-14','不合格',1,1,1,1,2,NULL);

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `browser` varchar(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `creater` varchar(20) DEFAULT NULL,
  `description` longtext,
  `execute_time` int(11) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `mac` varchar(20) DEFAULT NULL,
  `operation_code` varchar(50) NOT NULL,
  `os` varchar(20) DEFAULT NULL,
  `request_param` longtext,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8;

/*Data for the table `log` */

insert  into `log`(`id`,`browser`,`create_date`,`creater`,`description`,`execute_time`,`ip`,`mac`,`operation_code`,`os`,`request_param`,`type`) values (1,'Chrome','2016-01-13 17:03:38','admin',NULL,111,'127.0.0.1',NULL,'/system/area/create','Windows 7','{\"id\":[\"\"],\"areaName\":[\"达州市\"],\"areaCode\":[\"32856\"],\"pid\":[\"\"]}',NULL),(2,'Chrome','2016-01-13 17:08:05','admin',NULL,17,'127.0.0.1',NULL,'/system/organization/create','Windows 7','{\"id\":[\"\"],\"orgName\":[\"达州市农商银行\"],\"pid\":[\"\"],\"areaId\":[\"1\"],\"orgCode\":[\"DZ2857\"],\"orgType\":[\"银行\"],\"orgLevel\":[\"1\"],\"orgSort\":[\"1\"],\"longitude\":[\"1234\"],\"latitude\":[\"1234\"]}',NULL),(3,'Chrome','2016-01-13 17:08:50','admin',NULL,14,'127.0.0.1',NULL,'/system/area/update','Windows 7','{\"id\":[\"1\"],\"areaName\":[\"成都市\"],\"areaCode\":[\"123456\"],\"pid\":[\"0\"]}',NULL),(4,'Chrome','2016-01-13 17:10:18','admin',NULL,10,'127.0.0.1',NULL,'/system/organization/update','Windows 7','{\"id\":[\"1\"],\"orgName\":[\"申控测试点\"],\"pid\":[\"\"],\"areaId\":[\"1\"],\"orgCode\":[\"SK0001\"],\"orgType\":[\"服务端\"],\"orgLevel\":[\"1\"],\"orgSort\":[\"1\"],\"longitude\":[\"104.047595\"],\"latitude\":[\"30.645509\"]}',NULL),(5,'Chrome','2016-01-13 17:11:29','admin',NULL,9,'127.0.0.1',NULL,'/system/organization/create','Windows 7','{\"id\":[\"\"],\"orgName\":[\"外包手机开发点\"],\"pid\":[\"\"],\"areaId\":[\"1\"],\"orgCode\":[\"WB0001\"],\"orgType\":[\"1\"],\"orgLevel\":[\"1\"],\"orgSort\":[\"1\"],\"longitude\":[\"104.016357\"],\"latitude\":[\"30.576263\"]}',NULL),(6,'Chrome','2016-01-13 17:44:48','admin',NULL,34,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"主机\"],\"url\":[\"device/details\"],\"icon\":[\"icon-hamburg-graphic\"],\"pid\":[\"78\"],\"sort\":[\"1\"],\"code\":[\"DS\"],\"description\":[\"\"]}',NULL),(7,'Chrome','2016-01-13 17:46:07','admin',NULL,14,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"152\"],\"type\":[\"F\"],\"name\":[\"主机\"],\"url\":[\"device/details\"],\"icon\":[\"icon-hamburg-graphic\"],\"pid\":[\"78\"],\"sort\":[\"1\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(8,'Chrome','2016-01-13 17:49:46','admin',NULL,91,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"申控测试机\"],\"authorizationCode\":[\"asdf\"],\"ip\":[\"asdf\"],\"model\":[\"asdf\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"asdf\"],\"harddisk\":[\"asdf\"],\"motherboard\":[\"asdf\"],\"memory\":[\"sdf\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asd\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(9,'Chrome','2016-01-13 17:52:30','admin',NULL,84,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"asdf\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(10,'Chrome','2016-01-13 17:53:03','admin',NULL,10,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"外包测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"阿斯蒂芬\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(11,'Chrome','2016-01-14 10:01:14','admin',NULL,53,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"1\"],\"version\":[\"0\"],\"name\":[\"申控测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(12,'Chrome','2016-01-15 10:46:22','admin',NULL,177,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"长江一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(13,'Chrome','2016-01-15 10:50:51','admin',NULL,103,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"品牌管理\"],\"url\":[\"device/brand\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"78\"],\"sort\":[\"\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(14,'Chrome','2016-01-15 10:52:06','admin',NULL,26,'127.0.0.1',NULL,'/system/permission/delete/79','Windows 7','{\"_\":[\"1452826070231\"]}',NULL),(15,'Chrome','2016-01-15 10:52:10','admin',NULL,22,'127.0.0.1',NULL,'/system/permission/delete/82','Windows 7','{\"_\":[\"1452826070233\"]}',NULL),(16,'Chrome','2016-01-15 10:52:13','admin',NULL,18,'127.0.0.1',NULL,'/system/permission/delete/109','Windows 7','{\"_\":[\"1452826070235\"]}',NULL),(17,'Chrome','2016-01-15 10:53:50','admin',NULL,76,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"设备类型\"],\"url\":[\"device/userDeviceType\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"78\"],\"sort\":[\"\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(18,'Chrome','2016-01-15 10:54:19','admin',NULL,12,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"采购管理\"],\"url\":[\"device/devicePurchase\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"78\"],\"sort\":[\"\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(19,'Chrome','2016-01-15 10:55:39','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"采购汇总\"],\"url\":[\"device/deviceInventory\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"78\"],\"sort\":[\"\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(20,'Chrome','2016-01-15 10:58:00','admin',NULL,15,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"152\"],\"type\":[\"F\"],\"name\":[\"设备详情\"],\"url\":[\"device/details\"],\"icon\":[\"icon-hamburg-graphic\"],\"pid\":[\"78\"],\"sort\":[\"1\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(21,'Chrome','2016-01-15 11:02:30','admin',NULL,58,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"新增\"],\"permCode\":[\"device:brand:add\"],\"url\":[\"\"],\"pid\":[\"153\"],\"description\":[\"\"]}',NULL),(22,'Chrome','2016-01-15 11:02:51','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"删除\"],\"permCode\":[\"device:brand:delete\"],\"url\":[\"\"],\"pid\":[\"153\"],\"description\":[\"\"]}',NULL),(23,'Chrome','2016-01-15 11:03:09','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"修改\"],\"permCode\":[\"device:brand:update\"],\"url\":[\"\"],\"pid\":[\"153\"],\"description\":[\"\"]}',NULL),(24,'Chrome','2016-01-15 11:03:25','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"查看\"],\"permCode\":[\"device:brand:view\"],\"url\":[\"\"],\"pid\":[\"153\"],\"description\":[\"\"]}',NULL),(25,'Chrome','2016-01-15 11:03:50','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"device:userDeviceType:add\"],\"url\":[\"\"],\"pid\":[\"154\"],\"description\":[\"\"]}',NULL),(26,'Chrome','2016-01-15 11:04:12','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"del\"],\"permCode\":[\"device:userDeviceType:delete\"],\"url\":[\"\"],\"pid\":[\"154\"],\"description\":[\"\"]}',NULL),(27,'Chrome','2016-01-15 11:04:29','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"device:userDeviceType:update\"],\"url\":[\"\"],\"pid\":[\"154\"],\"description\":[\"\"]}',NULL),(28,'Chrome','2016-01-15 11:04:48','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"device:userDeviceType:view\"],\"url\":[\"\"],\"pid\":[\"154\"],\"description\":[\"\"]}',NULL),(29,'Chrome','2016-01-15 11:05:22','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"device:devicePurchase:add\"],\"url\":[\"\"],\"pid\":[\"155\"],\"description\":[\"\"]}',NULL),(30,'Chrome','2016-01-15 11:05:44','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"device:devicePurchase:update\"],\"url\":[\"\"],\"pid\":[\"155\"],\"description\":[\"\"]}',NULL),(31,'Chrome','2016-01-15 11:05:55','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"device:devicePurchase:view\"],\"url\":[\"\"],\"pid\":[\"155\"],\"description\":[\"\"]}',NULL),(32,'Chrome','2016-01-15 11:06:17','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"device:deviceInventory:view\"],\"url\":[\"\"],\"pid\":[\"156\"],\"description\":[\"\"]}',NULL),(33,'Chrome','2016-01-15 11:07:21','admin',NULL,93,'127.0.0.1',NULL,'/device/brand/add','Windows 7','{\"_\":[\"1452827228898\"]}',NULL),(34,'Chrome','2016-01-15 11:08:16','admin',NULL,13,'127.0.0.1',NULL,'/device/brand/add','Windows 7','{\"firstDeviceType\":[\"HOSTDEVICE\"],\"secondDeviceType\":[\"TERMINAL\"],\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"联想\"],\"enable\":[\"ENABLED\"],\"isInitial\":[\"UNINIT\"],\"otherNote\":[\"\"]}',NULL),(35,'Chrome','2016-01-15 11:08:18','admin',NULL,2,'127.0.0.1',NULL,'/device/brand/add','Windows 7','{\"_\":[\"1452827228903\"]}',NULL),(36,'Chrome','2016-01-15 11:08:27','admin',NULL,10,'127.0.0.1',NULL,'/device/brand/add','Windows 7','{\"firstDeviceType\":[\"HOSTDEVICE\"],\"secondDeviceType\":[\"SERVER\"],\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"戴尔\"],\"enable\":[\"ENABLED\"],\"isInitial\":[\"UNINIT\"],\"otherNote\":[\"\"]}',NULL),(37,'Chrome','2016-01-15 11:08:32','admin',NULL,85,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1452827310490\"]}',NULL),(38,'Chrome','2016-01-15 11:11:56','admin',NULL,3,'127.0.0.1',NULL,'/device/brand/add','Windows 7','{\"_\":[\"1452827228908\"]}',NULL),(39,'Chrome','2016-01-15 11:12:43','admin',NULL,12,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"154\"],\"type\":[\"F\"],\"name\":[\"产品类型划分\"],\"url\":[\"device/userDeviceType\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"78\"],\"sort\":[\"\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(40,'Chrome','2016-01-15 11:12:55','admin',NULL,97,'127.0.0.1',NULL,'/device/devicePurchase/add','Windows 7','{\"_\":[\"1452827573606\"]}',NULL),(41,'Chrome','2016-01-15 11:14:17','admin',NULL,110,'127.0.0.1',NULL,'/device/devicePurchase/add','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"abcdefg\"],\"quantity\":[\"4\"],\"firstDeviceType\":[\"HOSTDEVICE\"],\"secondDeviceType\":[\"TERMINAL\"],\"brandName\":[\"联想\"],\"brandId\":[\"1\"],\"orgName\":[\"申控测试点\"],\"orgId\":[\"1\"],\"purchaseDate\":[\"2016-01-15\"],\"warrantyDate\":[\"2017-01-25\"],\"isPurchase\":[\"1\"],\"otherNote\":[\"\"]}',NULL),(42,'Chrome','2016-01-15 11:16:52','admin',NULL,37,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"3\"],\"version\":[\"0\"],\"name\":[\"长江一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(43,'Chrome','2016-01-15 11:17:15','admin',NULL,10,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"长江二号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(44,'Chrome','2016-01-15 11:18:55','admin',NULL,11,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"长江三号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(45,'Chrome','2016-01-15 11:20:11','admin',NULL,10,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(46,'Chrome','2016-01-15 11:37:45','admin',NULL,57,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"6\"],\"version\":[\"0\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(47,'Chrome','2016-01-15 12:48:10','admin',NULL,86,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"长江五号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(48,'Chrome','2016-01-18 13:58:06','admin',NULL,28,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"3\"],\"version\":[\"1\"],\"name\":[\"长江一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"S5700\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-12\"],\"warrantyDate\":[\"2016-01-22\"],\"remark\":[\"\"]}',NULL),(49,'Chrome','2016-01-18 13:58:29','admin',NULL,18,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"4\"],\"version\":[\"0\"],\"name\":[\"长江二号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"S5700\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-19\"],\"warrantyDate\":[\"2016-01-20\"],\"remark\":[\"\"]}',NULL),(50,'Chrome','2016-01-18 14:00:40','admin',NULL,18,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"3\"],\"version\":[\"2\"],\"name\":[\"长江一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"1100\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-12\"],\"warrantyDate\":[\"2016-1-22\"],\"remark\":[\"\"]}',NULL),(51,'Chrome','2016-01-18 14:00:46','admin',NULL,12,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"4\"],\"version\":[\"1\"],\"name\":[\"长江二号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"2200\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-19\"],\"warrantyDate\":[\"2016-1-20\"],\"remark\":[\"\"]}',NULL),(52,'Chrome','2016-01-18 14:00:55','admin',NULL,16,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"5\"],\"version\":[\"0\"],\"name\":[\"长江三号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"3300\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-24\"],\"warrantyDate\":[\"2016-01-14\"],\"remark\":[\"\"]}',NULL),(53,'Chrome','2016-01-18 14:01:01','admin',NULL,17,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"6\"],\"version\":[\"1\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"4400\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(54,'Chrome','2016-01-18 14:01:11','admin',NULL,18,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"6\"],\"version\":[\"2\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"4400\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-21\"],\"warrantyDate\":[\"2016-01-13\"],\"remark\":[\"\"]}',NULL),(55,'Chrome','2016-01-18 14:01:21','admin',NULL,14,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"7\"],\"version\":[\"0\"],\"name\":[\"长江五号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"5500\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-20\"],\"warrantyDate\":[\"2016-01-21\"],\"remark\":[\"\"]}',NULL),(56,'Chrome','2016-01-18 14:02:33','admin',NULL,15,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"1\"],\"version\":[\"1\"],\"name\":[\"申控测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"pc\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(57,'Chrome','2016-01-18 14:02:42','admin',NULL,16,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"1\"],\"version\":[\"2\"],\"name\":[\"申控测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"pc\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"asdf\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-20\"],\"warrantyDate\":[\"2016-01-22\"],\"remark\":[\"\"]}',NULL),(58,'Chrome','2016-01-18 14:02:53','admin',NULL,15,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"2\"],\"version\":[\"0\"],\"name\":[\"外包测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"android\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"\"],\"purchaseName\":[\"阿斯蒂芬\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-27\"],\"warrantyDate\":[\"2016-01-20\"],\"remark\":[\"\"]}',NULL),(59,'Chrome','2016-01-18 14:04:12','admin',NULL,13,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"1\"],\"version\":[\"3\"],\"name\":[\"申控测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"pc\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-20\"],\"warrantyDate\":[\"2016-1-22\"],\"remark\":[\"\"]}',NULL),(60,'Chrome','2016-01-18 14:04:22','admin',NULL,15,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"2\"],\"version\":[\"1\"],\"name\":[\"外包测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"android\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-27\"],\"warrantyDate\":[\"2016-1-20\"],\"remark\":[\"\"]}',NULL),(61,'Chrome','2016-01-18 14:46:03','admin',NULL,60,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"3\"],\"version\":[\"3\"],\"name\":[\"长江一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"1100\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-12\"],\"warrantyDate\":[\"2016-1-22\"],\"remark\":[\"\"]}',NULL),(62,'Chrome','2016-01-18 14:46:14','admin',NULL,15,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"4\"],\"version\":[\"2\"],\"name\":[\"长江二号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"2200\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-19\"],\"warrantyDate\":[\"2016-1-20\"],\"remark\":[\"\"]}',NULL),(63,'Chrome','2016-01-18 14:46:24','admin',NULL,16,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"5\"],\"version\":[\"1\"],\"name\":[\"长江三号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"3300\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"1\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-24\"],\"warrantyDate\":[\"2016-1-14\"],\"remark\":[\"\"]}',NULL),(64,'Chrome','2016-01-18 14:46:36','admin',NULL,16,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"6\"],\"version\":[\"3\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"4400\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-21\"],\"warrantyDate\":[\"2016-1-13\"],\"remark\":[\"\"]}',NULL),(65,'Chrome','2016-01-18 14:46:54','admin',NULL,16,'127.0.0.1',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"7\"],\"version\":[\"1\"],\"name\":[\"长江五号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"5500\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-20\"],\"warrantyDate\":[\"2016-1-21\"],\"remark\":[\"\"]}',NULL),(66,'Chrome','2016-01-19 10:35:01','admin',NULL,197,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"01\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(67,'Chrome','2016-01-19 10:35:33','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"02\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(68,'Chrome','2016-01-19 10:35:46','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"03\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(69,'Chrome','2016-01-19 10:36:01','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"04\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(70,'Chrome','2016-01-19 10:36:13','admin',NULL,20,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"05\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(71,'Chrome','2016-01-19 10:36:23','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"06\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(72,'Chrome','2016-01-19 10:36:40','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"07\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(73,'Chrome','2016-01-19 10:36:50','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"08\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(74,'Chrome','2016-01-19 10:37:01','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"09\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(75,'Chrome','2016-01-19 10:37:12','admin',NULL,12,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"10\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(76,'Chrome','2016-01-19 10:37:25','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"11\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(77,'Chrome','2016-01-19 10:37:41','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"12\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(78,'Chrome','2016-01-19 10:37:54','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"13\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(79,'Chrome','2016-01-19 10:38:23','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"14\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(80,'Chrome','2016-01-19 10:38:41','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"15\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(81,'Chrome','2016-01-19 10:38:51','admin',NULL,29,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"16\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(82,'Chrome','2016-01-19 10:39:06','admin',NULL,18,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"17\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(83,'Chrome','2016-01-19 10:39:17','admin',NULL,14,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"18\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(84,'Chrome','2016-01-19 10:39:29','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"19\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(85,'Chrome','2016-01-19 10:39:40','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"20\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(86,'Chrome','2016-01-19 10:39:51','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"21\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(87,'Chrome','2016-01-19 10:40:03','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"22\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(88,'Chrome','2016-01-19 10:40:15','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"23\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(89,'Chrome','2016-01-19 10:40:25','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"24\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(90,'Chrome','2016-01-19 10:40:38','admin',NULL,16,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"25\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(91,'Chrome','2016-01-19 10:40:53','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"26\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(92,'Chrome','2016-01-19 10:41:04','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"27\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(93,'Chrome','2016-01-19 10:41:14','admin',NULL,17,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"28\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(94,'Chrome','2016-01-19 10:41:25','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"29\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(95,'Chrome','2016-01-19 10:41:38','admin',NULL,15,'192.168.2.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"30\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"2\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(96,'Chrome','2016-01-19 10:43:32','admin',NULL,88,'192.168.2.1',NULL,'/device/brand/add','Windows 7','{\"_\":[\"1453171423204\"]}',NULL),(97,'Chrome','2016-01-19 10:44:15','admin',NULL,93,'192.168.2.1',NULL,'/device/devicePurchase/add','Windows 7','{\"_\":[\"1453171412184\"]}',NULL),(98,'Chrome','2016-01-19 17:59:48','admin',NULL,83,'192.168.2.82',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"运维服务\"],\"url\":[\"\"],\"icon\":[\"\"],\"pid\":[\"\"],\"sort\":[\"3\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(99,'Chrome','2016-01-19 18:00:41','admin',NULL,53,'192.168.2.82',NULL,'/system/permission/update','Windows 7','{\"id\":[\"169\"],\"type\":[\"F\"],\"name\":[\"运维服务\"],\"url\":[\"\"],\"icon\":[\"icon-hamburg-graphic\"],\"pid\":[\"\"],\"sort\":[\"3\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(100,'Chrome','2016-01-19 18:01:55','admin',NULL,21,'192.168.2.82',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"问题类型\"],\"url\":[\"\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"1\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(101,'Chrome','2016-01-19 18:05:31','admin',NULL,104,'192.168.2.82',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"问题记录\"],\"url\":[\"\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"2\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(102,'Chrome','2016-01-19 18:18:45','admin',NULL,25,'192.168.2.82',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"运维排程\"],\"url\":[\"maintenance/scheduling\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"3\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(103,'Chrome','2016-01-19 18:19:05','admin',NULL,30,'192.168.2.82',NULL,'/system/permission/update','Windows 7','{\"id\":[\"170\"],\"type\":[\"F\"],\"name\":[\"问题类型\"],\"url\":[\"maintenance/problemType\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"1\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(104,'Chrome','2016-01-19 18:19:18','admin',NULL,26,'192.168.2.82',NULL,'/system/permission/update','Windows 7','{\"id\":[\"171\"],\"type\":[\"F\"],\"name\":[\"问题记录\"],\"url\":[\"maintenance/problem\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"2\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(105,'Chrome','2016-01-20 14:37:08','admin',NULL,104,'192.168.2.82',NULL,'/system/organization/update','Windows 7','{\"id\":[\"2\"],\"orgName\":[\"外包手机开发点\"],\"pid\":[\"\"],\"areaId\":[\"1\"],\"orgCode\":[\"WB0001\"],\"orgType\":[\"1\"],\"orgLevel\":[\"1\"],\"orgSort\":[\"1\"],\"longitude\":[\"104.047595\"],\"latitude\":[\"30.645509\"]}',NULL),(106,'Chrome','2016-01-20 15:40:03','admin',NULL,109,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453275601278\"]}',NULL),(107,'Chrome','2016-01-20 15:50:57','admin',NULL,26,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"maintenance:problemType:add\"],\"url\":[\"\"],\"pid\":[\"170\"],\"description\":[\"\"]}',NULL),(108,'Chrome','2016-01-20 15:51:15','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"del\"],\"permCode\":[\"maintenance:problemType:delete\"],\"url\":[\"\"],\"pid\":[\"170\"],\"description\":[\"\"]}',NULL),(109,'Chrome','2016-01-20 15:51:30','admin',NULL,12,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"maintenance:problemType:update\"],\"url\":[\"\"],\"pid\":[\"170\"],\"description\":[\"\"]}',NULL),(110,'Chrome','2016-01-20 15:51:49','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"maintenance:problemType:view\"],\"url\":[\"\"],\"pid\":[\"170\"],\"description\":[\"\"]}',NULL),(111,'Chrome','2016-01-20 16:07:52','admin',NULL,157,'192.168.2.82',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453277313994\"]}',NULL),(112,'Chrome','2016-01-20 16:10:07','admin',NULL,66,'192.168.2.82',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453277313996\"]}',NULL),(113,'Chrome','2016-01-20 16:44:55','admin',NULL,163,'192.168.2.82',NULL,'/device/devicePurchase/add','Windows 7','{\"_\":[\"1453279537720\"]}',NULL),(114,'Internet Explorer 9','2016-01-21 14:34:14','admin',NULL,0,'127.0.0.1',NULL,'/maintenance/problemType/add','Windows 7','{\"_\":[\"1453358045598\"]}',NULL),(115,'Internet Explorer 9','2016-01-21 17:44:30','admin',NULL,30,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453369468561\"]}',NULL),(116,'Internet Explorer 9','2016-01-21 18:19:29','admin',NULL,1,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453371568185\"]}',NULL),(117,'Internet Explorer 9','2016-01-21 18:24:27','admin',NULL,2,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453371866529\"]}',NULL),(118,'Internet Explorer 9','2016-01-21 18:24:49','admin',NULL,2,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453371866531\"]}',NULL),(119,'Internet Explorer 9','2016-01-21 18:28:11','admin',NULL,14,'127.0.0.1',NULL,'/maintenance/problemType/delete/5','Windows 7','{\"_\":[\"1453371966295\"]}',NULL),(120,'Chrome','2016-01-21 18:29:02','admin',NULL,20,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"1\"],\"name\":[\"线路故障1\"],\"deviceType\":[\"HOSTDEVICE\"],\"otherNote\":[\"\"]}',NULL),(121,'Internet Explorer 9','2016-01-22 09:18:13','admin',NULL,19,'127.0.0.1',NULL,'/maintenance/problemType/delete/1','Windows 7','{\"_\":[\"1453425484331\"]}',NULL),(122,'Internet Explorer 9','2016-01-22 09:19:12','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problemType/delete/6','Windows 7','{\"_\":[\"1453425546707\"]}',NULL),(123,'Chrome','2016-01-22 09:48:15','admin',NULL,94,'127.0.0.1',NULL,'/device/userDeviceType/add','Windows 7','{\"_\":[\"1453427293837\"]}',NULL),(124,'Internet Explorer 9','2016-01-22 09:57:31','admin',NULL,19,'127.0.0.1',NULL,'/maintenance/problemType/create','Windows 7','{\"id\":[\"\"],\"name\":[\"11\"],\"deviceType\":[\"COLLECTDEVICE\"],\"otherNote\":[\"asdf\"]}',NULL),(125,'Chrome','2016-01-22 10:08:36','admin',NULL,36,'127.0.0.1',NULL,'/maintenance/problemType/delete/7','Windows 7','{\"_\":[\"1453428304203\"]}',NULL),(126,'Internet Explorer 9','2016-01-22 11:00:55','admin',NULL,16,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"7\"],\"name\":[\"123421341234\"],\"deviceType\":[\"COLLECTDEVICE\"],\"otherNote\":[\"asdf\"]}',NULL),(127,'Internet Explorer 9','2016-01-22 11:07:16','admin',NULL,97,'127.0.0.1',NULL,'/maintenance/problemType/create','Windows 7','{\"id\":[\"\"],\"name\":[\"sdfasdf\"],\"deviceType\":[\"\"],\"otherNote\":[\"\"]}',NULL),(128,'Internet Explorer 9','2016-01-22 11:08:25','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"7\"],\"name\":[\"aaa\"],\"deviceType\":[\"COLLECTDEVICE\"],\"otherNote\":[\"asdf\"]}',NULL),(129,'Internet Explorer 9','2016-01-22 11:11:11','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"7\"],\"name\":[\"111\"],\"deviceType\":[\"COLLECTDEVICE\"],\"otherNote\":[\"asdf\"]}',NULL),(130,'Internet Explorer 9','2016-01-22 11:13:07','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"7\"],\"name\":[\"aaa\"],\"deviceType\":[\"COLLECTDEVICE\"],\"otherNote\":[\"asdf\"]}',NULL),(131,'Internet Explorer 9','2016-01-22 11:15:27','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/problemType/delete/7','Windows 7','{\"_\":[\"1453432379147\"]}',NULL),(132,'Internet Explorer 9','2016-01-22 11:16:40','admin',NULL,12,'127.0.0.1',NULL,'/maintenance/problemType/delete/7','Windows 7','{\"_\":[\"1453432591959\"]}',NULL),(133,'Internet Explorer 9','2016-01-22 13:41:30','admin',NULL,41,'127.0.0.1',NULL,'/system/organization/create','Windows 7','{\"id\":[\"\"],\"orgName\":[\"演示网点\"],\"pid\":[\"2\"],\"areaId\":[\"1\"],\"orgCode\":[\"YS0001\"],\"orgType\":[\"1\"],\"orgLevel\":[\"1\"],\"orgSort\":[\"1\"],\"longitude\":[\"\"],\"latitude\":[\"\"]}',NULL),(134,'Internet Explorer 9','2016-01-22 13:45:33','admin',NULL,143,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"演示一号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"3\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-19\"],\"warrantyDate\":[\"2016-01-20\"],\"remark\":[\"\"]}',NULL),(135,'Internet Explorer 9','2016-01-22 13:47:59','admin',NULL,10,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"演示二号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"3\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-18\"],\"warrantyDate\":[\"2016-01-29\"],\"remark\":[\"\"]}',NULL),(136,'Internet Explorer 9','2016-01-22 13:48:39','admin',NULL,9,'127.0.0.1',NULL,'/device/hostDevice/create','Windows 7','{\"id\":[\"\"],\"version\":[\"\"],\"name\":[\"演示三号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"AIX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"3\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-01-06\"],\"warrantyDate\":[\"2016-01-28\"],\"remark\":[\"\"]}',NULL),(137,'Internet Explorer 9','2016-01-22 15:37:10','admin',NULL,25,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"maintenance:problem:add\"],\"url\":[\"\"],\"pid\":[\"171\"],\"description\":[\"\"]}',NULL),(138,'Internet Explorer 9','2016-01-22 15:37:27','admin',NULL,8,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"del\"],\"permCode\":[\"maintenance:problem:delete\"],\"url\":[\"\"],\"pid\":[\"171\"],\"description\":[\"\"]}',NULL),(139,'Internet Explorer 9','2016-01-22 15:37:38','admin',NULL,8,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"maintenance:problem:update\"],\"url\":[\"\"],\"pid\":[\"171\"],\"description\":[\"\"]}',NULL),(140,'Internet Explorer 9','2016-01-22 15:37:47','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"maintenance:problem:view\"],\"url\":[\"\"],\"pid\":[\"171\"],\"description\":[\"\"]}',NULL),(141,'Internet Explorer 9','2016-01-22 16:49:03','admin',NULL,29,'127.0.0.1',NULL,'/maintenance/problem/delete/4','Windows 7','{\"_\":[\"1453452537909\"]}',NULL),(142,'Internet Explorer 9','2016-01-22 16:49:31','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problem/delete/4','Windows 7','{\"_\":[\"1453452565930\"]}',NULL),(143,'Internet Explorer 9','2016-01-25 11:48:18','admin',NULL,14,'127.0.0.1',NULL,'/maintenance/problem/delete/4','Windows 7','{\"_\":[\"1453693641984\"]}',NULL),(144,'Internet Explorer 9','2016-01-25 14:22:29','admin',NULL,31,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"3\"],\"identifier\":[\"021601151046102\"],\"problemTypeId\":[\"5\"],\"enable\":[\"NEW\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"无响应\"]}',NULL),(145,'Internet Explorer 9','2016-01-25 14:22:57','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"4\"],\"identifier\":[\"021601151120315\"],\"problemTypeId\":[\"2\"],\"enable\":[\"NEW\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"灰尘大多了\"]}',NULL),(146,'Internet Explorer 9','2016-01-25 14:29:40','admin',NULL,12,'127.0.0.1',NULL,'/maintenance/problemType/update','Windows 7','{\"id\":[\"4\"],\"identifier\":[\"021601151120315\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"灰尘大多了\"]}',NULL),(147,'Internet Explorer 9','2016-01-25 14:30:32','admin',NULL,15,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"4\"],\"identifier\":[\"021601151120315\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"灰尘大多了\"]}',NULL),(148,'Internet Explorer 9','2016-01-25 14:33:13','admin',NULL,44,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"4\"],\"identifier\":[\"021601151120315\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"灰尘大多了\"]}',NULL),(149,'Internet Explorer 9','2016-01-25 14:33:50','admin',NULL,43,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"asdfasdf\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(150,'Internet Explorer 9','2016-01-25 14:42:23','admin',NULL,44,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"asdf\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(151,'Internet Explorer 9','2016-01-25 14:42:38','admin',NULL,22,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"asdf\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(152,'Internet Explorer 9','2016-01-25 14:43:26','admin',NULL,16543,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"asdf\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(153,'Internet Explorer 9','2016-01-25 14:44:33','admin',NULL,57142,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"asdf\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(154,'Internet Explorer 9','2016-01-25 16:17:38','admin',NULL,21,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"problemTypeId\":[\"2\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(155,'Internet Explorer 9','2016-01-25 16:17:54','admin',NULL,19,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"problemTypeId\":[\"5\"],\"enable\":[\"NEW\"],\"reportWay\":[\"HANDSET\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(156,'Internet Explorer 9','2016-01-25 16:18:17','admin',NULL,16,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"problemTypeId\":[\"5\"],\"enable\":[\"RESOLVED\"],\"reportWay\":[\"HANDSET\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(157,'Internet Explorer 9','2016-01-25 16:18:42','admin',NULL,17,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"problemTypeId\":[\"5\"],\"enable\":[\"CLOSED\"],\"reportWay\":[\"HANDSET\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(158,'Internet Explorer 9','2016-01-25 17:29:45','admin',NULL,17,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"111\"],\"reportUserContact\":[\"222\"],\"description\":[\"333\"]}',NULL),(159,'Internet Explorer 9','2016-01-25 17:29:57','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"111\"],\"reportUserContact\":[\"222\"],\"description\":[\"333\"]}',NULL),(160,'Internet Explorer 9','2016-01-25 17:31:51','admin',NULL,2,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"111\"],\"reportUserContact\":[\"222\"],\"description\":[\"333\"]}',NULL),(161,'Internet Explorer 9','2016-01-25 17:32:31','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"111\"],\"reportUserContact\":[\"222\"],\"description\":[\"333\"]}',NULL),(162,'Internet Explorer 9','2016-01-25 17:35:55','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"111\"],\"reportUserContact\":[\"222\"],\"description\":[\"333\"]}',NULL),(163,'Internet Explorer 9','2016-01-25 17:42:24','admin',NULL,5,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(164,'Internet Explorer 9','2016-01-25 17:42:40','admin',NULL,0,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(165,'Internet Explorer 9','2016-01-25 17:56:56','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(166,'Internet Explorer 9','2016-01-25 18:12:15','admin',NULL,54,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"problemTypeId\":[\"5\"],\"enable\":[\"NEW\"],\"reportWay\":[\"HANDSET\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(167,'Internet Explorer 9','2016-01-25 18:13:32','admin',NULL,2,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(168,'Internet Explorer 9','2016-01-25 18:16:16','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(169,'Internet Explorer 9','2016-01-25 18:20:34','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(170,'Internet Explorer 9','2016-01-25 18:22:53','admin',NULL,15,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"version\":[\"\"],\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(171,'Internet Explorer 9','2016-01-25 18:23:15','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"version\":[\"1\"],\"identifier\":[\"021601151248970\"],\"problemTypeId\":[\"3\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"KNOWLEDGE_BASE\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(172,'Internet Explorer 9','2016-01-25 18:25:41','admin',NULL,8,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"3\"],\"enableKnowledge\":[\"\"],\"reportWay\":[\"KNOWLEDGE_BASE\"],\"reportUser\":[\"1\"],\"reportUserContact\":[\"2\"],\"description\":[\"3\"]}',NULL),(173,'Internet Explorer 9','2016-01-25 18:27:02','admin',NULL,3155,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601191035749\"],\"problemTypeId\":[\"5\"],\"enableKnowledge\":[\"true\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(174,'Internet Explorer 9','2016-01-26 11:14:04','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"identifier\":[\"021601131753480\"],\"problemTypeId\":[\"3\"],\"enableKnowledge\":[\"true\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(175,'Internet Explorer 9','2016-01-26 11:14:12','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/problem/delete/28','Windows 7','{\"_\":[\"1453778014816\"]}',NULL),(176,'Internet Explorer 9','2016-01-26 11:14:24','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"23\"],\"problemTypeId\":[\"5\"],\"enable\":[\"ASSIGNED\"],\"reportWay\":[\"PHONE\"],\"reportUser\":[\"\"],\"reportUserContact\":[\"\"],\"description\":[\"\"]}',NULL),(177,'Internet Explorer 9','2016-01-26 22:46:11','admin',NULL,26,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"172\"],\"type\":[\"F\"],\"name\":[\"运维排程\"],\"url\":[\"maintenance/scheduling\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"4\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(178,'Internet Explorer 9','2016-01-26 22:48:09','admin',NULL,90,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"F\"],\"name\":[\"运维处理\"],\"url\":[\"maintenance/handling\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"3\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(179,'Internet Explorer 9','2016-01-26 22:48:57','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"maintenance:handling:add\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(180,'Internet Explorer 9','2016-01-26 22:49:07','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"del\"],\"permCode\":[\"maintenance:handling:delete\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(181,'Internet Explorer 9','2016-01-26 22:49:26','admin',NULL,13,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"maintenance:handling:update\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(182,'Internet Explorer 9','2016-01-26 22:49:40','admin',NULL,14,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"maintenance:handling:view\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(183,'Internet Explorer 9','2016-01-27 14:30:24','admin',NULL,20,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"12\"],\"problemTypeId\":[\"1\"],\"enable\":[\"HANDLING\"],\"reportWay\":[\"HANDSET\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"\"]}',NULL),(184,'Internet Explorer 9','2016-01-27 15:25:28','admin',NULL,16,'127.0.0.1',NULL,'/system/permission/delete/183','Windows 7','{\"_\":[\"1453879490515\"]}',NULL),(185,'Internet Explorer 9','2016-01-27 15:25:47','admin',NULL,18,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"182\"],\"type\":[\"O\"],\"name\":[\"更新记录\"],\"permCode\":[\"maintenance:handling:update\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(186,'Internet Explorer 9','2016-01-27 15:26:28','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"184\"],\"type\":[\"O\"],\"name\":[\"查看处理历史记录\"],\"permCode\":[\"maintenance:handling:search\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(187,'Internet Explorer 9','2016-01-27 17:56:30','admin',NULL,44,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"4\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"11111111\"]}',NULL),(188,'Internet Explorer 9','2016-01-27 17:57:46','admin',NULL,3,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"12\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"22222222\"]}',NULL),(189,'Internet Explorer 9','2016-01-27 17:57:51','admin',NULL,2,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"12\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"3333333\"]}',NULL),(190,'Internet Explorer 9','2016-01-27 18:32:58','admin',NULL,14,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"12\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"4444444\"]}',NULL),(191,'Internet Explorer 9','2016-01-28 09:20:53','admin',NULL,35,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"12\"],\"enable\":[\"RESOLVED\"],\"attachment\":[\"\"],\"description\":[\"5555555\"]}',NULL),(192,'Chrome','2016-01-29 09:11:57','admin',NULL,69,'192.168.2.82',NULL,'/system/organization/create','Windows 7','{\"id\":[\"\"],\"orgName\":[\"客户端开发点\"],\"pid\":[\"2\"],\"areaId\":[\"1\"],\"orgCode\":[\"WB0002\"],\"orgType\":[\"1\"],\"orgLevel\":[\"2\"],\"orgSort\":[\"3\"],\"longitude\":[\"103.990726\"],\"latitude\":[\"30.587670\"]}',NULL),(193,'Chrome','2016-01-29 09:13:37','admin',NULL,59,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"8\"],\"version\":[\"1\"],\"name\":[\"01\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(194,'Chrome','2016-01-29 09:13:47','admin',NULL,6,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"9\"],\"version\":[\"0\"],\"name\":[\"02\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(195,'Chrome','2016-01-29 09:14:01','admin',NULL,30,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"10\"],\"version\":[\"0\"],\"name\":[\"03\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(196,'Chrome','2016-01-29 09:14:12','admin',NULL,28,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"11\"],\"version\":[\"0\"],\"name\":[\"04\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"\"],\"warrantyDate\":[\"\"],\"remark\":[\"\"]}',NULL),(197,'Chrome','2016-01-29 09:15:26','admin',NULL,37,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"2\"],\"version\":[\"3\"],\"name\":[\"外包测试机\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"android\"],\"oSType\":[\"LINUX\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-27\"],\"warrantyDate\":[\"2016-1-20\"],\"remark\":[\"\"]}',NULL),(198,'Chrome','2016-01-29 09:15:35','admin',NULL,8,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"6\"],\"version\":[\"6\"],\"name\":[\"长江四号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"4400\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-21\"],\"warrantyDate\":[\"2016-1-13\"],\"remark\":[\"\"]}',NULL),(199,'Chrome','2016-01-29 09:15:40','admin',NULL,30,'192.168.2.82',NULL,'/device/hostDevice/update','Windows 7','{\"id\":[\"7\"],\"version\":[\"2\"],\"name\":[\"长江五号\"],\"authorizationCode\":[\"\"],\"ip\":[\"\"],\"model\":[\"5500\"],\"oSType\":[\"WINDOWS\"],\"oSBits\":[\"32\"],\"cpu\":[\"\"],\"harddisk\":[\"\"],\"motherboard\":[\"\"],\"memory\":[\"\"],\"purchaseId\":[\"1\"],\"purchaseName\":[\"abcdefg\"],\"organizationId\":[\"4\"],\"enable\":[\"ENABLED\"],\"manufactureDate\":[\"2016-1-20\"],\"warrantyDate\":[\"2016-1-21\"],\"remark\":[\"\"]}',NULL),(200,'Internet Explorer 9','2016-01-29 16:33:36','admin',NULL,38,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"12\"],\"enable\":[\"RESPONSED\"],\"attachment\":[\"\"],\"description\":[\"asdfasdf\"]}',NULL),(201,'Internet Explorer 9','2016-01-29 18:11:20','admin',NULL,58,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"4\"],\"problemTypeId\":[\"2\"],\"enable\":[\"CALLBACK\"],\"reportWay\":[\"DICTATION\"],\"reportUser\":[\"admin\"],\"reportUserContact\":[\"15902840428\"],\"description\":[\"灰尘大多了\"]}',NULL),(202,'Internet Explorer 9','2016-02-01 10:30:33','admin',NULL,57,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"enable\":[\"ASSIGNED\"],\"resolveUserId\":[\"6\"]}',NULL),(203,'Internet Explorer 9','2016-02-01 10:30:57','admin',NULL,14,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"22\"],\"enable\":[\"ASSIGNED\"],\"resolveUserId\":[\"1\"]}',NULL),(204,'Internet Explorer 9','2016-02-01 10:31:24','admin',NULL,12,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"21\"],\"enable\":[\"ASSIGNED\"],\"resolveUserId\":[\"14\"]}',NULL),(205,'Internet Explorer 9','2016-02-01 11:51:54','admin',NULL,58,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"18\"],\"enable\":[\"CLOSED\"]}',NULL),(206,'Internet Explorer 9','2016-02-01 11:54:12','admin',NULL,16,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"20\"],\"enable\":[\"CLOSED\"]}',NULL),(207,'Internet Explorer 9','2016-02-01 11:56:48','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"5\"],\"enable\":[\"ASSIGNED\"],\"resolveUserId\":[\"1\"]}',NULL),(208,'Internet Explorer 9','2016-02-01 11:56:57','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"6\"],\"enable\":[\"ASSIGNED\"],\"resolveUserId\":[\"1\"]}',NULL),(209,'Internet Explorer 9','2016-02-01 13:01:09','admin',NULL,74687,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"11\"],\"enable\":[\"CLOSED\"]}',NULL),(210,'Internet Explorer 9','2016-02-01 13:04:17','admin',NULL,10238,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"10\"],\"enable\":[\"CLOSED\"]}',NULL),(211,'Internet Explorer 9','2016-02-01 13:05:01','admin',NULL,55015,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"10\"],\"enable\":[\"CLOSED\"]}',NULL),(212,'Internet Explorer 9','2016-02-01 13:05:47','admin',NULL,24359,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"7\"],\"enable\":[\"CLOSED\"]}',NULL),(213,'Internet Explorer 9','2016-02-01 13:07:15','admin',NULL,10156,'127.0.0.1',NULL,'/maintenance/problem/update','Windows 7','{\"id\":[\"8\"],\"enable\":[\"CLOSED\"]}',NULL),(214,'Internet Explorer 9','2016-02-01 14:16:30','admin',NULL,44,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"22\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"\"]}',NULL),(215,'Internet Explorer 9','2016-02-01 14:17:09','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"6\"],\"enable\":[\"RESOLVED\"],\"attachment\":[\"\"],\"description\":[\"\"]}',NULL),(216,'Internet Explorer 9','2016-02-01 14:17:22','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"6\"],\"enable\":[\"RESOLVED\"],\"attachment\":[\"\"],\"description\":[\"\"]}',NULL),(217,'Internet Explorer 9','2016-02-01 14:17:32','admin',NULL,1,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"6\"],\"enable\":[\"HANDLING\"],\"attachment\":[\"\"],\"description\":[\"\"]}',NULL),(218,'Internet Explorer 9','2016-02-01 14:21:02','admin',NULL,14,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"打回\"],\"permCode\":[\"maintenance:handling:callback\"],\"url\":[\"\"],\"pid\":[\"181\"],\"description\":[\"\"]}',NULL),(219,'Internet Explorer 9','2016-02-01 14:33:13','admin',NULL,24246,'127.0.0.1',NULL,'/maintenance/handling/create','Windows 7','{\"problemId\":[\"6\"],\"enable\":[\"CALLBACK\"],\"description\":[\"打回问题\"]}',NULL),(220,'Internet Explorer 9','2016-02-01 15:12:46','admin',NULL,27,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"maintenance::arrange:add\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(221,'Internet Explorer 9','2016-02-01 15:12:58','admin',NULL,11,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"delete\"],\"permCode\":[\"maintenance::arrange::delete\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(222,'Internet Explorer 9','2016-02-01 15:13:19','admin',NULL,13,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"188\"],\"type\":[\"O\"],\"name\":[\"delete\"],\"permCode\":[\"maintenance:arrange:delete\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(223,'Internet Explorer 9','2016-02-01 15:13:34','admin',NULL,9,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"187\"],\"type\":[\"O\"],\"name\":[\"add\"],\"permCode\":[\"maintenance:arrange:add\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(224,'Internet Explorer 9','2016-02-01 15:14:07','admin',NULL,10,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"view\"],\"permCode\":[\"maintenance:arrange:view\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(225,'Internet Explorer 9','2016-02-01 15:14:19','admin',NULL,7,'127.0.0.1',NULL,'/system/permission/create','Windows 7','{\"id\":[\"\"],\"type\":[\"O\"],\"name\":[\"update\"],\"permCode\":[\"maintenance:arrange:update\"],\"url\":[\"\"],\"pid\":[\"172\"],\"description\":[\"\"]}',NULL),(226,'Internet Explorer 9','2016-02-02 13:40:54','admin',NULL,18,'127.0.0.1',NULL,'/system/permission/update','Windows 7','{\"id\":[\"172\"],\"type\":[\"F\"],\"name\":[\"运维排程\"],\"url\":[\"maintenance/arrange\"],\"icon\":[\"icon-hamburg-date\"],\"pid\":[\"169\"],\"sort\":[\"4\"],\"code\":[\"DH\"],\"description\":[\"\"]}',NULL),(227,'Internet Explorer 9','2016-02-02 14:30:08','admin',NULL,21,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"22\"],\"enable\":[\"true\"],\"workDay\":[\"2\",\"5\",\"7\",\"4\",\"3\",\"6\",\"1\"],\"workTime\":[\"\"],\"remark\":[\"\"]}',NULL),(228,'Internet Explorer 9','2016-02-02 14:30:51','admin',NULL,3,'127.0.0.1',NULL,'/maintenance/problem/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"22\"],\"enable\":[\"true\"],\"workDay\":[\"2\",\"5\",\"7\",\"4\",\"3\",\"6\",\"1\"],\"workTime\":[\"\"],\"remark\":[\"\"]}',NULL),(229,'Internet Explorer 9','2016-02-02 14:42:06','admin',NULL,25,'127.0.0.1',NULL,'/maintenance/arrange/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"6\"],\"enable\":[\"true\"],\"workDay\":[\"2\",\"4\",\"6\"],\"workTime\":[\"02:00-03:00\"],\"remark\":[\"\"]}',NULL),(230,'Internet Explorer 9','2016-02-02 14:45:02','admin',NULL,8,'127.0.0.1',NULL,'/maintenance/arrange/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"1\"],\"enable\":[\"false\"],\"workDay\":[\"2\",\"4\",\"6\",\"1\"],\"workTime\":[\"04:00-09:00\"],\"remark\":[\"\"]}',NULL),(231,'Internet Explorer 9','2016-02-02 17:01:43','admin',NULL,26,'127.0.0.1',NULL,'/maintenance/arrange/delete/1','Windows 7','{\"_\":[\"1454403686254\"]}',NULL),(232,'Internet Explorer 9','2016-02-02 17:01:46','admin',NULL,13,'127.0.0.1',NULL,'/maintenance/arrange/delete/2','Windows 7','{\"_\":[\"1454403686256\"]}',NULL),(233,'Internet Explorer 9','2016-02-02 17:02:06','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/arrange/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"6\"],\"userName\":[\"超级管理员\"],\"enable\":[\"false\"],\"workDay\":[\"3\",\"5\",\"7\"],\"workTime\":[\"02:00-05:00\"],\"remark\":[\"\"]}',NULL),(234,'Internet Explorer 9','2016-02-02 18:05:39','admin',NULL,20,'127.0.0.1',NULL,'/maintenance/arrange/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"14\"],\"userName\":[\"administrator\"],\"enable\":[\"false\"],\"workDay\":[\"3\",\"1\"],\"workTime\":[\"01:00-02:00\"],\"remark\":[\"\"]}',NULL),(235,'Internet Explorer 9','2016-02-02 18:21:56','admin',NULL,29,'127.0.0.1',NULL,'/maintenance/arrange/update','Windows 7','{\"id\":[\"4\"],\"userId\":[\"14\"],\"userName\":[\"administrator\"],\"enable\":[\"false\"],\"workDay\":[\"[\",\"3\",\",\",\" \",\"1\",\"]\"],\"workTime\":[\"01:00-02:00\"],\"remark\":[\"\"]}',NULL),(236,'Internet Explorer 9','2016-02-03 10:52:34','admin',NULL,19,'127.0.0.1',NULL,'/maintenance/arrange/update','Windows 7','{\"id\":[\"4\"],\"userId\":[\"14\"],\"userName\":[\"administrator\"],\"enable\":[\"false\"],\"workDay\":[\"1\",\"3\",\"5\",\"2\"],\"workTime\":[\"01:00-02:00\"],\"remark\":[\"\"]}',NULL),(237,'Internet Explorer 9','2016-02-03 10:53:01','admin',NULL,12,'127.0.0.1',NULL,'/maintenance/arrange/delete/4','Windows 7','{\"_\":[\"1454467908136\"]}',NULL),(238,'Internet Explorer 9','2016-02-03 10:54:12','admin',NULL,11,'127.0.0.1',NULL,'/maintenance/arrange/create','Windows 7','{\"id\":[\"\"],\"userId\":[\"1\"],\"userName\":[\"admin\"],\"enable\":[\"true\"],\"workDay\":[\"2\",\"3\",\"4\",\"6\",\"5\"],\"workTime\":[\"09:00-17:00\"],\"remark\":[\"\"]}',NULL);

/*Table structure for table `network_device` */

DROP TABLE IF EXISTS `network_device`;

CREATE TABLE `network_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `authorization_code` varchar(255) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `interface_total` int(11) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `manufacture_date` datetime DEFAULT NULL,
  `max_network_load` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_device_type_id` int(11) NOT NULL,
  `warranty_date` datetime DEFAULT NULL,
  `alarm_template_id` int(11) DEFAULT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `network_device` */

/*Table structure for table `organization` */

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` int(11) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `org_level` int(11) DEFAULT NULL,
  `org_name` varchar(255) NOT NULL,
  `org_sort` int(11) DEFAULT NULL,
  `org_type` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `organization` */

insert  into `organization`(`id`,`area_id`,`latitude`,`longitude`,`org_code`,`org_level`,`org_name`,`org_sort`,`org_type`,`pid`) values (1,1,30.645509,104.047595,'SK0001',1,'申控测试点',1,'服务端',NULL),(2,1,30.576263,104.016357,'WB0001',1,'外包手机开发点',1,'1',NULL),(3,1,NULL,NULL,'YS0001',1,'演示网点',1,'1',2),(4,1,30.58767,103.990726,'WB0002',2,'客户端开发点',3,'1',2);

/*Table structure for table `pe_device` */

DROP TABLE IF EXISTS `pe_device`;

CREATE TABLE `pe_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `alarm_template_id` int(11) NOT NULL,
  `collect_device_id` int(11) NOT NULL,
  `dh_device_index` int(11) NOT NULL,
  `dh_device_interface_type` int(11) DEFAULT NULL,
  `dh_device_type` int(11) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `manufacture_date` datetime DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_device_type_id` int(11) NOT NULL,
  `warranty_date` datetime DEFAULT NULL,
  `current_state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pe_device` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` int(11) DEFAULT NULL COMMENT '父节点名称',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型:菜单or功能',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `URL` varchar(255) DEFAULT NULL,
  `PERM_CODE` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `ICON` varchar(255) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `DESCRIPTION` text,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`ID`,`PID`,`NAME`,`TYPE`,`SORT`,`URL`,`PERM_CODE`,`ICON`,`STATE`,`DESCRIPTION`,`code`) values (1,NULL,'基础配置','F',1,'','','icon-standard-cog','','','DH'),(2,1,'角色权限','F',4,'system/role','','icon-hamburg-my-account','closed','','DH'),(3,1,'用户管理','F',3,'system/user','','icon-hamburg-user','closed','','DH'),(4,2,'添加','O',NULL,'','sys:role:add','','','角色添加',NULL),(5,2,'删除','O',NULL,'','sys:role:delete','','','角色删除',NULL),(6,2,'修改','O',NULL,'','sys:role:update','','','角色修改',NULL),(7,3,'添加','O',NULL,'','sys:user:add','','','用户添加',NULL),(8,3,'删除','O',NULL,'','sys:user:delete','','','用户删除',NULL),(12,1,'权限管理','F',6,'system/permission','','icon-hamburg-login','closed','','DH'),(14,106,'数据源监控','F',2,'druid','','icon-hamburg-database','','','DH'),(15,NULL,'定时管理','F',2,'','','icon-hamburg-graphic','','','DH'),(16,3,'修改','O',NULL,'','sys:user:update','','','用户修改',NULL),(20,106,'系统管理','F',1,'system/log','','icon-hamburg-archives','','','DH'),(25,12,'添加','O',NULL,'','sys:perm:add','','','菜单添加',NULL),(26,12,'修改','O',NULL,'','sys:perm:update','','','菜单修改',NULL),(27,12,'删除','O',NULL,'','sys:perm:delete','','','菜单删除',NULL),(28,2,'查看','O',NULL,'','sys:role:view','','','角色查看',NULL),(29,3,'查看','O',NULL,'','sys:user:view','',NULL,'用户查看',NULL),(30,12,'查看','O',NULL,'','sys:perm:view','',NULL,'权限查看',NULL),(31,20,'删除','O',NULL,'','sys:log:delete','',NULL,'删除日志',NULL),(32,20,'导出excel','O',NULL,'','sys:log:exportExcel','',NULL,'导出日志excel',NULL),(33,3,'查看用户角色','O',NULL,'','sys:user:roleView','',NULL,'查看用户角色',NULL),(34,2,'保存授权','O',NULL,'','sys:role:permUpd','',NULL,'保存修改的角色权限',NULL),(35,3,'修改用户角色','O',NULL,'','sys:user:roleUpd','',NULL,'修改用户拥有的角色',NULL),(36,2,'查看角色权限','O',NULL,'','sys:role:permView','',NULL,'查看角色拥有的权限',NULL),(37,15,'定时任务管理','F',1,'system/scheduleJob','','icon-hamburg-full-time',NULL,'定时任务管理，支持集群','DH'),(38,15,'cron表达式生成','F',2,'system/scheduleJob/quartzCron','','icon-hamburg-future',NULL,'','DH'),(39,1,'菜单管理','F',5,'system/permission/menu','','icon-hamburg-old-versions',NULL,'','DH'),(40,1,'字典管理','F',7,'system/dict',NULL,'icon-hamburg-address',NULL,'数据字典管理','DH'),(45,39,'修改','O',NULL,'','sys:perm:update',NULL,NULL,'菜单管理',NULL),(58,39,'添加','O',NULL,'','sys:perm:add',NULL,NULL,'菜单管理',NULL),(59,39,'删除','O',NULL,'','sys:perm:delte',NULL,NULL,'菜单管理',NULL),(61,40,'添加','O',NULL,'','sys:dict:add',NULL,NULL,'字典管理',NULL),(62,40,'删除','O',NULL,'','sys:dict:delete',NULL,NULL,'字典管理',NULL),(63,40,'修改','O',NULL,'','sys:dict:update',NULL,NULL,'字典管理',NULL),(68,20,'查看','O',NULL,'','sys:log:view',NULL,NULL,'查看日志',NULL),(69,40,'查看','O',NULL,'','sys:dict:view',NULL,NULL,'字典管理',NULL),(70,39,'查看','O',NULL,'','sys:perm:menu:view',NULL,NULL,'菜单管理',NULL),(74,1,'区域信息','F',1,'system/area',NULL,'icon-hamburg-world',NULL,'管理行政区划','DH'),(75,1,'机构管理','F',2,'system/organization',NULL,'icon-cologne-home',NULL,'组织机构管理','DH'),(76,3,'查看用户机构','O',NULL,'','sys:user:orgView',NULL,NULL,'查看用户机构',NULL),(77,3,'修改用户机构','O',NULL,'','sys:user:orgUpd',NULL,NULL,'修改用户所在的机构',NULL),(78,NULL,'设备管理','F',3,'',NULL,'icon-hamburg-advertising',NULL,'','DH'),(83,NULL,'报表管理','F',4,'',NULL,'icon-hamburg-full-time',NULL,'','DH'),(85,79,'添加','O',NULL,'','sys:gitInfo:add',NULL,NULL,'',NULL),(86,79,'查看','O',NULL,'','sys:gitInfo:view',NULL,NULL,'',NULL),(87,79,'删除','O',NULL,'','sys:gitInfo:delete',NULL,NULL,'',NULL),(88,82,'扫描','O',NULL,'','sys:device:add',NULL,NULL,'',NULL),(89,82,'删除','O',NULL,'','sys:device:delete',NULL,NULL,'',NULL),(90,82,'修改','O',NULL,'','sys:device:update',NULL,NULL,'',NULL),(91,105,'查看','O',NULL,'','sys:upsStatus:view',NULL,NULL,'',NULL),(92,105,'主机设备查看','O',NULL,'','sys:upsStatus:gitInfoView',NULL,NULL,'',NULL),(93,105,'监控状态查看','O',NULL,'','sys:upsStatus:upsStatusView',NULL,NULL,'',NULL),(94,105,'删除','O',NULL,'','sys:upsStatus:delete',NULL,NULL,'',NULL),(95,105,'ups放电','O',NULL,'','sys:device:upsDischarge',NULL,NULL,'',NULL),(96,105,'ups关机','O',NULL,'','sys:device:upsClose',NULL,NULL,'',NULL),(97,79,'修改','O',NULL,'','sys:gitInfo:update',NULL,NULL,'',NULL),(98,82,'查看','O',NULL,'','sys:device:view',NULL,NULL,'',NULL),(99,83,'统计报表','F',2,'chart',NULL,'icon-hamburg-equalizer',NULL,'','DH'),(100,83,'监控报表','F',1,'chart/upsChartView',NULL,'icon-standard-chart-pie',NULL,'','DH'),(101,NULL,'告警配置','F',5,'',NULL,'icon-standard-table-edit',NULL,'','DH'),(102,101,'告警等级管理','F',1,'system/alarmLevel',NULL,'icon-standard-sound',NULL,'','DH'),(103,101,'告警模板管理','F',2,'system/alarmTemplate',NULL,'icon-standard-bell',NULL,'','DH'),(104,101,'报警参数配置','F',3,'system/sms',NULL,'icon-standard-folder',NULL,'','DH'),(105,NULL,'监控中心','F',NULL,'monitorPlatform/index',NULL,'icon-hamburg-database',NULL,'','DS'),(106,NULL,'日志管理','F',6,'',NULL,'icon-hamburg-billing',NULL,'','DH'),(107,3,'初始化密码','O',NULL,'','sys:user:resetPwd',NULL,NULL,'',NULL),(108,83,'TopN排序报表','F',3,'chart/topNChart',NULL,'icon-standard-chart-bar',NULL,'','DH'),(110,106,'UPS放电日志','F',3,'system/dischargeLog',NULL,'icon-standard-page-white-text',NULL,'','DH'),(111,109,'查看','O',NULL,'','sys:schedule:view',NULL,NULL,'',NULL),(112,109,'删除','O',NULL,'','sys:dischargeTask:delScheduleJob',NULL,NULL,'',NULL),(113,109,'修改','O',NULL,'','sys:dischargeTask:update',NULL,NULL,'',NULL),(114,109,'添加','O',NULL,'','sys:dischargeTask:add',NULL,NULL,'',NULL),(115,109,'启动放电计划','O',NULL,'','sys:dischargeTask:startDischargeTask',NULL,NULL,'',NULL),(116,109,'停止放电计划','O',NULL,'','sys:dischargeTask:stopDischargeTask',NULL,NULL,'',NULL),(117,109,'选择放电设备','O',NULL,'','sys:dischargeTask:selectedUpsDeviceList',NULL,NULL,'',NULL),(118,102,'查看','O',NULL,'','sys:alarmLevel:view',NULL,NULL,'',NULL),(119,102,'添加','O',NULL,'','sys:alarmLevel:add',NULL,NULL,'',NULL),(120,102,'删除','O',NULL,'','sys:alarmLevel:delete',NULL,NULL,'',NULL),(121,102,'修改','O',NULL,'','sys:alarmLevel:update',NULL,NULL,'',NULL),(122,103,'告警模板查看','O',NULL,'','sys:alarmTemplate:view',NULL,NULL,'',NULL),(123,103,'告警模板添加','O',NULL,'','sys:alarmTemplate:add',NULL,NULL,'',NULL),(124,103,'告警模板删除','O',NULL,'','sys:alarmTemplate:delete',NULL,NULL,'',NULL),(125,103,'告警模板修改','O',NULL,'','sys:alarmTemplate:update',NULL,NULL,'',NULL),(126,103,'告警条件查看','O',NULL,'','sys:alarmCondition:view',NULL,NULL,'',NULL),(127,103,'告警条件添加','O',NULL,'','sys:alarmCondition:add',NULL,NULL,'',NULL),(128,103,'告警条件删除','O',NULL,'','sys:alarmCondition:delete',NULL,NULL,'',NULL),(129,103,'告警条件修改','O',NULL,'','sys:alarmCondition:update',NULL,NULL,'',NULL),(130,103,'告警规则查看','O',NULL,'','sys:alarmRule:view',NULL,NULL,'',NULL),(131,103,'告警规则添加','O',NULL,'','sys:alarmRule:add',NULL,NULL,'',NULL),(132,103,'告警规则删除','O',NULL,'','sys:alarmRule:delete',NULL,NULL,'',NULL),(133,103,'告警规则修改','O',NULL,'','sys:alarmRule:update',NULL,NULL,'',NULL),(134,110,'删除','O',NULL,'','sys:dischargeLog:delete',NULL,NULL,'',NULL),(137,74,'qwe','O',NULL,'','',NULL,NULL,'',NULL),(138,74,'sd','O',NULL,'','',NULL,NULL,'',NULL),(144,140,'56','O',NULL,'','12',NULL,NULL,'',NULL),(145,140,'456','O',NULL,'','',NULL,NULL,'',NULL),(146,140,'564','O',NULL,'','',NULL,NULL,'',NULL),(148,147,'123','O',NULL,'','',NULL,NULL,'',NULL),(150,NULL,'123456789123456*4561','O',NULL,'','',NULL,NULL,'',NULL),(151,74,'qqwwwwertyuioplokmny','O',NULL,'','',NULL,NULL,'',NULL),(152,78,'设备详情','F',1,'device/details',NULL,'icon-hamburg-graphic',NULL,'','DH'),(153,78,'品牌管理','F',NULL,'device/brand',NULL,'icon-hamburg-date',NULL,'','DH'),(154,78,'产品类型划分','F',NULL,'device/userDeviceType',NULL,'icon-hamburg-date',NULL,'','DH'),(155,78,'采购管理','F',NULL,'device/devicePurchase',NULL,'icon-hamburg-date',NULL,'','DH'),(156,78,'采购汇总','F',NULL,'device/deviceInventory',NULL,'icon-hamburg-date',NULL,'','DH'),(157,153,'新增','O',NULL,'','device:brand:add',NULL,NULL,'',NULL),(158,153,'删除','O',NULL,'','device:brand:delete',NULL,NULL,'',NULL),(159,153,'修改','O',NULL,'','device:brand:update',NULL,NULL,'',NULL),(160,153,'查看','O',NULL,'','device:brand:view',NULL,NULL,'',NULL),(161,154,'add','O',NULL,'','device:userDeviceType:add',NULL,NULL,'',NULL),(162,154,'del','O',NULL,'','device:userDeviceType:delete',NULL,NULL,'',NULL),(163,154,'update','O',NULL,'','device:userDeviceType:update',NULL,NULL,'',NULL),(164,154,'view','O',NULL,'','device:userDeviceType:view',NULL,NULL,'',NULL),(165,155,'add','O',NULL,'','device:devicePurchase:add',NULL,NULL,'',NULL),(166,155,'update','O',NULL,'','device:devicePurchase:update',NULL,NULL,'',NULL),(167,155,'view','O',NULL,'','device:devicePurchase:view',NULL,NULL,'',NULL),(168,156,'view','O',NULL,'','device:deviceInventory:view',NULL,NULL,'',NULL),(169,NULL,'运维服务','F',3,'',NULL,'icon-hamburg-graphic',NULL,'','DH'),(170,169,'问题类型','F',1,'maintenance/problemType',NULL,'icon-hamburg-date',NULL,'','DH'),(171,169,'问题记录','F',2,'maintenance/problem',NULL,'icon-hamburg-date',NULL,'','DH'),(172,169,'运维排程','F',4,'maintenance/arrange',NULL,'icon-hamburg-date',NULL,'','DH'),(173,170,'add','O',NULL,'','maintenance:problemType:add',NULL,NULL,'',NULL),(174,170,'del','O',NULL,'','maintenance:problemType:delete',NULL,NULL,'',NULL),(175,170,'update','O',NULL,'','maintenance:problemType:update',NULL,NULL,'',NULL),(176,170,'view','O',NULL,'','maintenance:problemType:view',NULL,NULL,'',NULL),(177,171,'add','O',NULL,'','maintenance:problem:add',NULL,NULL,'',NULL),(178,171,'del','O',NULL,'','maintenance:problem:delete',NULL,NULL,'',NULL),(179,171,'update','O',NULL,'','maintenance:problem:update',NULL,NULL,'',NULL),(180,171,'view','O',NULL,'','maintenance:problem:view',NULL,NULL,'',NULL),(181,169,'运维处理','F',3,'maintenance/handling',NULL,'icon-hamburg-date',NULL,'','DH'),(182,181,'更新记录','O',NULL,'','maintenance:handling:update',NULL,NULL,'',NULL),(184,181,'查看处理历史记录','O',NULL,'','maintenance:handling:search',NULL,NULL,'',NULL),(185,181,'view','O',NULL,'','maintenance:handling:view',NULL,NULL,'',NULL),(186,181,'打回','O',NULL,'','maintenance:handling:callback',NULL,NULL,'',NULL),(187,172,'add','O',NULL,'','maintenance:arrange:add',NULL,NULL,'',NULL),(188,172,'delete','O',NULL,'','maintenance:arrange:delete',NULL,NULL,'',NULL),(189,172,'view','O',NULL,'','maintenance:arrange:view',NULL,NULL,'',NULL),(190,172,'update','O',NULL,'','maintenance:arrange:update',NULL,NULL,'',NULL);

/*Table structure for table `problem` */

DROP TABLE IF EXISTS `problem`;

CREATE TABLE `problem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enable_knowledge` int(11) DEFAULT NULL,
  `problem_type_id` int(11) NOT NULL,
  `record_time` datetime NOT NULL,
  `record_user_id` int(11) DEFAULT NULL,
  `report_user` varchar(50) DEFAULT NULL,
  `report_user_contact` varchar(50) DEFAULT NULL,
  `report_way` int(11) NOT NULL,
  `resolve_user_id` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `enable` int(11) NOT NULL,
  `description` longtext,
  `status` int(11) NOT NULL,
  `responsed` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

/*Data for the table `problem` */

insert  into `problem`(`id`,`enable_knowledge`,`problem_type_id`,`record_time`,`record_user_id`,`report_user`,`report_user_contact`,`report_way`,`resolve_user_id`,`identifier`,`enable`,`description`,`status`,`responsed`) values (3,0,5,'2016-01-19 15:46:54',1,'admin','15902840428',3,NULL,'021601151046102',0,'无响应',0,'\0'),(4,0,2,'2016-01-19 15:47:21',1,'admin','15902840428',1,NULL,'021601151120315',1,'灰尘大多了',0,'\0'),(5,0,3,'2016-01-19 16:28:09',1,'admin','15902840428',3,1,'021601151046102',2,'yp',0,'\0'),(6,0,3,'2016-01-20 09:44:24',1,'admin','15902840428',3,1,'021601151120315',2,'呵呵',0,'\0'),(7,0,1,'2016-01-20 14:47:09',1,'admin','15902840428',3,NULL,'021601151120315',5,'',0,'\0'),(8,0,3,'2016-01-20 17:13:19',1,'admin','15902840428',3,NULL,'021601151046102',5,'hgc',0,'\0'),(9,0,1,'2016-01-20 17:21:12',1,'admin','15902840428',3,NULL,'021601151120315',0,'23',0,'\0'),(10,0,1,'2016-01-21 09:36:17',1,'admin','15902840428',3,NULL,'021601151046102',5,'123',0,'\0'),(11,0,1,'2016-01-21 09:36:32',1,'admin','15902840428',3,NULL,'021601151046102',5,'',0,'\0'),(12,0,1,'2016-01-21 09:39:08',1,'admin','15902840428',3,NULL,'021601151046102',3,'',0,'\0'),(13,0,1,'2016-01-21 10:24:57',1,'admin','15902840428',3,NULL,'021601151120315',0,'呵呵',0,'\0'),(14,0,1,'2016-01-21 11:32:47',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(15,0,3,'2016-01-21 11:55:04',1,'admin','15902840428',3,NULL,'021601151120315',0,'ggg',0,'\0'),(16,0,1,'2016-01-21 14:03:33',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(17,0,1,'2016-01-21 14:15:25',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(18,0,1,'2016-01-22 09:29:52',1,'admin','15902840428',3,NULL,'021601151046102',5,'12',0,'\0'),(19,0,1,'2016-01-22 09:32:02',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(20,0,1,'2016-01-22 11:13:36',1,'admin','15902840428',3,NULL,'021601151046102',5,'',0,'\0'),(21,0,1,'2016-01-22 11:51:42',1,'admin','15902840428',3,14,'021601151046102',2,'',0,'\0'),(22,0,5,'2016-01-22 11:53:00',1,'admin','15902840428',3,1,'021601151046102',2,'',0,'\0'),(23,1,5,'2016-01-25 18:27:02',1,'','',0,NULL,'021601191035749',1,'',0,'\0'),(24,0,3,'2016-01-26 10:57:27',1,'admin','15902840428',3,NULL,'021601151046102',0,'ghh',0,'\0'),(25,0,1,'2016-01-26 11:00:18',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(26,0,1,'2016-01-26 11:01:12',1,'admin','15902840428',3,NULL,'021601151120315',0,'hhh',0,'\0'),(27,0,1,'2016-01-26 11:01:15',1,'admin','15902840428',3,NULL,'021601151120315',0,'hhh',0,'\0'),(28,1,3,'2016-01-26 11:14:04',1,'','',1,NULL,'021601131753480',0,'',1,'\0'),(29,0,1,'2016-01-26 11:48:42',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(30,0,1,'2016-01-26 14:10:03',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(31,0,1,'2016-01-26 14:12:09',1,'admin','15902840428',3,NULL,'021601151046102',0,'123',0,'\0'),(32,0,1,'2016-01-27 09:34:12',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(33,0,1,'2016-01-27 14:33:31',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(34,0,1,'2016-01-27 14:38:33',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(35,0,1,'2016-01-27 14:44:14',1,'admin','15902840428',3,NULL,'021601151120315',0,'gggg',0,'\0'),(36,0,1,'2016-01-27 14:48:49',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(37,0,1,'2016-01-27 15:19:49',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(38,0,1,'2016-01-27 15:41:16',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(39,0,1,'2016-01-27 15:41:23',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(40,0,1,'2016-01-27 15:42:17',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(41,0,1,'2016-01-28 09:15:14',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(42,0,1,'2016-01-28 09:50:52',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(43,0,1,'2016-01-29 09:32:43',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(44,0,1,'2016-01-29 10:09:25',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(45,0,1,'2016-01-29 11:20:13',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(46,0,5,'2016-01-29 11:31:45',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(47,0,1,'2016-01-29 11:33:08',1,'admin','15902840428',3,NULL,'021601151120315',0,'rvd',0,'\0'),(48,0,1,'2016-01-29 14:56:48',1,'admin','15902840428',3,NULL,'021601151046102',0,'ghj',0,'\0'),(49,0,1,'2016-01-29 15:00:49',1,'admin','15902840428',3,NULL,'021601151046102',0,'ddgg',0,'\0'),(50,0,1,'2016-01-29 15:17:41',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(51,0,1,'2016-01-29 15:22:58',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(52,0,2,'2016-02-01 16:49:30',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(53,0,5,'2016-02-01 16:49:57',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(54,0,1,'2016-02-01 17:20:42',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(55,0,1,'2016-02-01 17:20:58',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(56,0,1,'2016-02-01 17:21:22',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(57,0,1,'2016-02-02 11:54:38',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(58,0,1,'2016-02-02 13:50:50',1,'admin','15902840428',3,NULL,'021601151046102',0,'QQ去',0,'\0'),(59,0,1,'2016-02-02 13:56:01',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(60,0,1,'2016-02-03 09:30:33',1,'admin','15902840428',3,NULL,'021601151120315',0,'',0,'\0'),(61,0,1,'2016-02-03 09:43:07',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(62,0,1,'2016-02-03 09:44:00',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(63,0,1,'2016-02-03 09:59:09',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(64,0,1,'2016-02-03 10:01:42',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(65,0,1,'2016-02-03 10:03:46',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(66,0,1,'2016-02-03 10:38:11',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0'),(67,0,1,'2016-02-03 10:40:11',1,'admin','15902840428',3,NULL,'021601151046102',0,'',0,'\0');

/*Table structure for table `problem_handle` */

DROP TABLE IF EXISTS `problem_handle`;

CREATE TABLE `problem_handle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attachment` varchar(255) DEFAULT NULL,
  `description` longtext,
  `handle_time` datetime NOT NULL,
  `handle_user_id` int(11) DEFAULT NULL,
  `problem_id` int(11) NOT NULL,
  `problem_situation` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `problem_handle` */

/*Table structure for table `problem_type` */

DROP TABLE IF EXISTS `problem_type`;

CREATE TABLE `problem_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_type` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `other_note` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `initial` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `problem_type` */

insert  into `problem_type`(`id`,`device_type`,`name`,`other_note`,`version`,`status`,`initial`) values (1,1,'线路故障','',2,0,''),(2,1,'灰尘阻塞',NULL,0,0,''),(3,1,'硬盘烧坏',NULL,0,0,''),(4,2,'网络故障',NULL,0,0,''),(5,1,'开机无响应',NULL,1,0,''),(6,1,'显示器有色斑',NULL,1,1,''),(7,0,'aaa','asdf',7,1,'\0');

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_cron_triggers` */

insert  into `qrtz_cron_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) values ('scheduler','23','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','3435','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','456','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','dfd','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','sd','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','tyu','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai'),('scheduler','测试放电计划','upsDischargeJob','0 0 0 * * ? *','Asia/Shanghai');

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_job_details` */

insert  into `qrtz_job_details`(`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) values ('scheduler','23','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\023t\01x\0'),('scheduler','3435','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\03435t\01x\0'),('scheduler','456','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\0456t\01x\0'),('scheduler','dfd','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\0dfdt\01x\0'),('scheduler','sd','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\0sdt\01x\0'),('scheduler','tyu','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\0tyut\01x\0'),('scheduler','测试放电计划','upsDischargeJob',NULL,'com.agama.pemm.service.schedule.UpsDischargeJob','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0scheduleJobsr\0&com.agama.authority.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0	classNamet\0Ljava/lang/String;L\0cronExpressionq\0~\0	L\0descriptionq\0~\0	L\0groupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0nameq\0~\0	L\0statusq\0~\0	xpt\0/com.agama.pemm.service.schedule.UpsDischargeJobt\0\r0 0 0 * * ? *t\0#计划任务:每日0时开始放电t\0upsDischargeJobpt\0测试放电计划t\01x\0');

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

insert  into `qrtz_locks`(`SCHED_NAME`,`LOCK_NAME`) values ('scheduler','STATE_ACCESS'),('scheduler','TRIGGER_ACCESS');

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

insert  into `qrtz_scheduler_state`(`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) values ('scheduler','PC-20150618ZDLX1454460301303',1454468140308,15000),('scheduler','SHECHAOJUN-PC1454467583146',1454468214719,15000);

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_triggers` */

insert  into `qrtz_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) values ('scheduler','23','upsDischargeJob','23','upsDischargeJob','计划任务:每日0时开始放电',1451491200000,-1,5,'PAUSED','CRON',1451457252000,0,NULL,0,''),('scheduler','3435','upsDischargeJob','3435','upsDischargeJob','计划任务:每日0时开始放电',1451577600000,-1,5,'PAUSED','CRON',1451542337000,0,NULL,0,''),('scheduler','456','upsDischargeJob','456','upsDischargeJob','计划任务:每日0时开始放电',1451923200000,-1,5,'PAUSED','CRON',1451878501000,0,NULL,0,''),('scheduler','dfd','upsDischargeJob','dfd','upsDischargeJob','计划任务:每日0时开始放电',1451577600000,-1,5,'PAUSED','CRON',1451542381000,0,NULL,0,''),('scheduler','sd','upsDischargeJob','sd','upsDischargeJob','计划任务:每日0时开始放电',1451577600000,-1,5,'PAUSED','CRON',1451542376000,0,NULL,0,''),('scheduler','tyu','upsDischargeJob','tyu','upsDischargeJob','计划任务:每日0时开始放电',1451923200000,-1,5,'PAUSED','CRON',1451878529000,0,NULL,0,''),('scheduler','测试放电计划','upsDischargeJob','测试放电计划','upsDischargeJob','计划任务:每日0时开始放电',1451491200000,1451457273645,5,'PAUSED','CRON',1451290720000,0,NULL,0,'');

/*Table structure for table `recycle` */

DROP TABLE IF EXISTS `recycle`;

CREATE TABLE `recycle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `is_recovery` int(11) NOT NULL,
  `op_time` datetime NOT NULL,
  `op_user_id` int(11) NOT NULL,
  `recovery_time` datetime DEFAULT NULL,
  `recovery_user_id` int(11) DEFAULT NULL,
  `table_name` varchar(255) NOT NULL,
  `table_record_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `recycle` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `ROLE_CODE` varchar(20) NOT NULL,
  `DESCRIPTION` text,
  `SORT` smallint(6) DEFAULT NULL,
  `DEL_FLAG` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`ID`,`NAME`,`ROLE_CODE`,`DESCRIPTION`,`SORT`,`DEL_FLAG`) values (1,'admin','admin','admin',2,NULL),(5,'guest','guest','guest',3,NULL),(13,'superadmin','superadmin','超级管理员',1,NULL),(14,'123','ad','',NULL,NULL),(16,'11','1','',NULL,NULL),(19,'23','12','',NULL,NULL),(20,'45','23','',NULL,NULL),(22,'1234','123','',NULL,NULL),(23,'1','2','',NULL,NULL),(24,'2','9','在这个相遇又分手的季节里，懵懂的小青春肆意拨动。\r\n我们在如痴的岁月里，看到了春暖花开，在深深的季节里，将\r\n时光静静的埋葬。我们看破红尘陌上，在流水孱弱的梦里，将青春\r\n默默的歌唱，散发着凄然的微光。我们尝过了忧伤，在回忆里的韶\r\n光里，将心慢慢的撕扯，试着忘记曾经的沧桑&hellip;&hellip;淡淡的时光里我\r\n们唱着青春凄美的乐章，一丝一缕，满怀希望。\r\n我们在时光的流逝里，无能为力的握住那看似美丽但却虚妄、\r\n悲戚的韶光，任凭它一点一点淡去，一点一点抽离，一点一点消亡\r\n，在岁月的无情里，在回忆的悲剧里&hellip;&hellip;\r\n&ldquo;你总说毕业遥遥无期，转眼就各奔东西&hellip;&hellip;&rdquo;\r\n我们总是害怕，害怕那一切的一切都被埋藏，在毕业的摧残下\r\n，曾经再疯狂、再狂妄的我们，也被磨光了棱角，再不可一世的我\r\n们，也低下了孤傲的头颅，透着不言一世的凄凉，眼角的泪水肆意\r\n流淌，穿过凄苦的心房，慢慢的沉淀，化为最深切的天堂，将一切\r\n的一切都埋葬，在那个分手的季节里&hellip;&hellip;\r\n都很怀念那一段段青春年少，狂妄无知的岁月，即使曾经沧海\r\n横流浪波乾坤，也只是虚无缥缈。我们青春在回忆里珍藏，在记忆\r\n里埋葬。舞动的青春是否还是那样纯真善良，透着迷人气息，在月\r\n光下徜徉，静静的流淌？\r\n记得漆黑月下，魅影无双，执手衷肠，看洪流月光，在你的心\r\n里欢唱。一支支青春的乐章，华丽的奏响，那飞舞的我们，是否还\r\n记得曾经的青春里我们说过的&ldquo;时光不老，我们不散&rdquo;的诺言？\r\n风吹云浮尘，花落雨空潭。在泛着记忆的时光里，青丝白发，\r\n只是苍茫一瞬而已。我们的青春故事，在毕业的季节里，断了，散\r\n了&hellip;&hellip;那飘荡的丝线，依旧在寻找着，回忆着，黯淡的过往。\r\n我的青春里，走过了笑声走过了忧伤。在清幽的落寞里，看到\r\n了多年以后你那经历风雨后沧桑但却成熟的面庞，你的目光，是否\r\n会看到角落里不起眼的我，是否会想起我们共有的青春乐章？\r\n我们的青春在淡淡的花香里，透着迷人的芬芳，透着淡淡的忧\r\n伤，一点点的沉淀，一点点的埋葬&hellip;&hellip;\r\n青春，在我们的人生里，早已丢失了最真的模样，但在我们的\r\n记忆里，依旧是最初的模样，伴随着淡淡的梦想，回忆的韶光在青\r\n春里泛黄。\r\n青春，未完待续，在你和我的回忆里',3,NULL),(25,'12345678912312312312','1235','在这个相遇又分手的季节里，懵懂的小青春肆意拨动。\r\n我们在如痴的岁月里，看到了春暖花开，在深深的季节里，将\r\n时光静静的埋葬。我们看破红尘陌上，在流水孱弱的梦里，将青春\r\n默默的歌唱，散发着凄然的微光。我们尝过了忧伤，在回忆里的韶\r\n光里，将心慢慢的撕扯，试着忘记曾经的沧桑&hellip;&hellip;淡淡的时光里我\r\n们唱着青春凄美的乐章，一丝一缕，满怀希望。\r\n我们在时光的流逝里，无能为力的握住那看似美丽但却虚妄、\r\n悲戚的韶光，任凭它一点一点淡去，一点一点抽离，一点一点消亡\r\n，在岁月的无情里，在回忆的悲剧里&hellip;&hellip;\r\n&ldquo;你总说毕业遥遥无期，转眼就各奔东西&hellip;&hellip;&rdquo;\r\n我们总是害怕，害怕那一切的一切都被埋藏，在毕业的摧残下\r\n，曾经再疯狂、再狂妄的我们，也被磨光了棱角，再不可一世的我\r\n们，也低下了孤傲的头颅，透着不言一世的凄凉，眼角的泪水肆意\r\n流淌，穿过凄苦的心房，慢慢的沉淀，化为最深切的天堂，将一切\r\n的一切都埋葬，在那个分手的季节里&hellip;&hellip;\r\n都很怀念那一段段青春年少，狂妄无知的岁月，即使曾经沧海\r\n横流浪波乾坤，也只是虚无缥缈。我们青春在回忆里珍藏，在记忆\r\n里埋葬。舞动的青春是否还是那样纯真善良，透着迷人气息，在月\r\n光下徜徉，静静的流淌？\r\n记得漆黑月下，魅影无双，执手衷肠，看洪流月光，在你的心',NULL,NULL),(26,'1111111','12343','天边亮起了一颗璀璨的星，那是他，我敬佩的他，小时候，妈妈常给我讲他的故事，他是我心中美丽的天使，他住在我心中最闪亮的地方。他，我所敬佩的他，就是老师。\r\n\r\n　　老师是知识的工程师；是进步的启蒙者；是抚摸学生心灵的膏药；是开启学生心灵的钥匙；是世界之灵魂。\r\n\r\n　　有人说，老师是辛勤的园丁，无微不至地照顾着祖国的花朵；有人说，老师是一截蜡烛，燃烧自己，照亮他人；有人说，老师是一支小小的粉笔，留下的是无穷无尽的知识，牺牲的是宝贵的生命；有人说&hellip;&hellip;这些说法，都无可否认，但我觉得老师更像善良的天使，他试着去让每个恶魔屈服，虽然恶魔是野蛮的，但是老师仍理直气壮，用知识教育恶魔，用行动感动恶魔，他总是尽力而为，量力而行，只要有一点点希望的曙光，那么他就会去感化每个恶魔。我的启蒙老师，不就是这样的吗？\r\n\r\n　　岁月匆匆，时间好象被什么穷追不舍似的，行云流水般地溜走了，溜得无影无踪。些许记忆已模糊，但对于老师的所作所为，我依旧清晰。\r\n\r\n　　教育成功的秘籍在于尊重学生。爱默生说得极对。我的老师不也是这样的吗？他视学生的尊严为泰山重。上课的时候，他有时会出些问题，让同学们去讨论，1111111111111111',NULL,NULL);

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) DEFAULT NULL,
  `PERMISSION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ROLE_PER_REFERENCE_PERMISSI` (`PERMISSION_ID`) USING BTREE,
  KEY `FK_ROLE_PER_REFERENCE_ROLE` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permission` (`ID`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=449 DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

insert  into `role_permission`(`ID`,`ROLE_ID`,`PERMISSION_ID`) values (1,1,2),(2,1,1),(28,5,1),(61,13,1),(62,13,3),(63,13,16),(64,13,7),(65,13,2),(66,13,4),(67,13,5),(68,13,6),(69,13,12),(70,13,25),(71,13,26),(72,13,27),(74,13,15),(75,13,14),(76,13,20),(77,13,8),(81,1,3),(88,1,12),(94,1,14),(95,1,20),(118,1,28),(120,1,30),(121,1,31),(125,1,33),(126,1,36),(127,1,35),(129,1,34),(130,1,32),(133,5,15),(162,5,39),(164,5,58),(176,5,40),(177,1,39),(178,1,58),(179,1,59),(183,1,4),(184,1,6),(185,1,26),(186,1,27),(187,1,5),(189,1,25),(190,1,45),(191,1,7),(192,1,8),(193,1,16),(194,13,28),(195,13,34),(196,13,36),(197,13,29),(198,13,33),(199,13,35),(200,13,30),(201,13,39),(202,13,45),(203,13,58),(204,13,59),(205,13,40),(206,13,61),(207,13,62),(208,13,63),(209,13,31),(210,13,32),(211,13,37),(212,13,38),(215,5,69),(216,5,20),(219,5,68),(220,5,38),(221,1,70),(222,5,70),(223,5,3),(227,5,29),(228,5,33),(229,5,35),(231,5,2),(234,5,28),(235,5,45),(236,5,59),(239,5,36),(240,1,68),(244,1,74),(246,1,76),(247,1,77),(248,1,78),(255,1,83),(256,1,85),(257,1,86),(258,1,87),(259,1,88),(260,1,89),(261,1,90),(268,1,97),(269,1,98),(270,1,99),(271,1,100),(279,1,101),(280,1,102),(281,1,103),(282,1,104),(284,1,105),(285,1,91),(286,1,92),(287,1,93),(288,1,94),(289,1,95),(290,1,96),(291,1,106),(292,1,107),(293,1,108),(295,1,110),(296,1,111),(297,1,112),(298,1,113),(299,1,114),(300,1,115),(301,1,116),(302,1,117),(303,1,121),(304,1,120),(305,1,119),(306,1,118),(307,1,122),(308,1,123),(309,1,124),(310,1,125),(311,1,126),(312,1,127),(313,1,128),(314,1,129),(315,1,130),(316,1,131),(317,1,132),(318,1,133),(319,19,2),(320,19,1),(321,19,4),(322,19,5),(323,19,6),(324,19,28),(325,19,34),(326,19,36),(327,19,3),(328,19,7),(329,19,8),(330,19,16),(331,19,29),(332,19,33),(333,19,35),(334,19,76),(335,19,77),(336,19,107),(337,19,12),(338,19,25),(339,19,26),(340,19,27),(341,19,30),(342,19,45),(343,19,39),(344,19,59),(345,19,58),(357,1,134),(358,1,40),(359,1,61),(360,1,62),(361,1,63),(362,1,69),(363,1,137),(364,1,138),(368,1,75),(369,1,29),(370,22,45),(371,22,39),(372,22,1),(373,22,58),(374,22,59),(375,22,61),(376,22,40),(377,22,62),(378,22,63),(379,22,137),(380,22,74),(381,22,138),(382,23,45),(383,23,39),(384,23,1),(385,23,58),(386,23,70),(387,23,40),(388,23,61),(389,23,62),(390,23,63),(391,23,69),(394,1,144),(395,1,145),(396,1,146),(399,24,2),(400,24,1),(401,24,4),(402,24,5),(403,24,6),(404,24,28),(405,24,34),(406,24,36),(407,1,152),(408,1,151),(409,1,153),(410,1,155),(411,1,154),(412,1,156),(413,1,157),(414,1,158),(415,1,159),(416,1,160),(417,1,161),(418,1,162),(419,1,164),(420,1,163),(421,1,165),(422,1,166),(423,1,167),(424,1,168),(425,1,169),(426,1,170),(427,1,171),(428,1,173),(429,1,174),(430,1,175),(431,1,176),(432,1,177),(433,1,178),(434,1,179),(435,1,180),(436,1,181),(440,1,185),(441,1,184),(442,1,182),(443,1,186),(444,1,172),(445,1,187),(446,1,188),(447,1,189),(448,1,190);

/*Table structure for table `send_config` */

DROP TABLE IF EXISTS `send_config`;

CREATE TABLE `send_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `send_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `send_config` */

/*Table structure for table `sms` */

DROP TABLE IF EXISTS `sms`;

CREATE TABLE `sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `baud_rate` int(11) DEFAULT NULL,
  `com_port` varchar(255) DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  `gateway_id` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sms` */

/*Table structure for table `two_dimention_code` */

DROP TABLE IF EXISTS `two_dimention_code`;

CREATE TABLE `two_dimention_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `is_print` int(11) NOT NULL,
  `last_print_time` date DEFAULT NULL,
  `print_quantity` int(11) NOT NULL,
  `print_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `two_dimention_code` */

/*Table structure for table `unintelligent_device` */

DROP TABLE IF EXISTS `unintelligent_device`;

CREATE TABLE `unintelligent_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `manufacture_date` datetime DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` int(11) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_device_type_id` int(11) NOT NULL,
  `warranty_date` datetime DEFAULT NULL,
  `manager_name` varchar(255) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `purchase_name` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `user_device_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `unintelligent_device` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(20) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `SALT` varchar(255) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `GENDER` smallint(6) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `ICON` varchar(500) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `STATE` char(1) DEFAULT NULL,
  `DESCRIPTION` text,
  `LOGIN_COUNT` int(11) DEFAULT NULL,
  `PREVIOUS_VISIT` datetime DEFAULT NULL,
  `LAST_VISIT` datetime DEFAULT NULL,
  `DEL_FLAG` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`ID`,`LOGIN_NAME`,`NAME`,`PASSWORD`,`SALT`,`BIRTHDAY`,`GENDER`,`EMAIL`,`PHONE`,`ICON`,`CREATE_DATE`,`STATE`,`DESCRIPTION`,`LOGIN_COUNT`,`PREVIOUS_VISIT`,`LAST_VISIT`,`DEL_FLAG`) values (1,'admin','admin','fc247406a019b9acd4d22152a2bf3fa036e7d8e5','bde77f95fda71dcf','2014-03-16 00:00:00',1,'779205344@qq.com','15902840428','aaa','2014-03-20 14:38:57','0','',1964,'2016-02-03 10:51:48','2016-02-03 10:55:15',NULL),(6,'superadmin','超级管理员','df894ac0dd60772f22b5d67fe5d8b04fb4c9188d','97efb48ee6adff63','2015-01-15 00:00:00',1,'779205344@qq.com','18227621922',NULL,'2015-01-15 15:55:37',NULL,'超级管理员',NULL,NULL,NULL,NULL),(7,'11111','111','eba14361d84fe4954e180fa104d54f0fcfbf22b7','664dc0a18f38de8d',NULL,1,'','',NULL,'2015-12-25 17:51:31',NULL,'',NULL,NULL,NULL,NULL),(14,'administrator','administrator','b976668b3ce70d2b499abed63d275d40799d732b','03011716338c6990',NULL,0,'1182584458@qq.com','13648032853',NULL,'2015-12-30 17:07:55',NULL,'',NULL,NULL,NULL,NULL),(15,'456321','123','9fabe33f0a0a55b18e9807883772d6fadeda56a5','39a3a06f79c724f7',NULL,1,'','',NULL,'2016-01-04 10:09:38',NULL,'',NULL,NULL,NULL,NULL),(19,'123456','123456','78f343292382ddefbf55e0e608e7aa19c0c7a667','e479491343ed856c',NULL,1,'','',NULL,'2016-01-04 10:49:48',NULL,'',NULL,NULL,NULL,NULL),(20,'1234567','111111','aa90b293813f01e30d1b727cdc2737f63556869d','c7c483450a491c03',NULL,1,'','',NULL,'2016-01-04 10:53:00',NULL,'',NULL,NULL,NULL,NULL),(21,'4567889','111111','b2111395529f4c389be78907e0f9b30679f65520','9124b8afc32c0b4f','2016-01-14 00:00:00',1,'','13285345697',NULL,'2016-01-05 09:11:36',NULL,'在这个相遇又分手的季节里，懵懂的小青春肆意拨动。\r\n我们在如痴的岁月里，看到了春暖花开，在深深的季节里，将\r\n时光静静的埋葬。我们看破红尘陌上，在流水孱弱的梦里，将青春\r\n默默的歌唱，散发着凄然的微光。我们尝过了忧伤，在回忆里的韶\r\n光里，将心慢慢的撕扯，试着忘记曾经的沧桑&hellip;&hellip;淡淡的时光里我\r\n们唱着青春凄美的乐章，一丝一缕，满怀希望。\r\n我们在时光的流逝里，无能为力的握住那看似美丽但却虚妄、\r\n悲戚的韶光，任凭它一点一点淡去，一点一点抽离，一点一点消亡\r\n，在岁月的无情里，在回忆的悲剧里&hellip;&hellip;\r\n&ldquo;你总说毕业遥遥无期，转眼就各奔东西&hellip;&hellip;&rdquo;\r\n我们总是害怕，害怕那一切的一切都被埋藏，在毕业的摧残下\r\n，曾经再疯狂、再狂妄的我们，也被磨光了棱角，再不可一世的我\r\n们，也低下了孤傲的头颅，透着不言一世的凄凉，眼角的泪水肆意\r\n流淌，穿过凄苦的心房，慢慢的沉淀，化为最深切的天堂，将一切\r\n的一切都埋葬，在那个分手的季节里&hellip;&hellip;\r\n都很怀念那一段段青春年少，狂妄无知的岁月，即使曾经沧海\r\n横流浪波乾坤，也只是虚无缥缈。我们青春在回忆里珍藏，在记忆\r\n里埋葬。舞动的青春是否还是那样纯真善良，透着迷人气息，在月\r\n光下徜徉，静静的流淌？\r\n记得漆黑月下，魅影无双，执手衷肠，看洪流月光，在你的心\r\n里欢唱。一支支青春的乐章，华丽的奏响，那飞舞的我们，是否还\r\n记得曾经的青春里我们说过的&ldquo;时光不老，我们不散&rdquo;的诺言？\r\n风吹云浮尘，花落雨空潭。在泛着记忆的时光里，青丝白发，\r\n只是苍茫一瞬而已。我们的青春故事，在毕业的季节里，断了，散\r\n了&hellip;&hellip;那飘荡的丝线，依旧在寻找着，回忆着，黯淡的过往。\r\n我的青春里，走过了笑声走过了忧伤。在清幽的落寞里，看到\r\n了多年以后你那经历风雨后沧桑但却成熟的面庞，你的目光，是否\r\n会看到角落里不起眼的我，是否会想起我们共有的青春乐章？\r\n我们的青春在淡淡的花香里，透着迷人的芬芳，透着淡淡的忧\r\n伤，一点点的沉淀，一点点的埋葬&hellip;&hellip;\r\n青春，在我们的人生里，早已丢失了最真的模样，但在我们的\r\n记忆里，依旧是最初的模样，伴随着淡淡的梦想，回忆的韶光在青\r\n春里泛黄。\r\n青春，未完待续，在你和我的回忆里',NULL,NULL,NULL,NULL),(22,'12345678912345678912','11111111111111111111','3bd40a98c0a2a37ab4192733ca21cc35662d03d2','7c28474382c0720c',NULL,1,'','13645678912',NULL,'2016-01-05 11:07:24',NULL,'在这个相遇又分手的季节里，懵懂的小青春肆意拨动。\r\n我们在如痴的岁月里，看到了春暖花开，在深深的季节里，将\r\n时光静静的埋葬。我们看破红尘陌上，在流水孱弱的梦里，将青春\r\n默默的歌唱，散发着凄然的微光。我们尝过了忧伤，在回忆里的韶\r\n光里，将心慢慢的撕扯，试着忘记曾经的沧桑&hellip;&hellip;淡淡的时光里我\r\n们唱着青春凄美的乐章，一丝一缕，满怀希望。\r\n我们在时光的流逝里，无能为力的握住那看似美丽但却虚妄、\r\n悲戚的韶光，任凭它一点一点淡去，一点一点抽离，一点一点消亡\r\n，在岁月的无情里，在回忆的悲剧里&hellip;&hellip;\r\n&ldquo;你总说毕业遥遥无期，转眼就各奔东西&hellip;&hellip;&rdquo;\r\n我们总是害怕，害怕那一切的一切都被埋藏，在毕业的摧残下\r\n，曾经再疯狂、再狂妄的我们，也被磨光了棱角，再不可一世的我\r\n们，也低下了孤傲的头颅，透着不言一世的凄凉，眼角的泪水肆意\r\n流淌，穿过凄苦的心房，慢慢的沉淀，化为最深切的天堂，将一切\r\n的一切都埋葬，在那个分手的季节里&hellip;&hellip;\r\n都很怀念那一段段青春年少，狂妄无知的岁月，即使曾经沧海\r\n横流浪波乾坤，也只是虚无缥缈。我们青春在回忆里珍藏，在记忆\r\n里埋葬。舞动的青春是否还是那样纯真善良，透着迷人气息，在月\r\n光下徜徉，静静的流淌？\r\n记得漆黑月下，魅影无双，执手衷肠，看洪流月光，在你的心',NULL,NULL,NULL,NULL),(23,'12121212121212222121','1111','1586fef628982f189629b56f7951d161886e94b9','3511cb2ffeed4cdf',NULL,1,'','',NULL,'2016-01-06 13:57:16',NULL,'在亚马逊丛林中，我们这支探险队异常艰辛的跋涉着。五个探险队员中已有二个遇难，一个是被沼泽吞噬，另一个则遭到了野兽的袭击。可剩下的队员们仍在继续&hellip;&hellip;\r\n　　&ldquo;也许那传说中的&lsquo;麻族&rsquo;根本就不存在，人们只是捕风捉影罢了&rdquo;，杰克大喊道。&ldquo;是呀！再这么下去，人们都会死在这儿，&rdquo;西蒙也附和道。我们现在的处境看起来是九死一生，弹尽粮绝的我们看不到一点生的希望，仅剩下一艘破船和一架摄像机。我们现在唯一能做的就是孤注一掷，完成这次探险任务。\r\n　　一天工作后，疲惫取代了一切，我们很快就睡去了。船不知自己漂了多久，我们醒来时已是黄昏。太阳将要藏到丛林中去了，大自然使我们看起来是多么的脆弱与渺小。一层金沙裹住了丛林青涩的脸庞。终于，没多久，不太冷的月光便照在船头上。凉凉的风将我们脸边湿热的空气吹散。我们都沉浸在大自然的安抚中。可是谁也不知灾难来临。我们都被这种美丽所迷惑，因为在与鳄鱼搏斗时，船上的锚不见了，所以我让西蒙潜入水底，把强子绑在了湖底的枯树根上。我在船上安祥的躺着。突然，湖面的风变的凛冽起来，血腥味迎面而来。&ldquo;哦！杰克，小心身后！&rdquo;一只巨蟒像一道闪电钻入水中。我抄起刀子也跳入水中，水中一片漆黑，我什么都看不见，找了一会儿后，我麻木的上了船。刹时间，绝望与恐惧让我眼前一片漆黑。我突然意识到西蒙也失踪了，恐惧袜子的漏洞，慢扩大。我就如同是茫茫大海中的一叶扁舟，时时刻刻都是命悬一线，危险无处不在。\r\n　　我对自己的生死已不抱多大希望。现在只想找到&ldquo;麻族&rdquo;，那个传说中的原始部落，这样我的伙伴死得也会有价值。\r\n　　经过一个月的跋涉，我已经半人半鬼&mdash;&mdash;我少了两根半指，因为被蛇咬了我不得不砍掉。我的一只眼睛被狒狒抓瞎了，耳朵也莫明奇妙地失聪了。一个多月以来我如同在地狱中度过。\r\n　　然而就在一个清晨，我的下肢因浮肿已经站不起来了，我知道死亡在咄咄逼近，可就在这时，我透过大雾，朦胧的看到几个人影，其中的一个是西蒙。他朝我跑过来。我暗暗自喜道：任务完成了！\r\n　　朝阳斜射，大自然露出了少女一样青涩的笑。.................asdfghjkl;zxcvbnm,.',NULL,NULL,NULL,NULL),(24,'11111111111111111111','11111111111111111111','a9671b90a34c42f3989f2badd3250b8c64328307','b461644124366e4a',NULL,1,'','',NULL,'2016-01-06 14:07:01',NULL,'在亚马逊丛林中，我们这支探险队异常艰辛的跋涉着。五个探\r\n险队员中已有二个遇难，一个是被沼泽吞噬，另一个则遭到了野兽\r\n的袭击。可剩下的队员们仍在继续&hellip;&hellip;\r\n&ldquo;也许那传说中的&lsquo;麻族&rsquo;根本就不存在，人们只是捕风捉影\r\n罢了&rdquo;，杰克大喊道。&ldquo;是呀！再这么下去，人们都会死在这儿，\r\n&rdquo;西蒙也附和道。我们现在的处境看起来是九死一生，弹尽粮绝的\r\n我们看不到一点生的希望，仅剩下一艘破船和一架摄像机。我们现\r\n在唯一能做的就是孤注一掷，完成这次探险任务。\r\n一天工作后，疲惫取代了一切，我们很快就睡去了。船不知自\r\n己漂了多久，我们醒来时已是黄昏。太阳将要藏到丛林中去了，大\r\n自然使我们看起来是多么的脆弱与渺小。一层金沙裹住了丛林青涩\r\n的脸庞。终于，没多久，不太冷的月光便照在船头上。凉凉的风将\r\n我们脸边湿热的空气吹散。我们都沉浸在大自然的安抚中。可是谁\r\n也不知灾难来临。我们都被这种美丽所迷惑，因为在与鳄鱼搏斗时\r\n，船上的锚不见了，所以我让西蒙潜入水底，把强子绑在了湖底的\r\n枯树根上。我在船上安祥的躺着。突然，湖面的风变的凛冽起来，\r\n血腥味迎面而来。&ldquo;哦！杰克，小心身后！&rdquo;一只巨蟒像一道闪电\r\n钻入水中。我抄起刀子也跳入水中，水中一片漆黑，我什么都看不\r\n见，找了一会儿后，我麻木的上了船。刹时间，绝望与恐惧让我眼\r\n前一片漆黑。我突然意识到西蒙也失踪了，恐惧袜子的漏洞，慢扩\r\n大。我就如同是茫茫大海中的一叶扁舟，时时刻刻都是命悬一线，\r\n危险无处不在。\r\n我对自己的生死已不抱多大希望。现在只想找到&ldquo;麻族&rdquo;，那\r\n个传说中的原始部落，这样我的伙伴死得也会有价值。\r\n经过一个月的跋涉，我已经半人半鬼&mdash;&mdash;我少了两根半指，因\r\n为被蛇咬了我不得不砍掉。我的一只眼睛被狒狒抓瞎了，耳朵也莫\r\n明奇妙地失聪了。一个多月以来我如同在地狱中度过。\r\n然而就在一个清晨，我的下肢因浮肿已经站不起来了，我知道\r\n死亡在咄咄逼近，可就在这时，我透过大雾，朦胧的看到几个人影\r\n，其中的一个是西蒙。他朝我跑过来。我暗暗自喜道：任务完成了！\r\n朝阳斜射，大自然露出了少女一样青涩的笑',NULL,NULL,NULL,NULL),(25,'11111111111111111111','11111111111111111111','0b741fd01cc5a85cc7bfd598266959f9f36087ab','e29c2168f0552c10',NULL,1,'','',NULL,'2016-01-06 14:07:01',NULL,'在亚马逊丛林中，我们这支探险队异常艰辛的跋涉着。五个探\r\n险队员中已有二个遇难，一个是被沼泽吞噬，另一个则遭到了野兽\r\n的袭击。可剩下的队员们仍在继续&hellip;&hellip;\r\n&ldquo;也许那传说中的&lsquo;麻族&rsquo;根本就不存在，人们只是捕风捉影\r\n罢了&rdquo;，杰克大喊道。&ldquo;是呀！再这么下去，人们都会死在这儿，\r\n&rdquo;西蒙也附和道。我们现在的处境看起来是九死一生，弹尽粮绝的\r\n我们看不到一点生的希望，仅剩下一艘破船和一架摄像机。我们现\r\n在唯一能做的就是孤注一掷，完成这次探险任务。\r\n一天工作后，疲惫取代了一切，我们很快就睡去了。船不知自\r\n己漂了多久，我们醒来时已是黄昏。太阳将要藏到丛林中去了，大\r\n自然使我们看起来是多么的脆弱与渺小。一层金沙裹住了丛林青涩\r\n的脸庞。终于，没多久，不太冷的月光便照在船头上。凉凉的风将\r\n我们脸边湿热的空气吹散。我们都沉浸在大自然的安抚中。可是谁\r\n也不知灾难来临。我们都被这种美丽所迷惑，因为在与鳄鱼搏斗时\r\n，船上的锚不见了，所以我让西蒙潜入水底，把强子绑在了湖底的\r\n枯树根上。我在船上安祥的躺着。突然，湖面的风变的凛冽起来，\r\n血腥味迎面而来。&ldquo;哦！杰克，小心身后！&rdquo;一只巨蟒像一道闪电\r\n钻入水中。我抄起刀子也跳入水中，水中一片漆黑，我什么都看不\r\n见，找了一会儿后，我麻木的上了船。刹时间，绝望与恐惧让我眼\r\n前一片漆黑。我突然意识到西蒙也失踪了，恐惧袜子的漏洞，慢扩\r\n大。我就如同是茫茫大海中的一叶扁舟，时时刻刻都是命悬一线，\r\n危险无处不在。\r\n我对自己的生死已不抱多大希望。现在只想找到&ldquo;麻族&rdquo;，那\r\n个传说中的原始部落，这样我的伙伴死得也会有价值。\r\n经过一个月的跋涉，我已经半人半鬼&mdash;&mdash;我少了两根半指，因\r\n为被蛇咬了我不得不砍掉。我的一只眼睛被狒狒抓瞎了，耳朵也莫\r\n明奇妙地失聪了。一个多月以来我如同在地狱中度过。\r\n然而就在一个清晨，我的下肢因浮肿已经站不起来了，我知道\r\n死亡在咄咄逼近，可就在这时，我透过大雾，朦胧的看到几个人影\r\n，其中的一个是西蒙。他朝我跑过来。我暗暗自喜道：任务完成了！\r\n朝阳斜射，大自然露出了少女一样青涩的笑',NULL,NULL,NULL,NULL),(26,'12121212122221212121','1111111','15337f709b855576e330c53dee6539068b587270','36702baad4d8364c',NULL,1,'','',NULL,'2016-01-07 13:57:52',NULL,'天边亮起了一颗璀璨的星，那是他，我敬佩的他，小时候，妈妈常给我讲他的故事，他是我心中美丽的天使，他住在我心中最闪亮的地方。他，我所敬佩的他，就是老师。\r\n\r\n　　老师是知识的工程师；是进步的启蒙者；是抚摸学生心灵的膏药；是开启学生心灵的钥匙；是世界之灵魂。\r\n\r\n　　有人说，老师是辛勤的园丁，无微不至地照顾着祖国的花朵；有人说，老师是一截蜡烛，燃烧自己，照亮他人；有人说，老师是一支小小的粉笔，留下的是无穷无尽的知识，牺牲的是宝贵的生命；有人说&hellip;&hellip;这些说法，都无可否认，但我觉得老师更像善良的天使，他试着去让每个恶魔屈服，虽然恶魔是野蛮的，但是老师仍理直气壮，用知识教育恶魔，用行动感动恶魔，他总是尽力而为，量力而行，只要有一点点希望的曙光，那么他就会去感化每个恶魔。我的启蒙老师，不就是这样的吗？\r\n\r\n　　岁月匆匆，时间好象被什么穷追不舍似的，行云流水般地溜走了，溜得无影无踪。些许记忆已模糊，但对于老师的所作所为，我依旧清晰。\r\n\r\n　　教育成功的秘籍在于尊重学生。爱默生说得极对。我的老师不也是这样的吗？他视学生的尊严为泰山重。上课的时候，他有时会出些问题，让同学们去讨论，1111111111111111',NULL,NULL,NULL,NULL);

/*Table structure for table `user_device_type` */

DROP TABLE IF EXISTS `user_device_type`;

CREATE TABLE `user_device_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `other_note` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_device_type` */

/*Table structure for table `user_org` */

DROP TABLE IF EXISTS `user_org`;

CREATE TABLE `user_org` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `user_id` int(9) NOT NULL,
  `org_id` int(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `user_org` */

insert  into `user_org`(`id`,`user_id`,`org_id`) values (10,3,2),(11,5,2),(19,6,1),(20,6,2),(21,6,3),(22,1,2),(23,1,3),(24,1,4),(25,14,1),(26,16,15),(27,15,3);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_USER_ROL_REFERENCE_ROLE` (`ROLE_ID`) USING BTREE,
  KEY `FK_USER_ROL_REFERENCE_USERS` (`USER_ID`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`ID`,`USER_ID`,`ROLE_ID`) values (1,1,1),(35,6,13),(36,6,1),(38,14,1),(39,14,5),(40,14,13),(42,15,13),(43,22,23);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
