package com.gydz.user.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class chassisStatus {

	private Date start_time;
	private String IP;
	private boolean is_power_control_fault;
	private boolean is_power_fault;
	private boolean is_interlock;
	private boolean is_power_overload;
	private boolean is_power_on;
	private boolean was_ipmi_power_on;
	private boolean was_power_fault;
	private boolean was_interlock;
	private boolean ac_failed;
	private boolean is_chassis_identify_command_supported;
	private boolean cooling_fault_detected;
	private boolean drive_fault_detected;
	private boolean is_front_panel_lockout_active;
	private boolean is_chassis_intrusion_active;
	private boolean is_standby_button_disable_allowed;
	private boolean is_diagnostic_interrupt_button_disable_allowed;
	private boolean is_reset_button_disable_allowed;
	private boolean is_power_off_button_disable_allowed;
	private boolean is_standby_button_disabled;
	private boolean is_diagnostic_interrupt_button_disabled;
	private boolean is_reset_button_disabled;
	private boolean is_power_off_button_disabled;
	private boolean is_front_panel_button_capabilities_set;
	public boolean isIs_power_control_fault() {
		return is_power_control_fault;
	}
	public void setIs_power_control_fault(boolean is_power_control_fault) {
		this.is_power_control_fault = is_power_control_fault;
	}
	public boolean isIs_power_fault() {
		return is_power_fault;
	}
	public void setIs_power_fault(boolean is_power_fault) {
		this.is_power_fault = is_power_fault;
	}
	public boolean isIs_interlock() {
		return is_interlock;
	}
	public void setIs_interlock(boolean is_interlock) {
		this.is_interlock = is_interlock;
	}
	public boolean isIs_power_overload() {
		return is_power_overload;
	}
	public void setIs_power_overload(boolean is_power_overload) {
		this.is_power_overload = is_power_overload;
	}
	public boolean isIs_power_on() {
		return is_power_on;
	}
	public void setIs_power_on(boolean is_power_on) {
		this.is_power_on = is_power_on;
	}
	public boolean isWas_ipmi_power_on() {
		return was_ipmi_power_on;
	}
	public void setWas_ipmi_power_on(boolean was_ipmi_power_on) {
		this.was_ipmi_power_on = was_ipmi_power_on;
	}
	public boolean isWas_power_fault() {
		return was_power_fault;
	}
	public void setWas_power_fault(boolean was_power_fault) {
		this.was_power_fault = was_power_fault;
	}
	public boolean isWas_interlock() {
		return was_interlock;
	}
	public void setWas_interlock(boolean was_interlock) {
		this.was_interlock = was_interlock;
	}
	public boolean isAc_failed() {
		return ac_failed;
	}
	public void setAc_failed(boolean ac_failed) {
		this.ac_failed = ac_failed;
	}
	public boolean isIs_chassis_identify_command_supported() {
		return is_chassis_identify_command_supported;
	}
	public void setIs_chassis_identify_command_supported(boolean is_chassis_identify_command_supported) {
		this.is_chassis_identify_command_supported = is_chassis_identify_command_supported;
	}
	public boolean isCooling_fault_detected() {
		return cooling_fault_detected;
	}
	public void setCooling_fault_detected(boolean cooling_fault_detected) {
		this.cooling_fault_detected = cooling_fault_detected;
	}
	public boolean isDrive_fault_detected() {
		return drive_fault_detected;
	}
	public void setDrive_fault_detected(boolean drive_fault_detected) {
		this.drive_fault_detected = drive_fault_detected;
	}
	public boolean isIs_front_panel_lockout_active() {
		return is_front_panel_lockout_active;
	}
	public void setIs_front_panel_lockout_active(boolean is_front_panel_lockout_active) {
		this.is_front_panel_lockout_active = is_front_panel_lockout_active;
	}
	public boolean isIs_chassis_intrusion_active() {
		return is_chassis_intrusion_active;
	}
	public void setIs_chassis_intrusion_active(boolean is_chassis_intrusion_active) {
		this.is_chassis_intrusion_active = is_chassis_intrusion_active;
	}
	public boolean isIs_standby_button_disable_allowed() {
		return is_standby_button_disable_allowed;
	}
	public void setIs_standby_button_disable_allowed(boolean is_standby_button_disable_allowed) {
		this.is_standby_button_disable_allowed = is_standby_button_disable_allowed;
	}
	public boolean isIs_diagnostic_interrupt_button_disable_allowed() {
		return is_diagnostic_interrupt_button_disable_allowed;
	}
	public void setIs_diagnostic_interrupt_button_disable_allowed(boolean is_diagnostic_interrupt_button_disable_allowed) {
		this.is_diagnostic_interrupt_button_disable_allowed = is_diagnostic_interrupt_button_disable_allowed;
	}
	public boolean isIs_reset_button_disable_allowed() {
		return is_reset_button_disable_allowed;
	}
	public void setIs_reset_button_disable_allowed(boolean is_reset_button_disable_allowed) {
		this.is_reset_button_disable_allowed = is_reset_button_disable_allowed;
	}
	public boolean isIs_power_off_button_disable_allowed() {
		return is_power_off_button_disable_allowed;
	}
	public void setIs_power_off_button_disable_allowed(boolean is_power_off_button_disable_allowed) {
		this.is_power_off_button_disable_allowed = is_power_off_button_disable_allowed;
	}
	public boolean isIs_standby_button_disabled() {
		return is_standby_button_disabled;
	}
	public void setIs_standby_button_disabled(boolean is_standby_button_disabled) {
		this.is_standby_button_disabled = is_standby_button_disabled;
	}
	public boolean isIs_diagnostic_interrupt_button_disabled() {
		return is_diagnostic_interrupt_button_disabled;
	}
	public void setIs_diagnostic_interrupt_button_disabled(boolean is_diagnostic_interrupt_button_disabled) {
		this.is_diagnostic_interrupt_button_disabled = is_diagnostic_interrupt_button_disabled;
	}
	public boolean isIs_reset_button_disabled() {
		return is_reset_button_disabled;
	}
	public void setIs_reset_button_disabled(boolean is_reset_button_disabled) {
		this.is_reset_button_disabled = is_reset_button_disabled;
	}
	public boolean isIs_power_off_button_disabled() {
		return is_power_off_button_disabled;
	}
	public void setIs_power_off_button_disabled(boolean is_power_off_button_disabled) {
		this.is_power_off_button_disabled = is_power_off_button_disabled;
	}
	public boolean isIs_front_panel_button_capabilities_set() {
		return is_front_panel_button_capabilities_set;
	}
	public void setIs_front_panel_button_capabilities_set(boolean is_front_panel_button_capabilities_set) {
		this.is_front_panel_button_capabilities_set = is_front_panel_button_capabilities_set;
	}
	@Override
	public String toString() {
		return "chassisStatus [is_power_control_fault=" + is_power_control_fault + ", is_power_fault=" + is_power_fault
				+ ", is_interlock=" + is_interlock + ", is_power_overload=" + is_power_overload + ", is_power_on="
				+ is_power_on + ", was_ipmi_power_on=" + was_ipmi_power_on + ", was_power_fault=" + was_power_fault
				+ ", was_interlock=" + was_interlock + ", ac_failed=" + ac_failed
				+ ", is_chassis_identify_command_supported=" + is_chassis_identify_command_supported
				+ ", cooling_fault_detected=" + cooling_fault_detected + ", drive_fault_detected="
				+ drive_fault_detected + ", is_front_panel_lockout_active=" + is_front_panel_lockout_active
				+ ", is_chassis_intrusion_active=" + is_chassis_intrusion_active
				+ ", is_standby_button_disable_allowed=" + is_standby_button_disable_allowed
				+ ", is_diagnostic_interrupt_button_disable_allowed=" + is_diagnostic_interrupt_button_disable_allowed
				+ ", is_reset_button_disable_allowed=" + is_reset_button_disable_allowed
				+ ", is_power_off_button_disable_allowed=" + is_power_off_button_disable_allowed
				+ ", is_standby_button_disabled=" + is_standby_button_disabled
				+ ", is_diagnostic_interrupt_button_disabled=" + is_diagnostic_interrupt_button_disabled
				+ ", is_reset_button_disabled=" + is_reset_button_disabled + ", is_power_off_button_disabled="
				+ is_power_off_button_disabled + ", is_front_panel_button_capabilities_set="
				+ is_front_panel_button_capabilities_set + "]";
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
}
