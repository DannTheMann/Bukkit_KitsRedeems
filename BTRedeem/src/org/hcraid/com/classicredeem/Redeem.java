package org.hcraid.com.classicredeem;

import java.io.Serializable;
import java.util.HashMap;

public class Redeem implements Serializable{
	
	private static final long serialVersionUID = 2379647557112107255L;
	private HashMap<String, RedeemKit> redeems = new HashMap<String, RedeemKit>();
	private HashMap<String, Kit> kits = new HashMap<String, Kit>();

	public HashMap<String, RedeemKit> getRedeems(){
		return redeems;
	}
	
	public HashMap<String, Kit> getKits()
	{
		return kits;
	}
	
}
