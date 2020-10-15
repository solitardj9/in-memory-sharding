package com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.model;

/*
{
	"criteriaHashSeed" : 0.5
}
 */
public class RequestScaleOut extends RequestDefault {
	//
	private Double criteriaHashSeed;

	public RequestScaleOut() {

	}
	
	public RequestScaleOut(Double criteriaHashSeed) {
		this.criteriaHashSeed = criteriaHashSeed;
	}

	public Double getCriteriaHashSeed() {
		return criteriaHashSeed;
	}

	public void setCriteriaHashSeed(Double criteriaHashSeed) {
		this.criteriaHashSeed = criteriaHashSeed;
	}

	@Override
	public String toString() {
		return "RequestScaleOut [criteriaHashSeed=" + criteriaHashSeed + "]";
	}
}