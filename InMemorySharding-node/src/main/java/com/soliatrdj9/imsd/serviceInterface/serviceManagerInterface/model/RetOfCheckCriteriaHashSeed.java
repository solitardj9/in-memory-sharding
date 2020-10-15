package com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.model;

public class RetOfCheckCriteriaHashSeed {
	//
	private Boolean isValid;
	
	private Double criteriaHashSeed;

	public RetOfCheckCriteriaHashSeed() {
		
	}
	
	public RetOfCheckCriteriaHashSeed(Boolean isValid, Double criteriaHashSeed) {
		this.isValid = isValid;
		this.criteriaHashSeed = criteriaHashSeed;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Double getCriteriaHashSeed() {
		return criteriaHashSeed;
	}

	public void setCriteriaHashSeed(Double criteriaHashSeed) {
		this.criteriaHashSeed = criteriaHashSeed;
	}

	@Override
	public String toString() {
		return "RetOfCheckCriteriaHashSeed [isValid=" + isValid + ", criteriaHashSeed=" + criteriaHashSeed
				+ "]";
	}
}