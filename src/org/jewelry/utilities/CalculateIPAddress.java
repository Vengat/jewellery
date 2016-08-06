package org.jewelry.utilities;

import java.math.BigInteger;

import org.apache.log4j.Logger;


public class CalculateIPAddress {
	
	
	
	private static Logger logger = Logger.getLogger(CalculateIPAddress.class);
	
	private BigInteger ipnumber = null;
	private String ipAddress=null;
	
	/*private CalculateIPAddress(){
		throw new AssertionError();
	}*/
	
	public static CalculateIPAddress getCalculateIPAddressInstance() throws Exception{
		CalculateIPAddress calculateIpAd = null;
		if(calculateIpAd==null) {
			calculateIpAd = new CalculateIPAddress();
		}
		return calculateIpAd;
	}

	public void setIpnumber(BigInteger ipnumber) {
		this.ipnumber = ipnumber;
	}

	public BigInteger getIpnumber() {
		return ipnumber;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	} 
	

    public void getIPAddressFromIPNumber() throws Exception{
		if(ipnumber==null) return;
		/*int w =  ( ipnumber / 16777216 ) % 256;
		int x =  ( ipnumber / 65536    ) % 256;
		int y =  ( ipnumber / 256      ) % 256;
		int z =  (ipnumber            ) % 256;*/
		
		BigInteger w = (ipnumber.divide(BigInteger.valueOf(16777216))).mod(BigInteger.valueOf(256));
		BigInteger x = (ipnumber.divide(BigInteger.valueOf(65536))).mod(BigInteger.valueOf(256));
		BigInteger y = (ipnumber.divide(BigInteger.valueOf(256))).mod(BigInteger.valueOf(256));
		BigInteger z = (ipnumber).mod(BigInteger.valueOf(256));
		ipAddress = String.valueOf(w)+"."+String.valueOf(x)+"."+String.valueOf(y)+"."+String.valueOf(z); 
		logger.info("ipAddress from ipNumber "+String.valueOf(ipnumber)+" is "+ipAddress);
		setIpAddress(ipAddress);    	
    }
    
    public void getIPNumberFromIPAddress() throws Exception{
    	int w = 0,x = 0,y = 0,z = 0;
    	BigInteger ipnumber;
    	if(ipAddress==null) return;
    	String ipAdd [] = ipAddress.split(".");
    	for(int i=0;i<ipAdd.length; i++){
    		w = Integer.parseInt(ipAdd[0]);
    		x = Integer.parseInt(ipAdd[1]);
    		y = Integer.parseInt(ipAdd[2]);
    		z = Integer.parseInt(ipAdd[3]);
    	}
    	
    	ipnumber = BigInteger.valueOf(16777216*w + 65536*x + 256*y + z) ;  // where IP Address = w.x.y.z 
    	
    	setIpnumber(ipnumber);
    }
    
    public static void main(String[] args) throws Exception{
    	
    	//BigInteger ipNum= new BigInteger(3411132416);
    	/*BigInteger ipNum= 3411132416;
    	logger.info("IPNumber provided is "+ipNum);
    	System.out.println("IPNumber provided is "+ipNum);
    	CalculateIPAddress calcIPAd =CalculateIPAddress.getCalculateIPAddressInstance();
    	calcIPAd.setIpnumber(ipNum);
    	calcIPAd.getIPAddressFromIPNumber();
    	logger.info("The calculated ip address is "+calcIPAd.getIpAddress());
    	System.out.println("The calculated ip address is "+calcIPAd.getIpAddress());*/
    	
    	CalculateIPAddress calcIPAd =CalculateIPAddress.getCalculateIPAddressInstance();
    	String ipAdd = "202.186.13.4";
    	calcIPAd.setIpAddress(ipAdd);
    	calcIPAd.getIPNumberFromIPAddress();
    	logger.info("The calculated ipnumber is "+calcIPAd.getIpnumber());
    	System.out.println("The calculated ipnumber is "+calcIPAd.getIpnumber());
    	
    }


}
