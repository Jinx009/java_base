package database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_gnss_rtk_battery")
public class GnssRtkBatteryLog {

//	{
//	    "msg": "success",
//	    "code": 1000,
//	    "data": {
//	        "outer_temperature": 17,
//	        "battery_voltage": 13.1,
//	        "solar_panel_circuit": 1.6,
//	        "life_expectancy": 7.9856,
//	        "hardware_version": null,
//	        "led_power": 1.3,
//	        "battery_circuit": 1.85,
//	        "is_lighting": 1,
//	        "charge_status": 1,
//	        "count_overcharge": 1,
//	        "residual_life": 99.82,
//	        "battery_percent": 85,
//	        "inner_temperature": 17,
//	        "solar_panel_voltage": 16.0,
//	        "led_voltage": 13.0,
//	        "total_discharge": 110.0,
//	        "led_circuit": 0.1,
//	        "timestamp": 1606706236719,
//	        "battery_power": 24.3,
//	        "led_brightness": 0,
//	        "solar_panel_power": 25.6,
//	        "charge_capacity": 52.0,
//	        "count_discharge": 2,
//	        "discharge_capacity": 39.0,
//	        "total_charge": 52.0,
//	        "version": null,
//	        "cycle_index": 1.83,
//	        "is_warning": 0,
//	        "battery_capacity": 60
//	    }
//	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "life_expectancy")
	private String life_expectancy;//电池预期寿命
	@Column(name = "residual_life")
	private String residual_life;//电池预期寿命百分比
	@Column(name ="cycle_index")
	private String cycle_index;//电池循环次数
	@Column(name = "is_warning")
	private String is_warning;//是否告警 0 否 1 短路保护 2 过流保护 3 过温保护 4 低压保护 5 开路保护
	@Column(name = "solar_panel_power")
	private String solar_panel_power;//太阳能板功率
	@Column(name = "solar_panel_voltage")
	private String solar_panel_voltage;//太阳能板电压
	@Column(name = "solar_panel_circuit")
	private String solar_panel_circuit;//太阳能板电流
	@Column(name = "charge_capacity")
	private String charge_capacity;//充电量
	@Column(name = "discharge_capacity")
	private String discharge_capacity;//放电量
	@Column(name = "count_discharge")
	private String count_discharge;//过充次数
	@Column(name = "count_overcharge")
	private String count_overcharge;//过放次数
	@Column(name = "total_charge")
	private String total_charge;//累计充电量
	@Column(name = "total_discharge")
	private String total_discharge;//累计放电量
	@Column(name = "charge_status")
	private String charge_status;//充电状态(1 充电中 2 未充电)
	@Column(name = "update_time")
	private String update_time;
	@Column(name = "battery_percent")
	private String battery_percent;//电池电量百分比
	@Column(name = "inner_temperature")
	private String inner_temperature;//控制器温度
	@Column(name = "outer_temperature")
	private String outer_temperature;//控制器外温度
	@Column(name = "battery_capacity")
	private String battery_capacity;//电池容量
	@Column(name = "battery_circuit")
	private String battery_circuit;//电池电流
	@Column(name = "is_lighting")
	private String is_lighting;
	@Column(name = "battery_voltage")
	private String battery_voltage;//电池电压
	@Column(name = "battery_power")
	private String battery_power;//电池功率
	@Column(name = "led_power")
	private String led_power;
	@Column(name = "led_voltage")
	private String led_voltage;
	@Column(name = "led_circuit")
	private String led_circuit;
	@Column(name = "led_brightness")
	private String led_brightness;
	@Column(name = "create_time")
	private Date create_time;
	@Column(name = "imei")
	private String imei;
}
