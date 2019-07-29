/*
 * EntityId.java 
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

import org.apache.log4j.Logger;

/**
 * The EntityID is used for identifying the physical entity that a sensor or
 * device is associated with.
 * EntityID用于标识与传感器或设备相关联的物理实体。
 */
public enum EntityId {
	Unspecified(EntityId.UNSPECIFIED), Other(EntityId.OTHER), Unknown(EntityId.UNKNOWN), Processor(EntityId.PROCESSOR),
	/**
	 * Disk or disk bay.磁盘或磁盘区。
	 */
	Disk(EntityId.DISK), PeripheralBay(EntityId.PERIPHERALBAY), SystemManagementModule(EntityId.SYSTEMMANAGEMENTMODULE),
	/**
	 * Main system board, may also be a processor board and/or internal
	 * expansion board.
	 * 主系统板，也可以是处理器板和/或内部扩展板。
	 */
	SystemBoard(EntityId.SYSTEMBOARD),
	/**
	 * Board holding memory devices.存储设备板。
	 */
	MemoryModule(EntityId.MEMORYMODULE),
	/**
	 * Holds processors, use this designation when processors are not mounted on
	 * system board
	 * 持有处理器，当处理器未安装在系统板上时使用此指定
	 */
	ProcesorModule(EntityId.PROCESORMODULE), PowerSupply(EntityId.POWERSUPPLY), AddInCard(EntityId.ADDINCARD), FrontPanelBoard(EntityId.FRONTPANELBOARD), BackPanelBoard(
			EntityId.BACKPANELBOARD), PowerSystemBoard(EntityId.POWERSYSTEMBOARD), DriveBackplane(EntityId.DRIVEBACKPLANE), SystemInternalExpansionBoard(
					EntityId.SYSTEMINTERNALEXPANSIONBOARD), OtherSystemBoard(EntityId.OTHERSYSTEMBOARD), ProcessorBoard(EntityId.PROCESSORBOARD),
	/**
	 * This Entity ID is typically used as a pre-defined logical entity for
	 * grouping power supplies and/or sensors that are associated in monitoring
	 * a particular logical power domain.
	 * 此实体ID通常用作预定义的逻辑实体，用于分组在监视特定逻辑功率域时关联的电源和/或传感器。
	 */
	PowerUnit(EntityId.POWERUNIT),
	/**
	 * DC-to-DC internal converter.DC-to-DC内部转换器。
	 */
	PowerModule(EntityId.POWERMODULE),
	/**
	 * Power management / power distribution board
	 * 电源管理/配电板
	 */
	PowerManagement(EntityId.POWERMANAGEMENT), ChassisBackPanelBoard(EntityId.CHASSISBACKPANELBOARD), SystemChassis(EntityId.SYSTEMCHASSIS), SubChassis(
			EntityId.SUBCHASSIS), OtherChassis(EntityId.OTHERCHASSIS), DiskDriveBay(EntityId.DISKDRIVEBAY),
	/**
	 * Duplicate of {@link #PeripheralBay}
	 */
	PeripheralBay2(EntityId.PERIPHERALBAY2), DeviceBay(EntityId.DEVICEBAY), Fan(EntityId.FAN), CoolingUnit(EntityId.COOLINGUNIT), CableInterconnect(
			EntityId.CABLEINTERCONNECT), MemoryDevice(EntityId.MEMORYDEVICE), SystemManagementSoftware(EntityId.SYSTEMMANAGEMENTSOFTWARE), SystemFirmware(
			EntityId.SYSTEMFIRMWARE), OperatingSystem(EntityId.OPERATINGSYSTEM), SystemBus(EntityId.SYSTEMBUS),
	/**
	 * This is a logical entity for use with Entity Association records. It is
	 * provided to allow an Entityassociation record to define a grouping of
	 * entities when there is no appropriate pre-defined entity for the
	 * container entity. This Entity should not be used as a physical entity.
	 * 这是一个用于实体关联记录的逻辑实体。它允许Entityassociation记录在容器实体没有适当的
	 * 预定义实体时定义实体组.此实体不应作为物理实体使用。
	 */
	Group(EntityId.GROUP), RemoteManagementCommunicationDevice(EntityId.REMOTEMANAGEMENTCOMMUNICATIONDEVICE),
	/**
	 * This Entity ID can be used to identify the environment outside the system
	 * chassis. For example, a system may have a temperature sensor that
	 * monitors the temperature 'outside the box'. Such a temperature sensor can
	 * be associated with an External Environment entity.
	 * 此实体ID可用于标识系统机箱外部的环境。例如，一个系统可能有一个温度传感器，
	 * 可以监视“盒子外”的温度。这种温度传感器可以与外部环境实体相关联。
	 */
	ExternalEnvironment(EntityId.EXTERNALENVIRONMENT), Battery(EntityId.BATTERY),
	/**
	 * A blade module that contains processor, memory, and I/O connections that
	 * enable it to operate as a processing entity.
	 * 刀片服务器模块，它包含处理器、内存和I/O连接，使其能够作为处理实体进行操作。
	 */
	ProcessingBlade(EntityId.PROCESSINGBLADE),
	/**
	 * A blade module that provides the fabric or network connection for one or
	 * more processing blades or modules.
	 * 刀片模块，为一个或多个处理刀片或模块提供织物或网络连接。
	 */
	ConnectivitySwitch(EntityId.CONNECTIVITYSWITCH),
	/**
	 * Processor and memory together on a module.
	 * 处理器和内存一起放在一个模块上。
	 */
	ProcessorMemoryModule(EntityId.PROCESSORMEMORYMODULE),
	/**
	 * A module that contains the main elements of an I/O interface.
	 * 包含I/O接口的主要元素的模块。
	 */
	IoModule(EntityId.IOMODULE), ProcessorIoModule(EntityId.PROCESSORIOMODULE), ManagementControllerFirmware(EntityId.MANAGEMENTCONTROLLERFIRMWARE), IpmiChannel(
			EntityId.IPMICHANNEL), PciBus(EntityId.PCIBUS), PciExpressBus(EntityId.PCIEXPRESSBUS), ScsiBus(EntityId.SCSIBUS),
	/**
	 * SATA/SAS bus.SAS / SATA总线。
	 */
	SataBus(EntityId.SATABUS), FrontSideBus(EntityId.FRONTSIDEBUS), RealTimeClock(EntityId.REALTIMECLOCK), AirInlet(EntityId.AIRINLET),
	/**
	 * Duplicate of {@link #AirInlet}.
	 */
	AirInlet2(EntityId.AIRINLET2),
	/**
	 * Duplicate of {@link #Processor}.
	 */
	Processor2(EntityId.PROCESSOR2),
	/**
	 * Duplicate of {@link #SystemBoard}.
	 */
	Baseboard(EntityId.BASEBOARD);
	
	private static final int POWERSUPPLY = 10;
	private static final int ADDINCARD = 11;
	private static final int FRONTPANELBOARD = 12;
	private static final int BACKPANELBOARD = 13;
	private static final int POWERSYSTEMBOARD = 14;
	private static final int DRIVEBACKPLANE = 15;
	private static final int SYSTEMINTERNALEXPANSIONBOARD = 16;
	private static final int COOLINGUNIT = 30;
	private static final int OTHERSYSTEMBOARD = 17;
	private static final int CABLEINTERCONNECT = 31;
	private static final int PROCESSORBOARD = 18;
	private static final int MEMORYDEVICE = 32;
	private static final int POWERUNIT = 19;
	private static final int SYSTEMMANAGEMENTSOFTWARE = 33;
	private static final int SYSTEMFIRMWARE = 34;
	private static final int OPERATINGSYSTEM = 35;
	private static final int SYSTEMBUS = 36;
	private static final int SCSIBUS = 50;
	private static final int GROUP = 37;
	private static final int SATABUS = 51;
	private static final int REMOTEMANAGEMENTCOMMUNICATIONDEVICE = 38;
	private static final int FRONTSIDEBUS = 52;
	private static final int EXTERNALENVIRONMENT = 39;
	private static final int REALTIMECLOCK = 53;
	private static final int AIRINLET = 55;
	private static final int UNSPECIFIED = 0;
	private static final int OTHER = 1;
	private static final int UNKNOWN = 2;
	private static final int PROCESSOR = 3;
	private static final int DISK = 4;
	private static final int PERIPHERALBAY = 5;
	private static final int SYSTEMMANAGEMENTMODULE = 6;
	private static final int SYSTEMBOARD = 7;
	private static final int MEMORYMODULE = 8;
	private static final int PROCESORMODULE = 9;
	private static final int POWERMODULE = 20;
	private static final int POWERMANAGEMENT = 21;
	private static final int CHASSISBACKPANELBOARD = 22;
	private static final int SYSTEMCHASSIS = 23;
	private static final int SUBCHASSIS = 24;
	private static final int OTHERCHASSIS = 25;
	private static final int DISKDRIVEBAY = 26;
	private static final int BATTERY = 40;
	private static final int PERIPHERALBAY2 = 27;
	private static final int PROCESSINGBLADE = 41;
	private static final int DEVICEBAY = 28;
	private static final int CONNECTIVITYSWITCH = 42;
	private static final int FAN = 29;
	private static final int PROCESSORMEMORYMODULE = 43;
	private static final int IOMODULE = 44;
	private static final int PROCESSORIOMODULE = 45;
	private static final int MANAGEMENTCONTROLLERFIRMWARE = 46;
	private static final int IPMICHANNEL = 47;
	private static final int PCIBUS = 48;
	private static final int PCIEXPRESSBUS = 49;
	private static final int AIRINLET2 = 64;
	private static final int PROCESSOR2 = 65;
	private static final int BASEBOARD = 66;

	private int code;
	
	private static Logger logger = Logger.getLogger(EntityId.class);

	EntityId(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public static EntityId parseInt(int value) {
		switch(value) {
		case POWERSUPPLY:
			return PowerSupply;
		case ADDINCARD:
			return AddInCard;
		case FRONTPANELBOARD:
			return FrontPanelBoard;
		case BACKPANELBOARD:
			return BackPanelBoard;
		case POWERSYSTEMBOARD:
			return PowerSystemBoard;
		case DRIVEBACKPLANE:
			return DriveBackplane;
		case SYSTEMINTERNALEXPANSIONBOARD:
			return SystemInternalExpansionBoard;
		case COOLINGUNIT:
			return CoolingUnit;
		case OTHERSYSTEMBOARD:
			return OtherSystemBoard;
		case CABLEINTERCONNECT:
			return CableInterconnect;
		case PROCESSORBOARD:
			return ProcessorBoard;
		case MEMORYDEVICE:
			return MemoryDevice;
		case POWERUNIT:
			return PowerUnit;
		case SYSTEMMANAGEMENTSOFTWARE:
			return SystemManagementSoftware;
		case SYSTEMFIRMWARE:
			return SystemFirmware;
		case OPERATINGSYSTEM:
			return OperatingSystem;
		case SYSTEMBUS:
			return SystemBus;
		case SCSIBUS:
			return ScsiBus;
		case GROUP:
			return Group;
		case SATABUS:
			return SataBus;
		case REMOTEMANAGEMENTCOMMUNICATIONDEVICE:
			return RemoteManagementCommunicationDevice;
		case FRONTSIDEBUS:
			return FrontSideBus;
		case EXTERNALENVIRONMENT:
			return ExternalEnvironment;
		case REALTIMECLOCK:
			return RealTimeClock;
		case AIRINLET:
			return AirInlet;
		case UNSPECIFIED:
			return Unspecified;
		case OTHER:
			return Other;
		case UNKNOWN:
			return Unknown;
		case PROCESSOR:
			return Processor;
		case DISK:
			return Disk;
		case PERIPHERALBAY:
			return PeripheralBay;
		case SYSTEMMANAGEMENTMODULE:
			return SystemManagementModule;
		case SYSTEMBOARD:
			return SystemBoard;
		case MEMORYMODULE:
			return MemoryModule;
		case PROCESORMODULE:
			return ProcesorModule;
		case POWERMODULE:
			return PowerModule;
		case POWERMANAGEMENT:
			return PowerManagement;
		case CHASSISBACKPANELBOARD:
			return ChassisBackPanelBoard;
		case SYSTEMCHASSIS:
			return SystemChassis;
		case SUBCHASSIS:
			return SubChassis;
		case OTHERCHASSIS:
			return OtherChassis;
		case DISKDRIVEBAY:
			return DiskDriveBay;
		case BATTERY:
			return Battery;
		case PERIPHERALBAY2:
			return PeripheralBay2;
		case PROCESSINGBLADE:
			return ProcessingBlade;
		case DEVICEBAY:
			return DeviceBay;
		case CONNECTIVITYSWITCH:
			return ConnectivitySwitch;
		case FAN:
			return Fan;
		case PROCESSORMEMORYMODULE:
			return ProcessorMemoryModule;
		case IOMODULE:
			return IoModule;
		case PROCESSORIOMODULE:
			return ProcessorIoModule;
		case MANAGEMENTCONTROLLERFIRMWARE:
			return ManagementControllerFirmware;
		case IPMICHANNEL:
			return IpmiChannel;
		case PCIBUS:
			return PciBus;
		case PCIEXPRESSBUS:
			return PciExpressBus;
		case AIRINLET2:
			return AirInlet2;
		case PROCESSOR2:
			return Processor2;
		case BASEBOARD:
			return Baseboard;
		default:
			logger.error("Invalid value: " + value);
			return Other;
		}
	}
}