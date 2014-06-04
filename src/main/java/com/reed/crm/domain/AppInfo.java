package com.reed.crm.domain;

import java.io.Serializable;
import java.sql.Array;
import java.util.Date;

public class AppInfo implements Serializable {
	private Long id;

	private String appName;

	private Short appType;

	private String newestVersion;

	private String path;

	private String description;

	private String forceUpgradeVersions;

	private Date ctime;

	private Date mtime;

	private Long[] test;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Short getAppType() {
		return appType;
	}

	public void setAppType(Short appType) {
		this.appType = appType;
	}

	public String getNewestVersion() {
		return newestVersion;
	}

	public void setNewestVersion(String newestVersion) {
		this.newestVersion = newestVersion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getForceUpgradeVersions() {
		return forceUpgradeVersions;
	}

	public void setForceUpgradeVersions(String forceUpgradeVersions) {
		this.forceUpgradeVersions = forceUpgradeVersions;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Long[] getTest() {
		return test;
	}

	public void setTest(Long[] test) {
		this.test = test;
	}

}