package com.agama.pemm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
public class UpsStatus extends BaseDomain {
	private static final long serialVersionUID = -6272206113801603493L;
	private String name;
	private int interfaceType;
	private int communicationStatus;
	private int dischargePatterns;
	private int upsType;
	private String modelNumber;
	private String brand;
	private String versionNumber;
	private double rateVoltage;
	private double ratedCurrent;
	private double batteryVoltage;
	private double power;
	private int upsStatus;
	private double frequency;
	private double internalTemperature;
	private double bypassVoltage;
	private double bypassFrequency;
	private double inputVoltage;
	private double outputVoltage;
	private double errorVoltage;
	@Column(name="[load]")
	private double load;
	private double outputFrenquency;
	private double singleVoltage;
	private double totalVoltage;
	private double electricQuantity;
	private double passCurrent;
	private int remainingTime; 
	private Date collectTime;
	private int status;
	
	
	
	
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
	public int getUpsStatus() {
		return upsStatus;
	}
	public void setUpsStatus(int upsStatus) {
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
	public double getBypassVoltage() {
		return bypassVoltage;
	}
	public void setBypassVoltage(double bypassVoltage) {
		this.bypassVoltage = bypassVoltage;
	}
	public double getBypassFrequency() {
		return bypassFrequency;
	}
	public void setBypassFrequency(double bypassFrequency) {
		this.bypassFrequency = bypassFrequency;
	}
	public double getInputVoltage() {
		return inputVoltage;
	}
	public void setInputVoltage(double inputVoltage) {
		this.inputVoltage = inputVoltage;
	}
	public double getOutputVoltage() {
		return outputVoltage;
	}
	public void setOutputVoltage(double outputVoltage) {
		this.outputVoltage = outputVoltage;
	}
	public double getErrorVoltage() {
		return errorVoltage;
	}
	public void setErrorVoltage(double errorVoltage) {
		this.errorVoltage = errorVoltage;
	}
	public double getLoad() {
		return load;
	}
	public void setLoad(double load) {
		this.load = load;
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


}