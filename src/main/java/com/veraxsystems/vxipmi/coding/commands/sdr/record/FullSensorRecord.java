/*
 * FullSensorRecord.java 
 * Created on 2011-08-04
 *
 * Copyright (c) Verax Systems 2011.
 * All rights reserved.
 *
 * This software is furnished under a license. Use, duplication,
 * disclosure and all other uses are restricted to the rights
 * specified in the written license agreement.
 */
package com.veraxsystems.vxipmi.coding.commands.sdr.record;

import com.veraxsystems.vxipmi.common.TypeConverter;

/**
 * 用于完整传感器记录格式的包装器类
 */
public class FullSensorRecord extends SensorRecord {

	@Override
	public String toString() {
		return "FullSensorRecord [sensorOwnerId=" + sensorOwnerId + ", addressType=" + addressType + ", channelNumber="
				+ channelNumber + ", sensorOwnerLun=" + sensorOwnerLun + ", sensorNumber=" + sensorNumber
				+ ", entityId=" + entityId + ", entityPhysical=" + entityPhysical + ", entityInstanceNumber="
				+ entityInstanceNumber + ", hysteresisReadable=" + hysteresisReadable + ", thresholdsReadable="
				+ thresholdsReadable + ", sensorType=" + sensorType + ", eventReadingType=" + eventReadingType
				+ ", rateUnit=" + rateUnit + ", modifierUnitUsage=" + modifierUnitUsage + ", sensorBaseUnit="
				+ sensorBaseUnit + ", sensorModifierUnit=" + sensorModifierUnit + ", m=" + m + ", tolerance="
				+ tolerance + ", b=" + b + ", accuracy=" + accuracy + ", rExp=" + rExp + ", sensorDirection="
				+ sensorDirection + ", nominalReading=" + nominalReading + ", normalMaximum=" + normalMaximum
				+ ", normalMinimum=" + normalMinimum + ", sensorMaximumReading=" + sensorMaximumReading
				+ ", sensorMinmumReading=" + sensorMinmumReading + ", upperNonRecoverableThreshold="
				+ upperNonRecoverableThreshold + ", lowerNonRecoverableThreshold=" + lowerNonRecoverableThreshold
				+ ", upperCriticalThreshold=" + upperCriticalThreshold + ", lowerCriticalThreshold="
				+ lowerCriticalThreshold + ", upperNonCriticalThreshold=" + upperNonCriticalThreshold
				+ ", lowerNonCriticalThreshold=" + lowerNonCriticalThreshold + ", name=" + name + ", sensorUnits1="
				+ sensorUnits1 + ", linearization=" + linearization + "]";
	}

	private byte sensorOwnerId;//传感器拥有者id

	private AddressType addressType;

	private byte channelNumber;//通道数字

	private byte sensorOwnerLun;

	private byte sensorNumber;

	private EntityId entityId;

	/**
	 * 实体为真则为物理实体，否则为逻辑实体。
	 */
	private boolean entityPhysical;

	private byte entityInstanceNumber;

	private boolean hysteresisReadable;//滞后阅读

	private boolean thresholdsReadable;//门槛阅读

	private SensorType sensorType;

	private int eventReadingType;//事件读取类别

	private RateUnit rateUnit;//单元率

	private ModifierUnitUsage modifierUnitUsage;//已使用的单体改变量

	private SensorUnit sensorBaseUnit;

	private SensorUnit sensorModifierUnit;

	private double m;

	private double tolerance;//公差

	private double b;

	private double accuracy;//精度

	private int rExp;

	private SensorDirection sensorDirection;

	private double nominalReading;//名义上的阅读

	private double normalMaximum;

	private double normalMinimum;

	private double sensorMaximumReading;

	private double sensorMinmumReading;

	private double upperNonRecoverableThreshold;//上行不可恢复的阈值

	private double lowerNonRecoverableThreshold;

	private double upperCriticalThreshold;//上行重要的阈值

	private double lowerCriticalThreshold;

	private double upperNonCriticalThreshold;

	private double lowerNonCriticalThreshold;

	private String name;

	private byte sensorUnits1;

	private int linearization;//线性化

	@Override
	protected void populateTypeSpecficValues(byte[] recordData,
			SensorRecord record) {
		setSensorOwnerId(TypeConverter.intToByte((TypeConverter
				.byteToInt(recordData[5]) & 0xfe) >> 1));

		setAddressType(AddressType.parseInt(TypeConverter
				.byteToInt(recordData[5]) & 0x01));

		setChannelNumber(TypeConverter.intToByte((TypeConverter
				.byteToInt(recordData[6]) & 0xf0) >> 4));

		setSensorOwnerLun(TypeConverter.intToByte(TypeConverter
				.byteToInt(recordData[6]) & 0x3));

		setSensorNumber(recordData[7]);

		setEntityId(EntityId.parseInt(TypeConverter.byteToInt(recordData[8])));

		setEntityPhysical((TypeConverter.byteToInt(recordData[9]) & 0x80) == 0);

		setEntityInstanceNumber(TypeConverter.intToByte(TypeConverter
				.byteToInt(recordData[9]) & 0x7f));

		int hysteresis = (TypeConverter.byteToInt(recordData[11]) & 0x30) >> 4;

		if (hysteresis == 1 /* hysteresis readable */
				|| hysteresis == 2 /* hysteresis readable & settable */) {
			setHysteresisReadable(true);
		} else {
			setHysteresisReadable(false);
		}

		int thresholds = (TypeConverter.byteToInt(recordData[11]) & 0xc) >> 2;

		if (thresholds == 1 /* thresholds readable */
				|| thresholds == 2 /* thresholds readable & settable */) {
			setThresholdsReadable(true);
		} else {
			setThresholdsReadable(false);
		}

		setSensorType(SensorType.parseInt(TypeConverter
				.byteToInt(recordData[12])));

		setEventReadingType(TypeConverter.byteToInt(recordData[13]));

		setRateUnit(RateUnit
				.parseInt((TypeConverter.byteToInt(recordData[20]) & 0x38) >> 3));

		setModifierUnitUsage(ModifierUnitUsage.parseInt((TypeConverter
				.byteToInt(recordData[20]) & 0x6) >> 1));

		setSensorBaseUnit(SensorUnit.parseInt(TypeConverter
				.byteToInt(recordData[21])));

		setSensorModifierUnit(SensorUnit.parseInt(TypeConverter
				.byteToInt(recordData[22])));

		int calcM = TypeConverter.byteToInt(recordData[24]);

		calcM |= (TypeConverter.byteToInt(recordData[25]) & 0xc0) << 2;

		setM(TypeConverter.decode2sComplement(calcM, 9));

		sensorUnits1 = recordData[20];

		setTolerance(calcFormula(
				(TypeConverter.byteToInt(recordData[25]) & 0x3f) / 2, 8, 0,
				sensorUnits1));

		int calcB = TypeConverter.byteToInt(recordData[26]);

		calcB |= (TypeConverter.byteToInt(recordData[27]) & 0xc0) << 2;

		setB(TypeConverter.decode2sComplement(calcB, 9));

		int calcAcc = TypeConverter.byteToInt(recordData[27]) & 0x3f;

		calcAcc |= (TypeConverter.byteToInt(recordData[28]) & 0xf0) << 2;

		int exp = TypeConverter.byteToInt(recordData[28]) & 0xc >> 2;

		setAccuracy((double) calcAcc / 10000 * Math.pow(10, exp));

		setSensorDirection(SensorDirection.parseInt(TypeConverter
				.byteToInt(recordData[28]) & 0x3));

		setrExp(TypeConverter.decode2sComplement(
				(TypeConverter.byteToInt(recordData[29]) & 0xf0) >> 4, 3));

		int bExp = TypeConverter.decode2sComplement(
				(TypeConverter.byteToInt(recordData[29]) & 0xf), 3);

		setB(getB() * Math.pow(10, bExp));

		setNominalReading(calcFormula(TypeConverter.byteToInt(recordData[31])));
		setNormalMaximum(calcFormula(TypeConverter.byteToInt(recordData[32])));
		setNormalMinimum(calcFormula(TypeConverter.byteToInt(recordData[33])));

		setSensorMaximumReading(calcFormula(TypeConverter
				.byteToInt(recordData[34])));
		setSensorMinmumReading(calcFormula(TypeConverter
				.byteToInt(recordData[35])));

		if ((TypeConverter.byteToInt(recordData[10]) & 0x4) != 0) {
			if ((TypeConverter.byteToInt(recordData[18]) & 0x20) != 0) {
				setUpperNonRecoverableThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[36])));
			}
			if ((TypeConverter.byteToInt(recordData[18]) & 0x10) != 0) {
				setUpperCriticalThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[37])));
			}
			if ((TypeConverter.byteToInt(recordData[18]) & 0x8) != 0) {
				setUpperNonCriticalThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[38])));
			}

			if ((TypeConverter.byteToInt(recordData[18]) & 0x4) != 0) {
				setLowerNonRecoverableThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[39])));
			}
			if ((TypeConverter.byteToInt(recordData[18]) & 0x2) != 0) {
				setLowerCriticalThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[40])));
			}
			if ((TypeConverter.byteToInt(recordData[18]) & 0x1) != 0) {
				setLowerNonCriticalThreshold(calcFormula(TypeConverter
						.byteToInt(recordData[41])));
			}
		}

		byte[] name = new byte[recordData.length - 48];

		System.arraycopy(recordData, 48, name, 0, name.length);

		setName(decodeName(recordData[47], name));

		linearization = TypeConverter.byteToInt(recordData[23]) & 0x7f;
	}

	public void setSensorOwnerId(byte sensorOwnerId) {
		this.sensorOwnerId = sensorOwnerId;
	}

	public byte getSensorOwnerId() {
		return sensorOwnerId;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public byte getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(byte channelNumber) {
		this.channelNumber = channelNumber;
	}

	public byte getSensorOwnerLun() {
		return sensorOwnerLun;
	}

	public void setSensorOwnerLun(byte sensorOwnerLun) {
		this.sensorOwnerLun = sensorOwnerLun;
	}

	public byte getSensorNumber() {
		return sensorNumber;
	}

	public void setSensorNumber(byte sensorNumber) {
		this.sensorNumber = sensorNumber;
	}

	public EntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(EntityId entityId) {
		this.entityId = entityId;
	}

	public boolean isEntityPhysical() {
		return entityPhysical;
	}

	public void setEntityPhysical(boolean entityPhysical) {
		this.entityPhysical = entityPhysical;
	}

	public byte getEntityInstanceNumber() {
		return entityInstanceNumber;
	}

	public void setEntityInstanceNumber(byte entityInstanceNumber) {
		this.entityInstanceNumber = entityInstanceNumber;
	}

	public boolean isHysteresisReadable() {
		return hysteresisReadable;
	}

	public void setHysteresisReadable(boolean hysteresisReadable) {
		this.hysteresisReadable = hysteresisReadable;
	}

	public boolean isThresholdsReadable() {
		return thresholdsReadable;
	}

	public void setThresholdsReadable(boolean thresholdsReadable) {
		this.thresholdsReadable = thresholdsReadable;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public int getEventReadingType() {
		return eventReadingType;
	}

	public void setEventReadingType(int eventReadingType) {
		this.eventReadingType = eventReadingType;
	}

	public RateUnit getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(RateUnit rateUnit) {
		this.rateUnit = rateUnit;
	}

	public ModifierUnitUsage getModifierUnitUsage() {
		return modifierUnitUsage;
	}

	public void setModifierUnitUsage(ModifierUnitUsage modifierUnitUsage) {
		this.modifierUnitUsage = modifierUnitUsage;
	}

	public SensorUnit getSensorBaseUnit() {
		return sensorBaseUnit;
	}

	public void setSensorBaseUnit(SensorUnit sensorBaseUnit) {
		this.sensorBaseUnit = sensorBaseUnit;
	}

	public SensorUnit getSensorModifierUnit() {
		return sensorModifierUnit;
	}

	public void setSensorModifierUnit(SensorUnit sensorModifierUnit) {
		this.sensorModifierUnit = sensorModifierUnit;
	}

	private double getM() {
		return m;
	}

	private void setM(double m) {
		this.m = m;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double mTolerance) {
		this.tolerance = mTolerance;
	}

	private double getB() {
		return b;
	}

	private void setB(double b) {
		this.b = b;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double bAccuracy) {
		this.accuracy = bAccuracy;
	}

	private void setrExp(int rExp) {
		this.rExp = rExp;
	}

	private int getrExp() {
		return rExp;
	}

	public SensorDirection getSensorDirection() {
		return sensorDirection;
	}

	public void setSensorDirection(SensorDirection sensorDirection) {
		this.sensorDirection = sensorDirection;
	}

	public double getNominalReading() {
		return nominalReading;
	}

	public void setNominalReading(double nominalReading) {
		this.nominalReading = nominalReading;
	}

	public double getNormalMaximum() {
		return normalMaximum;
	}

	public void setNormalMaximum(double normalMaximum) {
		this.normalMaximum = normalMaximum;
	}

	public double getNormalMinimum() {
		return normalMinimum;
	}

	public void setNormalMinimum(double normalMinimum) {
		this.normalMinimum = normalMinimum;
	}

	public double getSensorMaximumReading() {
		return sensorMaximumReading;
	}

	public void setSensorMaximumReading(double sensorMaximumReading) {
		this.sensorMaximumReading = sensorMaximumReading;
	}

	public double getSensorMinmumReading() {
		return sensorMinmumReading;
	}

	public void setSensorMinmumReading(double sensorMinmumReading) {
		this.sensorMinmumReading = sensorMinmumReading;
	}

	public double getUpperNonRecoverableThreshold() {
		return upperNonRecoverableThreshold;
	}

	public void setUpperNonRecoverableThreshold(
			double upperNonRecoverableThreshold) {
		this.upperNonRecoverableThreshold = upperNonRecoverableThreshold;
	}

	public double getLowerNonRecoverableThreshold() {
		return lowerNonRecoverableThreshold;
	}

	public void setLowerNonRecoverableThreshold(
			double lowerNonRecoverableThreshold) {
		this.lowerNonRecoverableThreshold = lowerNonRecoverableThreshold;
	}

	public double getUpperCriticalThreshold() {
		return upperCriticalThreshold;
	}

	public void setUpperCriticalThreshold(double upperCriticalThreshold) {
		this.upperCriticalThreshold = upperCriticalThreshold;
	}

	public double getLowerCriticalThreshold() {
		return lowerCriticalThreshold;
	}

	public void setLowerCriticalThreshold(double lowerCriticalThreshold) {
		this.lowerCriticalThreshold = lowerCriticalThreshold;
	}

	public double getUpperNonCriticalThreshold() {
		return upperNonCriticalThreshold;
	}

	public void setUpperNonCriticalThreshold(double upperNonCriticalThreshold) {
		this.upperNonCriticalThreshold = upperNonCriticalThreshold;
	}

	public double getLowerNonCriticalThreshold() {
		return lowerNonCriticalThreshold;
	}

	public void setLowerNonCriticalThreshold(double lowerNonCriticalThreshold) {
		this.lowerNonCriticalThreshold = lowerNonCriticalThreshold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 使用'y=Mx+B'公式转换为基于单位的值。传感器单元1中每个标志位的1或2的补码有符号或无符号。
	 * 
	 * @param value
	 *            - 要转换的值。假设长度为8。
	 * @return converted value转换后的值
	 */
	public double calcFormula(int value) {
		return calcFormula(value, 8, getB(), sensorUnits1);
	}

	/**
	 * Converts to units-based value using the 'y=Mx+B' formula. 1's or 2's
	 * complement signed or unsigned per flag bits in Sensor Units 1.
	 * 使用'y=Mx+B'公式转换为基于单位的值。传感器单元1中每个标志位的1或2的补码有符号或无符号。
	 * @param value
	 *            - value to be converted
	 * @param length
	 *            - number of bits of value
	 * @param b
	 *            - {@link #b} modifier or 0 if b not used修饰符或0(如果不使用b)
	 * @param sensorUnits1
	 *            - byte containing numeric data format包含数字数据格式的字节
	 * @return converted value
	 * @throws IllegalArgumentException
	 *             when record's numeric values are linearized in a way that is
	 *             not supported
	 */
	protected double calcFormula(int value, int length, double b,
			byte sensorUnits1) throws IllegalArgumentException {
		int dataFormat = (TypeConverter.byteToInt(sensorUnits1) & 0xc0) >> 6;

		int base = 0;

		switch (dataFormat) {
		case 0: // unsigned
			base = value;
			break;
		case 1: // 1's complement
			base = TypeConverter.decode1sComplement(value, length - 1);
			break;
		case 2: // 2's complement
			base = TypeConverter.decode2sComplement(value, length - 1);
			break;
		case 3: // no analog reading
			base = value;
			break;
		default:
			throw new IllegalArgumentException(
					"Invalid data format in sensorUnits1");
		}

		double result = (getM() * base + getB()) * Math.pow(10, getrExp());

		switch (linearization) {
		case 0:
			return result;
		case 1:
			return Math.log(result);
		case 2:
			return Math.log10(result);
		case 3:
			return Math.log(result) / Math.log(2);
		case 5:
			return Math.pow(10, result);
		case 6:
			return Math.pow(2, result);
		case 7:
			return 1 / result;
		case 8:
			return Math.pow(result, 2);
		case 9:
			return Math.pow(result, 3);
		case 10:
			return Math.pow(result, 0.5);
		case 11:
			return Math.pow(result, 0.33);
		default:
			throw new IllegalArgumentException("Unsupported linearization type");
		}
	}

	public double getSensorResolution() {
		return Math.abs(getM() / 2.0 * Math.pow(10, getrExp()));
	}
}
