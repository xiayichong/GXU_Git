package cn.itcast.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TDept entity. @author MyEclipse Persistence Tools
 */

public class TDept implements java.io.Serializable {

	// Fields

	private String detpId;
	private TOrg TOrg;
	private String name;
	private Set TEmployees = new HashSet(0);

	// Constructors

	/** default constructor */
	public TDept() {
	}

	/** minimal constructor */
	public TDept(TOrg TOrg) {
		this.TOrg = TOrg;
	}

	/** full constructor */
	public TDept(TOrg TOrg, String name, Set TEmployees) {
		this.TOrg = TOrg;
		this.name = name;
		this.TEmployees = TEmployees;
	}

	// Property accessors

	public String getDetpId() {
		return this.detpId;
	}

	public void setDetpId(String detpId) {
		this.detpId = detpId;
	}

	public TOrg getTOrg() {
		return this.TOrg;
	}

	public void setTOrg(TOrg TOrg) {
		this.TOrg = TOrg;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getTEmployees() {
		return this.TEmployees;
	}

	public void setTEmployees(Set TEmployees) {
		this.TEmployees = TEmployees;
	}

}