package com.soliatrdj9.imsd.application.mainNode.mainNodeManager.service.data;

public class MainNodeManagerParams {
	//
	public enum MainNodeMapParamEnum {
	    //
		TOPOLOGY_MAP("topologyMap")
		;
		
		private String type;
		
		private MainNodeMapParamEnum(String type) {
			this.type = type;
	    }
	    
		public String getType() { 
	        return type;
	    }
	    
	    @Override
	    public String toString() {
	        return type;
	    }
	}
}