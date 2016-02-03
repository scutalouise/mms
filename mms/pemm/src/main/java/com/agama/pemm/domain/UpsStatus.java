package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.agama.authority.entity.BaseDomain;

@Entity
public class UpsStatus extends BaseDomain {
	private static final long serialVersionUID = -6272206113801603493L;
	private String name;
	private int interfaceType;
	private int communicationStatus;
	private int dischargePatterns;
	private int upsType;  //UPS类型 0:未知,1:二相
	private String modelNumber;
	private String brand;
	private String versionNumber;
	private double rateVoltage;
	private double ratedCurrent;
	private double ratedFrequency;
	private double batteryVoltage;
	private double power;
	private String upsStatus;
	private double frequency;
	private double internalTemperature;
	private String bypassVoltage;
	private double bypassFrequency;
	private String inputVoltage;
	private String outputVoltage;
	private double errorVoltage;
	
	private String upsLoad;
	private double outputFrenquency;
	private double singleVoltage;
	private double totalVoltage;
	private double electricQuantity;
	private double passCurrent;
	private int remainingTime; 
	private Date collectTime;
	
	private int status;
	/**
	 * 市电电压状态（1：异常）
	 */
	private String cityVoltageStatus;
	/**
	 * 电池电压状态（1：电池电压低）
	 */
	private String batteryVoltageStatus;
	/**
	 * 运行状态（1：旁路）
	 */
	private String runningStatus; 
	/**
	 * 测试状态（1：测试中）
	 */
	private String testStatus;
	/**
	 * UPS模式（1：后备式，0：在线式）
	 */
	private String patternsStatus;
	
	/**
	 * 关机状态（1：关机有效）
	 */
	private String shutdownStatus;
	
	/**
	 * 蜂鸣器状态（1：蜂鸣器开）
	 */
	private String buzzerStatus;
	
	/**
	 * 放电状态0为正常
	 */
	private Integer dischargeStatus;
	@Transient
	private Integer deviceIndex;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deviceId")
	private Device device;
	@Transient
	private Integer deviceId;
	@Transient
	private String deviceName;
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	private Integer linkState;
	
	@Transient
	private Integer currentState;
	
	public Integer getCurrentState() {
		return currentState;
	}
	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(int interfaceType) {
		this.interfaceType = interfaceType;
	}
	public int getCommunicationStatus() {
		return communicationStatus;
	}
	public void setCommunicationStatus(int communicationStatus) {
		this.communicationStatus = communicationStatus;
	}
	public int getDischargePatterns() {
		return dischargePatterns;
	}
	public void setDischargePatterns(int dischargePatterns) {
		this.dischargePatterns = dischargePatterns;
	}
	public int getUpsType() {
		return upsType;
	}
	public void setUpsType(int upsType) {
		this.upsType = upsType;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public double getRateVoltage() {
		return rateVoltage;
	}
	public void setRateVoltage(double rateVoltage) {
		this.rateVoltage = rateVoltage;
	}
	
	
	
	public double getRatedFrequency() {
		return ratedFrequency;
	}
	public void setRatedFrequency(double ratedFrequency) {
		this.ratedFrequency = ratedFrequency;
	}
	public double getRatedCurrent() {
		return ratedCurrent;
	}
	public void setRatedCurrent(double ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}
	public double getBatteryVoltage() {
		return batteryVoltage;
	}
	public void setBatteryVoltage(double batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public String getUpsStatus() {
		return upsStatus;
	}
	public void setUpsStatus(String upsStatus) {
		this.upsStatus = upsStatus;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public double getInternalTemperature() {
		return internalTemperature;
	}
	public void setInternalTemperature(double internalTemperature) {
		this.internalTemperature = internalTemperature;
	}
	public String getBypassVoltage() {
		return bypassVoltage;
	}
	public void setBypassVoltage(String bypassVoltage) {
		this.bypassVoltage = bypassVoltage;
	}
	public double getBypassFrequency() {
		return bypassFrequency;
	}
	public void setBypassFrequency(double bypassFrequency) {
		this.bypassFrequency = bypassFrequency;
	}
	public String getInputVoltage() {
		return inputVoltage;
	}
	public void setInputVoltage(String inputVoltage) {
		this.inputVoltage = inputVoltage;
	}
	public String getOutputVoltage() {
		return outputVoltage;
	}
	public void setOutputVoltage(String outputVoltage) {
		this.outputVoltage = outputVoltage;
	}
	public double getErrorVoltage() {
		return errorVoltage;
	}
	public void setErrorVoltage(double errorVoltage) {
		this.errorVoltage = errorVoltage;
	}
	
	public String getUpsLoad() {
		return upsLoad;
	}
	public void setUpsLoad(String upsLoad) {
		this.upsLoad = upsLoad;
	}
	public double getOutputFrenquency() {
		return outputFrenquency;
	}
	public void setOutputFrenquency(double outputFrenquency) {
		this.outputFrenquency = outputFrenquency;
	}
	public double getSingleVoltage() {
		return singleVoltage;
	}
	public void setSingleVoltage(double singleVoltage) {
		this.singleVoltage = singleVoltage;
	}
	public double getTotalVoltage() {
		return totalVoltage;
	}
	public void setTotalVoltage(double totalVoltage) {
		this.totalVoltage = totalVoltage;
	}
	public double getElectricQuantity() {
		return electricQuantity;
	}
	public void setElectricQuantity(double electricQuantity) {
		this.electricQuantity = electricQuantity;
	}
	public double getPassCurrent() {
		return passCurrent;
	}
	public void setPassCurrent(double passCurrent) {
		this.passCurrent = passCurrent;
	}
	public int getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getPatternsStatus() {
		return patternsStatus;
	}
	public void setPatternsStatus(String patternsStatus) {
		this.patternsStatus = patternsStatus;
	}
	public String getCityVoltageStatus() {
		return cityVoltageStatus;
	}
	public void setCityVoltageStatus(String cityVoltageStatus) {
		this.cityVoltageStatus = cityVoltageStatus;
	}
	
	
	public String getRunningStatus() {
		return runningStatus;
	}
	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}
	public String getShutdownStatus() {
		return shutdownStatus;
	}
	public void setShutdownStatus(String shutdownStatus) {
		this.shutdownStatus = shutdownStatus;
	}
	public String getBatteryVoltageStatus() {
		return batteryVoltageStatus;
	}
	public void setBatteryVoltageStatus(String batteryVoltageStatus) {
		this.batteryVoltageStatus = batteryVoltageStatus;
	}
	public String getBuzzerStatus() {
		return buzzerStatus;
	}
	public void setBuzzerStatus(String buzzerStatus) {
		this.buzzerStatus = buzzerStatus;
	}
	public Integer getDeviceIndex() {
		return deviceIndex;
	}
	public void setDeviceIndex(Integer deviceIndex) {
		this.deviceIndex = deviceIndex;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getLinkState() {
		return linkState;
	}
	public void setLinkState(Integer linkState) {
		this.linkState = linkState;
	}
	public Integer getDischargeStatus() {
		return dischargeStatus;
	}
	public void setDischargeStatus(Integer dischargeStatus) {
		this.dischargeStatus = dischargeStatus;
	}
	

	
	

}
