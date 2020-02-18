package com.example.test;

import java.io.Serializable;

public class Msg implements Serializable{

	String msg;
	String ip;
	public Msg() {
		super();
	}
	public Msg(String msg, String ip) {
		super();
		this.msg = msg;
		this.ip = ip;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
}
