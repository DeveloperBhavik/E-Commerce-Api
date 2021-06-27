package com.e_commerce_app.response;

public class ResponseData<T> {
	
	private String msg;
	
	private T data;
	
	private int status;
	
	public ResponseData(String msg, T data, int status) {
		super();
		this.msg = msg;
		this.data = data;
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}