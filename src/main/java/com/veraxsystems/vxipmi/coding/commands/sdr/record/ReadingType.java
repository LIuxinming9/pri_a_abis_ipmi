/*
 * ReadingType.java 
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
 * Type of the reading of the discrete sensor.
 * 离散传感器的读出类型。
 */
public enum ReadingType {
    /**
     * FRU in standby or 'hot spare' state.
     * FRU处于待机或“热备用”状态。
     */
    FruInactive(ReadingType.FRUINACTIVE), SlotConnectorIdentifyStatusAsserted(
            ReadingType.SLOTCONNECTORIDENTIFYSTATUSASSERTED), HardReset(ReadingType.HARDRESET),
    /**
     * Switch indicating FRU latch is in 'unlatched' position and FRU is mechanically removable
     * 开关指示FRU锁存处于“未锁存”位置，FRU是可机械拆卸的
     */
    FruLatchOpen(ReadingType.FRULATCHOPEN), FruActivationRequested(ReadingType.FRUACTIVATIONREQUESTED), SlotConnectorDeviceInstalled(
            ReadingType.SLOTCONNECTORDEVICEINSTALLED), WarmReset(ReadingType.WARMRESET), FruActivationInProgress(
            ReadingType.FRUACTIVATIONINPROGRESS),
    /**
     * Typically, this means that the slot power is off. The Ready for Installation, Ready for Removal, and Slot Power
     * states can transition together, depending on the slot implementation.
     * 通常，这意味着插槽电源是关闭的。根据插槽的实现，准备安装、准备删除和插槽电源状态可以一起过渡
     */
    SlotConnectorReadyForDeviceInstallation(ReadingType.SLOTCONNECTORREADYFORDEVICEINSTALLATION), PxeBootRequested(
            ReadingType.PXEBOOTREQUESTED), FruActive(ReadingType.FRUACTIVE),
    /**
     * An Invalid Username or Password was received during the session establishment process.
     * 在会话建立过程中接收到无效的用户名或密码
     */
    InvalidUsernameOrPassword(ReadingType.INVALIDUSERNAMEORPASSWORD), SlotConnectorReadyForDeviceRemoval(
            ReadingType.SLOTCONNECTORREADYFORDEVICEREMOVAL), AutomaticBootToDiagnostic(
            ReadingType.AUTOMATICBOOTTODIAGNOSTIC), FruDeactivationRequested(ReadingType.FRUDEACTIVATIONREQUESTED),
    /**
     * A user's access has been disabled due to a series of bad password attempts. This offset can be used in
     * conjunction with the Bad Password Threshold option. Refer to the LAN or serial/modem configuration parameter for
     * 'Bad Password Threshold' for more information.
     * 由于一系列错误的密码尝试，用户的访问已被禁用。此偏移量可与错误的密码阈值选项一起使用。
     * 有关“错误密码阈值”的更多信息，请参阅局域网或串行/调制解调器配置参数
     */
    InvalidPasswordDisable(ReadingType.INVALIDPASSWORDDISABLE), EntityPresent(ReadingType.ENTITYPRESENT), SlotPowerOff(
            ReadingType.SLOTPOWEROFF), SoftwareInitiatedHardReset(ReadingType.SOFTWAREINITIATEDHARDRESET), FruDeactivationInProgress(
            ReadingType.FRUDEACTIVATIONINPROGRESS),
    /**
     * The sensor is known to be in error. It may still be accessible by software.
     * 已知传感器有误差。它仍然可以通过软件访问。
     */
    SensorFailure(ReadingType.SENSORFAILURE), EntityAbsent(ReadingType.ENTITYABSENT), SoftwareInitiatedWarmReset(
            ReadingType.SOFTWAREINITIATEDWARMRESET), FruCommunicationLost(ReadingType.FRUCOMMUNICATIONLOST), FruFailure(
            ReadingType.FRUFAILURE), SystemRestart(ReadingType.SYSTEMRESTART), StateDeasserted(
            ReadingType.STATEDEASSERTED), SystemFirmwareError(ReadingType.SYSTEMFIRMWAREERROR), StateAsserted(
            ReadingType.STATEASSERTED), SystemFirmwareHang(ReadingType.SYSTEMFIRMWAREHANG), SystemFirmwareProgress(
            ReadingType.SYSTEMFIRMWAREPROGRESS),
    /**
     * Informational. This does not imply whether the hardware change was successful or not. Only that a change
     * occurred.
     * 信息。这并不意味着硬件更改是否成功。只是发生了变化。
     */
    HardwareChangeDetected(ReadingType.HARDWARECHANGEDETECTED), Frb1BistFailure(ReadingType.FRB1BISTFAILURE),
    /**
     * Informational. Success or failure not implied.
     * 信息。成功或失败并不意味着什么。
     */
    FirmwareOrSoftwareChangeDetected(ReadingType.FIRMWAREORSOFTWARECHANGEDETECTED),
    /**
     * Used hang is believed to be due or related to a processor failure. Use System Firmware Progress sensor for other
     * BIOS hangs.
     * 使用的挂起被认为是由于或与处理器故障有关。使用系统固件进程传感器进行其他BIOS挂起。
     */
    Frb2HangInPostFailure(ReadingType.FRB2HANGINPOSTFAILURE), HardwareIncompatibilityDetected(
            ReadingType.HARDWAREINCOMPATIBILITYDETECTED),
    /**
     * CPU didn't start.
     */
    Frb3ProcessorStartupFailure(ReadingType.FRB3PROCESSORSTARTUPFAILURE), FirmwareOrSoftwareIncompatibilityDetected(
            ReadingType.FIRMWAREORSOFTWAREINCOMPATIBILITYDETECTED), DrivePresence(ReadingType.DRIVEPRESENCE), ConfigurationError(
            ReadingType.CONFIGURATIONERROR), InvalidOrUnsupportedHardware(ReadingType.INVALIDORUNSUPPORTEDHARDWARE), DriveFault(
            ReadingType.DRIVEFAULT), UncorrectableCpuComplexError(ReadingType.UNCORRECTABLECPUCOMPLEXERROR), InvalidOrUnsupportedFirmwareOrSoftware(
            ReadingType.INVALIDORUNSUPPORTEDFIRMWAREORSOFTWARE), PredictiveFailure(ReadingType.PREDICTIVEFAILURE), ProcessorPresenceDetected(
            ReadingType.PROCESSORPRESENCEDETECTED), HotSpare(ReadingType.HOTSPARE), ProcessorDisabled(
            ReadingType.PROCESSORDISABLED), ConsistencyOrParityCheckInProgress(
            ReadingType.CONSISTENCYORPARITYCHECKINPROGRESS), TerminatorPresenceDetected(
            ReadingType.TERMINATORPRESENCEDETECTED), SecureModeViolationAttempt(ReadingType.SECUREMODEVIOLATIONATTEMPT),
    /**
     * System powered up, but normal OS operation has shut down and system is awaiting reset pushbutton, powercycle or
     * other external input
     * 系统开机，但操作系统正常运行已关机，系统正在等待复位按钮、电源循环或其他外部输入
     */
    OsGracefulStop(ReadingType.OSGRACEFULSTOP), InCriticalArray(ReadingType.INCRITICALARRAY),
    /**
     * Processor throttling triggered by a hardware-based mechanism operating independent from system software, such as
     * automatic thermal throttling or throttling to limit power consumption
     * 处理器节流由独立于系统软件的基于硬件的机制触发，如自动热节流或节流以限制功耗
     */
    ProcessorAutomaticallyThrottled(ReadingType.PROCESSORAUTOMATICALLYTHROTTLED), PreBootUserPasswordViolation(
            ReadingType.PREBOOTUSERPASSWORDVIOLATION),
    /**
     * System graceful power down by OS.
     * 系统优雅的功率下降的操作系统
     */
    OsGracefulShutdown(ReadingType.OSGRACEFULSHUTDOWN), MachineCheckException(ReadingType.MACHINECHECKEXCEPTION), PreBootSetupPasswordViolation(
            ReadingType.PREBOOTSETUPPASSWORDVIOLATION), SoftOsShutdown(ReadingType.SOFTOSSHUTDOWN), PreBootNetworkPasswordViolation(
            ReadingType.PREBOOTNETWORKPASSWORDVIOLATION), LANHeartbeatLost(ReadingType.LANHEARTBEATLOST),
    /**
     * Graceful shutdown request to agent via BMC did not occur due to missing or malfunctioning local agent.
     * 由于本地代理丢失或故障，没有通过BMC向代理发出优雅的关机请求。
     */
    AgentNotResponding(ReadingType.AGENTNOTRESPONDING), OtherPreBootPasswordViolation(
            ReadingType.OTHERPREBOOTPASSWORDVIOLATION), LANHeartbeat(ReadingType.LANHEARTBEAT), OutOfBandAccessPasswordViolation(
            ReadingType.OUTOFBANDACCESSPASSWORDVIOLATION), Current(ReadingType.CURRENT),
    /**
     * Device Removed / Device Absent
     * 设备移除/设备缺失
     */
    DeviceAbsent(ReadingType.DEVICEABSENT), Parity(ReadingType.PARITY),
    /**
     * Device Inserted / Device Present
     */
    DevicePresent(ReadingType.DEVICEPRESENT), MemoryScrubFailed(ReadingType.MEMORYSCRUBFAILED), MemoryDeviceDisabled(
            ReadingType.MEMORYDEVICEDISABLED),
    /**
     * Correctable ECC / other correctable memory error logging limit reached
     * 可纠正ECC /其他可纠正的内存错误日志记录限制
     */
    CorrectableEccOtherCorrectableMemoryErrorLoggingLimitReached(
            ReadingType.CORRECTABLEECCOTHERCORRECTABLEMEMORYERRORLOGGINGLIMITREACHED),
    /**
     * Indicates presence of entity associated with the sensor. Typically the entity will be a 'memory module' or other
     * entity representing a physically replaceable unit of memory.
     * 指示与传感器关联的实体的存在。通常，
     * 该实体将是一个“内存模块”或表示物理上可替换的内存单元的其他实体。
     */
    MemoryPresenceDetected(ReadingType.MEMORYPRESENCEDETECTED),
    /**
     * Indicates a memory configuration error for the entity associated with the sensor. This can include when a given
     * implementation of the entity is not supported by the system (e.g., when the particular size of the memory module
     * is unsupported) or that the entity is part of an unsupported memory configuration (e.g. the configuration is not
     * supported because the memory module doesn't match other memory modules)
     * 指示与传感器关联的实体的内存配置错误。这可以包括在一个给定的实体是不支持的系统的实现
     * (例如,当特定大小的内存模块不支持)或实体是一个不受支持的内存配置的一部分
     * (例如,配置不支持,因为内存模块不匹配其他内存模块)
     */
    MemoryConfigurationError(ReadingType.MEMORYCONFIGURATIONERROR),
    /**
     * Indicates entity associated with the sensor represents a 'spare' unit of memory.
     * 指示与传感器关联的实体表示“备用”内存单元。
     */
    SpareMemoryUnit(ReadingType.SPAREMEMORYUNIT),
    /**
     * Memory throttling triggered by a hardware-based mechanism operating independent from system software, such as
     * automatic thermal throttling or throttling to limit power consumption.
     * 内存节流由独立于系统软件的基于硬件的机制触发，如自动热节流或节流以限制功耗。
     */
    MemoryAutomaticallyThrottled(ReadingType.MEMORYAUTOMATICALLYTHROTTLED),
    /**
     * Memory device has entered a critical overtemperature state, exceeding specified operating conditions. Memory
     * devices in this state may produce errors or become inaccessible.
     * 存储设备已进入临界超温状态，
     * 超过指定的操作条件。处于这种状态的内存设备可能会产生错误或变得不可访问。
     */
    MemoryCriticalOvertemperature(ReadingType.MEMORYCRITICALOVERTEMPERATURE), SystemReconfigured(
            ReadingType.SYSTEMRECONFIGURED), OemSystemBootEvent(ReadingType.OEMSYSTEMBOOTEVENT), ABootCompleted(
            ReadingType.ABOOTCOMPLETED), UndeterminedSystemHardwareFailure(
            ReadingType.UNDETERMINEDSYSTEMHARDWAREFAILURE), TransitionToRunning(ReadingType.TRANSITIONTORUNNING), CBootCompleted(
            ReadingType.CBOOTCOMPLETED), EntryAddedToAuxiliaryLog(ReadingType.ENTRYADDEDTOAUXILIARYLOG), TransitionToInTest(
            ReadingType.TRANSITIONTOINTEST), PxeBootCompleted(ReadingType.PXEBOOTCOMPLETED), PefAction(
            ReadingType.PEFACTION), TransitionToPowerOff(ReadingType.TRANSITIONTOPOWEROFF), DiagnosticBootCompleted(
            ReadingType.DIAGNOSTICBOOTCOMPLETED),
    /**
     * This event can be used to record when changes are made to the timestamp clock(s) so that relative time
     * differences between SEL entries can be determined.
     * 此事件可用于记录何时对时间戳时钟进行更改，以便确定SEL条目之间的相对时间差。
     */
    TimestampClockSynch(ReadingType.TIMESTAMPCLOCKSYNCH), TransitionToOnLine(ReadingType.TRANSITIONTOONLINE), CdRomBootCompleted(
            ReadingType.CDROMBOOTCOMPLETED), BootSourceSelectionTimeout(ReadingType.BOOTSOURCESELECTIONTIMEOUT), PowerSupplyConfigurationError(
            ReadingType.POWERSUPPLYCONFIGURATIONERROR), TransitionToOffLine(ReadingType.TRANSITIONTOOFFLINE), TimerExpired(
            ReadingType.TIMEREXPIRED), RomBootCompleted(ReadingType.ROMBOOTCOMPLETED), TransitionToOffDuty(
            ReadingType.TRANSITIONTOOFFDUTY), TimerHardReset(ReadingType.TIMERHARDRESET),
    /**
     * Boot device not specified.
     * 未指定引导设备。
     */
    BootCompleted(ReadingType.BOOTCOMPLETED), PredictiveFailureDeasserted(ReadingType.PREDICTIVEFAILUREDEASSERTED), TransitionToDegraded(
            ReadingType.TRANSITIONTODEGRADED), TimerPowerDown(ReadingType.TIMERPOWERDOWN), AcpiS5EnteredByOverride(
            ReadingType.ACPIS5ENTEREDBYOVERRIDE), LANLeashLost(ReadingType.LANLEASHLOST), Predictive(
            ReadingType.PREDICTIVE), TransitionToPowerSave(ReadingType.TRANSITIONTOPOWERSAVE), TimerPowerCycle(
            ReadingType.TIMERPOWERCYCLE), AcpiLegacyOnState(ReadingType.ACPILEGACYONSTATE), PowerOffOrDown(
            ReadingType.POWEROFFORDOWN), UnauthorizedDock(ReadingType.UNAUTHORIZEDDOCK), InstallError(
            ReadingType.INSTALLERROR), AcpiLegacyOffState(ReadingType.ACPILEGACYOFFSTATE), PowerCycle(
            ReadingType.POWERCYCLE),
    /**
     * Supports detection of hot plug fan tampering.
     * 支持检测热塞风机篡改。
     */
    FANAreaIntrusion(ReadingType.FANAREAINTRUSION),
    /**
     * Indicates that full redundancy has been regained.
     * 指示已恢复完全冗余。
     */
    FullyRedundant(ReadingType.FULLYREDUNDANT), CableInterconnectConnected(ReadingType.CABLEINTERCONNECTCONNECTED), PowerDown240V(
            ReadingType.POWERDOWN240V),
    /**
     * Entered any non-redundant state, including {@link ReadingType#NonRedundant_InsufficientResources}
     * 输入任何非冗余状态，
     * 包括{@link ReadingType# non冗余dant_insufficientresources}
     */
    RedundancyLost(ReadingType.REDUNDANCYLOST), AcpiUnknown(ReadingType.ACPIUNKNOWN),
    /**
     * Incorrect cable connected / Incorrect interconnection
     * 不正确的电缆连接/不正确的互连
     */
    CableInterconnectConfigurationError(ReadingType.CABLEINTERCONNECTCONFIGURATIONERROR), InterlockPowerDown(
            ReadingType.INTERLOCKPOWERDOWN),
    /**
     * Redundancy still exists, but at a less than full level. For example, a system has four fans, and can tolerate the
     * failure of two of them, and presently one has failed.
     * 冗余仍然存在，但还没有达到全部级别。
     * 例如，一个系统有四个扇，可以容忍其中两个扇的失败，目前有一个扇失败了。
     */
    RedundancyDegraded(ReadingType.REDUNDANCYDEGRADED),
    /**
     * Predictive failure.
     */
    BatteryLow(ReadingType.BATTERYLOW),
    /**
     * The power source for the power unit was lost
     * 动力装置的电源丢失了
     */
    PowerInputLost(ReadingType.POWERINPUTLOST),
    /**
     * Redundancy has been lost but unit is functioning with minimum resources needed for 'normal' operation.
     * 冗余已经丢失，但单元正在以“正常”操作所需的最小资源运行。
     */
    NonRedundant_SufficientResourcesFromRedundant(ReadingType.NONREDUNDANT_SUFFICIENTRESOURCESFROMREDUNDANT), BatteryFailed(
            ReadingType.BATTERYFAILED),
    /**
     * Unit did not respond to request to turn on.
     * 单位未回复开机请求。
     */
    PowerUnitSoftPowerControlFailure(ReadingType.POWERUNITSOFTPOWERCONTROLFAILURE), Voltage(ReadingType.VOLTAGE), BatteryPresenceDetected(
            ReadingType.BATTERYPRESENCEDETECTED), PowerUnitFailure(ReadingType.POWERUNITFAILURE), PowerUnitPredictiveFailure(
            ReadingType.POWERUNITPREDICTIVEFAILURE), BusFatalError(ReadingType.BUSFATALERROR), CorrectableMemoryErrorLoggingDisabled(
            ReadingType.CORRECTABLEMEMORYERRORLOGGINGDISABLED), BusDegraded(ReadingType.BUSDEGRADED), EventTypeLoggingDisabled(
            ReadingType.EVENTTYPELOGGINGDISABLED), LogAreaReset(ReadingType.LOGAREARESET), TransitionToOK(
            ReadingType.TRANSITIONTOOK), AllEventLoggingDisabled(ReadingType.ALLEVENTLOGGINGDISABLED), TransitionToNonCriticalFromOK(
            ReadingType.TRANSITIONTONONCRITICALFROMOK), FruServiceRequestButtonPressed(
            ReadingType.FRUSERVICEREQUESTBUTTONPRESSED), SelFull(ReadingType.SELFULL), TransitionToCriticalFromLessSevere(
            ReadingType.TRANSITIONTOCRITICALFROMLESSSEVERE), SelAlmostFull(ReadingType.SELALMOSTFULL), TransitionToNonRecoverableFromLessSevere(
            ReadingType.TRANSITIONTONONRECOVERABLEFROMLESSSEVERE), CorrectableMachineCheckErrorLoggingDisabled(
            ReadingType.CORRECTABLEMACHINECHECKERRORLOGGINGDISABLED), TransitionToNonCriticalFromMoreSevere(
            ReadingType.TRANSITIONTONONCRITICALFROMMORESEVERE), TransitionToCriticalFromNonRecoverable(
            ReadingType.TRANSITIONTOCRITICALFROMNONRECOVERABLE),
    /**
     * This is typically connected to a switch that becomes asserted to request removal of the device)
     * 这通常连接到一个交换机，该交换机被断言请求移除设备)
     */
    SlotConnectorDeviceRemovalRequest(ReadingType.SLOTCONNECTORDEVICEREMOVALREQUEST), TransitionToNonRecoverable(
            ReadingType.TRANSITIONTONONRECOVERABLE), EntityDisabled(ReadingType.ENTITYDISABLED),
    /**
     * This is typically connected to a switch that mechanically enables/disables power to the slot, or locks the slot
     * in the 'Ready for Installation / Ready for Removal states' - depending on the slot implementation. The asserted
     * state indicates that the lock-out is active.
     * 这通常连接到一个开关，该开关机械地启用/禁用槽的电源，
     * 或者在“准备安装/准备删除状态”中锁定槽——这取决于槽的实现。断言的状态表明锁定是活动的。
     */
    InterlockAsserted(ReadingType.INTERLOCKASSERTED), Monitor(ReadingType.MONITOR), SlotDisabled(
            ReadingType.SLOTDISABLED), SlotHoldsSpareDevice(ReadingType.SLOTHOLDSSPAREDEVICE), PlatformGeneratedPage(
            ReadingType.PLATFORMGENERATEDPAGE), PlatformGeneratedLanAlert(ReadingType.PLATFORMGENERATEDLANALERT), PlatformEventTrapGenerated(
            ReadingType.PLATFORMEVENTTRAPGENERATED), PlatformGeneratedSnmpTrap(ReadingType.PLATFORMGENERATEDSNMPTRAP), SessionActivated(
            ReadingType.SESSIONACTIVATED),
    /**
     * Sensor access degraded or unavailable. A sensor that is degraded will still return valid results, but may be
     * operating with a slower response time, or may not detect certain possible states. A sensor that is unavailable is
     * not able to return any results
     * 传感器访问降级或不可用。降级的传感器仍然会返回有效的结果，
     * 但是可能在响应时间较慢的情况下工作，或者可能无法检测到某些可能的状态。
     * 不可用的传感器不能返回任何结果
     */
    SensorAccessUnavailable(ReadingType.SENSORACCESSUNAVAILABLE), SessionDeactivated(ReadingType.SESSIONDEACTIVATED),
    /**
     * Controller access degraded or unavailable. The ability to access the controller has been degraded, or access is
     * unavailable, but the party that is doing the monitoring cannot determine which.
     * 控制器访问降级或不可用。访问控制器的能力已降低，或访问不可用，
     * 但进行监视的一方无法确定是哪一方。
     */
    ControllerAccessUnavailable(ReadingType.CONTROLLERACCESSUNAVAILABLE),
    /**
     * Controller cannot be accessed for normal operation because it has been intentionally taken off-line for a
     * non-error condition. Note that any commands that are available must function according to specification.
     * 控制器不能用于正常操作，因为它已被人为地离线，
     * 以避免出现错误。注意，任何可用的命令都必须按照规范运行。
     */
    ManagementControllerOffLine(ReadingType.MANAGEMENTCONTROLLEROFFLINE), SuccessfulHardwareChangeDetected(
            ReadingType.SUCCESSFULHARDWARECHANGEDETECTED),
    /**
     * Controller cannot be accessed because of an error condition
     * 由于错误条件，无法访问控制器
     */
    ManagementControllerUnavailable(ReadingType.MANAGEMENTCONTROLLERUNAVAILABLE), SuccessfulSoftwareOrFWChangeDetected(
            ReadingType.SUCCESSFULSOFTWAREORFWCHANGEDETECTED), InFailedArray(ReadingType.INFAILEDARRAY), RebuildRemapInProgress(
            ReadingType.REBUILDREMAPINPROGRESS), CorrectableMachineCheckError(ReadingType.CORRECTABLEMACHINECHECKERROR), RebuildRemapAborted(
            ReadingType.REBUILDREMAPABORTED), Ierr(ReadingType.IERR),
    /**
     * Chip set did not respond to BMC request to change system power state
     * 芯片组未响应BMC更改系统电源状态的请求
     */
    ChipsetSoftPowerControlFailure(ReadingType.CHIPSETSOFTPOWERCONTROLFAILURE), ProcessorThermalTrip(
            ReadingType.PROCESSORTHERMALTRIP), ChipsetThermalTrip(ReadingType.CHIPSETTHERMALTRIP), D0PowerState(
            ReadingType.D0POWERSTATE), D1PowerState(ReadingType.D1POWERSTATE), Temperature(ReadingType.TEMPERATURE), D2PowerState(
            ReadingType.D2POWERSTATE), D3PowerState(ReadingType.D3POWERSTATE),
    /**
     * Unexpected error during system startup. Stopped waiting for input or power cycle/reset.
     * 系统启动时出现意外错误。停止等待输入或电源循环/复位
     */
    CriticalStopDuringOsLoad(ReadingType.CRITICALSTOPDURINGOSLOAD),
    /**
     * a.k.a. 'core dump', 'blue screen'
     * *选择。“核心转储”、“蓝屏”
     */
    RunTimeCriticalStop(ReadingType.RUNTIMECRITICALSTOP), DeviceDisabled(ReadingType.DEVICEDISABLED), DeviceEnabled(
            ReadingType.DEVICEENABLED),
    /**
     * Correctable ECC / other correctable memory error
     * 可纠正的ECC /其他可纠正的内存错误
     */
    CorrectableEcc(ReadingType.CORRECTABLEECC),
    /**
     * Uncorrectable ECC / other uncorrectable memory error
     * 不可纠正的ECC /其他不可纠正的内存错误
     */
    UncorrectableECC(ReadingType.UNCORRECTABLEECC), LimitNotExceeded(ReadingType.LIMITNOTEXCEEDED), LimitExceeded(
            ReadingType.LIMITEXCEEDED), AcpiS0G0Working(ReadingType.ACPIS0G0WORKING), AcpiS1SleepingProcessorContextMaintained(
            ReadingType.ACPIS1SLEEPINGPROCESSORCONTEXTMAINTAINED), PowerSupplyPresenceDetected(
            ReadingType.POWERSUPPLYPRESENCEDETECTED), AcpiS2SleepingProcessorContextLost(
            ReadingType.ACPIS2SLEEPINGPROCESSORCONTEXTLOST), PowerSupplyFailureDetected(
            ReadingType.POWERSUPPLYFAILUREDETECTED), TimerInterrupt(ReadingType.TIMERINTERRUPT), AcpiS3SleepingProcessorContextLostMemoryRetained(
            ReadingType.ACPIS3SLEEPINGPROCESSORCONTEXTLOSTMEMORYRETAINED), PowerSupplyPredictiveFailure(
            ReadingType.POWERSUPPLYPREDICTIVEFAILURE), Fan(ReadingType.FAN),
    /**
     * Unit has regained minimum resources needed for 'normal' operation. Entered from Non-redundant:Insufficient
     * Resources.
     * 机组已恢复“正常”运行所需的最低资源。从非冗余输入:资源不足。
     */
    NonRedundant_SufficientResourcesFromInsufficientResources(
            ReadingType.NONREDUNDANT_SUFFICIENTRESOURCESFROMINSUFFICIENTRESOURCES), AcpiS4NonVolatileSleep(
            ReadingType.ACPIS4NONVOLATILESLEEP), PowerSupplyInputLost(ReadingType.POWERSUPPLYINPUTLOST),
    /**
     * Unit has regained minimum resources needed for 'normal' operation. Entered from Non-redundant:Insufficient
     * Resources.
     * 机组已恢复“正常”运行所需的最低资源。从非冗余输入:资源不足。
     */
    NonRedundant_InsufficientResources(ReadingType.NONREDUNDANT_INSUFFICIENTRESOURCES), AcpiS5G2SoftOff(
            ReadingType.ACPIS5G2SOFTOFF), PowerSupplyInputLostOrOutOfRange(ReadingType.POWERSUPPLYINPUTLOSTOROUTOFRANGE),
    /**
     * Unit has lost some redundant resource(s) but is still in a redundant state. Entered by a transition from Fully
     * Redundant condition.
     * 单元丢失了一些冗余资源，但仍处于冗余状态。从完全冗余状态转换而来。
     */
    RedundancyDegradedFromFullyRedundant(ReadingType.REDUNDANCYDEGRADEDFROMFULLYREDUNDANT), PerformanceMet(
            ReadingType.PERFORMANCEMET), AcpiS4S5SoftOffStateUndetermined(ReadingType.ACPIS4S5SOFTOFFSTATEUNDETERMINED), PowerSupplyInputOutOfRange(
            ReadingType.POWERSUPPLYINPUTOUTOFRANGE),
    /**
     * Unit has regained some resource(s) and is redundant but not fully redundant. Entered from
     * Non-redundant:Sufficient Resources or Non-redundant:Insufficient Resources.
     * 单元已经恢复了一些资源，并且是冗余的，但不是完全冗余的。从非冗余:资源充足或非冗余:资源不足。
     */
    RedundancyDegradedFromNonRedundant(ReadingType.REDUNDANCYDEGRADEDFROMNONREDUNDANT), PerformanceLags(
            ReadingType.PERFORMANCELAGS), AcpiG3MechanicalOff(ReadingType.ACPIG3MECHANICALOFF), AcpiSleepingInS1S2OrS3(
            ReadingType.ACPISLEEPINGINS1S2ORS3), AcpiG1Sleeping(ReadingType.ACPIG1SLEEPING), FrontPanelInterrupt(
            ReadingType.FRONTPANELINTERRUPT), Informational(ReadingType.INFORMATIONAL), NoBootableMedia(
            ReadingType.NOBOOTABLEMEDIA), BusTimeout(ReadingType.BUSTIMEOUT), NonBootableDisketteLeftInDrive(
            ReadingType.NONBOOTABLEDISKETTELEFTINDRIVE), IoChannelCheckNmi(ReadingType.IOCHANNELCHECKNMI), PxeServerNotFound(
            ReadingType.PXESERVERNOTFOUND), Software(ReadingType.SOFTWARE), InvalidBootSector(
            ReadingType.INVALIDBOOTSECTOR), PciPErr(ReadingType.PCIPERR), GeneralChassisIntrusion(
            ReadingType.GENERALCHASSISINTRUSION), TransitionToIdle(ReadingType.TRANSITIONTOIDLE), PciSErr(
            ReadingType.PCISERR), DriveBayIntrusion(ReadingType.DRIVEBAYINTRUSION), TransitionToActive(
            ReadingType.TRANSITIONTOACTIVE), EisaFailSafeTimeout(ReadingType.EISAFAILSAFETIMEOUT), IoCardAreaIntrusion(
            ReadingType.IOCARDAREAINTRUSION), TransitionToBusy(ReadingType.TRANSITIONTOBUSY), BusCorrectableError(
            ReadingType.BUSCORRECTABLEERROR), ProcessorAreaIntrusion(ReadingType.PROCESSORAREAINTRUSION), BusUncorrectableError(
            ReadingType.BUSUNCORRECTABLEERROR), FatalNmi(ReadingType.FATALNMI), PowerButtonPressed(
            ReadingType.POWERBUTTONPRESSED), SleepButtonPressed(ReadingType.SLEEPBUTTONPRESSED), FruNotInstalled(
            ReadingType.FRUNOTINSTALLED), SlotConnectorFaultStatusAsserted(ReadingType.SLOTCONNECTORFAULTSTATUSASSERTED), PowerUp(
            ReadingType.POWERUP), ResetButtonPressed(ReadingType.RESETBUTTONPRESSED), LowerNonCriticalGoingLow(
            ReadingType.LOWERNONCRITICALGOINGLOW), LowerNonCriticalGoingHigh(ReadingType.LOWERNONCRITICALGOINGHIGH), LowerCriticalGoingLow(
            ReadingType.LOWERCRITICALGOINGLOW), LowerCriticalGoingHigh(ReadingType.LOWERCRITICALGOINGHIGH), LowerNonRecoverableGoingLow(
            ReadingType.LOWERNONRECOVERABLEGOINGLOW), LowerNonRecoverableGoingHigh(
            ReadingType.LOWERNONRECOVERABLEGOINGHIGH), UpperNonCriticalGoingLow(ReadingType.UPPERNONCRITICALGOINGLOW), UpperNonCriticalGoingHigh(
            ReadingType.UPPERNONCRITICALGOINGHIGH), UpperCriticalGoingLow(ReadingType.UPPERCRITICALGOINGLOW), UpperCriticalGoingHigh(
            ReadingType.UPPERCRITICALGOINGHIGH), UpperNonRecoverableGoingLow(ReadingType.UPPERNONRECOVERABLEGOINGLOW), UpperNonRecoverableGoingHigh(
            ReadingType.UPPERNONRECOVERABLEGOINGHIGH), UnknownOEMEvent(ReadingType.UNKNOWNOEM), CoolingDevice(
            ReadingType.COOLINGDEVICE), OtherUnitsBasedSensor(ReadingType.OTHERUNITSBASEDSENSOR), PostMemoryResize(
            ReadingType.POSTMEMORYRESIZE), ModuleBoard(ReadingType.MODULEBOARD), MicrocontrollerCoprocessor(
            ReadingType.MICROCONTROLLERCOPROCESSOR), AddInCard(ReadingType.ADDINCARD), Chassis(ReadingType.CHASSIS), OtherFru(
            ReadingType.OTHERFRU), Terminator(ReadingType.TERMINATOR), MonitorAsicIc(ReadingType.MONITORASICIC), Unknown(
            ReadingType.UNKNOWN), ;
    private static final int LOWERNONCRITICALGOINGLOW = 256;

    private static final int LOWERNONCRITICALGOINGHIGH = 257;

    private static final int LOWERCRITICALGOINGLOW = 258;

    private static final int LOWERCRITICALGOINGHIGH = 259;

    private static final int LOWERNONRECOVERABLEGOINGLOW = 260;

    private static final int LOWERNONRECOVERABLEGOINGHIGH = 261;

    private static final int UPPERNONCRITICALGOINGLOW = 262;

    private static final int UPPERNONCRITICALGOINGHIGH = 263;

    private static final int UPPERCRITICALGOINGLOW = 264;

    private static final int UPPERCRITICALGOINGHIGH = 265;

    private static final int UPPERNONRECOVERABLEGOINGLOW = 266;

    private static final int UPPERNONRECOVERABLEGOINGHIGH = 267;

    private static final int FRUINACTIVE = 2912001;

    private static final int SLOTCONNECTORIDENTIFYSTATUSASSERTED = 2191105;

    private static final int HARDRESET = 1928961;

    private static final int FRULATCHOPEN = 1339139;

    private static final int FRUACTIVATIONREQUESTED = 2912002;

    private static final int SLOTCONNECTORDEVICEINSTALLED = 2191106;

    private static final int WARMRESET = 1928962;

    private static final int FRUACTIVATIONINPROGRESS = 2912003;

    private static final int SLOTCONNECTORREADYFORDEVICEINSTALLATION = 2191107;

    private static final int PXEBOOTREQUESTED = 1928963;

    private static final int FRUACTIVE = 2912004;

    private static final int INVALIDUSERNAMEORPASSWORD = 2780930;

    private static final int SLOTCONNECTORREADYFORDEVICEREMOVAL = 2191108;

    private static final int AUTOMATICBOOTTODIAGNOSTIC = 1928964;

    private static final int FRUDEACTIVATIONREQUESTED = 2912005;

    private static final int INVALIDPASSWORDDISABLE = 2780931;

    private static final int ENTITYPRESENT = 2453248;

    private static final int SLOTPOWEROFF = 2191109;

    private static final int SOFTWAREINITIATEDHARDRESET = 1928965;

    private static final int FRUDEACTIVATIONINPROGRESS = 2912006;

    private static final int SENSORFAILURE = 2649860;

    private static final int ENTITYABSENT = 2453249;

    private static final int SOFTWAREINITIATEDWARMRESET = 1928966;

    private static final int FRUCOMMUNICATIONLOST = 2912007;

    private static final int FRUFAILURE = 2649861;

    private static final int SYSTEMRESTART = 1928967;

    private static final int STATEDEASSERTED = 768;

    private static final int SYSTEMFIRMWAREERROR = 1011456;

    private static final int STATEASSERTED = 769;

    private static final int SYSTEMFIRMWAREHANG = 1011457;

    private static final int SYSTEMFIRMWAREPROGRESS = 1011458;

    private static final int HARDWARECHANGEDETECTED = 2846464;

    private static final int FRB1BISTFAILURE = 487170;

    private static final int FIRMWAREORSOFTWARECHANGEDETECTED = 2846465;

    private static final int FRB2HANGINPOSTFAILURE = 487171;

    private static final int HARDWAREINCOMPATIBILITYDETECTED = 2846466;

    private static final int FRB3PROCESSORSTARTUPFAILURE = 487172;

    private static final int FIRMWAREORSOFTWAREINCOMPATIBILITYDETECTED = 2846467;

    private static final int DRIVEPRESENCE = 880384;

    private static final int CONFIGURATIONERROR = 487173;

    private static final int INVALIDORUNSUPPORTEDHARDWARE = 2846468;

    private static final int DRIVEFAULT = 880385;

    private static final int UNCORRECTABLECPUCOMPLEXERROR = 487174;

    private static final int INVALIDORUNSUPPORTEDFIRMWAREORSOFTWARE = 2846469;

    private static final int PREDICTIVEFAILURE = 880386;

    private static final int PROCESSORPRESENCEDETECTED = 487175;

    private static final int HOTSPARE = 880387;

    private static final int PROCESSORDISABLED = 487176;

    private static final int CONSISTENCYORPARITYCHECKINPROGRESS = 880388;

    private static final int TERMINATORPRESENCEDETECTED = 487177;

    private static final int SECUREMODEVIOLATIONATTEMPT = 421632;

    private static final int OSGRACEFULSTOP = 2125570;

    private static final int INCRITICALARRAY = 880389;

    private static final int PROCESSORAUTOMATICALLYTHROTTLED = 487178;

    private static final int PREBOOTUSERPASSWORDVIOLATION = 421633;

    private static final int OSGRACEFULSHUTDOWN = 2125571;

    private static final int MACHINECHECKEXCEPTION = 487179;

    private static final int PREBOOTSETUPPASSWORDVIOLATION = 421634;

    private static final int SOFTOSSHUTDOWN = 2125572;

    private static final int PREBOOTNETWORKPASSWORDVIOLATION = 421635;

    private static final int LANHEARTBEATLOST = 2584320;

    private static final int AGENTNOTRESPONDING = 2125573;

    private static final int OTHERPREBOOTPASSWORDVIOLATION = 421636;

    private static final int LANHEARTBEAT = 2584321;

    private static final int OUTOFBANDACCESSPASSWORDVIOLATION = 421637;

    private static final int CURRENT = 225024;

    private static final int DEVICEABSENT = 2048;

    private static final int PARITY = 814850;

    private static final int DEVICEPRESENT = 2049;

    private static final int MEMORYSCRUBFAILED = 814851;

    private static final int MEMORYDEVICEDISABLED = 814852;

    private static final int CORRECTABLEECCOTHERCORRECTABLEMEMORYERRORLOGGINGLIMITREACHED = 814853;

    private static final int MEMORYPRESENCEDETECTED = 814854;

    private static final int MEMORYCONFIGURATIONERROR = 814855;

    private static final int SPAREMEMORYUNIT = 814856;

    private static final int MEMORYAUTOMATICALLYTHROTTLED = 814857;

    private static final int MEMORYCRITICALOVERTEMPERATURE = 814858;

    private static final int SYSTEMRECONFIGURED = 1208064;

    private static final int OEMSYSTEMBOOTEVENT = 1208065;

    private static final int ABOOTCOMPLETED = 2060032;

    private static final int UNDETERMINEDSYSTEMHARDWAREFAILURE = 1208066;

    private static final int TRANSITIONTORUNNING = 2560;

    private static final int CBOOTCOMPLETED = 2060033;

    private static final int ENTRYADDEDTOAUXILIARYLOG = 1208067;

    private static final int TRANSITIONTOINTEST = 2561;

    private static final int PXEBOOTCOMPLETED = 2060034;

    private static final int PEFACTION = 1208068;

    private static final int TRANSITIONTOPOWEROFF = 2562;

    private static final int DIAGNOSTICBOOTCOMPLETED = 2060035;

    private static final int TIMESTAMPCLOCKSYNCH = 1208069;

    private static final int TRANSITIONTOONLINE = 2563;

    private static final int CDROMBOOTCOMPLETED = 2060036;

    private static final int BOOTSOURCESELECTIONTIMEOUT = 1994500;

    private static final int POWERSUPPLYCONFIGURATIONERROR = 552710;

    private static final int TRANSITIONTOOFFLINE = 2564;

    private static final int TIMEREXPIRED = 2322176;

    private static final int ROMBOOTCOMPLETED = 2060037;

    private static final int TRANSITIONTOOFFDUTY = 2565;

    private static final int TIMERHARDRESET = 2322177;

    private static final int BOOTCOMPLETED = 2060038;

    private static final int PREDICTIVEFAILUREDEASSERTED = 1024;

    private static final int TRANSITIONTODEGRADED = 2566;

    private static final int TIMERPOWERDOWN = 2322178;

    private static final int ACPIS5ENTEREDBYOVERRIDE = 2256650;

    private static final int LANLEASHLOST = 356100;

    private static final int PREDICTIVE = 1025;

    private static final int TRANSITIONTOPOWERSAVE = 2567;

    private static final int TIMERPOWERCYCLE = 2322179;

    private static final int ACPILEGACYONSTATE = 2256651;

    private static final int POWEROFFORDOWN = 618240;

    private static final int UNAUTHORIZEDDOCK = 356101;

    private static final int INSTALLERROR = 2568;

    private static final int ACPILEGACYOFFSTATE = 2256652;

    private static final int POWERCYCLE = 618241;

    private static final int FANAREAINTRUSION = 356102;

    private static final int FULLYREDUNDANT = 2816;

    private static final int CABLEINTERCONNECTCONNECTED = 1797888;

    private static final int POWERDOWN240V = 618242;

    private static final int REDUNDANCYLOST = 2817;

    private static final int ACPIUNKNOWN = 2256654;

    private static final int CABLEINTERCONNECTCONFIGURATIONERROR = 1797889;

    private static final int INTERLOCKPOWERDOWN = 618243;

    private static final int REDUNDANCYDEGRADED = 2818;

    private static final int BATTERYLOW = 2715392;

    private static final int POWERINPUTLOST = 618244;

    private static final int NONREDUNDANT_SUFFICIENTRESOURCESFROMREDUNDANT = 2819;

    private static final int BATTERYFAILED = 2715393;

    private static final int POWERUNITSOFTPOWERCONTROLFAILURE = 618245;

    private static final int VOLTAGE = 159488;

    private static final int BATTERYPRESENCEDETECTED = 2715394;

    private static final int POWERUNITFAILURE = 618246;

    private static final int POWERUNITPREDICTIVEFAILURE = 618247;

    private static final int BUSFATALERROR = 1273610;

    private static final int CORRECTABLEMEMORYERRORLOGGINGDISABLED = 1076992;

    private static final int BUSDEGRADED = 1273611;

    private static final int EVENTTYPELOGGINGDISABLED = 1076993;

    private static final int LOGAREARESET = 1076994;

    private static final int TRANSITIONTOOK = 1792;

    private static final int ALLEVENTLOGGINGDISABLED = 1076995;

    private static final int TRANSITIONTONONCRITICALFROMOK = 1793;

    private static final int FRUSERVICEREQUESTBUTTONPRESSED = 1339140;

    private static final int SELFULL = 1076996;

    private static final int TRANSITIONTOCRITICALFROMLESSSEVERE = 1794;

    private static final int SELALMOSTFULL = 1076997;

    private static final int TRANSITIONTONONRECOVERABLEFROMLESSSEVERE = 1795;

    private static final int CORRECTABLEMACHINECHECKERRORLOGGINGDISABLED = 1076998;

    private static final int TRANSITIONTONONCRITICALFROMMORESEVERE = 1796;

    private static final int TRANSITIONTOCRITICALFROMNONRECOVERABLE = 1797;

    private static final int SLOTCONNECTORDEVICEREMOVALREQUEST = 2191110;

    private static final int TRANSITIONTONONRECOVERABLE = 1798;

    private static final int ENTITYDISABLED = 2453250;

    private static final int INTERLOCKASSERTED = 2191111;

    private static final int MONITOR = 1799;

    private static final int SLOTDISABLED = 2191112;

    private static final int SLOTHOLDSSPAREDEVICE = 2191113;

    private static final int PLATFORMGENERATEDPAGE = 2387712;

    private static final int PLATFORMGENERATEDLANALERT = 2387713;

    private static final int PLATFORMEVENTTRAPGENERATED = 2387714;

    private static final int PLATFORMGENERATEDSNMPTRAP = 2387715;

    private static final int SESSIONACTIVATED = 2780928;

    private static final int SENSORACCESSUNAVAILABLE = 2649856;

    private static final int SESSIONDEACTIVATED = 2780929;

    private static final int CONTROLLERACCESSUNAVAILABLE = 2649857;

    private static final int MANAGEMENTCONTROLLEROFFLINE = 2649858;

    private static final int SUCCESSFULHARDWARECHANGEDETECTED = 2846470;

    private static final int MANAGEMENTCONTROLLERUNAVAILABLE = 2649859;

    private static final int SUCCESSFULSOFTWAREORFWCHANGEDETECTED = 2846471;

    private static final int INFAILEDARRAY = 880390;

    private static final int REBUILDREMAPINPROGRESS = 880391;

    private static final int CORRECTABLEMACHINECHECKERROR = 487180;

    private static final int REBUILDREMAPABORTED = 880392;

    private static final int IERR = 487168;

    private static final int CHIPSETSOFTPOWERCONTROLFAILURE = 1666816;

    private static final int PROCESSORTHERMALTRIP = 487169;

    private static final int CHIPSETTHERMALTRIP = 1666817;

    private static final int D0POWERSTATE = 3072;

    private static final int D1POWERSTATE = 3073;

    private static final int TEMPERATURE = 93952;

    private static final int D2POWERSTATE = 3074;

    private static final int D3POWERSTATE = 3075;

    private static final int CRITICALSTOPDURINGOSLOAD = 2125568;

    private static final int RUNTIMECRITICALSTOP = 2125569;

    private static final int DEVICEDISABLED = 2304;

    private static final int DEVICEENABLED = 2305;

    private static final int CORRECTABLEECC = 814848;

    private static final int UNCORRECTABLEECC = 814849;

    private static final int LIMITNOTEXCEEDED = 1280;

    private static final int LIMITEXCEEDED = 1281;

    private static final int ACPIS0G0WORKING = 2256640;

    private static final int ACPIS1SLEEPINGPROCESSORCONTEXTMAINTAINED = 2256641;

    private static final int POWERSUPPLYPRESENCEDETECTED = 552704;

    private static final int ACPIS2SLEEPINGPROCESSORCONTEXTLOST = 2256642;

    private static final int POWERSUPPLYFAILUREDETECTED = 552705;

    private static final int TIMERINTERRUPT = 2322184;

    private static final int ACPIS3SLEEPINGPROCESSORCONTEXTLOSTMEMORYRETAINED = 2256643;

    private static final int POWERSUPPLYPREDICTIVEFAILURE = 552706;

    private static final int FAN = 290560;

    private static final int NONREDUNDANT_SUFFICIENTRESOURCESFROMINSUFFICIENTRESOURCES = 2820;

    private static final int ACPIS4NONVOLATILESLEEP = 2256644;

    private static final int POWERSUPPLYINPUTLOST = 552707;

    private static final int NONREDUNDANT_INSUFFICIENTRESOURCES = 2821;

    private static final int ACPIS5G2SOFTOFF = 2256645;

    private static final int POWERSUPPLYINPUTLOSTOROUTOFRANGE = 552708;

    private static final int REDUNDANCYDEGRADEDFROMFULLYREDUNDANT = 2822;

    private static final int PERFORMANCEMET = 1536;

    private static final int ACPIS4S5SOFTOFFSTATEUNDETERMINED = 2256646;

    private static final int POWERSUPPLYINPUTOUTOFRANGE = 552709;

    private static final int REDUNDANCYDEGRADEDFROMNONREDUNDANT = 2823;

    private static final int PERFORMANCELAGS = 1537;

    private static final int ACPIG3MECHANICALOFF = 2256647;

    private static final int ACPISLEEPINGINS1S2ORS3 = 2256648;

    private static final int ACPIG1SLEEPING = 2256649;

    private static final int FRONTPANELINTERRUPT = 1273600;

    private static final int INFORMATIONAL = 1800;

    private static final int NOBOOTABLEMEDIA = 1994496;

    private static final int BUSTIMEOUT = 1273601;

    private static final int NONBOOTABLEDISKETTELEFTINDRIVE = 1994497;

    private static final int IOCHANNELCHECKNMI = 1273602;

    private static final int PXESERVERNOTFOUND = 1994498;

    private static final int SOFTWARE = 1273603;

    private static final int INVALIDBOOTSECTOR = 1994499;

    private static final int PCIPERR = 1273604;

    private static final int GENERALCHASSISINTRUSION = 356096;

    private static final int TRANSITIONTOIDLE = 512;

    private static final int PCISERR = 1273605;

    private static final int DRIVEBAYINTRUSION = 356097;

    private static final int TRANSITIONTOACTIVE = 513;

    private static final int EISAFAILSAFETIMEOUT = 1273606;

    private static final int IOCARDAREAINTRUSION = 356098;

    private static final int TRANSITIONTOBUSY = 514;

    private static final int BUSCORRECTABLEERROR = 1273607;

    private static final int PROCESSORAREAINTRUSION = 356099;

    private static final int BUSUNCORRECTABLEERROR = 1273608;

    private static final int FATALNMI = 1273609;

    private static final int POWERBUTTONPRESSED = 1339136;

    private static final int SLEEPBUTTONPRESSED = 1339137;

    private static final int FRUNOTINSTALLED = 2912000;

    private static final int SLOTCONNECTORFAULTSTATUSASSERTED = 2191104;

    private static final int POWERUP = 1928960;

    private static final int RESETBUTTONPRESSED = 1339138;

    private static final int UNKNOWNOEM = 192;

    private static final int COOLINGDEVICE = 683776;

    private static final int OTHERUNITSBASEDSENSOR = 749312;

    private static final int POSTMEMORYRESIZE = 945920;

    private static final int MODULEBOARD = 1404672;

    private static final int MICROCONTROLLERCOPROCESSOR = 1470208;

    private static final int ADDINCARD = 1535744;

    private static final int CHASSIS = 1601280;

    private static final int OTHERFRU = 1732352;

    private static final int TERMINATOR = 1863424;

    private static final int MONITORASICIC = 2518784;

    private static final int UNKNOWN = 0;
    
    private static Logger logger = Logger.getLogger(ReadingType.class);

    private int code;

    ReadingType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Determines type of discrete sensor reading.
     * @param sensorType
     * - {@link SensorType} of the sensor
     * @param eventReadingType
     * - value received via {@link FullSensorRecord#getEventReadingType()},
     * {@link CompactSensorRecord#getEventReadingType()} or {@link EventOnlyRecord#getEventReadingType()}
     * @param offset
     * - index of the state asserted
     * @return Type of the discrete sensor reading.
     */
    public static ReadingType parseInt(SensorType sensorType, int eventReadingType, int offset) {

        if (sensorType == SensorType.Oem) {
            return UnknownOEMEvent;
        }

        if (eventReadingType == 0x6f && sensorType.getCode() <= 0x4) {
            offset = 0;
        }

        int value = (eventReadingType << 8) | offset;

        if (eventReadingType == 0x6f) { // sensor-specific reading type
            value |= sensorType.getCode() << 16;
        }

        switch (value) {
        case LOWERNONCRITICALGOINGLOW:
            return LowerNonCriticalGoingLow;
        case LOWERNONCRITICALGOINGHIGH:
            return LowerNonCriticalGoingHigh;
        case LOWERCRITICALGOINGLOW:
            return LowerCriticalGoingLow;
        case LOWERCRITICALGOINGHIGH:
            return LowerCriticalGoingHigh;
        case LOWERNONRECOVERABLEGOINGLOW:
            return LowerNonRecoverableGoingLow;
        case LOWERNONRECOVERABLEGOINGHIGH:
            return LowerNonRecoverableGoingHigh;
        case UPPERNONCRITICALGOINGLOW:
            return UpperNonCriticalGoingLow;
        case UPPERNONCRITICALGOINGHIGH:
            return UpperNonCriticalGoingHigh;
        case UPPERCRITICALGOINGLOW:
            return UpperCriticalGoingLow;
        case UPPERCRITICALGOINGHIGH:
            return UpperCriticalGoingHigh;
        case UPPERNONRECOVERABLEGOINGLOW:
            return UpperNonRecoverableGoingLow;
        case UPPERNONRECOVERABLEGOINGHIGH:
            return UpperNonRecoverableGoingHigh;
        case FRUINACTIVE:
            return FruInactive;
        case SLOTCONNECTORIDENTIFYSTATUSASSERTED:
            return SlotConnectorIdentifyStatusAsserted;
        case HARDRESET:
            return HardReset;
        case FRULATCHOPEN:
            return FruLatchOpen;
        case FRUACTIVATIONREQUESTED:
            return FruActivationRequested;
        case SLOTCONNECTORDEVICEINSTALLED:
            return SlotConnectorDeviceInstalled;
        case WARMRESET:
            return WarmReset;
        case FRUACTIVATIONINPROGRESS:
            return FruActivationInProgress;
        case SLOTCONNECTORREADYFORDEVICEINSTALLATION:
            return SlotConnectorReadyForDeviceInstallation;
        case PXEBOOTREQUESTED:
            return PxeBootRequested;
        case FRUACTIVE:
            return FruActive;
        case INVALIDUSERNAMEORPASSWORD:
            return InvalidUsernameOrPassword;
        case SLOTCONNECTORREADYFORDEVICEREMOVAL:
            return SlotConnectorReadyForDeviceRemoval;
        case AUTOMATICBOOTTODIAGNOSTIC:
            return AutomaticBootToDiagnostic;
        case FRUDEACTIVATIONREQUESTED:
            return FruDeactivationRequested;
        case INVALIDPASSWORDDISABLE:
            return InvalidPasswordDisable;
        case ENTITYPRESENT:
            return EntityPresent;
        case SLOTPOWEROFF:
            return SlotPowerOff;
        case SOFTWAREINITIATEDHARDRESET:
            return SoftwareInitiatedHardReset;
        case FRUDEACTIVATIONINPROGRESS:
            return FruDeactivationInProgress;
        case SENSORFAILURE:
            return SensorFailure;
        case ENTITYABSENT:
            return EntityAbsent;
        case SOFTWAREINITIATEDWARMRESET:
            return SoftwareInitiatedWarmReset;
        case FRUCOMMUNICATIONLOST:
            return FruCommunicationLost;
        case FRUFAILURE:
            return FruFailure;
        case SYSTEMRESTART:
            return SystemRestart;
        case STATEDEASSERTED:
            return StateDeasserted;
        case SYSTEMFIRMWAREERROR:
            return SystemFirmwareError;
        case STATEASSERTED:
            return StateAsserted;
        case SYSTEMFIRMWAREHANG:
            return SystemFirmwareHang;
        case SYSTEMFIRMWAREPROGRESS:
            return SystemFirmwareProgress;
        case HARDWARECHANGEDETECTED:
            return HardwareChangeDetected;
        case FRB1BISTFAILURE:
            return Frb1BistFailure;
        case FIRMWAREORSOFTWARECHANGEDETECTED:
            return FirmwareOrSoftwareChangeDetected;
        case FRB2HANGINPOSTFAILURE:
            return Frb2HangInPostFailure;
        case HARDWAREINCOMPATIBILITYDETECTED:
            return HardwareIncompatibilityDetected;
        case FRB3PROCESSORSTARTUPFAILURE:
            return Frb3ProcessorStartupFailure;
        case FIRMWAREORSOFTWAREINCOMPATIBILITYDETECTED:
            return FirmwareOrSoftwareIncompatibilityDetected;
        case DRIVEPRESENCE:
            return DrivePresence;
        case CONFIGURATIONERROR:
            return ConfigurationError;
        case INVALIDORUNSUPPORTEDHARDWARE:
            return InvalidOrUnsupportedHardware;
        case DRIVEFAULT:
            return DriveFault;
        case UNCORRECTABLECPUCOMPLEXERROR:
            return UncorrectableCpuComplexError;
        case INVALIDORUNSUPPORTEDFIRMWAREORSOFTWARE:
            return InvalidOrUnsupportedFirmwareOrSoftware;
        case PREDICTIVEFAILURE:
            return PredictiveFailure;
        case PROCESSORPRESENCEDETECTED:
            return ProcessorPresenceDetected;
        case HOTSPARE:
            return HotSpare;
        case PROCESSORDISABLED:
            return ProcessorDisabled;
        case CONSISTENCYORPARITYCHECKINPROGRESS:
            return ConsistencyOrParityCheckInProgress;
        case TERMINATORPRESENCEDETECTED:
            return TerminatorPresenceDetected;
        case SECUREMODEVIOLATIONATTEMPT:
            return SecureModeViolationAttempt;
        case OSGRACEFULSTOP:
            return OsGracefulStop;
        case INCRITICALARRAY:
            return InCriticalArray;
        case PROCESSORAUTOMATICALLYTHROTTLED:
            return ProcessorAutomaticallyThrottled;
        case PREBOOTUSERPASSWORDVIOLATION:
            return PreBootUserPasswordViolation;
        case OSGRACEFULSHUTDOWN:
            return OsGracefulShutdown;
        case MACHINECHECKEXCEPTION:
            return MachineCheckException;
        case PREBOOTSETUPPASSWORDVIOLATION:
            return PreBootSetupPasswordViolation;
        case SOFTOSSHUTDOWN:
            return SoftOsShutdown;
        case PREBOOTNETWORKPASSWORDVIOLATION:
            return PreBootNetworkPasswordViolation;
        case LANHEARTBEATLOST:
            return LANHeartbeatLost;
        case AGENTNOTRESPONDING:
            return AgentNotResponding;
        case OTHERPREBOOTPASSWORDVIOLATION:
            return OtherPreBootPasswordViolation;
        case LANHEARTBEAT:
            return LANHeartbeat;
        case OUTOFBANDACCESSPASSWORDVIOLATION:
            return OutOfBandAccessPasswordViolation;
        case CURRENT:
            return Current;
        case DEVICEABSENT:
            return DeviceAbsent;
        case PARITY:
            return Parity;
        case DEVICEPRESENT:
            return DevicePresent;
        case MEMORYSCRUBFAILED:
            return MemoryScrubFailed;
        case MEMORYDEVICEDISABLED:
            return MemoryDeviceDisabled;
        case CORRECTABLEECCOTHERCORRECTABLEMEMORYERRORLOGGINGLIMITREACHED:
            return CorrectableEccOtherCorrectableMemoryErrorLoggingLimitReached;
        case MEMORYPRESENCEDETECTED:
            return MemoryPresenceDetected;
        case MEMORYCONFIGURATIONERROR:
            return MemoryConfigurationError;
        case SPAREMEMORYUNIT:
            return SpareMemoryUnit;
        case MEMORYAUTOMATICALLYTHROTTLED:
            return MemoryAutomaticallyThrottled;
        case MEMORYCRITICALOVERTEMPERATURE:
            return MemoryCriticalOvertemperature;
        case SYSTEMRECONFIGURED:
            return SystemReconfigured;
        case OEMSYSTEMBOOTEVENT:
            return OemSystemBootEvent;
        case ABOOTCOMPLETED:
            return ABootCompleted;
        case UNDETERMINEDSYSTEMHARDWAREFAILURE:
            return UndeterminedSystemHardwareFailure;
        case TRANSITIONTORUNNING:
            return TransitionToRunning;
        case CBOOTCOMPLETED:
            return CBootCompleted;
        case ENTRYADDEDTOAUXILIARYLOG:
            return EntryAddedToAuxiliaryLog;
        case TRANSITIONTOINTEST:
            return TransitionToInTest;
        case PXEBOOTCOMPLETED:
            return PxeBootCompleted;
        case PEFACTION:
            return PefAction;
        case TRANSITIONTOPOWEROFF:
            return TransitionToPowerOff;
        case DIAGNOSTICBOOTCOMPLETED:
            return DiagnosticBootCompleted;
        case TIMESTAMPCLOCKSYNCH:
            return TimestampClockSynch;
        case TRANSITIONTOONLINE:
            return TransitionToOnLine;
        case CDROMBOOTCOMPLETED:
            return CdRomBootCompleted;
        case BOOTSOURCESELECTIONTIMEOUT:
            return BootSourceSelectionTimeout;
        case POWERSUPPLYCONFIGURATIONERROR:
            return PowerSupplyConfigurationError;
        case TRANSITIONTOOFFLINE:
            return TransitionToOffLine;
        case TIMEREXPIRED:
            return TimerExpired;
        case ROMBOOTCOMPLETED:
            return RomBootCompleted;
        case TRANSITIONTOOFFDUTY:
            return TransitionToOffDuty;
        case TIMERHARDRESET:
            return TimerHardReset;
        case BOOTCOMPLETED:
            return BootCompleted;
        case PREDICTIVEFAILUREDEASSERTED:
            return PredictiveFailureDeasserted;
        case TRANSITIONTODEGRADED:
            return TransitionToDegraded;
        case TIMERPOWERDOWN:
            return TimerPowerDown;
        case ACPIS5ENTEREDBYOVERRIDE:
            return AcpiS5EnteredByOverride;
        case LANLEASHLOST:
            return LANLeashLost;
        case PREDICTIVE:
            return Predictive;
        case TRANSITIONTOPOWERSAVE:
            return TransitionToPowerSave;
        case TIMERPOWERCYCLE:
            return TimerPowerCycle;
        case ACPILEGACYONSTATE:
            return AcpiLegacyOnState;
        case POWEROFFORDOWN:
            return PowerOffOrDown;
        case UNAUTHORIZEDDOCK:
            return UnauthorizedDock;
        case INSTALLERROR:
            return InstallError;
        case ACPILEGACYOFFSTATE:
            return AcpiLegacyOffState;
        case POWERCYCLE:
            return PowerCycle;
        case FANAREAINTRUSION:
            return FANAreaIntrusion;
        case FULLYREDUNDANT:
            return FullyRedundant;
        case CABLEINTERCONNECTCONNECTED:
            return CableInterconnectConnected;
        case POWERDOWN240V:
            return PowerDown240V;
        case REDUNDANCYLOST:
            return RedundancyLost;
        case ACPIUNKNOWN:
            return AcpiUnknown;
        case CABLEINTERCONNECTCONFIGURATIONERROR:
            return CableInterconnectConfigurationError;
        case INTERLOCKPOWERDOWN:
            return InterlockPowerDown;
        case REDUNDANCYDEGRADED:
            return RedundancyDegraded;
        case BATTERYLOW:
            return BatteryLow;
        case POWERINPUTLOST:
            return PowerInputLost;
        case NONREDUNDANT_SUFFICIENTRESOURCESFROMREDUNDANT:
            return NonRedundant_SufficientResourcesFromRedundant;
        case BATTERYFAILED:
            return BatteryFailed;
        case POWERUNITSOFTPOWERCONTROLFAILURE:
            return PowerUnitSoftPowerControlFailure;
        case VOLTAGE:
            return Voltage;
        case BATTERYPRESENCEDETECTED:
            return BatteryPresenceDetected;
        case POWERUNITFAILURE:
            return PowerUnitFailure;
        case POWERUNITPREDICTIVEFAILURE:
            return PowerUnitPredictiveFailure;
        case BUSFATALERROR:
            return BusFatalError;
        case CORRECTABLEMEMORYERRORLOGGINGDISABLED:
            return CorrectableMemoryErrorLoggingDisabled;
        case BUSDEGRADED:
            return BusDegraded;
        case EVENTTYPELOGGINGDISABLED:
            return EventTypeLoggingDisabled;
        case LOGAREARESET:
            return LogAreaReset;
        case TRANSITIONTOOK:
            return TransitionToOK;
        case ALLEVENTLOGGINGDISABLED:
            return AllEventLoggingDisabled;
        case TRANSITIONTONONCRITICALFROMOK:
            return TransitionToNonCriticalFromOK;
        case FRUSERVICEREQUESTBUTTONPRESSED:
            return FruServiceRequestButtonPressed;
        case SELFULL:
            return SelFull;
        case TRANSITIONTOCRITICALFROMLESSSEVERE:
            return TransitionToCriticalFromLessSevere;
        case SELALMOSTFULL:
            return SelAlmostFull;
        case TRANSITIONTONONRECOVERABLEFROMLESSSEVERE:
            return TransitionToNonRecoverableFromLessSevere;
        case CORRECTABLEMACHINECHECKERRORLOGGINGDISABLED:
            return CorrectableMachineCheckErrorLoggingDisabled;
        case TRANSITIONTONONCRITICALFROMMORESEVERE:
            return TransitionToNonCriticalFromMoreSevere;
        case TRANSITIONTOCRITICALFROMNONRECOVERABLE:
            return TransitionToCriticalFromNonRecoverable;
        case SLOTCONNECTORDEVICEREMOVALREQUEST:
            return SlotConnectorDeviceRemovalRequest;
        case TRANSITIONTONONRECOVERABLE:
            return TransitionToNonRecoverable;
        case ENTITYDISABLED:
            return EntityDisabled;
        case INTERLOCKASSERTED:
            return InterlockAsserted;
        case MONITOR:
            return Monitor;
        case SLOTDISABLED:
            return SlotDisabled;
        case SLOTHOLDSSPAREDEVICE:
            return SlotHoldsSpareDevice;
        case PLATFORMGENERATEDPAGE:
            return PlatformGeneratedPage;
        case PLATFORMGENERATEDLANALERT:
            return PlatformGeneratedLanAlert;
        case PLATFORMEVENTTRAPGENERATED:
            return PlatformEventTrapGenerated;
        case PLATFORMGENERATEDSNMPTRAP:
            return PlatformGeneratedSnmpTrap;
        case SESSIONACTIVATED:
            return SessionActivated;
        case SENSORACCESSUNAVAILABLE:
            return SensorAccessUnavailable;
        case SESSIONDEACTIVATED:
            return SessionDeactivated;
        case CONTROLLERACCESSUNAVAILABLE:
            return ControllerAccessUnavailable;
        case MANAGEMENTCONTROLLEROFFLINE:
            return ManagementControllerOffLine;
        case SUCCESSFULHARDWARECHANGEDETECTED:
            return SuccessfulHardwareChangeDetected;
        case MANAGEMENTCONTROLLERUNAVAILABLE:
            return ManagementControllerUnavailable;
        case SUCCESSFULSOFTWAREORFWCHANGEDETECTED:
            return SuccessfulSoftwareOrFWChangeDetected;
        case INFAILEDARRAY:
            return InFailedArray;
        case REBUILDREMAPINPROGRESS:
            return RebuildRemapInProgress;
        case CORRECTABLEMACHINECHECKERROR:
            return CorrectableMachineCheckError;
        case REBUILDREMAPABORTED:
            return RebuildRemapAborted;
        case IERR:
            return Ierr;
        case CHIPSETSOFTPOWERCONTROLFAILURE:
            return ChipsetSoftPowerControlFailure;
        case PROCESSORTHERMALTRIP:
            return ProcessorThermalTrip;
        case CHIPSETTHERMALTRIP:
            return ChipsetThermalTrip;
        case D0POWERSTATE:
            return D0PowerState;
        case D1POWERSTATE:
            return D1PowerState;
        case TEMPERATURE:
            return Temperature;
        case D2POWERSTATE:
            return D2PowerState;
        case D3POWERSTATE:
            return D3PowerState;
        case CRITICALSTOPDURINGOSLOAD:
            return CriticalStopDuringOsLoad;
        case RUNTIMECRITICALSTOP:
            return RunTimeCriticalStop;
        case DEVICEDISABLED:
            return DeviceDisabled;
        case DEVICEENABLED:
            return DeviceEnabled;
        case CORRECTABLEECC:
            return CorrectableEcc;
        case UNCORRECTABLEECC:
            return UncorrectableECC;
        case LIMITNOTEXCEEDED:
            return LimitNotExceeded;
        case LIMITEXCEEDED:
            return LimitExceeded;
        case ACPIS0G0WORKING:
            return AcpiS0G0Working;
        case ACPIS1SLEEPINGPROCESSORCONTEXTMAINTAINED:
            return AcpiS1SleepingProcessorContextMaintained;
        case POWERSUPPLYPRESENCEDETECTED:
            return PowerSupplyPresenceDetected;
        case ACPIS2SLEEPINGPROCESSORCONTEXTLOST:
            return AcpiS2SleepingProcessorContextLost;
        case POWERSUPPLYFAILUREDETECTED:
            return PowerSupplyFailureDetected;
        case TIMERINTERRUPT:
            return TimerInterrupt;
        case ACPIS3SLEEPINGPROCESSORCONTEXTLOSTMEMORYRETAINED:
            return AcpiS3SleepingProcessorContextLostMemoryRetained;
        case POWERSUPPLYPREDICTIVEFAILURE:
            return PowerSupplyPredictiveFailure;
        case FAN:
            return Fan;
        case NONREDUNDANT_SUFFICIENTRESOURCESFROMINSUFFICIENTRESOURCES:
            return NonRedundant_SufficientResourcesFromInsufficientResources;
        case ACPIS4NONVOLATILESLEEP:
            return AcpiS4NonVolatileSleep;
        case POWERSUPPLYINPUTLOST:
            return PowerSupplyInputLost;
        case NONREDUNDANT_INSUFFICIENTRESOURCES:
            return NonRedundant_InsufficientResources;
        case ACPIS5G2SOFTOFF:
            return AcpiS5G2SoftOff;
        case POWERSUPPLYINPUTLOSTOROUTOFRANGE:
            return PowerSupplyInputLostOrOutOfRange;
        case REDUNDANCYDEGRADEDFROMFULLYREDUNDANT:
            return RedundancyDegradedFromFullyRedundant;
        case PERFORMANCEMET:
            return PerformanceMet;
        case ACPIS4S5SOFTOFFSTATEUNDETERMINED:
            return AcpiS4S5SoftOffStateUndetermined;
        case POWERSUPPLYINPUTOUTOFRANGE:
            return PowerSupplyInputOutOfRange;
        case REDUNDANCYDEGRADEDFROMNONREDUNDANT:
            return RedundancyDegradedFromNonRedundant;
        case PERFORMANCELAGS:
            return PerformanceLags;
        case ACPIG3MECHANICALOFF:
            return AcpiG3MechanicalOff;
        case ACPISLEEPINGINS1S2ORS3:
            return AcpiSleepingInS1S2OrS3;
        case ACPIG1SLEEPING:
            return AcpiG1Sleeping;
        case FRONTPANELINTERRUPT:
            return FrontPanelInterrupt;
        case INFORMATIONAL:
            return Informational;
        case NOBOOTABLEMEDIA:
            return NoBootableMedia;
        case BUSTIMEOUT:
            return BusTimeout;
        case NONBOOTABLEDISKETTELEFTINDRIVE:
            return NonBootableDisketteLeftInDrive;
        case IOCHANNELCHECKNMI:
            return IoChannelCheckNmi;
        case PXESERVERNOTFOUND:
            return PxeServerNotFound;
        case SOFTWARE:
            return Software;
        case INVALIDBOOTSECTOR:
            return InvalidBootSector;
        case PCIPERR:
            return PciPErr;
        case GENERALCHASSISINTRUSION:
            return GeneralChassisIntrusion;
        case TRANSITIONTOIDLE:
            return TransitionToIdle;
        case PCISERR:
            return PciSErr;
        case DRIVEBAYINTRUSION:
            return DriveBayIntrusion;
        case TRANSITIONTOACTIVE:
            return TransitionToActive;
        case EISAFAILSAFETIMEOUT:
            return EisaFailSafeTimeout;
        case IOCARDAREAINTRUSION:
            return IoCardAreaIntrusion;
        case TRANSITIONTOBUSY:
            return TransitionToBusy;
        case BUSCORRECTABLEERROR:
            return BusCorrectableError;
        case PROCESSORAREAINTRUSION:
            return ProcessorAreaIntrusion;
        case BUSUNCORRECTABLEERROR:
            return BusUncorrectableError;
        case FATALNMI:
            return FatalNmi;
        case POWERBUTTONPRESSED:
            return PowerButtonPressed;
        case SLEEPBUTTONPRESSED:
            return SleepButtonPressed;
        case FRUNOTINSTALLED:
            return FruNotInstalled;
        case SLOTCONNECTORFAULTSTATUSASSERTED:
            return SlotConnectorFaultStatusAsserted;
        case POWERUP:
            return PowerUp;
        case RESETBUTTONPRESSED:
            return ResetButtonPressed;
        case COOLINGDEVICE:
            return CoolingDevice;
        case OTHERUNITSBASEDSENSOR:
            return OtherUnitsBasedSensor;
        case POSTMEMORYRESIZE:
            return PostMemoryResize;
        case MODULEBOARD:
            return ModuleBoard;
        case MICROCONTROLLERCOPROCESSOR:
            return MicrocontrollerCoprocessor;
        case ADDINCARD:
            return AddInCard;
        case CHASSIS:
            return Chassis;
        case OTHERFRU:
            return OtherFru;
        case TERMINATOR:
            return Terminator;
        case MONITORASICIC:
            return MonitorAsicIc;
        default:
            logger.warn("Invalid value: " + value + " (" + Integer.toHexString(value)
                    + ") for sensor " + sensorType);
            return Unknown;
        }
    }
}