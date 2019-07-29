package com.gydz.user.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.veraxsystems.vxipmi.coding.commands.sdr.record.AddressType;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.ModifierUnitUsage;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.RateUnit;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorDirection;
import com.veraxsystems.vxipmi.coding.commands.sdr.record.SensorUnit;

@XmlRootElement
public class sensorrecord {

	private Date start_time;
	private Date end_time;
	private String IP;
	private String name;
	private String IPname;
	private String IPnamelevel;
	private String entity_id;
	private String sensor_type;
	private String record_type;
	private double normal_maximum;
	private double normal_minimum;
	private double current_num;
	private String state;
	private byte sensor_owner_lun;
	private String sensor_base_unit;
	private String entity_physical;
	private int sensor_number;
	private int entity_instance_number;
	private String address_type;
	private String rate_unit;
	private String modifier_unit_usage;
	private String sensor_modifier_unit;
	private String sensor_direction;
	private double nominal_reading;
	private double sensor_maximum_reading;
	private double sensor_minmum_reading;
	private double lower_non_recoverable_threshold;
	private double upper_non_recoverable_threshold;
	private double upper_critical_threshold;
	private double lower_critical_threshold;
	private double upper_non_critical_threshold;
	private double lower_non_critical_threshold;
	private String sensor_state_valid;
	private String statesAsserted;
	private String warnlevel;
	private int warn_state;
	private String emptyresource;
	private String warncontent;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	public double getNormal_maximum() {
		return normal_maximum;
	}
	public void setNormal_maximum(double normal_maximum) {
		this.normal_maximum = normal_maximum;
	}
	public double getNormal_minimum() {
		return normal_minimum;
	}
	public void setNormal_minimum(double normal_minimum) {
		this.normal_minimum = normal_minimum;
	}
	public double getCurrent_num() {
		return current_num;
	}
	public void setCurrent_num(double current_num) {
		this.current_num = current_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public byte getSensor_owner_lun() {
		return sensor_owner_lun;
	}
	public void setSensor_owner_lun(byte sensor_owner_lun) {
		this.sensor_owner_lun = sensor_owner_lun;
	}
	public String getSensor_base_unit() {
		return sensor_base_unit;
	}
	public void setSensor_base_unit(String sensor_base_unit) {
		this.sensor_base_unit = sensor_base_unit;
	}
	public String isEntity_physical() {
		return entity_physical;
	}
	public void setEntity_physical(String entity_physical) {
		this.entity_physical = entity_physical;
	}
	public int getSensor_number() {
		return sensor_number;
	}
	public void setSensor_number(int sensor_number) {
		this.sensor_number = sensor_number;
	}
	public int getEntity_instance_number() {
		return entity_instance_number;
	}
	public void setEntity_instance_number(int entity_instance_number) {
		this.entity_instance_number = entity_instance_number;
	}
	public String getAddress_type() {
		return address_type;
	}
	public void setAddress_type(String address_type) {
		this.address_type = address_type;
	}
	public String getRate_unit() {
		return rate_unit;
	}
	public void setRate_unit(String rate_unit) {
		this.rate_unit = rate_unit;
	}
	public String getModifier_unit_usage() {
		return modifier_unit_usage;
	}
	public void setModifier_unit_usage(String modifier_unit_usage) {
		this.modifier_unit_usage = modifier_unit_usage;
	}
	public String getSensor_modifier_unit() {
		return sensor_modifier_unit;
	}
	public void setSensor_modifier_unit(String sensor_modifier_unit) {
		this.sensor_modifier_unit = sensor_modifier_unit;
	}
	public String getSensor_direction() {
		return sensor_direction;
	}
	public void setSensor_direction(String sensor_direction) {
		this.sensor_direction = sensor_direction;
	}
	public double getNominal_reading() {
		return nominal_reading;
	}
	public void setNominal_reading(double nominal_reading) {
		this.nominal_reading = nominal_reading;
	}
	public double getSensor_maximum_reading() {
		return sensor_maximum_reading;
	}
	public void setSensor_maximum_reading(double sensor_maximum_reading) {
		this.sensor_maximum_reading = sensor_maximum_reading;
	}
	public double getSensor_minmum_reading() {
		return sensor_minmum_reading;
	}
	public void setSensor_minmum_reading(double sensor_minmum_reading) {
		this.sensor_minmum_reading = sensor_minmum_reading;
	}
	public double getLower_non_recoverable_threshold() {
		return lower_non_recoverable_threshold;
	}
	public void setLower_non_recoverable_threshold(double lower_non_recoverable_threshold) {
		this.lower_non_recoverable_threshold = lower_non_recoverable_threshold;
	}
	public double getUpper_non_recoverable_threshold() {
		return upper_non_recoverable_threshold;
	}
	public void setUpper_non_recoverable_threshold(double upper_non_recoverable_threshold) {
		this.upper_non_recoverable_threshold = upper_non_recoverable_threshold;
	}
	public double getUpper_critical_threshold() {
		return upper_critical_threshold;
	}
	public void setUpper_critical_threshold(double upper_critical_threshold) {
		this.upper_critical_threshold = upper_critical_threshold;
	}
	public double getLower_critical_threshold() {
		return lower_critical_threshold;
	}
	public void setLower_critical_threshold(double lower_critical_threshold) {
		this.lower_critical_threshold = lower_critical_threshold;
	}
	public double getUpper_non_critical_threshold() {
		return upper_non_critical_threshold;
	}
	public void setUpper_non_critical_threshold(double upper_non_critical_threshold) {
		this.upper_non_critical_threshold = upper_non_critical_threshold;
	}
	public double getLower_non_critical_threshold() {
		return lower_non_critical_threshold;
	}
	public void setLower_non_critical_threshold(double lower_non_critical_threshold) {
		this.lower_non_critical_threshold = lower_non_critical_threshold;
	}
	
	public String isSensor_state_valid() {
		return sensor_state_valid;
	}
	public void setSensor_state_valid(String sensor_state_valid) {
		this.sensor_state_valid = sensor_state_valid;
	}
	public String getStatesAsserted() {
		return statesAsserted;
	}
	public void setStatesAsserted(String statesAsserted) {
		this.statesAsserted = statesAsserted;
	}
	@Override
	public String toString() {
		return "sensorrecord [start_time=" + start_time + ", IP=" + IP + ", name=" + name + ", entity_id=" + entity_id
				+ ", sensor_type=" + sensor_type + ", normal_maximum=" + normal_maximum + ", normal_minimum="
				+ normal_minimum + ", current_num=" + current_num + ", state=" + state + ", sensor_owner_lun="
				+ sensor_owner_lun + ", sensor_base_unit=" + sensor_base_unit + ", entity_physical=" + entity_physical
				+ ", sensor_number=" + sensor_number + ", entity_instance_number=" + entity_instance_number
				+ ", address_type=" + address_type + ", rate_unit=" + rate_unit + ", modifier_unit_usage="
				+ modifier_unit_usage + ", sensor_modifier_unit=" + sensor_modifier_unit + ", sensor_direction="
				+ sensor_direction + ", nominal_reading=" + nominal_reading + ", sensor_maximum_reading="
				+ sensor_maximum_reading + ", sensor_minmum_reading=" + sensor_minmum_reading
				+ ", lower_non_recoverable_threshold=" + lower_non_recoverable_threshold
				+ ", upper_non_recoverable_threshold=" + upper_non_recoverable_threshold + ", upper_critical_threshold="
				+ upper_critical_threshold + ", lower_critical_threshold=" + lower_critical_threshold
				+ ", upper_non_critical_threshold=" + upper_non_critical_threshold + ", lower_non_critical_threshold="
				+ lower_non_critical_threshold + ", sensor_state_valid=" + sensor_state_valid + ", statesAsserted="
				+ statesAsserted + "]";
	}
	public String getIPname() {
		return IPname;
	}
	public void setIPname(String iPname) {
		IPname = iPname;
	}
	public String getWarnlevel() {
		return warnlevel;
	}
	public void setWarnlevel(String warnlevel) {
		this.warnlevel = warnlevel;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public int getWarn_state() {
		return warn_state;
	}
	public void setWarn_state(int warn_state) {
		this.warn_state = warn_state;
	}
	public String getIPnamelevel() {
		return IPnamelevel;
	}
	public void setIPnamelevel(String iPnamelevel) {
		IPnamelevel = iPnamelevel;
	}
	public String getEmptyresource() {
		return emptyresource;
	}
	public void setEmptyresource(String emptyresource) {
		this.emptyresource = emptyresource;
	}
	public String getWarncontent() {
		return warncontent;
	}
	public void setWarncontent(String warncontent) {
		this.warncontent = warncontent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IP == null) ? 0 : IP.hashCode());
		result = prime * result + ((IPname == null) ? 0 : IPname.hashCode());
		result = prime * result + ((IPnamelevel == null) ? 0 : IPnamelevel.hashCode());
		result = prime * result + ((address_type == null) ? 0 : address_type.hashCode());
		long temp;
		temp = Double.doubleToLongBits(current_num);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((emptyresource == null) ? 0 : emptyresource.hashCode());
		result = prime * result + ((end_time == null) ? 0 : end_time.hashCode());
		result = prime * result + ((entity_id == null) ? 0 : entity_id.hashCode());
		result = prime * result + entity_instance_number;
		result = prime * result + ((entity_physical == null) ? 0 : entity_physical.hashCode());
		temp = Double.doubleToLongBits(lower_critical_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lower_non_critical_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lower_non_recoverable_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((modifier_unit_usage == null) ? 0 : modifier_unit_usage.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(nominal_reading);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normal_maximum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(normal_minimum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rate_unit == null) ? 0 : rate_unit.hashCode());
		result = prime * result + ((sensor_base_unit == null) ? 0 : sensor_base_unit.hashCode());
		result = prime * result + ((sensor_direction == null) ? 0 : sensor_direction.hashCode());
		temp = Double.doubleToLongBits(sensor_maximum_reading);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sensor_minmum_reading);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((sensor_modifier_unit == null) ? 0 : sensor_modifier_unit.hashCode());
		result = prime * result + sensor_number;
		result = prime * result + sensor_owner_lun;
		result = prime * result + ((sensor_state_valid == null) ? 0 : sensor_state_valid.hashCode());
		result = prime * result + ((sensor_type == null) ? 0 : sensor_type.hashCode());
		result = prime * result + ((start_time == null) ? 0 : start_time.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((statesAsserted == null) ? 0 : statesAsserted.hashCode());
		temp = Double.doubleToLongBits(upper_critical_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(upper_non_critical_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(upper_non_recoverable_threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + warn_state;
		result = prime * result + ((warncontent == null) ? 0 : warncontent.hashCode());
		result = prime * result + ((warnlevel == null) ? 0 : warnlevel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		sensorrecord other = (sensorrecord) obj;
		if (IP == null) {
			if (other.IP != null)
				return false;
		} else if (!IP.equals(other.IP))
			return false;
		if (IPname == null) {
			if (other.IPname != null)
				return false;
		} else if (!IPname.equals(other.IPname))
			return false;
		if (IPnamelevel == null) {
			if (other.IPnamelevel != null)
				return false;
		} else if (!IPnamelevel.equals(other.IPnamelevel))
			return false;
		if (address_type == null) {
			if (other.address_type != null)
				return false;
		} else if (!address_type.equals(other.address_type))
			return false;
		if (Double.doubleToLongBits(current_num) != Double.doubleToLongBits(other.current_num))
			return false;
		if (emptyresource == null) {
			if (other.emptyresource != null)
				return false;
		} else if (!emptyresource.equals(other.emptyresource))
			return false;
		if (end_time == null) {
			if (other.end_time != null)
				return false;
		} else if (!end_time.equals(other.end_time))
			return false;
		if (entity_id == null) {
			if (other.entity_id != null)
				return false;
		} else if (!entity_id.equals(other.entity_id))
			return false;
		if (entity_instance_number != other.entity_instance_number)
			return false;
		if (entity_physical == null) {
			if (other.entity_physical != null)
				return false;
		} else if (!entity_physical.equals(other.entity_physical))
			return false;
		if (Double.doubleToLongBits(lower_critical_threshold) != Double
				.doubleToLongBits(other.lower_critical_threshold))
			return false;
		if (Double.doubleToLongBits(lower_non_critical_threshold) != Double
				.doubleToLongBits(other.lower_non_critical_threshold))
			return false;
		if (Double.doubleToLongBits(lower_non_recoverable_threshold) != Double
				.doubleToLongBits(other.lower_non_recoverable_threshold))
			return false;
		if (modifier_unit_usage == null) {
			if (other.modifier_unit_usage != null)
				return false;
		} else if (!modifier_unit_usage.equals(other.modifier_unit_usage))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(nominal_reading) != Double.doubleToLongBits(other.nominal_reading))
			return false;
		if (Double.doubleToLongBits(normal_maximum) != Double.doubleToLongBits(other.normal_maximum))
			return false;
		if (Double.doubleToLongBits(normal_minimum) != Double.doubleToLongBits(other.normal_minimum))
			return false;
		if (rate_unit == null) {
			if (other.rate_unit != null)
				return false;
		} else if (!rate_unit.equals(other.rate_unit))
			return false;
		if (sensor_base_unit == null) {
			if (other.sensor_base_unit != null)
				return false;
		} else if (!sensor_base_unit.equals(other.sensor_base_unit))
			return false;
		if (sensor_direction == null) {
			if (other.sensor_direction != null)
				return false;
		} else if (!sensor_direction.equals(other.sensor_direction))
			return false;
		if (Double.doubleToLongBits(sensor_maximum_reading) != Double.doubleToLongBits(other.sensor_maximum_reading))
			return false;
		if (Double.doubleToLongBits(sensor_minmum_reading) != Double.doubleToLongBits(other.sensor_minmum_reading))
			return false;
		if (sensor_modifier_unit == null) {
			if (other.sensor_modifier_unit != null)
				return false;
		} else if (!sensor_modifier_unit.equals(other.sensor_modifier_unit))
			return false;
		if (sensor_number != other.sensor_number)
			return false;
		if (sensor_owner_lun != other.sensor_owner_lun)
			return false;
		if (sensor_state_valid == null) {
			if (other.sensor_state_valid != null)
				return false;
		} else if (!sensor_state_valid.equals(other.sensor_state_valid))
			return false;
		if (sensor_type == null) {
			if (other.sensor_type != null)
				return false;
		} else if (!sensor_type.equals(other.sensor_type))
			return false;
		if (start_time == null) {
			if (other.start_time != null)
				return false;
		} else if (!start_time.equals(other.start_time))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (statesAsserted == null) {
			if (other.statesAsserted != null)
				return false;
		} else if (!statesAsserted.equals(other.statesAsserted))
			return false;
		if (Double.doubleToLongBits(upper_critical_threshold) != Double
				.doubleToLongBits(other.upper_critical_threshold))
			return false;
		if (Double.doubleToLongBits(upper_non_critical_threshold) != Double
				.doubleToLongBits(other.upper_non_critical_threshold))
			return false;
		if (Double.doubleToLongBits(upper_non_recoverable_threshold) != Double
				.doubleToLongBits(other.upper_non_recoverable_threshold))
			return false;
		if (warn_state != other.warn_state)
			return false;
		if (warncontent == null) {
			if (other.warncontent != null)
				return false;
		} else if (!warncontent.equals(other.warncontent))
			return false;
		if (warnlevel == null) {
			if (other.warnlevel != null)
				return false;
		} else if (!warnlevel.equals(other.warnlevel))
			return false;
		return true;
	}
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	
}
