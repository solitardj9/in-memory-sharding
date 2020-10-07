package com.soliatrdj9.imsd.serviceInterface.serviceManagerInterface.model;

/*
{
	"criteriaHashSeed" : 0.5
}
 */
public class RequestScaleOut extends RequestDefault {
	//
	private Float criteriaHashSeed;

	public RequestScaleOut() {

	}
	
	public RequestScaleOut(Float criteriaHashSeed) {
		this.criteriaHashSeed = criteriaHashSeed;
	}

	public Float getCriteriaHashSeed() {
		return criteriaHashSeed;
	}

	public void setCriteriaHashSeed(Float criteriaHashSeed) {
		this.criteriaHashSeed = criteriaHashSeed;
	}

	@Override
	public String toString() {
		return "RequestScaleOut [criteriaHashSeed=" + criteriaHashSeed + "]";
	}
}