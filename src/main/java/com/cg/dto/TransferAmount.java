package com.cg.dto;

public class TransferAmount {

	private String senderEmailId;
	private String receiverEmailId;
	private Double amount;
	public String getSenderEmailId() {
		return senderEmailId;
	}
	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}
	public String getReceiverEmailId() {
		return receiverEmailId;
	}
	public void setReceiverEmailId(String receiverEmailId) {
		this.receiverEmailId = receiverEmailId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
