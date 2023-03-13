package com.example.application.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class AbstractEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Version
	private int consistencyVersion = -1;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion(int consistencyVersion) {
		this.consistencyVersion = consistencyVersion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractEntity) {
			AbstractEntity r = (AbstractEntity)obj;
			return getId() == r.getId();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

}
